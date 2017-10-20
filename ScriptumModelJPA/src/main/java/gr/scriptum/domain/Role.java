package gr.scriptum.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name = "role")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String description;
	private Integer isProtocol;
	private String name;
	private Set<Users> users;
	private Integer rank;
	private Boolean active;
	private Set<Permission> permissions;
	private Set<UserHierarchy> userHierarchies;
	private Integer permissionsSum;
	
	public Role() {
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

	@Column(name = "is_protocol")
	public Integer getIsProtocol() {
		return this.isProtocol;
	}

	public void setIsProtocol(Integer isProtocol) {
		this.isProtocol = isProtocol;
	}

	@Column(length = 256)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// bi-directional many-to-many association to User
	@ManyToMany(mappedBy = "roles")
	// @JoinTable(
	// name="user_to_role"
	// , joinColumns={
	// @JoinColumn(name="role_id", nullable=false)
	// }
	// , inverseJoinColumns={
	// @JoinColumn(name="users_id", nullable=false)
	// }
	// )

	public Set<Users> getUsers() {
		return this.users;
	}

	public void setUsers(Set<Users> users) {
		this.users = users;
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
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer param) {
		this.rank = param;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean param) {
		this.active = param;
	}

	@ManyToMany
	// @JoinTable(
	// name="Permission_role"
	// , joinColumns={
	// @JoinColumn(name="roles_id", nullable=false)
	// }
	// , inverseJoinColumns={
	// @JoinColumn(name="permissions_id", nullable=false)
	// }
	// )
	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> param) {
		this.permissions = param;
	}

	@ManyToMany
	public Set<UserHierarchy> getUserHierarchies() {
		return userHierarchies;
	}

	public void setUserHierarchies(Set<UserHierarchy> param) {
		this.userHierarchies = param;
	}

	public Integer getPermissionsSum() {
		return permissionsSum;
	}

	public void setPermissionsSum(Integer param) {
		this.permissionsSum = param;
	}

}