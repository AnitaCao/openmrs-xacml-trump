package org.openmrs.web.controller.policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openmrs.Policy;
import org.openmrs.api.PolicyService;
import org.openmrs.api.context.Context;
import org.openmrs.web.WebConstants;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PolicyListController extends SimpleFormController {
	
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		binder.registerCustomEditor(java.lang.Integer.class, new CustomNumberEditor(java.lang.Integer.class, true));
	}
	
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object obj,
	        BindException errors) throws Exception {
		
		HttpSession httpSession = request.getSession();
		String[] policyIdList = request.getParameterValues("policyId");
		String view = getFormView();
		String success = "";
		String error = "";
		MessageSourceAccessor msa = getMessageSourceAccessor();
		
		if (policyIdList != null && policyIdList.length > 0) {
			PolicyService ps = Context.getPolicyService();
			
			String deleted = msa.getMessage("general.deleted");
			String notDeleted = msa.getMessage("Policy.cannot.delete");
			
			boolean status = ps.purgePolicies();
			
			if (status) {
				for (String policyId : policyIdList) {
					
					if (!success.equals("")) {
						success += "<br/>";
					}
					success += policyId + " " + deleted;
					
				}
			} else {
				error = notDeleted;
			}
			
		} else {
			error = msa.getMessage("Policy.select");
		}
		
		view = getSuccessView();
		
		if (!success.equals("")) {
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, success);
		}
		
		if (!error.equals("")) {
			httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, error);
		}
		
		return new ModelAndView(new RedirectView(view));
	}
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		//map containing the privilege and true/false whether the privilege is core or not
		Map<String, String> policyMap = new HashMap<String, String>();
		
		if (Context.isAuthenticated()) {
			PolicyService ps = Context.getPolicyService();
			List<Policy> policyList = ps.getAllPolicies();
			for (Policy p : policyList) {
				policyMap.put(p.getName(), p.getDescription());
			}
		}
		
		//only fill the Object is the user has authenticated properly
		return policyMap;
	}
	
}
