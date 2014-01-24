package org.openmrs.util;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import org.openmrs.Privilege;
import org.openmrs.Role;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.herasaf.xacml.core.combiningAlgorithm.policy.impl.PolicyFirstApplicableAlgorithm;
import org.herasaf.xacml.core.combiningAlgorithm.rule.impl.RulePermitOverridesAlgorithm;
import org.herasaf.xacml.core.dataTypeAttribute.impl.StringDataTypeAttribute;
import org.herasaf.xacml.core.function.impl.equalityPredicates.StringEqualFunction;
import org.herasaf.xacml.core.policy.EvaluatableID;
import org.herasaf.xacml.core.policy.impl.AttributeDesignatorType;
import org.herasaf.xacml.core.policy.impl.AttributeValueType;
import org.herasaf.xacml.core.policy.impl.EffectType;
import org.herasaf.xacml.core.policy.impl.EvaluatableIDImpl;
import org.herasaf.xacml.core.policy.impl.IdReferenceType;
import org.herasaf.xacml.core.policy.impl.ObjectFactory;
import org.herasaf.xacml.core.policy.impl.PolicySetType;
import org.herasaf.xacml.core.policy.impl.PolicyType;
import org.herasaf.xacml.core.policy.impl.ResourceAttributeDesignatorType;
import org.herasaf.xacml.core.policy.impl.ResourceMatchType;
import org.herasaf.xacml.core.policy.impl.ResourceType;
import org.herasaf.xacml.core.policy.impl.ResourcesType;
import org.herasaf.xacml.core.policy.impl.RuleType;
import org.herasaf.xacml.core.policy.impl.SubjectAttributeDesignatorType;
import org.herasaf.xacml.core.policy.impl.SubjectMatchType;
import org.herasaf.xacml.core.policy.impl.SubjectType;
import org.herasaf.xacml.core.policy.impl.SubjectsType;
import org.herasaf.xacml.core.policy.impl.TargetType;
import org.openmrs.util.ContextManager;

public class PolicyGenerator {
	
	private static final PolicyGenerator instance = new PolicyGenerator();
	
	public static final String XML_PREFIX_MAPPER = "com.sun.xml.bind.namespacePrefixMapper";
	
	private PolicyGenerator() {
	}
	
	public static PolicyGenerator getInstance() {
		return instance;
	}
	
	public String getPolicyContent(Map<String, UserInformation> userInformationMap, boolean isUserBased) throws Exception {
		PolicySetType policySet = new PolicySetType();
		policySet.setCombiningAlg(new PolicyFirstApplicableAlgorithm());
		if (isUserBased) {
			policySet.setPolicySetId(new EvaluatableIDImpl("openmrs-user-policyset"));
		} else {
			policySet.setPolicySetId(new EvaluatableIDImpl("openmrs-role-policyset"));
		}
		
		policySet.setTarget(new TargetType());
		
		if (isUserBased) {
			generateUserPolicy(policySet, userInformationMap);
		} else {
			generateRolePolicy(policySet, userInformationMap);
		}
		
		JAXBContext context = ContextManager.getPolicyContext();
		Marshaller marshaller = context.createMarshaller();
		
		StringWriter content = new StringWriter();
		ObjectFactory policyObjectFactory = new ObjectFactory();
		
		JAXBElement<PolicySetType> jaxbElement = policyObjectFactory.createPolicySet(policySet);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		marshaller.setProperty(XML_PREFIX_MAPPER, new PolicyPrefixMapper());
		marshaller.marshal(jaxbElement, content);
		
		return content.toString();
		
	}
	
