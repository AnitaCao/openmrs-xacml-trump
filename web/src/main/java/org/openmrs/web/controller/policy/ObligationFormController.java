package org.openmrs.web.controller.policy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.Obligation;
import org.openmrs.api.context.Context;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ObligationFormController {
	
	@RequestMapping(value = "/admin/users/obligation.form", method = RequestMethod.GET)
	public String showObligationForm(Model model) {
		return "/admin/users/obligationForm";
	}
	
	@RequestMapping(value = "/admin/users/obligation.form", method = RequestMethod.POST)
	public String saveObligation(HttpServletRequest request, @RequestParam("obligationName") String obligationName,
	        @RequestParam("contextName") String contextName, @RequestParam("description") String description,
	        @RequestParam("role") String roleName, @RequestParam("value") String value) throws Exception {
		
		HttpSession httpSession = request.getSession();
		
		if (obligationName == null || obligationName.isEmpty()) {
			httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "Obligation Name is Empty");
			return "/admin/users/obligationForm";
		}
		
		Obligation obligation = new Obligation();
		obligation.setObligationName(obligationName);
		obligation.setContextName(contextName);
		obligation.setDescription(description);
		obligation.setRole(roleName);
		obligation.setValue(value);
		
		Context.getPolicyService().saveObligation(obligation);
		
		return "redirect:/admin/users/obligation.list";
	}
}
