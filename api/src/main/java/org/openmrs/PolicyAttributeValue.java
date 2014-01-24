package org.openmrs;

public class PolicyAttributeValue extends BaseOpenmrsMetadata implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7789096590030591394L;
	
	private Integer attributeValueId;
	
	private PolicyAttribute attribute;
	
	private User user;
	
	private Role role;
	
	private String attributeValue;
	
	@Override
	public Integer getId() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setId(Integer id) {
		throw new UnsupportedOperationException();
	}
	
	public Integer getAttributeValueId() {
		return attributeValueId;
	}
	
	public void setAttributeValueId(Integer attributeValueId) {
		this.attributeValueId = attributeValueId;
	}
	
	public PolicyAttribute getAttribute() {
		return attribute;
	}
	
	public void setAttribute(PolicyAttribute attribute) {
		this.attribute = attribute;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getAttributeValue() {
		return attributeValue;
	}
	
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
}