	private void generateUserPolicy(PolicySetType policySet, Map<String, UserInformation> userInformationMap) {
		Set<String> userList = userInformationMap.keySet();
		
		UserService us = Context.getUserService();
		ObjectFactory objectFactory = new ObjectFactory();
		
		for (String userName : userList) {
			
			UserInformation userInfo = userInformationMap.get(userName);
			List<String> roleList = userInfo.getAssignedRoles();
			
			Set<Privilege> userPriviledges = new HashSet<Privilege>();
			
			for (String roleName : roleList) {
				Role role = us.getRole(roleName);
				
				Set<Privilege> rolePrviledges = role.getPrivileges();
				for (Privilege priviledge : rolePrviledges) {
					if (!userPriviledges.contains(priviledge)) {
						userPriviledges.add(priviledge);
					}
				}
			}
			
			PolicyType policy = new PolicyType();
			policy.setCombiningAlg(new RulePermitOverridesAlgorithm());
			
			TargetType policyTarget = new TargetType();
			SubjectsType policySubjects = new SubjectsType();
			SubjectType policySubject = new SubjectType();
			SubjectMatchType policySubjectMatch = new SubjectMatchType();
			policySubjectMatch.setMatchFunction(new StringEqualFunction());
			
			SubjectAttributeDesignatorType policySubjectAttributeDesignator = new SubjectAttributeDesignatorType();
			policySubjectAttributeDesignator.setAttributeId("opnmrs.user.id");
			policySubjectAttributeDesignator.setDataType(new StringDataTypeAttribute());
			policySubjectMatch.setSubjectAttributeDesignator(policySubjectAttributeDesignator);
			
			AttributeValueType attributeValueType = new AttributeValueType();
			attributeValueType.setDataType(new StringDataTypeAttribute());
			attributeValueType.getContent().add(userName);
			policySubjectMatch.setAttributeValue(attributeValueType);
			policySubject.getSubjectMatches().add(policySubjectMatch);
			policySubjects.getSubjects().add(policySubject);
			
			policyTarget.setSubjects(policySubjects);
			policy.setTarget(policyTarget);
			
			int counter = 0;
			for (Privilege userPriviledge : userPriviledges) {
				counter++;
				RuleType rule = new RuleType();
				
				TargetType ruleTarget = new TargetType();
				
				ResourcesType ruleTargetResources = new ResourcesType();
				ResourceType ruleTargetResource = new ResourceType();
				ResourceMatchType resourceMatchType = new ResourceMatchType();
				resourceMatchType.setMatchFunction(new StringEqualFunction());
				
				ResourceAttributeDesignatorType resourceAttributeDesignator = new ResourceAttributeDesignatorType();
				resourceAttributeDesignator.setAttributeId("openmrs.priviledge");
				resourceAttributeDesignator.setDataType(new StringDataTypeAttribute());
				resourceMatchType.setResourceAttributeDesignator(resourceAttributeDesignator);
				
				AttributeValueType resourceAttribute = new AttributeValueType();
				resourceAttribute.setDataType(new StringDataTypeAttribute());
				resourceAttribute.getContent().add(userPriviledge.getPrivilege());
				resourceMatchType.setAttributeValue(resourceAttribute);
				
				ruleTargetResource.getResourceMatches().add(resourceMatchType);
				ruleTargetResources.getResources().add(ruleTargetResource);
				
				ruleTarget.setResources(ruleTargetResources);
				
				rule.setTarget(ruleTarget);
				rule.setRuleId(userName + "_" + counter);
				rule.setEffect(EffectType.PERMIT);
				
				policy.getAdditionalInformation().add(rule);
			}
			
			counter++;
			RuleType lastRule = new RuleType();
			lastRule.setRuleId(userName + "_" + counter);
			lastRule.setEffect(EffectType.DENY);
			policy.getAdditionalInformation().add(lastRule);
			
			policy.setPolicyId(new EvaluatableIDImpl("openmrs-" + userName));
			
			JAXBElement<PolicyType> policyType = objectFactory.createPolicy(policy);
			policySet.getAdditionalInformation().add(policyType);
			
		} // end of list of users
		
	}
	
