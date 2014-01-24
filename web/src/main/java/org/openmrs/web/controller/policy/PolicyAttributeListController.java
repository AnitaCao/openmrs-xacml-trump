package org.openmrs.web.controller.policy;

import java.util.List;

import org.openmrs.PolicyAttribute;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PolicyAttributeListController {
	
	@RequestMapping(value = "/admin/users/attribute.list", method = RequestMethod.GET)
	public String showObligationList(Model model) {
		List<PolicyAttribute> attributeList = Context.getPolicyService().getPolicyAttributeList();
		model.addAttribute("attributeList", attributeList);
		return "admin/users/attributeList";
	}
	
}
