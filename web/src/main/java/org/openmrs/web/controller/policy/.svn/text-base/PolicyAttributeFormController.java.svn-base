package org.openmrs.web.controller.policy;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.PolicyAttribute;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PolicyAttributeFormController {
	
	@RequestMapping(value = "/admin/users/attribute.form", method = RequestMethod.GET)
	public String showAttributeList(Model model) {
		return "admin/users/attributeForm";
	}
	
	@RequestMapping(value = "/admin/users/attribute.form", method = RequestMethod.POST)
	public String saveAttribute(HttpServletRequest request, @RequestParam("attrib_name") String attributeName,
	        @RequestParam("attrib_type") Integer attributeType, @RequestParam("data_type") String dataType,
	        @RequestParam("role") String roleName, @RequestParam("change_policy") Integer changeStratergy,
	        @RequestParam("initial_value") String initialValue) {
		
		PolicyAttribute policyAttribute = new PolicyAttribute();
		policyAttribute.setAttributeName(attributeName);
		policyAttribute.setAttributeType(attributeType);
		policyAttribute.setDataType(dataType);
		
		policyAttribute.setRole(roleName);
		policyAttribute.setChangeStratergy(changeStratergy);
		policyAttribute.setInitialValue(initialValue);
		policyAttribute.setOwnerType("role");
		
		Context.getPolicyService().savePolicyAttribute(policyAttribute);
		
		return "redirect:/admin/users/attribute.list";
	}
	
}
