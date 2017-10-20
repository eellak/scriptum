package gr.scriptum.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Permission")
public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	private Set<Role> roles;
	private Integer value;
	private Set<UserAssignment> userAssignments;


	public Permission() {
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
		Permission other = (Permission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String param) {
		this.description = param;
	}

	@ManyToMany(mappedBy="permissions")
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> param) {
		this.roles = param;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer param) {
		this.value = param;
	}

	@OneToMany(mappedBy = "permission")
	public Set<UserAssignment> getUserAssignments() {
		return userAssignments;
	}

	public void setUserAssignments(Set<UserAssignment> param) {
		this.userAssignments = param;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long param) {
		this.id = param;
	}

}