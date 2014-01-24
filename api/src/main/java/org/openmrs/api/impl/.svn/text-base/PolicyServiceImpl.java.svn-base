package org.openmrs.api.impl;

import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.openmrs.Obligation;
import org.openmrs.ObligationAssignment;
import org.openmrs.Policy;
import org.openmrs.PolicyAttribute;
import org.openmrs.PolicyAttributeValue;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.UserObligation;
import org.openmrs.api.APIException;
import org.openmrs.api.PolicyService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.PolicyDAO;
import org.openmrs.util.AuthExceptedUserInfo;
import org.springframework.transaction.annotation.Transactional;

public class PolicyServiceImpl extends BaseOpenmrsService implements PolicyService {
	
	PolicyDAO policyDAO;
	
	public PolicyServiceImpl() {
	}
	
	public void setPolicyDAO(PolicyDAO policyDAO) {
		this.policyDAO = policyDAO;
	}
	
	/**
	 * @see PolicyService#savePolicy(Policy)
	 */
	@Override
	public Policy savePolicy(Policy policy) throws APIException {
		return policyDAO.savePolicy(policy);
	}
	
	/**
	 * @see PolicyService#getAllPolicies()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Policy> getAllPolicies() throws APIException {
		return policyDAO.getAllPolicies();
	}
	
	/**
	 * @see PolicyService#purgePolicies()
	 */
	@Override
	public boolean purgePolicies() throws APIException {
		List<Policy> policyList = policyDAO.getAllPolicies();
		boolean status = false;
		for (Policy policy : policyList) {
			policyDAO.deletePolicy(policy);
		}
		
		status = true;
		return status;
	}
	
	/**
	 * @see PolicyService#getAuthExceptedUserList()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<String> getAuthExceptedUserList() throws APIException {
		AuthExceptedUserInfo exceptedUserInfo = AuthExceptedUserInfo.INSTANCE;
		return exceptedUserInfo.getExpemtedUsers();
	}
	
	/**
	 * @see PolicyService#getVirtualRole(User)
	 */
	@Override
	@Transactional(readOnly = true)
	public String getVirtualRole(User user) throws APIException {
		StringBuffer virtualRole = new StringBuffer();
		String userRoleSeparator = "_";
		String interRoleSperator = "#";
		
		UserService us = Context.getUserService();
		List<Role> roleList = us.getUserRoles(user);
		
		for (Role role : roleList) {
			
			virtualRole.append(getTokenizedName(role.getName()));
			Set<Role> parentRolesList = role.getAllParentRoles();
			
			for (Role parentRole : parentRolesList) {
				virtualRole.append(interRoleSperator);
				virtualRole.append(getTokenizedName(parentRole.getName()));
			}
			
			virtualRole.append(userRoleSeparator);
			
		}
		
		String virtualRoleValue = virtualRole.toString();
		
		if (virtualRoleValue.endsWith(userRoleSeparator)) {
			virtualRoleValue = virtualRoleValue.substring(0, virtualRoleValue.length() - userRoleSeparator.length());
		}
		
		if (virtualRoleValue.endsWith(interRoleSperator)) {
			virtualRoleValue = virtualRoleValue.substring(0, virtualRoleValue.length() - interRoleSperator.length());
		}
		
		return virtualRoleValue;
	}
	
	private String getTokenizedName(String name) {
		StringTokenizer tz = new StringTokenizer(name);
		
		StringBuilder content = new StringBuilder();
		int tokenCount = tz.countTokens();
		
		int counter = 0;
		while (tz.hasMoreTokens()) {
			if (counter == tokenCount - 1) {
				content.append(tz.nextToken());
			} else {
				content.append(tz.nextToken()).append('@');
			}
			
			counter++;
		}
		
		return content.toString();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PolicyAttribute> getAttributesByRoles(List<String> roleList) throws APIException {
		return policyDAO.getAttributesByRoles(roleList);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PolicyAttributeValue> getAttributeValues(User user, List<PolicyAttribute> attributeList) throws APIException {
		return policyDAO.getAttributeValue(user, attributeList);
	}
	
	@Override
	public void saveAttributeValues(List<PolicyAttributeValue> attributeValueList) throws APIException {
		policyDAO.saveAttributeValues(attributeValueList);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Obligation getObligationtByName(String obligationName) throws APIException {
		return policyDAO.getObligationtByName(obligationName);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserObligation getUserObligation(User user, Obligation obligation) throws APIException {
		return policyDAO.getUserObligation(user, obligation);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Obligation> getAllObligations() throws APIException {
		return policyDAO.getAllObligations();
	}
	
	@Override
	public void saveObligation(Obligation obligation) throws APIException {
		policyDAO.saveObligation(obligation);
	}
	
	@Override
	public void saveObligationAssignment(ObligationAssignment assignment) throws APIException {
		policyDAO.saveObligationAssignment(assignment);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Obligation> getObligationsByRoles(List<String> roleNameList) throws APIException {
		return policyDAO.getObligationsByRoles(roleNameList);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserObligation> getUserObligation(User user) throws APIException {
		return policyDAO.getUserObligation(user);
	}
	
	@Override
	public void saveUserObligation(UserObligation userObligation) throws APIException {
		policyDAO.saveUserObligation(userObligation);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PolicyAttribute> getPolicyAttributeList() throws APIException {
		return policyDAO.getPolicyAttributeList();
	}
	
	@Override
	public void savePolicyAttribute(PolicyAttribute attribute) throws APIException {
		policyDAO.savePolicyAttribute(attribute);
	}
	
	@Override
	public void removePolicyAttributesValues(List<PolicyAttributeValue> valueList) throws APIException {
		policyDAO.removePolicyAttributesValues(valueList);
	}
	
}
