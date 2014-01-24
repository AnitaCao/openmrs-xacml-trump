package org.openmrs.api.db.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.Obligation;
import org.openmrs.ObligationAssignment;
import org.openmrs.Policy;
import org.openmrs.PolicyAttribute;
import org.openmrs.PolicyAttributeAssignment;
import org.openmrs.PolicyAttributeValue;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.UserObligation;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.db.PolicyDAO;

public class HibernatePolicyDAO implements PolicyDAO {
	
	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;
	
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Set session factory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Policy savePolicy(Policy policy) throws DAOException {
		sessionFactory.getCurrentSession().saveOrUpdate(policy);
		return policy;
	}
	
	@Override
	public List<Policy> getAllPolicies() throws DAOException {
		return sessionFactory.getCurrentSession().createQuery("from Policy p order by p.policyId").list();
	}
	
	@Override
	public void deletePolicy(Policy policy) throws DAOException {
		sessionFactory.getCurrentSession().delete(policy);
	}
	
	@Override
	public List<PolicyAttribute> getAttributesByRoles(List<String> roleList) throws DAOException {
		Query query = sessionFactory.getCurrentSession()
		        .createQuery("from PolicyAttribute pa where pa.role in (:roleList) ");
		query.setParameterList("roleList", roleList);
		
		List<PolicyAttribute> attributeList = query.list();
		return attributeList;
	}
	
	@Override
	public List<PolicyAttributeValue> getAttributeValue(User user, List<PolicyAttribute> attributeList) throws DAOException {
		List<PolicyAttributeValue> attributeAssignmentList = new ArrayList<PolicyAttributeValue>();
		
		if (attributeList != null && attributeList.size() > 0) {
			Query query = sessionFactory.getCurrentSession().createQuery(
			    "from PolicyAttributeValue pav where pav.user = :user and pav.attribute in (:attributeList)");
			query.setParameter("user", user);
			query.setParameterList("attributeList", attributeList);
			
			attributeAssignmentList = query.list();
		}
		
		return attributeAssignmentList;
	}
	
	@Override
	public void saveAttributeValues(List<PolicyAttributeValue> attributeValueList) throws DAOException {
		//TODO: Handle session for large attributeValueLists
		Session currentSession = sessionFactory.getCurrentSession();
		
		for (PolicyAttributeValue attributeValue : attributeValueList) {
			currentSession.saveOrUpdate(attributeValue);
		}
	}
	
	@Override
	public Obligation getObligationtByName(String obligationName) throws DAOException {
		Query query = sessionFactory.getCurrentSession().createQuery("from Obligation ob where ob.obligationName = ?");
		query.setString(0, obligationName);
		List<Obligation> obligations = query.list();
		
		if (obligations == null || obligations.size() == 0) {
			log.warn("request for obligation '" + obligationName + "' not found");
			return null;
		}
		
		return obligations.get(0);
	}
	
	@Override
	public UserObligation getUserObligation(User user, Obligation obligation) throws DAOException {
		Query query = sessionFactory.getCurrentSession().createQuery(
		    "from UserObligation uo where uo.user = ? and uo.obligation = ?");
		query.setParameter(0, user);
		query.setParameter(1, obligation);
		
		List<UserObligation> userObligations = query.list();
		
		if (userObligations == null || userObligations.size() == 0) {
			log.warn("request for user obligation '" + user.getUsername() + ": " + obligation.getObligationId()
			        + "' not found");
			return null;
		}
		
		return userObligations.get(0);
	}
	
	@Override
	public List<Obligation> getAllObligations() throws DAOException {
		return sessionFactory.getCurrentSession().createQuery("from Obligation ob order by ob.obligationName").list();
	}
	
	@Override
	public Obligation saveObligation(Obligation obligation) throws DAOException {
		sessionFactory.getCurrentSession().saveOrUpdate(obligation);
		return obligation;
	}
	
	@Override
	public ObligationAssignment saveObligationAssignment(ObligationAssignment assignment) throws DAOException {
		sessionFactory.getCurrentSession().saveOrUpdate(assignment);
		return assignment;
	}
	
	@Override
	public List<Obligation> getObligationsByRoles(List<String> roleNameList) throws DAOException {
		List<Obligation> obligationList = new ArrayList<Obligation>();
		if (roleNameList != null && roleNameList.size() > 0) {
			Query query = sessionFactory.getCurrentSession().createQuery(
			    "from Obligation ob where ob.role in (:roleNameList)");
			query.setParameterList("roleNameList", roleNameList);
			
			obligationList = query.list();
		}
		
		return obligationList;
	}
	
	@Override
	public List<UserObligation> getUserObligation(User user) throws DAOException {
		Query query = sessionFactory.getCurrentSession().createQuery("from UserObligation uo where uo.user = ?");
		query.setParameter(0, user);
		
		List<UserObligation> userObligations = query.list();
		
		return userObligations;
	}
	
	@Override
	public void saveUserObligation(UserObligation userObligation) throws DAOException {
		sessionFactory.getCurrentSession().saveOrUpdate(userObligation);
	}
	
	@Override
	public List<PolicyAttribute> getPolicyAttributeList() throws DAOException {
		return sessionFactory.getCurrentSession().createQuery("from PolicyAttribute pa order by pa.attributeName").list();
	}
	
	@Override
	public PolicyAttribute savePolicyAttribute(PolicyAttribute attribute) throws DAOException {
		sessionFactory.getCurrentSession().saveOrUpdate(attribute);
		return attribute;
	}
	
	public void removePolicyAttributesValues(List<PolicyAttributeValue> valueList) throws DAOException {
		
		for (PolicyAttributeValue value : valueList) {
			sessionFactory.getCurrentSession().delete(value);
		}
		
	}
	
}
