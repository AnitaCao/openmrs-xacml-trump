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
package org.openmrs.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.Provider;
import org.openmrs.ProviderAttribute;
import org.openmrs.ProviderAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.customdatatype.datatype.FreeTextDatatype;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.test.Verifies;

/**
 * This test class (should) contain tests for all of the ProviderService
 * 
 * @see org.openmrs.api.ProviderService
 */
public class ProviderServiceTest extends BaseContextSensitiveTest {
	
	private static final String PROVIDERS_INITIAL_XML = "org/openmrs/api/include/ProviderServiceTest-initial.xml";
	
	private static final String PROVIDER_ATTRIBUTE_TYPES_XML = "org/openmrs/api/include/ProviderServiceTest-providerAttributes.xml";
	
	private static final String OTHERS_PROVIDERS_XML = "org/openmrs/api/include/ProviderServiceTest-otherProviders.xml";
	
	private ProviderService service;
	
	@Before
	public void before() throws Exception {
		service = Context.getProviderService();
		executeDataSet(PROVIDERS_INITIAL_XML);
		executeDataSet(PROVIDER_ATTRIBUTE_TYPES_XML);
	}
	
	/**
	 * @see ProviderService#getAllProviderAttributeTypes(boolean)
	 * @verifies get all provider attribute types excluding retired
	 */
	@Test
	public void getAllProviderAttributeTypes_shouldGetAllProviderAttributeTypesExcludingRetired() throws Exception {
		List<ProviderAttributeType> types = service.getAllProviderAttributeTypes(false);
		assertEquals(2, types.size());
	}
	
	/**
	 * @see ProviderService#getAllProviderAttributeTypes(boolean)
	 * @verifies get all provider attribute types including retired
	 */
	@Test
	public void getAllProviderAttributeTypes_shouldGetAllProviderAttributeTypesIncludingRetired() throws Exception {
		List<ProviderAttributeType> types = service.getAllProviderAttributeTypes(true);
		assertEquals(3, types.size());
	}
	
	/**
	 * @see ProviderService#getAllProviderAttributeTypes()
	 * @verifies get all provider attribute types including retired by default
	 */
	@Test
	public void getAllProviderAttributeTypes_shouldGetAllProviderAttributeTypesIncludingRetiredByDefault() throws Exception {
		List<ProviderAttributeType> types = service.getAllProviderAttributeTypes();
		assertEquals(3, types.size());
	}
	
	/**
	 * @see ProviderService#getAllProviders()
	 * @verifies get all providers
	 */
	@Test
	public void getAllProviders_shouldGetAllProviders() throws Exception {
		List<Provider> providers = service.getAllProviders();
		assertEquals(9, providers.size());
	}
	
	/**
	 * @see ProviderService#getAllProviders(boolean)
	 * @verifies get all providers that are unretired
	 */
	@Test
	public void getAllProviders_shouldGetAllProvidersThatAreUnretired() throws Exception {
		List<Provider> providers = service.getAllProviders(false);
		assertEquals(7, providers.size());
	}
	
	/**
	 * @see ProviderService#getCountOfProviders(String)
	 * @verifies fetch number of provider matching given query.
	 */
	@Test
	public void getCountOfProviders_shouldFetchNumberOfProviderMatchingGivenQuery() throws Exception {
		assertEquals(1, service.getCountOfProviders("Hippo").intValue());
		Person person = Context.getPersonService().getPerson(502);
		Set<PersonName> names = person.getNames();
		for (Iterator<PersonName> iterator = names.iterator(); iterator.hasNext();) {
			PersonName name = (PersonName) iterator.next();
			name.setVoided(true);
			
		}
		PersonName personName = new PersonName("Hippot", "A", "B");
		personName.setPreferred(true);
		person.addName(personName);
		Context.getPersonService().savePerson(person);
		assertEquals(1, service.getCountOfProviders("Hippo").intValue());
	}
	
