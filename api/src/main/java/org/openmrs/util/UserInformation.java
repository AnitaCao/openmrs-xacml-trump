package org.openmrs.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserInformation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4729755247343846201L;
	
	private List<String> assignedRoles;
	
	private String virtualRole;
	
	public UserInformation() {
		assignedRoles = new ArrayList<String>();
	}
	
	public List<String> getAssignedRoles() {
		return assignedRoles;
	}
	
	public void setAssignedRoles(List<String> assignedRoles) {
		this.assignedRoles = assignedRoles;
	}
	
	public String getVirtualRole() {
		return virtualRole;
	}
	
	public void setVirtualRole(String virtualRole) {
		this.virtualRole = virtualRole;
	}
	
}
