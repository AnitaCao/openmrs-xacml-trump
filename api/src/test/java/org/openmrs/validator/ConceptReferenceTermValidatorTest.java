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
package org.openmrs.validator;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.ConceptMapType;
import org.openmrs.ConceptReferenceTerm;
import org.openmrs.ConceptReferenceTermMap;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.test.Verifies;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

/**
 * Contains tests methods for the {@link ConceptReferenceTermValidator}
 */
public class ConceptReferenceTermValidatorTest extends BaseContextSensitiveTest {
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if the code is a white space character", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTheCodeIsAWhiteSpaceCharacter() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setName("name");
		term.setCode(" ");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("code"));
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if the code is an empty string", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTheCodeIsAnEmptyString() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setName("name");
		term.setCode("");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("code"));
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if the code is null", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTheCodeIsNull() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setName("name");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("code"));
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if the concept reference term code is a duplicate in its concept source", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTheConceptReferenceTermCodeIsADuplicateInItsConceptSource() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setName("name");
		term.setCode("WGT234");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("code"));
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test(expected = IllegalArgumentException.class)
	@Verifies(value = "should fail if the concept reference term object is null", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTheConceptReferenceTermObjectIsNull() throws Exception {
		Errors errors = new BindException(new ConceptReferenceTerm(), "term");
		new ConceptReferenceTermValidator().validate(null, errors);
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if the concept source is null", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTheConceptSourceIsNull() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setName("name");
		term.setCode("code");
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("conceptSource"));
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Ignore
	//we might need these back when the constraint is put back
	@Verifies(value = "should fail if the name is a white space character", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTheNameIsAWhiteSpaceCharacter() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setName(" ");
		term.setCode("code");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("name"));
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Ignore
	@Verifies(value = "should fail if the name is an empty string", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTheNameIsAnEmptyString() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setName("");
		term.setCode("code");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("name"));
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Ignore
	@Verifies(value = "should fail if the name is null", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTheNameIsNull() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setCode("code");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("name"));
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should pass if all the required fields are set and valid", method = "validate(Object,Errors)")
	public void validate_shouldPassIfAllTheRequiredFieldsAreSetAndValid() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setName("name");
		term.setCode("code");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(false, errors.hasErrors());
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should pass if the duplicate code is for a term from another concept source", method = "validate(Object,Errors)")
	public void validate_shouldPassIfTheDuplicateCodeIsForATermFromAnotherConceptSource() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setName("unique name");
		//set to a duplicate code for a term from another source
		term.setCode("2332523");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(false, errors.hasErrors());
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should pass if the duplicate name is for a term from another concept source", method = "validate(Object,Errors)")
	public void validate_shouldPassIfTheDuplicateNameIsForATermFromAnotherConceptSource() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		//set to a duplicate name for a term from another source
		term.setName("weight term2");
		term.setCode("unique code");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(false, errors.hasErrors());
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if a concept reference term map has no concept map type", method = "validate(Object,Errors)")
	public void validate_shouldFailIfAConceptReferenceTermMapHasNoConceptMapType() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setName("name");
		term.setCode("code");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		term.addConceptReferenceTermMap(new ConceptReferenceTermMap(new ConceptReferenceTerm(1), null));
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("conceptReferenceTermMaps[0].conceptMapType"));
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if a mapped concept map type does not exist in the database", method = "validate(Object,Errors)")
	public void validate_shouldFailIfAMappedConceptMapTypeDoesNotExistInTheDatabase() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setName("name");
		term.setCode("code");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		term.addConceptReferenceTermMap(new ConceptReferenceTermMap(new ConceptReferenceTerm(1), new ConceptMapType()));
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("conceptReferenceTermMaps[0].conceptMapType"));
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if a mapped concept reference term does not exist in the database", method = "validate(Object,Errors)")
	public void validate_shouldFailIfAMappedConceptReferenceTermDoesNotExistInTheDatabase() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setName("name");
		term.setCode("code");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		term.addConceptReferenceTermMap(new ConceptReferenceTermMap(new ConceptReferenceTerm(), new ConceptMapType(1)));
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("conceptReferenceTermMaps[0].termB"));
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if termB of a concept reference term map is not set", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTermBOfAConceptReferenceTermMapIsNotSet() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setName("name");
		term.setCode("code");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		Set<ConceptReferenceTermMap> maps = new LinkedHashSet<ConceptReferenceTermMap>();
		maps.add(new ConceptReferenceTermMap(null, new ConceptMapType(1)));
		term.setConceptReferenceTermMaps(maps);
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("conceptReferenceTermMaps[0].termB"));
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if a term is mapped to itself", method = "validate(Object,Errors)")
	public void validate_shouldFailIfATermIsMappedToItself() throws Exception {
		ConceptReferenceTerm term = Context.getConceptService().getConceptReferenceTerm(1);
		Set<ConceptReferenceTermMap> maps = term.getConceptReferenceTermMaps();
		ConceptReferenceTermMap invalidMap = maps.iterator().next();
		invalidMap.setTermB(term);
		term.setConceptReferenceTermMaps(maps);
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("conceptReferenceTermMaps[0].termB"));
	}
	
	/**
	 * @see {@link ConceptReferenceTermValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if a term is mapped multiple times to the same term", method = "validate(Object,Errors)")
	public void validate_shouldFailIfATermIsMappedMultipleTimesToTheSameTerm() throws Exception {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		ConceptService cs = Context.getConceptService();
		term.setCode("unique code");
		term.setConceptSource(Context.getConceptService().getConceptSource(1));
		ConceptReferenceTermMap map1 = new ConceptReferenceTermMap(cs.getConceptReferenceTerm(1), cs.getConceptMapType(1));
		term.addConceptReferenceTermMap(map1);
		//test should fail if we change the term below
		ConceptReferenceTermMap map2 = new ConceptReferenceTermMap(cs.getConceptReferenceTerm(1), cs.getConceptMapType(1));
		term.addConceptReferenceTermMap(map2);
		
		Errors errors = new BindException(term, "term");
		new ConceptReferenceTermValidator().validate(term, errors);
		System.err.println(errors.getAllErrors());
		
		//the term for second mapping should be rejected
		Assert.assertEquals(true, errors.hasFieldErrors("conceptReferenceTermMaps[1].termB"));
	}
}
