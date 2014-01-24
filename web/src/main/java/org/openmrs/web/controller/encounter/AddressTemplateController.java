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
package org.openmrs.web.controller.encounter;

import org.openmrs.LocationTag;
import org.openmrs.api.context.Context;
import org.openmrs.layout.web.address.AddressTemplate;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

/**
 * Controller for managing {@link LocationTag}s
 */
@Controller
@SessionAttributes("addressTemplate")
public class AddressTemplateController {
	
	/**
	 * Show AddressTemplate
	 */
	@RequestMapping("/admin/locations/addressTemplate")
	public void show(ModelMap model) {
		model.addAttribute("addressTemplateXml", Context.getLocationService().getAddressTemplate());
	}
	
	/**
	 * Add a new AddressTemplate (quickly, without a dedicated page)
	 */
	@RequestMapping("/admin/locations/addressTemplateAdd")
	public String add(@RequestParam("xml") String xml, WebRequest request) {
		
		if (!StringUtils.hasText(xml) || xml.trim().equals("")) {
			request.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Context.getMessageSourceService().getMessage(
			    "AddressTemplate.error.empty"), WebRequest.SCOPE_SESSION);
		} else {
			try {
				//To test whether this is a valid conversion
				AddressTemplate test = Context.getSerializationService().getDefaultSerializer().deserialize(xml,
				    AddressTemplate.class);
				Context.getLocationService().saveAddressTemplate(xml);
				request.setAttribute(WebConstants.OPENMRS_MSG_ATTR, Context.getMessageSourceService().getMessage(
				    "AddressTemplate.saved"), WebRequest.SCOPE_SESSION);
			}
			catch (Exception e) {
				request.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Context.getMessageSourceService().getMessage(
				    "AddressTemplate.error"), WebRequest.SCOPE_SESSION);
			}
			
		}
		return "redirect:addressTemplate.form";
	}
	
}
