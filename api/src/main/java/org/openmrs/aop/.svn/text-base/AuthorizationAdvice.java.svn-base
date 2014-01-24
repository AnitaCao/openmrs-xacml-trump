/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Obligation;
import org.openmrs.User;
import org.openmrs.UserObligation;
import org.openmrs.annotation.AuthorizedAnnotationAttributes;
import org.openmrs.api.APIAuthenticationException;
import org.openmrs.api.EnforceService;
import org.openmrs.api.InvalidObligationConfigException;
import org.openmrs.api.MissingObligationValueException;
import org.openmrs.api.context.Context;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * This class provides the authorization AOP advice performed before every service layer method
 * call.
 */
public class AuthorizationAdvice implements MethodBeforeAdvice {
	
	/**
	 * Logger for this class and subclasses
	 */
	protected final Log log = LogFactory.getLog(AuthorizationAdvice.class);
	
	/**
	 * Allows us to check whether a user is authorized to access a particular method.
	 * 
	 * @param method
	 * @param args
	 * @param target
	 * @throws Throwable
	 * @should notify listeners about checked privileges
	 */
	@SuppressWarnings( { "unchecked" })
	public void before(Method method, Object[] args, Object target) throws Throwable {
		List<String> exceptedUserNames = new ArrayList<String>();
		exceptedUserNames.add("daemon");
		exceptedUserNames.add("admin");
		
		boolean isPathClear = false;
		
		if (log.isDebugEnabled())
			log.debug("Calling authorization advice before " + method.getName());
		
		User user = Context.getAuthenticatedUser();
		
		if (user != null) {
			if (exceptedUserNames.contains(user.getUsername())) {
				isPathClear = true;
			}
		} else { //Otherwise installation won't work
			return;
		}
		
		if (isPathClear) {
			log.info("Exempted user " + user.getUsername());
		}
		
		if (log.isDebugEnabled()) {
			log.debug("User " + user);
			if (user != null)
				log.debug("has roles " + user.getAllRoles());
		}
		
		AuthorizedAnnotationAttributes attributes = new AuthorizedAnnotationAttributes();
		Collection<String> privileges = attributes.getAttributes(method);
		boolean requireAll = attributes.getRequireAll(method);
		
		if (!privileges.isEmpty()) {
			EnforceService pep = Context.getEnforceService();
			for (String privilege : privileges) {
				
				if (privilege == null || privilege.isEmpty())
					return;
				
				if (Context.hasPrivilege(privilege, isPathClear)) {
					if (!requireAll) {
						return;
					}
					
				} else if (pep.isAuthorized(privilege, user)) {
					List<String> obligationList = pep.getObligations();
					
					if (obligationList != null && obligationList.size() > 0) {
						//Obligation expected
						String obligationName = obligationList.get(0);
						
						Obligation obligation = Context.getPolicyService().getObligationtByName(obligationName);
						if (obligation == null) {
							throwInvalidObligationConfig(user, method, obligationName);
						}
						
						UserObligation userObligation = Context.getPolicyService().getUserObligation(user, obligation);
						
						if (userObligation == null) {
							throwMissingObligationValue(user, method, obligationName);
						}
						
						if (userObligation.getValue().equals(obligation.getValue())) {
							//							Context.getUserService().notifyPrivilegeListeners(user, privilege, true);
							return;
						} else {
							throwInvalidObligationValue(user, method, obligationName, privilege);
						}
						
					} else {
						//						Context.getUserService().notifyPrivilegeListeners(user, privilege, true);
						return;
					}
					
				} else {
					if (requireAll) {
						// if all are required, the first miss causes them
						// to "fail"
						throwUnauthorized(user, method, privilege);
					}
				}
			}
			//			
			if (requireAll == false) {
				// If there's no match, then we know there are privileges and
				// that the user didn't have any of them. The user is not
				// authorized to access the method
				throwUnauthorized(user, method, privileges);
			}
			
		} else if (attributes.hasAuthorizedAnnotation(method)) {
			
			// if there are no privileges defined, just require that 
			// the user be authenticated
			if (Context.isAuthenticated() == false)
				throwUnauthorized(user, method);
		}
		
	}
	
	/**
	 * Throws an APIAuthorization exception stating why the user failed
	 * 
	 * @param user authenticated user
	 * @param method acting method
	 * @param attrs Collection of String privilege names that the user must have
	 */
	private void throwUnauthorized(User user, Method method, Collection<String> attrs) {
		if (log.isDebugEnabled())
			log.debug("User " + user + " is not authorized to access " + method.getName());
		throw new APIAuthenticationException("Privileges required: " + attrs);
	}
	
	/**
	 * Throws an APIAuthorization exception stating why the user failed
	 * 
	 * @param user authenticated user
	 * @param method acting method
	 * @param attrs privilege names that the user must have
	 */
	private void throwUnauthorized(User user, Method method, String attr) {
		if (log.isDebugEnabled())
			log.debug("User " + user + " is not authorized to access " + method.getName());
		throw new APIAuthenticationException("Privilege required: " + attr);
	}
	
	/**
	 * Throws an APIAuthorization exception stating why the user failed
	 * 
	 * @param user authenticated user
	 * @param method acting method
	 */
	private void throwUnauthorized(User user, Method method) {
		if (log.isDebugEnabled())
			log.debug("User " + user + " is not authorized to access " + method.getName());
		throw new APIAuthenticationException("Basic authentication required");
	}
	
	private void throwInvalidObligationConfig(User user, Method method, String obliagtionName) {
		throw new InvalidObligationConfigException(Context.getMessageSourceService().getMessage(
		    "error.invalidObligationConfig", new Object[] { obliagtionName }, null));
	}
	
	private void throwMissingObligationValue(User user, Method method, String obligationName) {
		throw new MissingObligationValueException(Context.getMessageSourceService().getMessage(
		    "error.missingObligationValue", new Object[] { obligationName }, null));
	}
	
	private void throwInvalidObligationValue(User user, Method method, String obligationName, String priviledge) {
		throw new InvalidObligationConfigException(Context.getMessageSourceService().getMessage(
		    "error.invalidObligationValue", new Object[] { obligationName, priviledge }, null));
	}
}
