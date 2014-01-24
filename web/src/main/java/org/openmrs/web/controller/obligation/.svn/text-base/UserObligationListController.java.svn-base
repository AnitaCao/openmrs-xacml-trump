package org.openmrs.web.controller.obligation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Obligation;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.UserObligation;
import org.openmrs.api.PolicyService;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserObligationListController {
	
	@RequestMapping(value = "/obligations/userObligation.list", method = RequestMethod.GET)
	public String showObligationList(Model model) {
		User currentUser = Context.getAuthenticatedUser();
		List<Role> roleList = Context.getUserService().getUserRoles(currentUser);
		
		List<String> roleNameList = new ArrayList<String>();
		for (Role role : roleList) {
			roleNameList.add(role.getName());
		}
		
		PolicyService policyService = Context.getPolicyService();
		List<Obligation> obligationList = policyService.getObligationsByRoles(roleNameList);
		List<UserObligation> userObligationList = policyService.getUserObligation(currentUser);
		
		model.addAttribute("userObligationList", userObligationList);
		model.addAttribute("obligationList", obligationList);
		return "obligations/userObligationList";
	}
	
	@RequestMapping(value = "/obligations/userObligation.list", method = RequestMethod.POST)
	public String saveUserObligation(Model model, HttpServletRequest request,
	        @RequestParam("obligationName") String obligationName, @RequestParam("value") String value) {
		
		PolicyService policyService = Context.getPolicyService();
		
		Obligation obligation = policyService.getObligationtByName(obligationName);
		User user = Context.getAuthenticatedUser();
		
		UserObligation userObligation = new UserObligation();
		userObligation.setUser(user);
		userObligation.setObligation(obligation);
		userObligation.setValue(value);
		
		policyService.saveUserObligation(userObligation);
		
		User currentUser = Context.getAuthenticatedUser();
		List<Role> roleList = Context.getUserService().getUserRoles(currentUser);
		
		List<String> roleNameList = new ArrayList<String>();
		for (Role role : roleList) {
			roleNameList.add(role.getName());
		}
		
		List<Obligation> obligationList = policyService.getObligationsByRoles(roleNameList);
		List<UserObligation> userObligationList = policyService.getUserObligation(currentUser);
		
		model.addAttribute("userObligationList", userObligationList);
		model.addAttribute("obligationList", obligationList);
		
		return "obligations/userObligationList";
	}
}
