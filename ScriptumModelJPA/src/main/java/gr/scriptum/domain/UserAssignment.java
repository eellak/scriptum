package gr.scriptum.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserAssignment")
public class UserAssignment implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;
	private Boolean allow;
	private Boolean deny;
	private Integer rank;
	@ManyToOne(optional=false)
	private Users user;
	@ManyToOne
	private Resource resource;
	@ManyToOne
	private Permission permission;
	
	public UserAssignment() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((permission == null) ? 0 : permission.hashCode());
		result = prime * result + ((resource == null) ? 0 : resource.hashCode());
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
		UserAssignment other = (UserAssignment) obj;
		if (permission == null) {
			if (other.permission != null)
				return false;
		} else if (!permission.equals(other.permission))
			return false;
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer param) {
		this.id = param;
	}

	public Boolean getAllow() {
		return allow;
	}

	public void setAllow(Boolean param) {
		this.allow = param;
	}

	public Boolean getDeny() {
		return deny;
	}

	public void setDeny(Boolean param) {
		this.deny = param;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer param) {
		this.rank = param;
	}

	public Users getUser() {
	    return user;
	}

	public void setUser(Users param) {
	    this.user = param;
	}

	public Resource getResource() {
	    return resource;
	}

	public void setResource(Resource param) {
	    this.resource = param;
	}

	public Permission getPermission() {
	    return permission;
	}

	public void setPermission(Permission param) {
	    this.permission = param;
	}

}