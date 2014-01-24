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
package org.openmrs.web.taglib;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.ConceptNameTag;
import org.openmrs.Encounter;
import org.openmrs.EncounterRole;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.LocationTag;
import org.openmrs.Obs;
import org.openmrs.OpenmrsMetadata;
import org.openmrs.Person;
import org.openmrs.Program;
import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.Visit;
import org.openmrs.VisitType;
import org.openmrs.api.ConceptNameType;
import org.openmrs.api.context.Context;
import org.openmrs.customdatatype.CustomDatatype;
import org.openmrs.customdatatype.CustomDatatype.Summary;
import org.openmrs.customdatatype.CustomDatatypeHandler;
import org.openmrs.customdatatype.CustomDatatypeUtil;
import org.openmrs.customdatatype.CustomValueDescriptor;
import org.openmrs.customdatatype.DownloadableDatatypeHandler;
import org.openmrs.customdatatype.SingleCustomValue;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.web.attribute.handler.HtmlDisplayableDatatypeHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.util.JavaScriptUtils;

/**
 * <pre>
 * Prints out a pretty-formatted versions of an OpenMRS object
 * TODO: add the other openmrs domain objects
 * TODO: add a size=compact|NORMAL|full|? option
 * </pre>
 */
public class FormatTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	
	private final Log log = LogFactory.getLog(getClass());
	
	private String var;
	
	private Integer conceptId;
	
	private Concept concept;
	
	private String withConceptNameType;
	
	private String withConceptNameTag;
	
	private Obs obsValue;
	
	private Integer userId;
	
	private User user;
	
	private Integer personId;
	
	private Person person;
	
	private Integer encounterId;
	
	private Encounter encounter;
	
	private Integer encounterTypeId;
	
	private EncounterType encounterType;
	
	private Integer locationId;
	
	private Location location;
	
	private Integer locationTagId;
	
	private LocationTag locationTag;
	
	private Integer programId;
	
	private Program program;
	
	private Integer visitTypeId;
	
	private VisitType visitType;
	
	private Integer visitId;
	
	private Visit visit;
	
	private Boolean javaScriptEscape = Boolean.FALSE;
	
	private Integer providerId;
	
	private Provider provider;
	
	private Map<EncounterRole, Set<Provider>> encounterProviders;
	
	private Form form;
	
	private SingleCustomValue<?> singleCustomValue;
	
	@Override
	public int doStartTag() {
		StringBuilder sb = new StringBuilder();
		if (conceptId != null)
			concept = Context.getConceptService().getConcept(conceptId);
		if (concept != null) {
			printConcept(sb, concept);
		}
		
		if (obsValue != null)
			sb.append(obsValue.getValueAsString(Context.getLocale()));
		
		if (userId != null)
			user = Context.getUserService().getUser(userId);
		if (user != null)
			printUser(sb, user);
		
		if (personId != null)
			person = Context.getPersonService().getPerson(personId);
		if (person != null)
			printPerson(sb, person);
		
		if (encounterId != null)
			encounter = Context.getEncounterService().getEncounter(encounterId);
		if (encounter != null) {
			printMetadata(sb, encounter.getEncounterType());
			sb.append(" @");
			printMetadata(sb, encounter.getLocation());
			sb.append(" | ");
			printDate(sb, encounter.getEncounterDatetime());
			sb.append(" | ");
			printPerson(sb, encounter.getProvider());
		}
		
		if (encounterTypeId != null)
			encounterType = Context.getEncounterService().getEncounterType(encounterTypeId);
		if (encounterType != null) {
			printMetadata(sb, encounterType);
		}
		
		if (visitTypeId != null)
			visitType = Context.getVisitService().getVisitType(visitTypeId);
		if (visitType != null) {
			printMetadata(sb, visitType);
		}
		
		if (visitId != null)
			visit = Context.getVisitService().getVisit(visitId);
		if (visit != null) {
			printMetadata(sb, visit.getVisitType());
			sb.append(" @");
			printMetadata(sb, visit.getLocation());
			sb.append(" | ");
			printDate(sb, visit.getStartDatetime());
		}
		
		if (locationId != null)
			location = Context.getLocationService().getLocation(locationId);
		if (location != null) {
			printMetadata(sb, location);
		}
		
		if (locationTagId != null)
			locationTag = Context.getLocationService().getLocationTag(locationTagId);
		if (locationTag != null) {
			printMetadata(sb, locationTag);
		}
		
		if (programId != null)
			program = Context.getProgramWorkflowService().getProgram(programId);
		if (program != null) {
			if (StringUtils.hasText(program.getName())) {
				printMetadata(sb, program);
			} else if (program.getConcept() != null) {
				printConcept(sb, program.getConcept());
			}
		}
		
		if (providerId != null)
			provider = Context.getProviderService().getProvider(providerId);
		if (provider != null)
			printProvider(sb, provider);
		
		if (encounterProviders != null) {
			printEncounterProviders(sb, encounterProviders);
		}
		
		if (form != null) {
			printForm(sb, form);
		}
		
		if (singleCustomValue != null) {
			printSingleCustomValue(sb, singleCustomValue);
		}
		
		if (StringUtils.hasText(var)) {
			if (javaScriptEscape)
				pageContext.setAttribute(var, JavaScriptUtils.javaScriptEscape(sb.toString()));
			else
				pageContext.setAttribute(var, sb.toString());
		} else {
			try {
				if (javaScriptEscape)
					pageContext.getOut().write(JavaScriptUtils.javaScriptEscape(sb.toString()));
				else
					pageContext.getOut().write(sb.toString());
			}
			catch (IOException e) {
				log.error("Failed to write to pageContext.getOut()", e);
			}
		}
		return SKIP_BODY;
	}
	
	/**
	 * Formats a {@link Form} and prints it to sb
	 * 
	 * @param sb
	 * @param form
	 */
	private void printForm(StringBuilder sb, Form form) {
		sb.append(form.getName() + " (v" + form.getVersion() + ")");
	}
	
	/**
	 * Formats a {@link SingleCustomValue} and prints it to sb
	 * 
	 * @param sb
	 * @param val
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" })
	private void printSingleCustomValue(StringBuilder sb, SingleCustomValue<?> val) {
		CustomValueDescriptor descriptor = val.getDescriptor();
		CustomDatatype<?> datatype = CustomDatatypeUtil.getDatatype(descriptor);
		CustomDatatypeHandler handler = CustomDatatypeUtil.getHandler(descriptor);
		if (handler != null && handler instanceof HtmlDisplayableDatatypeHandler) {
			Summary summary = ((HtmlDisplayableDatatypeHandler) handler).toHtmlSummary(datatype, val.getValueReference());
			if (summary.isComplete()) {
				sb.append(summary);
			} else {
				sb.append(summary);
				sb.append("...");
				if (handler instanceof HtmlDisplayableDatatypeHandler) {
					String link = "viewCustomValue.form?handler=" + handler.getClass().getName() + "&datatype="
					        + datatype.getClass().getName() + "&value=" + val.getValueReference();
					sb.append(" (<a target=\"_blank\" href=\"" + link + "\">"
					        + Context.getMessageSourceService().getMessage("general.view") + "</a>)");
				}
				if (handler instanceof DownloadableDatatypeHandler) {
					String link = "downloadCustomValue.form?handler=" + handler.getClass().getName() + "&datatype="
					        + datatype.getClass().getName() + "&value=" + val.getValueReference();
					sb.append(" (<a href=\"" + link + "\">"
					        + Context.getMessageSourceService().getMessage("general.download") + "</a>)");
				}
			}
		} else if (datatype != null) {
			Summary summary = datatype.getTextSummary(val.getValueReference());
			if (summary.isComplete()) {
				sb.append(summary);
			} else {
				sb.append(summary);
				sb.append("...");
			}
		} else {
			sb.append(Context.getMessageSourceService().getMessage("CustomDatatype.error.missingDatatype",
			    new Object[] { descriptor.getDatatypeClassname() }, Context.getLocale()));
			sb.append(val.getValueReference());
		}
	}
	
	/**
	 * Formats a Concept and prints it to sb, respecting conceptNameType and conceptNameTag if they are
	 * specified and a match is found. (This will always prints something.)
	 * 
	 * @param sb
	 * @param concept
	 * @should print the name with the correct name and type
	 */
	protected void printConcept(StringBuilder sb, Concept concept) {
		Locale loc = Context.getLocale();
		
		if (withConceptNameType != null || withConceptNameTag != null) {
			ConceptNameType lookForNameType = null;
			
			if (withConceptNameType != null) {
				lookForNameType = ConceptNameType.valueOf(withConceptNameType);
			}
			
			ConceptNameTag lookForNameTag = null;
			if (withConceptNameTag != null)
				lookForNameTag = Context.getConceptService().getConceptNameTagByName(withConceptNameTag);
			
			ConceptName name = concept.getName(loc, lookForNameType, lookForNameTag);
			if (name != null) {
				sb.append(name.getName());
				return;
			}
		}
		
		ConceptName name = concept.getPreferredName(loc);
		if (name != null) {
			sb.append(name.getName());
			return;
		}
		
		sb.append(concept.getDisplayString());
	}
	
	/**
	 * formats a date and prints it to sb
	 * 
	 * @param sb
	 * @param date
	 */
	private void printDate(StringBuilder sb, Date date) {
		sb.append(Context.getDateFormat().format(date));
	}
	
	/**
	 * formats any OpenmrsMetadata and prints it to sb
	 * 
	 * @param sb
	 * @param metadata
	 */
	private void printMetadata(StringBuilder sb, OpenmrsMetadata metadata) {
		if (metadata != null)
			sb.append(metadata.getName());
	}
	
	/**
	 * formats a user and prints it to sb
	 * 
	 * @param sb
	 * @param u
	 */
	private void printUser(StringBuilder sb, User u) {
		sb.append("<span class=\"user\">");
		sb.append("<span class=\"username\">");
		sb.append(u.getUsername());
		sb.append("</span>");
		if (u.getPerson() != null) {
			sb.append("<span class=\"personName\">");
			sb.append(" (").append(u.getPersonName()).append(")");
			sb.append("</span>");
		}
		sb.append("</span>");
	}
	
	/**
	 * formats a person and prints it to sb
	 * 
	 * @param sb
	 * @param p
	 */
	private void printPerson(StringBuilder sb, Person p) {
		if (p != null)
			sb.append(p.getPersonName());
	}
	
	/**
	 * formats a provider and prints him or her to a string builder
	 * 
	 * @param sb the string builder
	 * @param p the provider
	 */
	private void printProvider(StringBuilder sb, Provider p) {
		if (p != null)
			sb.append(getProviderName(p));
	}
	
	/**
	 * formats encounter providers and prints them to a string builder
	 * 
	 * @param sb the string builder
	 * @param eps the encounter providers.
	 */
	private void printEncounterProviders(StringBuilder sb, Map<EncounterRole, Set<Provider>> eps) {
		if (eps != null) {
			
			LinkedHashSet<Provider> providerList = getDisplayEncounterProviders(eps);
			
			String providers = null;
			for (Provider provider : providerList) {
				if (providers == null) {
					providers = "";
				} else {
					providers += ", ";
				}
				
				providers += getProviderName(provider);
			}
			
			if (providers != null) {
				sb.append(providers);
			}
		}
	}
	
	/**
	 * Gets the name of a provider.
	 * 
	 * @param provider the provider.
	 * @return the provider name.
	 */
	private String getProviderName(Provider provider) {
		if (provider.getPerson() != null)
			return provider.getPerson().getPersonName().getFullName();
		else
			return provider.getName();
	}
	
	/**
	 * Filters a list of encounter providers according to the global property 
	 * which determines providers in which encounter roles to display.
	 * 
	 * @param eps the encounter providers to filter.
	 * @return the filtered encounter providers.
	 */
	private LinkedHashSet<Provider> getDisplayEncounterProviders(Map<EncounterRole, Set<Provider>> encounterProviders) {
		String encounterRoles = Context.getAdministrationService().getGlobalProperty(
		    OpenmrsConstants.GP_DASHBOARD_PROVIDER_DISPLAY_ENCOUNTER_ROLES, null);
		
		if (!StringUtils.hasText(encounterRoles)) {
			
			//we do not filter if user has not yet set the global property.
			LinkedHashSet<Provider> allProviders = new LinkedHashSet<Provider>();
			
			for (Set<Provider> providers : encounterProviders.values()) {
				allProviders.addAll(providers);
			}
			
			return allProviders;
		}
		
		return filterProviders(encounterProviders, trimStringArray(encounterRoles.split(",")));
	}
	
	/**
	 * Filters and returns a list of providers from an encounter role provider map.
	 * The filtering is based on a given array of encounter roles names or ids.
	 * 
	 * @param encounterProviders map of encounter role providers to filter.
	 * @param rolesArray the roles string array.
	 * @return a filtered list of providers.
	 */
	private LinkedHashSet<Provider> filterProviders(Map<EncounterRole, Set<Provider>> encounterProviders, String[] rolesArray) {
		LinkedHashSet<Provider> filteredProviders = new LinkedHashSet<Provider>();
		
		Set<EncounterRole> roles = encounterProviders.keySet();
		for (EncounterRole encounterRole : roles) {
			if (containsRole(encounterRole, rolesArray)) {
				filteredProviders.addAll(encounterProviders.get(encounterRole));
			}
		}
		
		return filteredProviders;
	}
	
	/**
	 * Checks if an encounter role has its name or id in a string array.
	 * 
	 * @param encounterRole the encounter role.
	 * @param rolesArray the roles string array.
	 * @return true if yes, else false.
	 */
	private boolean containsRole(EncounterRole encounterRole, String[] rolesArray) {
		for (String role : rolesArray) {
			//Check for name and id
			if (role.equalsIgnoreCase(encounterRole.getName()) || role.equals(encounterRole.getId().toString())) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Trims elements of a string array.
	 * 
	 * @param unTrimmedArray the un trimmed array.
	 * @return the trimmed array.
	 */
	private String[] trimStringArray(String[] unTrimmedArray) {
		String[] trimmedArray = new String[unTrimmedArray.length];
		for (int index = 0; index < unTrimmedArray.length; index++) {
			trimmedArray[index] = unTrimmedArray[index].trim();
		}
		
		return trimmedArray;
	}
	
	@Override
	public int doEndTag() {
		reset();
		return EVAL_PAGE;
	}
	
	private void reset() {
		var = null;
		conceptId = null;
		concept = null;
		withConceptNameType = null;
		withConceptNameTag = null;
		obsValue = null;
		userId = null;
		user = null;
		personId = null;
		person = null;
		encounterId = null;
		encounter = null;
		encounterTypeId = null;
		encounterType = null;
		locationId = null;
		location = null;
		locationTagId = null;
		locationTag = null;
		visitType = null;
		visitTypeId = null;
		visitId = null;
		visit = null;
		providerId = null;
		provider = null;
		encounterProviders = null;
		form = null;
		singleCustomValue = null;
	}
	
	public Integer getConceptId() {
		return conceptId;
	}
	
	public void setConceptId(Integer conceptId) {
		this.conceptId = conceptId;
	}
	
	public Concept getConcept() {
		return concept;
	}
	
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	public Obs getObsValue() {
		return obsValue;
	}
	
	public void setObsValue(Obs obsValue) {
		this.obsValue = obsValue;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getEncounterId() {
		return encounterId;
	}
	
	public void setEncounterId(Integer encounterId) {
		this.encounterId = encounterId;
	}
	
	public Encounter getEncounter() {
		return encounter;
	}
	
	public void setEncounter(Encounter encounter) {
		this.encounter = encounter;
	}
	
	public Integer getEncounterTypeId() {
		return encounterTypeId;
	}
	
	public void setEncounterTypeId(Integer encounterTypeId) {
		this.encounterTypeId = encounterTypeId;
	}
	
	public EncounterType getEncounterType() {
		return encounterType;
	}
	
	public void setEncounterType(EncounterType encounterType) {
		this.encounterType = encounterType;
	}
	
	public Integer getLocationId() {
		return locationId;
	}
	
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Integer getLocationTagId() {
		return locationTagId;
	}
	
	public void setLocationTagId(Integer locationTagId) {
		this.locationTagId = locationTagId;
	}
	
	public LocationTag getLocationTag() {
		return locationTag;
	}
	
	public void setLocationTag(LocationTag locationTag) {
		this.locationTag = locationTag;
	}
	
	public Integer getProgramId() {
		return programId;
	}
	
	public void setProgramId(Integer programId) {
		this.programId = programId;
	}
	
	public Program getProgram() {
		return program;
	}
	
	public void setProgram(Program program) {
		this.program = program;
	}
	
	public String getVar() {
		return var;
	}
	
	public void setVar(String var) {
		this.var = var;
	}
	
	public Integer getPersonId() {
		return personId;
	}
	
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	/**
	 * @return the visitTypeId
	 */
	public Integer getVisitTypeId() {
		return visitTypeId;
	}
	
	/**
	 * @param visitTypeId the visitTypeId to set
	 */
	public void setVisitTypeId(Integer visitTypeId) {
		this.visitTypeId = visitTypeId;
	}
	
	/**
	 * @return the visitType
	 */
	public VisitType getVisitType() {
		return visitType;
	}
	
	/**
	 * @param visitType the visitType to set
	 */
	public void setVisitType(VisitType visitType) {
		this.visitType = visitType;
	}
	
	/**
	 * @return the visitId
	 * @since 1.9
	 */
	public Integer getVisitId() {
		return visitId;
	}
	
	/**
	 * @param visitId the visitId to set
	 * @since 1.9
	 */
	public void setVisitId(Integer visitId) {
		this.visitId = visitId;
	}
	
	/**
	 * @return the visit
	 * @since 1.9
	 */
	public Visit getVisit() {
		return visit;
	}
	
	/**
	 * @param visit the visit to set
	 * @since 1.9
	 */
	public void setVisit(Visit visit) {
		this.visit = visit;
	}
	
	/**
	 * @return the javaScriptEscape
	 */
	public Boolean getJavaScriptEscape() {
		return javaScriptEscape;
	}
	
	/**
	 * @param javaScriptEscape the javaScriptEscape to set
	 */
	public void setJavaScriptEscape(Boolean javaScriptEscape) {
		this.javaScriptEscape = javaScriptEscape;
	}
	
	/**
	 * @since 1.9
	 * @param encounterProviders the encounterProviders to set
	 */
	public Map<EncounterRole, Set<Provider>> getEncounterProviders() {
		return encounterProviders;
	}
	
	/**
	 * @since 1.9
	 * @return the encounterProviders
	 */
	public void setEncounterProviders(Map<EncounterRole, Set<Provider>> encounterProviders) {
		this.encounterProviders = encounterProviders;
	}
	
	/**
	 * @return the withConceptNameType
	 * @Since 1.9
	 */
	public String getWithConceptNameType() {
		return withConceptNameType;
	}
	
	/**
	 * @param withConceptNameType the withConceptNameType to set
	 * @Since 1.9
	 */
	public void setWithConceptNameType(String withConceptNameType) {
		this.withConceptNameType = withConceptNameType;
	}
	
	/**
	 * @return the withConceptNameTag
	 * @Since 1.9
	 */
	public String getWithConceptNameTag() {
		return withConceptNameTag;
	}
	
	/**
	 * @param withConceptNameTag the withConceptNameTag to set
	 * @Since 1.9
	 */
	public void setWithConceptNameTag(String withConceptNameTag) {
		this.withConceptNameTag = withConceptNameTag;
	}
	
	/**
	 * @return the form
	 */
	public Form getForm() {
		return form;
	}
	
	/**
	 * @param form the form to set
	 */
	public void setForm(Form form) {
		this.form = form;
	}
	
	/**
	 * @return the singleCustomValue
	 */
	public SingleCustomValue<?> getSingleCustomValue() {
		return singleCustomValue;
	}
	
	/**
	 * @param singleCustomValue the singleCustomValue to set
	 */
	public void setSingleCustomValue(SingleCustomValue<?> singleCustomValue) {
		this.singleCustomValue = singleCustomValue;
	}
	
}
