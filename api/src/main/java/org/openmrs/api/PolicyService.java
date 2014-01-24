package org.openmrs.api;

import java.util.List;
import java.util.Set;

import org.openmrs.Obligation;
import org.openmrs.ObligationAssignment;
import org.openmrs.Policy;
import org.openmrs.PolicyAttribute;
import org.openmrs.PolicyAttributeValue;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.UserObligation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PolicyService extends OpenmrsService {
	
	/**
	 * Save policy in to the database.
	 * @param policy
	 * @return
	 * @throws APIException
	 */
	public Policy savePolicy(Policy policy) throws APIException;
	
	/**
	 * Return all policies in the system.
	 * @return
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	public List<Policy> getAllPolicies() throws APIException;
	
	/**
	 * Completely remove policy from the system.
	 * @return
	 * @throws APIException
	 */
	public boolean purgePolicies() throws APIException;
	
	@Transactional(readOnly = true)
	public List<String> getAuthExceptedUserList() throws APIException;
	
	@Transactional(readOnly = true)
	public String getVirtualRole(User user) throws APIException;
	
	@Transactional(readOnly = true)
	public List<PolicyAttribute> getAttributesByRoles(List<String> roleList) throws APIException;
	
	@Transactional(readOnly = true)
	public List<PolicyAttributeValue> getAttributeValues(User user, List<PolicyAttribute> attributeList) throws APIException;
	
	/**
	 * 
	 * @param attributeValueList
	 * @throws APIException
	 */
	public void saveAttributeValues(List<PolicyAttributeValue> attributeValueList) throws APIException;
	
	/**
	 * 
	 * @param obligationName
	 * @return
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	public Obligation getObligationtByName(String obligationName) throws APIException;
	
	/**
	 * 
	 * @param user
	 * @param obligation
	 * @return
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	public UserObligation getUserObligation(User user, Obligation obligation) throws APIException;
	
	/**
	 * 
	 * @return
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	public List<Obligation> getAllObligations() throws APIException;
	
	/**
	 * 
	 * @param obligation
	 * @throws APIException
	 */
	public void saveObligation(Obligation obligation) throws APIException;
	
	/**
	 * 
	 * @param assignment
	 * @throws APIException
	 */
	public void saveObligationAssignment(ObligationAssignment assignment) throws APIException;
	
	/**
	 * 
	 * @param roleNameList
	 * @return
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	public List<Obligation> getObligationsByRoles(List<String> roleNameList) throws APIException;
	
	/**
	 * 
	 * @param roleNameList
	 * @return
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	public List<UserObligation> getUserObligation(User user) throws APIException;
	
	/**
	 * 
	 * @param userObligation
	 * @throws APIException
	 */
	public void saveUserObligation(UserObligation userObligation) throws APIException;
	
	/**
	 * 
	 * @return
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	public List<PolicyAttribute> getPolicyAttributeList() throws APIException;
	
	/**
	 * 
	 * @throws APIException
	 */
	public void savePolicyAttribute(PolicyAttribute attribute) throws APIException;
	
	/**
	 * 
	 * @param valueList
	 * @throws APIException
	 */
	public void removePolicyAttributesValues(List<PolicyAttributeValue> valueList) throws APIException;
	
}