	/**
	 * @see ProviderService#getProvider(Integer)
	 * @verifies get provider given ID
	 */
	@Test
	public void getProvider_shouldGetProviderGivenID() throws Exception {
		Provider provider = service.getProvider(1);
		assertEquals("RobertClive", provider.getName());
		assertEquals("a2c3868a-6b90-11e0-93c3-18a905e044dc", provider.getUuid());
	}
	
	/**
	 * @see ProviderService#getProviderAttribute(Integer)
	 * @verifies get providerAttribute given ID
	 */
	@Test
	public void getProviderAttribute_shouldGetProviderAttributeGivenID() throws Exception {
		ProviderAttribute providerAttribute = service.getProviderAttribute(1);
		assertEquals("RobertClive", providerAttribute.getProvider().getName());
		assertEquals("a2c3868a-6b90-11e0-93c3-18a905e044dc", providerAttribute.getProvider().getUuid());
	}
	
	/**
	 * @see ProviderService#getProviderAttributeByUuid(String)
	 * @verifies get providerAttribute given Uuid
	 */
	
	@Test
	public void getProviderAttributeByUuid_shouldGetProviderAttributeGivenUuid() throws Exception {
		ProviderAttribute providerAttribute = service.getProviderAttributeByUuid("3a2bdb18-6faa-11e0-8414-001e378eb67e");
		assertEquals("RobertClive", providerAttribute.getProvider().getName());
		assertEquals("a2c3868a-6b90-11e0-93c3-18a905e044dc", providerAttribute.getProvider().getUuid());
	}
	
	/**
	 * @see ProviderService#getProviderAttributeType(Integer)
	 * @verifies get provider attribute type for the given id
	 */
	@Test
	public void getProviderAttributeType_shouldGetProviderAttributeTypeForTheGivenId() throws Exception {
		ProviderAttributeType providerAttributeType = service.getProviderAttributeType(1);
		assertEquals("Audit Date", providerAttributeType.getName());
		assertEquals("9516cc50-6f9f-11e0-8414-001e378eb67e", providerAttributeType.getUuid());
	}
	
	/**
	 * @see ProviderService#getProviderAttributeTypeByUuid(String)
	 * @verifies get the provider attribute type by it's uuid
	 */
	@Test
	public void getProviderAttributeTypeByUuid_shouldGetTheProviderAttributeTypeByItsUuid() throws Exception {
		ProviderAttributeType providerAttributeType = service
		        .getProviderAttributeTypeByUuid("9516cc50-6f9f-11e0-8414-001e378eb67e");
		assertEquals("Audit Date", providerAttributeType.getName());
	}
	
	/**
	 * @see ProviderService#getProviderByUuid(String)
	 * @verifies get provider given Uuid
	 */
	@Test
	public void getProviderByUuid_shouldGetProviderGivenUuid() throws Exception {
		Provider provider = service.getProviderByUuid("a2c3868a-6b90-11e0-93c3-18a905e044dc");
		Assert.assertNotNull(provider);
		assertEquals("RobertClive", provider.getName());
	}
	
	/**
	 * @see ProviderService#getProviders(String, Integer, Integer, java.util.Map)
	 * @verifies fetch provider with given name with case in sensitive
	 */
	@Test
	public void getProviders_shouldFetchProviderWithGivenNameWithCaseInSensitive() throws Exception {
		Provider provider = new Provider();
		provider.setIdentifier("unique");
		provider.setName("Catherin");
		service.saveProvider(provider);
		assertEquals(1, service.getProviders("Cath", 0, null, null).size());
	}
	
	/**
	 * @see ProviderService#getProviders(String, Integer, Integer, java.util.Map)
	 * @verifies fetch provider by matching query string with any unVoided PersonName's Given Name
	 */
	@Test
	public void getProviders_shouldFetchProviderByMatchingQueryStringWithAnyUnVoidedPersonNamesGivenName() throws Exception {
		assertEquals(1, service.getProviders("COL", 0, null, null).size());
	}
	
	/**
	 * @see ProviderService#getProviders(String, Integer, Integer, java.util.Map)
	 * @verifies fetch provider by matching query string with any unVoided PersonName's middleName
	 */
	@Test
	public void getProviders_shouldFetchProviderByMatchingQueryStringWithAnyUnVoidedPersonNamesMiddleName() throws Exception {
		assertEquals(6, service.getProviders("Tes", 0, null, null).size());
	}
	
