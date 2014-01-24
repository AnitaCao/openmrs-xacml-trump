package org.openmrs.authorization.herasaf;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.herasaf.xacml.core.api.OrderedPolicyRepository;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.EvaluatableID;

public class HerasafPolicyRepository implements OrderedPolicyRepository {
	
	@Override
	public List<Evaluatable> getDeployment() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void undeploy(EvaluatableID arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void undeploy(Collection<EvaluatableID> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deploy(Map<Integer, Evaluatable> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deploy(Evaluatable arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
