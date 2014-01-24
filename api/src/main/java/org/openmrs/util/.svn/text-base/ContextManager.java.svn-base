package org.openmrs.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContextManager {
	
	private static final Log LOG = LogFactory.getLog(ContextManager.class);
	
	private static final ThreadLocal<JAXBContext> policyLocal = new ThreadLocal<JAXBContext>() {
		
		protected JAXBContext initialValue() {
			try {
				return JAXBContext.newInstance(PolicyUtil.POLICY_CONTEXT_PATH);
			}
			catch (JAXBException e) {
				LOG.error("Policy context creation error", e);
			}
			return null;
		}
	};
	
	private static final ThreadLocal<JAXBContext> contextLocal = new ThreadLocal<JAXBContext>() {
		
		protected JAXBContext initialValue() {
			try {
				return JAXBContext.newInstance(PolicyUtil.REQUEST_CONTEXT_PATH);
			}
			catch (JAXBException e) {
				LOG.error("Policy context creation error", e);
			}
			return null;
		}
	};
	
	public static JAXBContext getPolicyContext() {
		return policyLocal.get();
	}
	
	public static JAXBContext getRequestContext() {
		return contextLocal.get();
	}
	
}