	/**
	 * @see ProviderService#getProviders(String, Integer, Integer, java.util.Map)
	 * @verifies fetch provider by matching query string with any unVoided Person's familyName
	 */
	@Test
	public void getProviders_shouldFetchProviderByMatchingQueryStringWithAnyUnVoidedPersonsFamilyName() throws Exception {
		assertEquals(2, service.getProviders("Che", 0, null, null).size());
	}
	
	/**
	 * @see ProviderService#getProviders(String, Integer, Integer, java.util.Map)
	 * @verifies not fetch provider if the query string matches with any voided Person name for that
	 */
	@Test
	public void getProviders_shouldNotFetchProviderIfTheQueryStringMatchesWithAnyVoidedPersonNameForThat() throws Exception {
		assertEquals(0, service.getProviders("Hit", 0, null, null).size());
		assertEquals(1, service.getProviders("coll", 0, null, null).size());
	}
	
	/**
	 * @see ProviderService#purgeProvider(Provider)
	 * @verifies delete a provider
	 */
	@Test
	public void purgeProvider_shouldDeleteAProvider() throws Exception {
		Provider provider = service.getProvider(2);
		service.purgeProvider(provider);
		assertEquals(8, Context.getProviderService().getAllProviders().size());
	}
	
	/**
	 * @see ProviderService#purgeProviderAttributeType(ProviderAttributeType)
	 * @verifies delete a provider attribute type
	 */
	@Test
	public void purgeProviderAttributeType_shouldDeleteAProviderAttributeType() throws Exception {
		int size = service.getAllProviderAttributeTypes().size();
		ProviderAttributeType providerAttributeType = service.getProviderAttributeType(2);
		service.purgeProviderAttributeType(providerAttributeType);
		assertEquals(size - 1, service.getAllProviderAttributeTypes().size());
	}
	
	/**
	 * @see ProviderService#retireProvider(Provider,String)
	 * @verifies retire a provider
	 */
	@Test
	public void retireProvider_shouldRetireAProvider() throws Exception {
		Provider provider = service.getProvider(1);
		assertFalse(provider.isRetired());
		assertNull(provider.getRetireReason());
		service.retireProvider(provider, "retire reason");
		assertTrue(provider.isRetired());
		assertEquals("retire reason", provider.getRetireReason());
		assertEquals(6, service.getAllProviders(false).size());
	}
	
	/**
	 * @see ProviderService#retireProviderAttributeType(ProviderAttributeType,String)
	 * @verifies retire provider type attribute
	 */
	@Test
	public void retireProviderAttributeType_shouldRetireProviderTypeAttribute() throws Exception {
		ProviderAttributeType providerAttributeType = service.getProviderAttributeType(1);
		assertFalse(providerAttributeType.isRetired());
		assertNull(providerAttributeType.getRetireReason());
		assertEquals(2, service.getAllProviderAttributeTypes(false).size());
		service.retireProviderAttributeType(providerAttributeType, "retire reason");
		assertTrue(providerAttributeType.isRetired());
		assertEquals("retire reason", providerAttributeType.getRetireReason());
		assertEquals(1, service.getAllProviderAttributeTypes(false).size());
	}
	
	/**
	 * @see ProviderService#saveProvider(Provider)
	 * @verifies save a Provider with provider name alone
	 */
	@Test
	public void saveProvider_shouldSaveAProviderWithProviderNameAlone() throws Exception {
		Provider provider = new Provider();
		provider.setIdentifier("unique");
		provider.setName("Provider9");
		service.saveProvider(provider);
		Assert.assertNotNull(provider.getId());
		Assert.assertNotNull(provider.getUuid());
		Assert.assertNotNull(provider.getCreator());
		Assert.assertNotNull(provider.getDateCreated());
		Assert.assertEquals("Provider9", provider.getName());
	}
	
