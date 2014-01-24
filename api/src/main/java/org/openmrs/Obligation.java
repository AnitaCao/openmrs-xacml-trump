package org.openmrs;

public class Obligation extends BaseOpenmrsMetadata implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3598656938500285788L;
	
	private Integer obligationId;
	
	private String obligationName;
	
	private String contextName;
	
	private String description;
	
	private String role;
	
	private String user;
	
	private String value;
	
	@Override
	public Integer getId() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setId(Integer id) {
		throw new UnsupportedOperationException();
	}
	
	public Integer getObligationId() {
		return obligationId;
	}
	
	public void setObligationId(Integer obligationId) {
		this.obligationId = obligationId;
	}
	
	public String getObligationName() {
		return obligationName;
	}
	
	public void setObligationName(String obligationName) {
		this.obligationName = obligationName;
	}
	
	public String getContextName() {
		return contextName;
	}
	
	public void setContextName(String contextName) {
		this.contextName = contextName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
}
