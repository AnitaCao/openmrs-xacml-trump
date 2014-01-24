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
package org.openmrs.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.VisitAttribute;
import org.openmrs.VisitAttributeType;
import org.openmrs.VisitType;
import org.openmrs.api.APIException;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.VisitDAO;
import org.openmrs.customdatatype.CustomDatatypeUtil;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.PrivilegeConstants;
import org.openmrs.validator.ValidateUtil;

/**
 * Default implementation of the {@link VisitService}. This class should not be used on its own. The
 * current OpenMRS implementation should be fetched from the Context.
 * 
 * @since 1.9
 */
public class VisitServiceImpl extends BaseOpenmrsService implements VisitService {
	
	private VisitDAO dao;
	
	/**
	 * Method used to inject the visit data access object.
	 * 
	 * @param dao the visit data access object.
	 */
	public void setVisitDAO(VisitDAO dao) {
		this.dao = dao;
	}
	
	public VisitDAO getVisitDAO() {
		return dao;
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getAllVisitTypes()
	 */
	public List<VisitType> getAllVisitTypes() {
		return getVisitDAO().getAllVisitTypes();
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getVisitType(java.lang.Integer)
	 */
	public VisitType getVisitType(Integer visitTypeId) {
		return getVisitDAO().getVisitType(visitTypeId);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getVisitTypeByUuid(java.lang.String)
	 */
	public VisitType getVisitTypeByUuid(String uuid) {
		return getVisitDAO().getVisitTypeByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getVisitTypes(java.lang.String)
	 */
	public List<VisitType> getVisitTypes(String fuzzySearchPhrase) {
		return getVisitDAO().getVisitTypes(fuzzySearchPhrase);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#saveVisitType(org.openmrs.VisitType)
	 */
	public VisitType saveVisitType(VisitType visitType) throws APIException {
		ValidateUtil.validate(visitType);
		return getVisitDAO().saveVisitType(visitType);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#retireVisitType(org.openmrs.VisitType, java.lang.String)
	 */
	public VisitType retireVisitType(VisitType visitType, String reason) {
		return saveVisitType(visitType);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#unretireVisitType(org.openmrs.VisitType)
	 */
	public VisitType unretireVisitType(VisitType visitType) {
		return saveVisitType(visitType);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#purgeVisitType(org.openmrs.VisitType)
	 */
	public void purgeVisitType(VisitType visitType) {
		getVisitDAO().purgeVisitType(visitType);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getAllVisits()
	 */
	@Override
	public List<Visit> getAllVisits() throws APIException {
		return dao.getVisits(null, null, null, null, null, null, null, null, null, true, false);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getVisit(java.lang.Integer)
	 */
	@Override
	public Visit getVisit(Integer visitId) throws APIException {
		return dao.getVisit(visitId);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getVisitByUuid(java.lang.String)
	 */
	@Override
	public Visit getVisitByUuid(String uuid) throws APIException {
		return dao.getVisitByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#saveVisit(org.openmrs.Visit)
	 */
	@Override
	public Visit saveVisit(Visit visit) throws APIException {
		if (visit.getVisitId() == null)
			Context.requirePrivilege(PrivilegeConstants.ADD_VISITS);
		else
			Context.requirePrivilege(PrivilegeConstants.EDIT_VISITS);
		
		CustomDatatypeUtil.saveAttributesIfNecessary(visit);
		return dao.saveVisit(visit);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#endVisit(org.openmrs.Visit,java.util.Date)
	 */
	@Override
	public Visit endVisit(Visit visit, Date stopDate) {
		if (stopDate == null)
			stopDate = new Date();
		
		visit.setStopDatetime(stopDate);
		
		return Context.getVisitService().saveVisit(visit);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#voidVisit(org.openmrs.Visit, java.lang.String)
	 */
	@Override
	public Visit voidVisit(Visit visit, String reason) throws APIException {
		return dao.saveVisit(visit);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#unvoidVisit(org.openmrs.Visit)
	 */
	@Override
	public Visit unvoidVisit(Visit visit) throws APIException {
		return dao.saveVisit(visit);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#purgeVisit(org.openmrs.Visit)
	 */
	@Override
	public void purgeVisit(Visit visit) throws APIException {
		if (visit.getVisitId() == null)
			return;
		if (Context.getEncounterService().getEncountersByVisit(visit, true).size() > 0)
			throw new APIException(Context.getMessageSourceService().getMessage("Visit.purge.inUse", null,
			    "Cannot purge a visit that has encounters associated to it", Context.getLocale()));
		dao.deleteVisit(visit);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getVisits(java.util.Collection, java.util.Collection,
	 *      java.util.Collection, java.util.Collection, java.util.Date, java.util.Date,
	 *      java.util.Date, java.util.Date, boolean, boolean)
	 */
	@Override
	public List<Visit> getVisits(Collection<VisitType> visitTypes, Collection<Patient> patients,
	        Collection<Location> locations, Collection<Concept> indications, Date minStartDatetime, Date maxStartDatetime,
	        Date minEndDatetime, Date maxEndDatetime, Map<VisitAttributeType, Object> attributeValues,
	        boolean includeInactive, boolean includeVoided) throws APIException {
		
		Map<VisitAttributeType, String> serializedAttributeValues = CustomDatatypeUtil.getValueReferences(attributeValues);
		return dao.getVisits(visitTypes, patients, locations, indications, minStartDatetime, maxStartDatetime,
		    minEndDatetime, maxEndDatetime, serializedAttributeValues, includeInactive, includeVoided);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getVisitsByPatient(org.openmrs.Patient)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Visit> getVisitsByPatient(Patient patient) throws APIException {
		//Don't bother to hit the database
		if (patient == null || patient.getId() == null)
			return Collections.EMPTY_LIST;
		
		return getVisits(null, Collections.singletonList(patient), null, null, null, null, null, null, null, true, false);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getActiveVisitsByPatient(org.openmrs.Patient)
	 */
	@Override
	public List<Visit> getActiveVisitsByPatient(Patient patient) throws APIException {
		return getVisitsByPatient(patient, false, false);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getVisitsByPatient(org.openmrs.Patient, boolean, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Visit> getVisitsByPatient(Patient patient, boolean includeInactive, boolean includeVoided)
	        throws APIException {
		if (patient == null || patient.getId() == null)
			return Collections.EMPTY_LIST;
		
		return dao.getVisits(null, Collections.singletonList(patient), null, null, null, null, null, null, null,
		    includeInactive, includeVoided);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getAllVisitAttributeTypes()
	 */
	@Override
	public List<VisitAttributeType> getAllVisitAttributeTypes() {
		return dao.getAllVisitAttributeTypes();
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getVisitAttributeType(java.lang.Integer)
	 */
	@Override
	public VisitAttributeType getVisitAttributeType(Integer id) {
		return dao.getVisitAttributeType(id);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getVisitAttributeTypeByUuid(java.lang.String)
	 */
	@Override
	public VisitAttributeType getVisitAttributeTypeByUuid(String uuid) {
		return dao.getVisitAttributeTypeByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#saveVisitAttributeType(org.openmrs.VisitAttributeType)
	 */
	@Override
	public VisitAttributeType saveVisitAttributeType(VisitAttributeType visitAttributeType) {
		return dao.saveVisitAttributeType(visitAttributeType);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#retireVisitAttributeType(org.openmrs.VisitAttributeType,
	 *      java.lang.String)
	 */
	@Override
	public VisitAttributeType retireVisitAttributeType(VisitAttributeType visitAttributeType, String reason) {
		return dao.saveVisitAttributeType(visitAttributeType);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#unretireVisitAttributeType(org.openmrs.VisitAttributeType)
	 */
	@Override
	public VisitAttributeType unretireVisitAttributeType(VisitAttributeType visitAttributeType) {
		return dao.saveVisitAttributeType(visitAttributeType);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#purgeVisitAttributeType(org.openmrs.VisitAttributeType)
	 */
	@Override
	public void purgeVisitAttributeType(VisitAttributeType visitAttributeType) {
		dao.deleteVisitAttributeType(visitAttributeType);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#getVisitAttributeByUuid(java.lang.String)
	 */
	@Override
	public VisitAttribute getVisitAttributeByUuid(String uuid) {
		return dao.getVisitAttributeByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.api.VisitService#stopVisits(Date)
	 */
	@Override
	public void stopVisits(Date maximumStartDate) {
		String gpValue = Context.getAdministrationService().getGlobalProperty(OpenmrsConstants.GP_VISIT_TYPES_TO_AUTO_CLOSE);
		VisitService vs = Context.getVisitService();
		if (StringUtils.isNotBlank(gpValue)) {
			if (maximumStartDate == null)
				maximumStartDate = new Date();
			
			List<VisitType> visitTypesToStop = new ArrayList<VisitType>();
			String[] visitTypeNames = StringUtils.split(gpValue.trim(), ",");
			for (int i = 0; i < visitTypeNames.length; i++) {
				String currName = visitTypeNames[i];
				visitTypeNames[i] = currName.trim().toLowerCase();
			}
			
			List<VisitType> allVisitTypes = vs.getAllVisitTypes();
			for (VisitType visitType : allVisitTypes) {
				if (ArrayUtils.contains(visitTypeNames, visitType.getName().toLowerCase()))
					visitTypesToStop.add(visitType);
			}
			
			if (visitTypesToStop.size() > 0) {
				int counter = 0;
				Date stopDate = new Date();
				Visit nextVisit = dao.getNextVisit(null, visitTypesToStop, maximumStartDate);
				while (nextVisit != null) {
					nextVisit.setStopDatetime(stopDate);
					dao.saveVisit(nextVisit);
					if (counter++ > 50) {
						//ensure changes are persisted to DB before reclaiming memory
						Context.flushSession();
						Context.clearSession();
						counter = 0;
					}
					
					nextVisit = dao.getNextVisit(nextVisit, visitTypesToStop, maximumStartDate);
				}
			}
		}
	}
}
