package gr.scriptum.domain;

// Generated Oct 5, 2011 7:41:16 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Department generated by hbm2java
 */
public class Department implements java.io.Serializable {

	private Integer id;
	private Department department;
	private String name;
	private String description;
	private Set<UserHierarchy> userHierarchies = new HashSet<UserHierarchy>(0);
	private Set<Department> departments = new HashSet<Department>(0);

	public Department() {
	}

	public Department(String name) {
		this.name = name;
	}

	public Department(Department department, String name, String description,
			Set<UserHierarchy> userHierarchies, Set<Department> departments) {
		this.department = department;
		this.name = name;
		this.description = description;
		this.userHierarchies = userHierarchies;
		this.departments = departments;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<UserHierarchy> getUserHierarchies() {
		return this.userHierarchies;
	}

	public void setUserHierarchies(Set<UserHierarchy> userHierarchies) {
		this.userHierarchies = userHierarchies;
	}

	public Set<Department> getDepartments() {
		return this.departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

}
