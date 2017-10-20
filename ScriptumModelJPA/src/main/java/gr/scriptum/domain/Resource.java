package gr.scriptum.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import gr.scriptum.domain.Resource.ResourceType;

/**
 * The persistent class for the resource database table.
 * 
 */
@Entity
@Table(name = "resource")
@NamedQuery(name = "Resource.findAll", query = "SELECT r FROM Resource r")
public class Resource implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String description;
	private String resource;
	private String resourceClass;
	private String action;
	private String keyCode;
	private String value;
	private Boolean active;
	private ResourceType type;
	private Integer rank;
	private Set<UserAssignment> userAssignments;

	public enum ResourceType{
		ENTITY;
	}
	
	public Resource() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resource other = (Resource) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length = 512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(length = 256)
	public String getResource() {
		return this.resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getResourceClass() {
		return resourceClass;
	}

	public void setResourceClass(String param) {
		this.resourceClass = param;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String param) {
		this.action = param;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String param) {
		this.keyCode = param;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String param) {
		this.value = param;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean param) {
		this.active = param;
	}

	@Enumerated(EnumType.STRING)
	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer param) {
		this.rank = param;
	}

	@OneToMany(mappedBy = "resource")
	public Set<UserAssignment> getUserAssignments() {
	    return userAssignments;
	}

	public void setUserAssignments(Set<UserAssignment> param) {
	    this.userAssignments = param;
	}

}