	/**
	 * @see ProviderService#saveProvider(Provider)
	 * @verifies save a Provider with Person alone
	 */
	@Test
	public void saveProvider_shouldSaveAProviderWithPersonAlone() throws Exception {
		Provider provider = new Provider();
		provider.setIdentifier("unique");
		Person person = Context.getPersonService().getPerson(new Integer(999));
		provider.setPerson(person);
		service.saveProvider(provider);
		Assert.assertNotNull(provider.getId());
		Assert.assertNotNull(provider.getUuid());
		Assert.assertNotNull(provider.getCreator());
		Assert.assertNotNull(provider.getDateCreated());
		Assert.assertEquals(999, provider.getPerson().getId().intValue());
		
	}
	
	/**
	 * @see ProviderService#saveProviderAttributeType(ProviderAttributeType)
	 * @verifies save the provider attribute type
	 */
	@Test
	public void saveProviderAttributeType_shouldSaveTheProviderAttributeType() throws Exception {
		int size = service.getAllProviderAttributeTypes().size();
		ProviderAttributeType providerAttributeType = new ProviderAttributeType();
		providerAttributeType.setName("new");
		providerAttributeType.setDatatypeClassname(FreeTextDatatype.class.getName());
		providerAttributeType = service.saveProviderAttributeType(providerAttributeType);
		assertEquals(size + 1, service.getAllProviderAttributeTypes().size());
		assertNotNull(providerAttributeType.getId());
	}
	
	/**
	 * @see ProviderService#unretireProvider(Provider)
	 * @verifies unretire a provider
	 */
	@Test
	public void unretireProvider_shouldUnretireAProvider() throws Exception {
		Provider provider = service.getProvider(1);
		service.unretireProvider(provider);
		assertFalse(provider.isRetired());
		assertNull(provider.getRetireReason());
	}
	
	/**
	 * @see ProviderService#unretireProviderAttributeType(ProviderAttributeType)
	 * @verifies unretire a provider attribute type
	 */
	@Test
	public void unretireProviderAttributeType_shouldUnretireAProviderAttributeType() throws Exception {
		ProviderAttributeType providerAttributeType = service.getProviderAttributeType(2);
		assertTrue(providerAttributeType.isRetired());
		service.unretireProviderAttributeType(providerAttributeType);
		assertFalse(providerAttributeType.isRetired());
		assertNull(providerAttributeType.getRetireReason());
	}
	
	/**
	 * @see ProviderService#getProviders(String, Integer, Integer, java.util.Map)
	 * @verifies get all providers with given attribute values
	 */
	@Test
	public void getProviders_shouldGetAllProvidersWithGivenAttributeValues() throws Exception {
		Map<ProviderAttributeType, Object> attributes = new HashMap<ProviderAttributeType, Object>();
		attributes.put(service.getProviderAttributeType(1), new SimpleDateFormat("yyyy-MM-dd").parse("2011-04-25"));
		List<Provider> providers = service.getProviders("RobertClive", 0, null, attributes);
		Assert.assertEquals(1, providers.size());
		Assert.assertEquals(Integer.valueOf(1), providers.get(0).getProviderId());
	}
	
	/**
	 * @see ProviderService#getProviders(String, Integer, Integer, java.util.Map)
	 * @verifies not find any providers if none have given attribute values
	 */
	@Test
	public void getProviders_shouldNotFindAnyProvidersIfNoneHaveGivenAttributeValues() throws Exception {
		Map<ProviderAttributeType, Object> attributes = new HashMap<ProviderAttributeType, Object>();
		attributes.put(service.getProviderAttributeType(1), new SimpleDateFormat("yyyy-MM-dd").parse("1411-04-25"));
		List<Provider> providers = service.getProviders("RobertClive", 0, null, attributes);
		Assert.assertEquals(0, providers.size());
	}
	
