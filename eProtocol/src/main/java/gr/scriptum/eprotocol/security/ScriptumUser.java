/**
 * 
 */
package gr.scriptum.eprotocol.security;

import gr.scriptum.domain.Department;
import gr.scriptum.domain.DepartmentType;
import gr.scriptum.domain.Permission;
import gr.scriptum.domain.Role;
import gr.scriptum.domain.UserAssignment;
import gr.scriptum.domain.Users;
import gr.scriptum.domain.DepartmentType.Name;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author aanagnostopoulos
 * 
 */
public class ScriptumUser implements Serializable, UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -15123100770358327L;

	public static final String ROLE_PREFIX = "ROLE_";

	private Users user = null;
	private String okmToken = null;
	private Department department = null;
	private DepartmentType.Name departmentType;

	public ScriptumUser(Users user, Department department) {
		super();
		this.user = user;
		this.department = department;
		this.departmentType = department == null ? null : Name.valueOf(department.getDepartmentType().getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getAuthorities
	 * ()
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities() {

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

		Set<Permission> permissions = new HashSet<Permission>();
		for (Role role : user.getRoles()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ROLE_PREFIX + role.getName());
			grantedAuthorities.add(grantedAuthority);
			// add all permissions belonging to each role
			permissions.addAll(role.getPermissions());
		}

		// add permissions explicitly assigned to user
		Set<UserAssignment> userAssignments = user.getUserAssignments();
		for (UserAssignment userAssignment : userAssignments) {
			if (userAssignment.getResource() == null) {
				permissions.add(userAssignment.getPermission());
			}
		}

		for (Permission permission : permissions) {
			grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
		}

		return grantedAuthorities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isAccountNonExpired ()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isAccountNonLocked ()
	 */
	@Override
	public boolean isAccountNonLocked() {
		return !user.getIsDisabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return !user.getIsDisabled();
	}

	/*
	 * (non-Javadoc) Required for session concurrency control
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc) Required for session concurrency control
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScriptumUser other = (ScriptumUser) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getOkmToken() {
		return okmToken;
	}

	public void setOkmToken(String okmToken) {
		this.okmToken = okmToken;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public DepartmentType.Name getDepartmentType() {
		return departmentType;
	}

}
