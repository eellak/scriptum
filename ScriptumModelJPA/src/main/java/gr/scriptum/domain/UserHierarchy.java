package gr.scriptum.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import gr.scriptum.domain.Contact;


/**
 * The persistent class for the user_hierarchy database table.
 * 
 */
@Entity
@Table(name="user_hierarchy")
@NamedQuery(name="UserHierarchy.findAll", query="SELECT u FROM UserHierarchy u")
public class UserHierarchy implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Department department;
	private UserHierarchy userHierarchy;
	private Set<UserHierarchy> userHierarchies;
	private Users user;
	private Set<Role> roles;
	private Contact contact;
	public UserHierarchy() {
	}

	public UserHierarchy(Department department, Users user) {
		super();
		this.department = department;
		this.user = user;
	}

	public UserHierarchy(Department department, Contact contact) {
		super();
		this.department = department;
		this.contact = contact;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		UserHierarchy other = (UserHierarchy) obj;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="department_id", nullable=false)
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}


	//bi-directional many-to-one association to UserHierarchy
	@ManyToOne
	@JoinColumn(name="manager_id")
	public UserHierarchy getUserHierarchy() {
		return this.userHierarchy;
	}

	public void setUserHierarchy(UserHierarchy userHierarchy) {
		this.userHierarchy = userHierarchy;
	}


	//bi-directional many-to-one association to UserHierarchy
	@OneToMany(mappedBy="userHierarchy")
	public Set<UserHierarchy> getUserHierarchies() {
		return this.userHierarchies;
	}

	public void setUserHierarchies(Set<UserHierarchy> userHierarchies) {
		this.userHierarchies = userHierarchies;
	}

	public UserHierarchy addUserHierarchy(UserHierarchy userHierarchy) {
		getUserHierarchies().add(userHierarchy);
		userHierarchy.setUserHierarchy(this);

		return userHierarchy;
	}

	public UserHierarchy removeUserHierarchy(UserHierarchy userHierarchy) {
		getUserHierarchies().remove(userHierarchy);
		userHierarchy.setUserHierarchy(null);

		return userHierarchy;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(optional = true)
	@JoinColumn(name="users_id")
	public Users getUser() {
		return this.user;
	}

	public void setUser(Users user) {
		this.user = user;
	}


	@ManyToMany(mappedBy = "userHierarchies")
	public Set<Role> getRoles() {
	    return roles;
	}


	public void setRoles(Set<Role> param) {
	    this.roles = param;
	}

	@ManyToOne
	public Contact getContact() {
	    return contact;
	}

	public void setContact(Contact param) {
	    this.contact = param;
	}

}