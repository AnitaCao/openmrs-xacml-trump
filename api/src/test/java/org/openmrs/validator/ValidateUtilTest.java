package org.openmrs.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.APIException;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.test.Verifies;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

/**
 * Tests methods on the {@link ValidateUtil} class.
 */
public class ValidateUtilTest extends BaseContextSensitiveTest {
	
	/**
	 * @see {@link ValidateUtil#validate(Object)}
	 */
	@Test(expected = APIException.class)
	@Verifies(value = "should throw APIException if errors occur during validation", method = "validate(Object)")
	public void validate_shouldThrowAPIExceptionIfErrorsOccurDuringValidation() throws Exception {
		Location loc = new Location();
		ValidateUtil.validate(loc);
	}
	
	/**
	 * @see {@link ValidateUtil#validateFieldLengths(org.springframework.validation.Errors, Class, String...)}
	 */
	@Test
	@Verifies(value = "fail validation if name field length is too long", method = "validateFieldLengths(org.springframework.validation.Errors, Class, String...)")
	public void validateFieldLength_shouldRejectValueWhenNameIsToLong() {
		PatientIdentifierType patientIdentifierType = new PatientIdentifierType();
		patientIdentifierType.setName("asdfghjkl asdfghjkl asdfghjkl asdfghjkl asdfghjkl xx");
		
		BindException errors = new BindException(patientIdentifierType, "patientIdentifierType");
		ValidateUtil.validateFieldLengths(errors, PatientIdentifierType.class, "name");
		assertTrue(errors.hasFieldErrors("name"));
	}
	
	/**
	 * @see {@link ValidateUtil#validateFieldLengths(org.springframework.validation.Errors, Class, String...)}
	 */
	@Test
	@Verifies(value = "pass validation if name field length is equal to maximum length", method = "validateFieldLengths(org.springframework.validation.Errors, Class, String...)")
	public void validateFieldLength_shouldNotRejectValueWhenNameIsEqualMax() {
		PatientIdentifierType patientIdentifierType = new PatientIdentifierType();
		patientIdentifierType.setName("asdfghjkl asdfghjkl asdfghjkl asdfghjkl asdfghjkl ");
		
		BindException errors = new BindException(patientIdentifierType, "patientIdentifierType");
		ValidateUtil.validateFieldLengths(errors, PatientIdentifierType.class, "name");
		assertFalse(errors.hasFieldErrors("name"));
	}
	
	/**
	 * @see ValidateUtil#validate(Object,Errors)
	 * @verifies populate errors if object invalid
	 */
	@Test
	public void validate_shouldPopulateErrorsIfObjectInvalid() throws Exception {
		Location loc = new Location();
		Errors errors = new BindException(loc, "");
		ValidateUtil.validate(loc, errors);
		
		assertTrue(errors.hasErrors());
	}
}