	private void generateRolePolicy(PolicySetType policySet, Map<String, UserInformation> userInformationMap) {
		Set<String> userList = userInformationMap.keySet();
		Set<String> virtualRoles = new HashSet<String>();
		String userRoleSeparator = "_";
		String interRoleSperator = "#";
		String spaceSeperator = "@";
		
		UserService us = Context.getUserService();
		ObjectFactory objectFactory = new ObjectFactory();
		
		for (String userName : userList) {
			UserInformation userInfo = userInformationMap.get(userName);
			String virtualRole = userInfo.getVirtualRole();
			
			if (!virtualRoles.contains(virtualRole)) {
				virtualRoles.add(virtualRole);
			}
		}
		
		int roleCounter = 0;
		
		for (String virtualRole : virtualRoles) {
			Set<Privilege> totalPrivileges = new HashSet<Privilege>();
			String[] userRoles = virtualRole.split(userRoleSeparator);
			roleCounter++;
			for (String userRole : userRoles) {
				String[] roles = userRole.split(interRoleSperator);
				
				for (String role : roles) {
					String roleNameParts[] = role.split(spaceSeperator);
					String roleName = getRoleName(roleNameParts);
					Set<Privilege> priviledgeList = us.getRole(roleName).getPrivileges();
					
					for (Privilege priv : priviledgeList) {
						if (!totalPrivileges.contains(priv)) {
							totalPrivileges.add(priv);
						}
					}//end of priviledges 
				}//end of roles
			} //end of user roles
			
			PolicyType policy = new PolicyType();
			policy.setCombiningAlg(new RulePermitOverridesAlgorithm());
			
			TargetType policyTarget = new TargetType();
			SubjectsType policySubjects = new SubjectsType();
			SubjectType policySubject = new SubjectType();
			SubjectMatchType policySubjectMatch = new SubjectMatchType();
			policySubjectMatch.setMatchFunction(new StringEqualFunction());
			
			SubjectAttributeDesignatorType policySubjectAttributeDesignator = new SubjectAttributeDesignatorType();
			policySubjectAttributeDesignator.setAttributeId("opnmrs.virtual.role");
			policySubjectAttributeDesignator.setDataType(new StringDataTypeAttribute());
			policySubjectMatch.setSubjectAttributeDesignator(policySubjectAttributeDesignator);
			
			AttributeValueType attributeValueType = new AttributeValueType();
			attributeValueType.setDataType(new StringDataTypeAttribute());
			attributeValueType.getContent().add(virtualRole);
			policySubjectMatch.setAttributeValue(attributeValueType);
			policySubject.getSubjectMatches().add(policySubjectMatch);
			policySubjects.getSubjects().add(policySubject);
			
			policyTarget.setSubjects(policySubjects);
			policy.setTarget(policyTarget);
			
			int counter = 0;
			for (Privilege userPriviledge : totalPrivileges) {
				counter++;
				RuleType rule = new RuleType();
				
				TargetType ruleTarget = new TargetType();
				
				ResourcesType ruleTargetResources = new ResourcesType();
				ResourceType ruleTargetResource = new ResourceType();
				ResourceMatchType resourceMatchType = new ResourceMatchType();
				resourceMatchType.setMatchFunction(new StringEqualFunction());
				
				ResourceAttributeDesignatorType resourceAttributeDesignator = new ResourceAttributeDesignatorType();
				resourceAttributeDesignator.setAttributeId("openmrs.priviledge");
				resourceAttributeDesignator.setDataType(new StringDataTypeAttribute());
				resourceMatchType.setResourceAttributeDesignator(resourceAttributeDesignator);
				
				AttributeValueType resourceAttribute = new AttributeValueType();
				resourceAttribute.setDataType(new StringDataTypeAttribute());
				resourceAttribute.getContent().add(userPriviledge.getPrivilege());
				resourceMatchType.setAttributeValue(resourceAttribute);
				
				ruleTargetResource.getResourceMatches().add(resourceMatchType);
				ruleTargetResources.getResources().add(ruleTargetResource);
				
				ruleTarget.setResources(ruleTargetResources);
				
				rule.setTarget(ruleTarget);
				rule.setRuleId(virtualRole + "_" + counter);
				rule.setEffect(EffectType.PERMIT);
				
				policy.getAdditionalInformation().add(rule);
			}
			
			counter++;
			RuleType lastRule = new RuleType();
			lastRule.setRuleId(virtualRole + "_" + counter);
			lastRule.setEffect(EffectType.DENY);
			policy.getAdditionalInformation().add(lastRule);
			
			policy.setPolicyId(new EvaluatableIDImpl("openmrs-" + roleCounter));
			
			JAXBElement<PolicyType> policyType = objectFactory.createPolicy(policy);
			policySet.getAdditionalInformation().add(policyType);
			
		}// end fo virtual roles
		
	}
	
	private String getRoleName(String[] nameParts) {
		StringBuilder roleName = new StringBuilder();
		int counter = 0;
		for (String namePart : nameParts) {
			if (counter == nameParts.length - 1) {
				roleName.append(namePart);
			} else {
				roleName.append(namePart).append(" ");
			}
			
			counter++;
		}
		
		return roleName.toString();
	}
	
}
