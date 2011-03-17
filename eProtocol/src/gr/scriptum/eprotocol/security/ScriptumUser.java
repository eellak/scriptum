/**
 * 
 */
package gr.scriptum.eprotocol.security;

import gr.scriptum.domain.Role;
import gr.scriptum.domain.Users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
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

	
	public ScriptumUser(Users user, String okmToken) {
		super();
		this.user = user;
		this.okmToken = okmToken;
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

		Role role = user.getRoles().toArray(new Role[0])[0];

		GrantedAuthority grantedAuthority = new GrantedAuthorityImpl(
				ROLE_PREFIX + role.getName());
		grantedAuthorities.add(grantedAuthority);

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
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired
	 * ()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked
	 * ()
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

}
