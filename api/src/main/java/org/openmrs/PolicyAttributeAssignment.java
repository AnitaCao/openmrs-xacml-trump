package org.openmrs;

public class PolicyAttributeAssignment extends BaseOpenmrsMetadata implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4379385792751334432L;
	
	private Integer assignmentId;
	
	private PolicyAttribute attribute;
	
	private User user;
	
	private Role role;
	
	@Override
	public Integer getId() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setId(Integer id) {
		throw new UnsupportedOperationException();
	}
	
	public Integer getAssignmentId() {
		return assignmentId;
	}
	
	public void setAssignmentId(Integer assignmentId) {
		this.assignmentId = assignmentId;
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
	
}
