package org.openmrs.validator;

import org.openmrs.Policy;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PolicyValidator implements Validator {
	
	@SuppressWarnings("unchecked")
	public boolean supports(Class c) {
		return c.equals(Policy.class);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		Policy policy = (Policy) obj;
		if (policy == null) {
			errors.rejectValue("policy", "error.general");
		} else {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.policyname");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.description");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "error.content");
		}
	}
	
}
