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
package org.openmrs.api.handler;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.ConceptReferenceTerm;
import org.openmrs.ConceptSource;
import org.openmrs.OpenmrsObject;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.annotation.AllowEmptyStrings;
import org.openmrs.annotation.AllowLeadingOrTrailingWhitespace;
import org.openmrs.test.Verifies;

/**
 * Tests for {@link OpenmrsObjectSaveHandler}
 */
public class OpenmrsObjectSaveHandlerTest {
	
	/**
	 * @see {@link OpenmrsObjectSaveHandler#handle(OpenmrsObject,User,Date,String)}
	 */
	@Test
	@Verifies(value = "set empty string properties to null", method = "handle(OpenmrsObject,User,Date,String)")
	public void handle_shouldSetEmptyStringPropertiesToNull() {
		Role role = new Role();
		role.setName("");
		role.setDescription("");
		role.setRole("");
		
		new OpenmrsObjectSaveHandler().handle(role, null, null, null);
		
		Assert.assertNull(role.getName());
		Assert.assertNull(role.getDescription());
		Assert.assertNull(role.getRole());
	}
	
	/**
	 * @see {@link OpenmrsObjectSaveHandler#handle(OpenmrsObject,User,Date,String)}
	 */
	@Test
	@Verifies(value = "not set empty string properties to null for AllowEmptyStrings annotation", method = "handle(OpenmrsObject,User,Date,String)")
	public void handle_shouldNotSetEmptyStringPropertiesToNullForAllowEmptyStringsAnnotation() {
		SomeClass obj = new SomeClass("");
		new OpenmrsObjectSaveHandler().handle(obj, null, null, null);
		Assert.assertNotNull(obj.getName());
	}
	
	/**
	 * @see {@link OpenmrsObjectSaveHandler#handle(OpenmrsObject,User,Date,String)}
	 */
	@Test
	@Verifies(value = "not trim empty strings for AllowLeadingOrTrailingWhitespace annotation", method = "handle(OpenmrsObject,User,Date,String)")
	public void handle_shouldNotTrimEmptyStringsForAllowLeadingOrTrailingWhitespaceAnnotation() {
		SomeClass obj = new SomeClass(null, " ");
		new OpenmrsObjectSaveHandler().handle(obj, null, null, null);
		Assert.assertNotNull(obj.getDescription());
	}
	
	/**
	 * @see {@link OpenmrsObjectSaveHandler#handle(OpenmrsObject,User,Date,String)}
	 */
	@Test
	@Verifies(value = "trim strings without AllowLeadingOrTrailingWhitespace annotation", method = "handle(OpenmrsObject,User,Date,String)")
	public void handle_shouldTrimStringsWithoutAllowLeadingOrTrailingWhitespaceAnnotation() {
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setCode(" code ");
		term.setConceptSource(new ConceptSource(1));
		new OpenmrsObjectSaveHandler().handle(term, null, null, null);
		Assert.assertEquals("code", term.getCode());
	}
	
	/**
	 * @see {@link OpenmrsObjectSaveHandler#handle(OpenmrsObject,User,Date,String)}
	 */
	@Test
	@Verifies(value = "trim empty strings for AllowEmptyStrings annotation", method = "handle(OpenmrsObject,User,Date,String)")
	public void handle_shouldTrimEmptyStringsForAllowEmptyStringsAnnotation() {
		SomeClass obj = new SomeClass(" name ");
		new OpenmrsObjectSaveHandler().handle(obj, null, null, null);
		Assert.assertEquals("name", obj.getName());
	}
	
	public class SomeClass extends BaseOpenmrsObject {
		
		private Integer id;
		
		private String name;
		
		private String description;
		
		public SomeClass(String name) {
			setName(name);
		}
		
		public SomeClass(String name, String description) {
			setName(name);
			setDescription(description);
		}
		
		public String getName() {
			return name;
		}
		
		@AllowEmptyStrings
		public void setName(String name) {
			this.name = name;
		}
		
		public String getDescription() {
			return description;
		}
		
		@AllowLeadingOrTrailingWhitespace
		public void setDescription(String description) {
			this.description = description;
		}
		
		public void setId(Integer id) {
			this.id = id;
		}
		
		public Integer getId() {
			return id;
		}
	}
}
