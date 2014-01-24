package org.openmrs.api.db;

import java.util.List;

import org.openmrs.Obligation;
import org.openmrs.ObligationAssignment;
import org.openmrs.Policy;
import org.openmrs.PolicyAttribute;
import org.openmrs.PolicyAttributeValue;
import org.openmrs.User;
import org.openmrs.UserObligation;

public interface PolicyDAO {
	
	public Policy savePolicy(Policy policy) throws DAOException;
	
	public List<Policy> getAllPolicies() throws DAOException;
	
	public void deletePolicy(Policy policy) throws DAOException;
	
	public List<PolicyAttribute> getAttributesByRoles(List<String> roleList) throws DAOException;
	
	public List<PolicyAttributeValue> getAttributeValue(User user, List<PolicyAttribute> attributeList) throws DAOException;
	
	public void saveAttributeValues(List<PolicyAttributeValue> attributeValueList) throws DAOException;
	
	public Obligation getObligationtByName(String obligationName) throws DAOException;
	
	public UserObligation getUserObligation(User user, Obligation obligation) throws DAOException;
	
	public List<Obligation> getAllObligations() throws DAOException;
	
	public Obligation saveObligation(Obligation obligation) throws DAOException;
	
	public ObligationAssignment saveObligationAssignment(ObligationAssignment assignment) throws DAOException;
	
	public List<Obligation> getObligationsByRoles(List<String> roleNameList) throws DAOException;
	
	public List<UserObligation> getUserObligation(User user) throws DAOException;
	
	public void saveUserObligation(UserObligation userObligation) throws DAOException;
	
	public List<PolicyAttribute> getPolicyAttributeList() throws DAOException;
	
	public PolicyAttribute savePolicyAttribute(PolicyAttribute attribute) throws DAOException;
	
	public void removePolicyAttributesValues(List<PolicyAttributeValue> valueList) throws DAOException;
}