	/**
	 * @see ProviderService#getProvidersByPerson(Person)
	 * @verifies fail if person is null
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getProvidersByPerson_shouldFailIfPersonIsNull() throws Exception {
		//given
		
		//when
		service.getProvidersByPerson(null);
		
		//then
		Assert.fail();
	}
	
	/**
	 * @see ProviderService#getProvidersByPerson(Person)
	 * @verifies return provider for given person
	 */
	@Test
	public void getProvidersByPerson_shouldReturnProvidersForGivenPerson() throws Exception {
		//given
		Person person = Context.getPersonService().getPerson(999);
		Provider provider = new Provider();
		provider.setIdentifier("unique");
		provider.setPerson(person);
		provider = service.saveProvider(provider);
		
		//when
		Collection<Provider> providers = service.getProvidersByPerson(person);
		
		//then
		Assert.assertEquals(1, providers.size());
		Assert.assertTrue(providers.contains(provider));
	}
	
	/**
	 * @see ProviderService#getProviders(String,Integer,Integer,Map)
	 * @verifies return all providers if query is empty
	 */
	@Test
	public void getProviders_shouldReturnAllProvidersIfQueryIsEmpty() throws Exception {
		//given
		List<Provider> allProviders = service.getAllProviders();
		
		//when
		List<Provider> providers = service.getProviders("", null, null, null);
		
		//then
		Assert.assertEquals(allProviders.size(), providers.size());
	}
	
	/**
	 * @see {@link ProviderService#isProviderIdentifierUnique(Provider)}
	 */
	@Test
	@Verifies(value = "should return false if the identifier is a duplicate", method = "isProviderIdentifierUnique(Provider)")
	public void isProviderIdentifierUnique_shouldReturnFalseIfTheIdentifierIsADuplicate() throws Exception {
		executeDataSet(OTHERS_PROVIDERS_XML);
		Provider duplicateProvider = service.getProvider(200);
		
		Provider existingProviderToEdit = service.getProvider(1);
		existingProviderToEdit.setName("name");
		existingProviderToEdit.setIdentifier(duplicateProvider.getIdentifier());
		Assert.assertFalse(service.isProviderIdentifierUnique(existingProviderToEdit));
	}
	
	/**
	 * @see {@link ProviderService#getProviderByIdentifier(String)}
	 */
	@Test
	@Verifies(value = "should get a provider matching the specified identifier ignoring case", method = "getProviderByIdentifier(String)")
	public void getProviderByIdentifier_shouldGetAProviderMatchingTheSpecifiedIdentifierIgnoringCase() throws Exception {
		String identifier = "8a760";
		Provider provider = service.getProviderByIdentifier(identifier);
		Assert.assertEquals("a2c3868a-6b90-11e0-93c3-18a905e044dc", provider.getUuid());
		//ensures that the case sensitive test stays valid just in case 
		//the test dataset is edited and the case is changed
		Assert.assertNotSame(identifier, provider.getIdentifier());
	}
	
	/**
	 * @see ProviderService#getProviders(String,Integer,Integer,Map,boolean)
	 * @verifies find provider by identifier
	 */
	@Test
	public void getProviders_shouldFindProviderByIdentifier() throws Exception {
		String identifier = "8a760";
		List<Provider> providers = service.getProviders(identifier, null, null, null);
		Provider provider = service.getProviderByIdentifier(identifier);
		
		Assert.assertTrue(providers.contains(provider));
	}
	
	/**
	 * @see {@link ProviderService#isProviderIdentifierUnique(Provider)}
	 */
	@Test
	@Verifies(value = "should return true if the identifier is null", method = "isProviderIdentifierUnique(Provider)")
	public void isProviderIdentifierUnique_shouldReturnTrueIfTheIdentifierIsNull() throws Exception {
		Provider provider = new Provider();
		provider.setName("new developer");
		Assert.assertTrue(service.isProviderIdentifierUnique(provider));
	}
	
	/**
	 * @see {@link ProviderService#isProviderIdentifierUnique(Provider)}
	 */
	@Test
	@Verifies(value = "should return true if the identifier is a blank string", method = "isProviderIdentifierUnique(Provider)")
	public void isProviderIdentifierUnique_shouldReturnTrueIfTheIdentifierIsABlankString() throws Exception {
		Provider provider = new Provider();
		provider.setName("new developer");
		provider.setIdentifier("");
		Assert.assertTrue(service.isProviderIdentifierUnique(provider));
	}
}
