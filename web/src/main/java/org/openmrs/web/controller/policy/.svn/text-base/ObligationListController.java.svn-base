package org.openmrs.web.controller.policy;

import java.util.List;

import org.openmrs.Obligation;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ObligationListController {
	
	@RequestMapping(value = "/admin/users/obligation.list", method = RequestMethod.GET)
	public String showObligationList(Model model) {
		List<Obligation> obligationList = Context.getPolicyService().getAllObligations();
		model.addAttribute("obligationList", obligationList);
		return "admin/users/obligationList";
	}
	
}
