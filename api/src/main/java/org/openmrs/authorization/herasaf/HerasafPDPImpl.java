package org.openmrs.authorization.herasaf;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.herasaf.xacml.core.api.PDP;
import org.herasaf.xacml.core.context.RequestMarshaller;
import org.herasaf.xacml.core.context.ResponseMarshaller;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.context.impl.ResponseType;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.PolicyMarshaller;
import org.herasaf.xacml.core.simplePDP.OrderedMapBasedSimplePolicyRepository;
import org.herasaf.xacml.core.simplePDP.SimplePDPConfiguration;
import org.herasaf.xacml.core.simplePDP.SimplePDPFactory;
import org.herasaf.xacml.core.simplePDP.initializers.InitializerExecutor;
import org.openmrs.Policy;
import org.openmrs.api.APIException;
import org.openmrs.api.DecesionService;
import org.openmrs.api.context.Context;

public class HerasafPDPImpl implements DecesionService {
	
	private static final Log LOG = LogFactory.getLog(HerasafPDPImpl.class);
	
	public String authorized(String request) throws APIException {
		String response = null;
		
		try {
			List<Policy> policyList = Context.getPolicyService().getAllPolicies();
			
			InitializerExecutor.runInitializers();
			SimplePDPConfiguration configuration = new SimplePDPConfiguration();
			OrderedMapBasedSimplePolicyRepository policyRetrievalPoint = new OrderedMapBasedSimplePolicyRepository();
			List<Evaluatable> evaluatableList = new ArrayList<Evaluatable>();
			
			for (Policy policy : policyList) {
				Evaluatable evaluatable = PolicyMarshaller.unmarshal(new StringReader(policy.getContent()));
				evaluatableList.add(evaluatable);
			}
			
			policyRetrievalPoint.deploy(evaluatableList);
			
			configuration.setPolicyRetrievalPoint(policyRetrievalPoint);
			
			PDP pdp = SimplePDPFactory.getSimplePDP(configuration);
			
			RequestType req = RequestMarshaller.unmarshal(new StringReader(request));
			ResponseType resp = pdp.evaluate(req);
			
			StringWriter responseWriter = new StringWriter();
			ResponseMarshaller.marshal(resp, responseWriter);
			
			response = responseWriter.toString();
		}
		catch (Exception e) {
			LOG.error("PDP authorization error ", e);
		}
		
		return response;
	}
	
}
