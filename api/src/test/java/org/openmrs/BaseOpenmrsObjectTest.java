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
package org.openmrs;

import org.junit.Assert;
import org.junit.Test;

public class BaseOpenmrsObjectTest {
	
	private static class BaseOpenmrsObjectMock extends BaseOpenmrsObject {
		
		@Override
		public Integer getId() {
			return null;
		}
		
		@Override
		public void setId(Integer id) {
		}
		
	}
	
	/**
	 * @see BaseOpenmrsObject#equals(Object)
	 * @verifies return false if given obj has null uuid
	 */
	@Test
	public void equals_shouldReturnFalseIfGivenObjHasNullUuid() throws Exception {
		//given
		BaseOpenmrsObject o = new BaseOpenmrsObjectMock();
		BaseOpenmrsObject obj = new BaseOpenmrsObjectMock();
		
		//when
		obj.setUuid(null);
		
		//then
		Assert.assertFalse(o.equals(obj));
	}
	
	/**
	 * @see BaseOpenmrsObject#equals(Object)
	 * @verifies return false if given obj is not instance of BaseOpenmrsObject
	 */
	@Test
	public void equals_shouldReturnFalseIfGivenObjIsNotInstanceOfBaseOpenmrsObject() throws Exception {
		//given
		BaseOpenmrsObject o = new BaseOpenmrsObjectMock();
		
		//when
		Object obj = new Object();
		
		//then
		Assert.assertFalse(o.equals(obj));
	}
	
	/**
	 * @see BaseOpenmrsObject#equals(Object)
	 * @verifies return false if given obj is null
	 */
	@Test
	public void equals_shouldReturnFalseIfGivenObjIsNull() throws Exception {
		//given
		BaseOpenmrsObject o = new BaseOpenmrsObjectMock();
		
		//when
		BaseOpenmrsObject obj = null;
		
		//then
		Assert.assertFalse(o.equals(obj));
	}
	
	/**
	 * @see BaseOpenmrsObject#equals(Object)
	 * @verifies return false if uuid is null
	 */
	@Test
	public void equals_shouldReturnFalseIfUuidIsNull() throws Exception {
		//given
		BaseOpenmrsObject o = new BaseOpenmrsObjectMock();
		BaseOpenmrsObject obj = new BaseOpenmrsObjectMock();
		
		//when
		o.setUuid(null);
		
		//then
		Assert.assertFalse(o.equals(obj));
	}
	
	/**
	 * @see BaseOpenmrsObject#equals(Object)
	 * @verifies return true if objects are the same
	 */
	@Test
	public void equals_shouldReturnTrueIfObjectsAreTheSame() throws Exception {
		//given
		BaseOpenmrsObject o = new BaseOpenmrsObjectMock();
		
		//when
		BaseOpenmrsObject obj = o;
		
		//then
		Assert.assertTrue(o.equals(obj));
	}
	
	/**
	 * @see BaseOpenmrsObject#equals(Object)
	 * @verifies return true if uuids are equal
	 */
	@Test
	public void equals_shouldReturnTrueIfUuidsAreEqual() throws Exception {
		//given
		BaseOpenmrsObject o = new BaseOpenmrsObjectMock();
		BaseOpenmrsObject obj = new BaseOpenmrsObjectMock();
		
		//when
		obj.setUuid(o.getUuid());
		
		//then
		Assert.assertTrue(o.equals(obj));
	}
	
	/**
	 * @see BaseOpenmrsObject#hashCode()
	 * @verifies not fail if uuid is null
	 */
	@Test
	public void hashCode_shouldNotFailIfUuidIsNull() throws Exception {
		//given
		BaseOpenmrsObject o = new BaseOpenmrsObjectMock();
		
		//when
		o.setUuid(null);
		
		//then
		o.hashCode();
	}
	
	/**
	 * @see BaseOpenmrsObject#toString()
	 * @verifies not fail if uuid is null
	 */
	@Test
	public void toString_shouldNotFailIfUuidIsNull() throws Exception {
		//given
		BaseOpenmrsObject o = new BaseOpenmrsObjectMock();
		
		//when
		o.setUuid(null);
		
		//then
		o.toString();
	}
	
	@Test
	public void shouldNotBeEqualWhenDifferentClassesAndSameId() throws Exception {
		Encounter encounter = new Encounter(2);
		Order order = new Order(2);
		
		Assert.assertFalse(encounter.equals(order));
	}
	
	@Test
	public void shouldNotBeEqualWhenFirstIsNull() throws Exception {
		Encounter encounter = new Encounter(2);
		Assert.assertFalse(encounter.equals(null));
	}
}
