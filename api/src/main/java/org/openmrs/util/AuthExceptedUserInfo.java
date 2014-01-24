package org.openmrs.util;

import java.util.ArrayList;
import java.util.List;

public enum AuthExceptedUserInfo {
	INSTANCE;
	
	private List<String> expemtedUsers = new ArrayList<String>();
	
	private List<String> exmeptedRoles = new ArrayList<String>();
	
	private AuthExceptedUserInfo() {
		expemtedUsers.add("admin");
		expemtedUsers.add("daemon");
	}
	
	public List<String> getExpemtedUsers() {
		return expemtedUsers;
	}
	
	public List<String> getExmeptedRoles() {
		return exmeptedRoles;
	}
	
}
