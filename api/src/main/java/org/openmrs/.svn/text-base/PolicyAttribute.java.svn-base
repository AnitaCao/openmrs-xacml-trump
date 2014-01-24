package org.openmrs;

public class PolicyAttribute extends BaseOpenmrsMetadata implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3095616973192950202L;
	
	private Integer attributeId;
	
	private String attributeName;
	
	private Integer attributeType;
	
	private String dataType;
	
	private String ownerType;
	
	private Integer changeStratergy;
	
	private String user;
	
	private String role;
	
	private String initialValue;
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public Integer getId() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setId(Integer id) {
		throw new UnsupportedOperationException();
	}
	
	public Integer getAttributeId() {
		return attributeId;
	}
	
	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}
	
	public String getAttributeName() {
		return attributeName;
	}
	
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public Integer getAttributeType() {
		return attributeType;
	}
	
	public void setAttributeType(Integer attributeType) {
		this.attributeType = attributeType;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getOwnerType() {
		return ownerType;
	}
	
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	
	public Integer getChangeStratergy() {
		return changeStratergy;
	}
	
	public void setChangeStratergy(Integer changeStratergy) {
		this.changeStratergy = changeStratergy;
	}
	
	public String getInitialValue() {
		return initialValue;
	}
	
	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attributeName == null) ? 0 : attributeName.hashCode());
		result = prime * result + ((attributeType == null) ? 0 : attributeType.hashCode());
		result = prime * result + ((changeStratergy == null) ? 0 : changeStratergy.hashCode());
		result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + ((ownerType == null) ? 0 : ownerType.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PolicyAttribute other = (PolicyAttribute) obj;
		if (attributeName == null) {
			if (other.attributeName != null)
				return false;
		} else if (!attributeName.equals(other.attributeName))
			return false;
		if (attributeType == null) {
			if (other.attributeType != null)
				return false;
		} else if (!attributeType.equals(other.attributeType))
			return false;
		if (changeStratergy == null) {
			if (other.changeStratergy != null)
				return false;
		} else if (!changeStratergy.equals(other.changeStratergy))
			return false;
		if (dataType == null) {
			if (other.dataType != null)
				return false;
		} else if (!dataType.equals(other.dataType))
			return false;
		if (ownerType == null) {
			if (other.ownerType != null)
				return false;
		} else if (!ownerType.equals(other.ownerType))
			return false;
		return true;
	}
	
}
