package org.openmrs;

import java.io.Serializable;

public class UserObligation extends BaseOpenmrsMetadata implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2863678262523694163L;
	
	private User user;
	
	private Obligation obligation;
	
	private String value;
	
	@Override
	public Integer getId() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setId(Integer id) {
		throw new UnsupportedOperationException();
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Obligation getObligation() {
		return obligation;
	}
	
	public void setObligation(Obligation obligation) {
		this.obligation = obligation;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
