package gr.scriptum.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Set;
import javax.persistence.OneToMany;

@Entity
@Table(name = "DepartmentType")
public class DepartmentType implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Name{
		CENTRAL, REGIONAL, BRANCH, CENTRAL_PROTOCOL, UNIT_DEPARTMENT;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String code;
	private String description;

	@OneToMany(mappedBy = "departmentType")
	private Set<Department> departments;

	public DepartmentType() {
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
		DepartmentType other = (DepartmentType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer param) {
		this.id = param;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String param) {
		this.code = param;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String param) {
		this.description = param;
	}

	public Set<Department> getDepartments() {
	    return departments;
	}

	public void setDepartments(Set<Department> param) {
	    this.departments = param;
	}

}