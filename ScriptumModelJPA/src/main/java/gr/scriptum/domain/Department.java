package gr.scriptum.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


/**
 * The persistent class for the department database table.
 * 
 */
@Entity
@Table(name="department")
@NamedQuery(name="Department.findAll", query="SELECT d FROM Department d")
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Integer CAN_ASSIGN_ANYWHERE_TRUE = 1;
	
	public static final Integer CAN_ASSIGN_ANYWHERE_FALSE = 0;
	
	private Integer id;
	private String code;
	private Integer canAssignAnywhere;
	private String description;
	private String name;
	private Department department;
	private Set<Department> departments = new HashSet<Department>();
	private Set<UserHierarchy> userHierarchies;
	private Set<ProtocolCorrespondent> protocolCorrespondents;

	private DepartmentType departmentType;

	public Department() {
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
		Department other = (Department) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="can_assign_anywhere")
	public Integer getCanAssignAnywhere() {
		return this.canAssignAnywhere;
	}

	public void setCanAssignAnywhere(Integer canAssignAnywhere) {
		this.canAssignAnywhere = canAssignAnywhere;
	}


	@Column(length=512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(nullable=true, length=512)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="parent_department_id")
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}


	//bi-directional many-to-one association to Department
	@OneToMany(mappedBy="department")
	@OrderBy("id")
	public Set<Department> getDepartments() {
		return this.departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

	public Department addDepartment(Department department) {
		getDepartments().add(department);
		department.setDepartment(this);

		return department;
	}

	public Department removeDepartment(Department department) {
		getDepartments().remove(department);
		department.setDepartment(null);

		return department;
	}


	//bi-directional many-to-one association to UserHierarchy
	@OneToMany(mappedBy="department")
	@OrderBy("id")
	public Set<UserHierarchy> getUserHierarchies() {
		return this.userHierarchies;
	}

	public void setUserHierarchies(Set<UserHierarchy> userHierarchies) {
		this.userHierarchies = userHierarchies;
	}

	public UserHierarchy addUserHierarchy(UserHierarchy userHierarchy) {
		getUserHierarchies().add(userHierarchy);
		userHierarchy.setDepartment(this);

		return userHierarchy;
	}

	public UserHierarchy removeUserHierarchy(UserHierarchy userHierarchy) {
		getUserHierarchies().remove(userHierarchy);
		userHierarchy.setDepartment(null);

		return userHierarchy;
	}


	@OneToMany(mappedBy = "department")
	public Set<ProtocolCorrespondent> getProtocolCorrespondents() {
	    return protocolCorrespondents;
	}


	public void setProtocolCorrespondents(Set<ProtocolCorrespondent> param) {
	    this.protocolCorrespondents = param;
	}

	@ManyToOne(optional=false)
	public DepartmentType getDepartmentType() {
	    return departmentType;
	}

	public void setDepartmentType(DepartmentType param) {
	    this.departmentType = param;
	}

}