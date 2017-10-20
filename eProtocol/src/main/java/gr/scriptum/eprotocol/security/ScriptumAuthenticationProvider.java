/**
 * 
 */
package gr.scriptum.eprotocol.security;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.zkoss.zkplus.spring.SpringUtil;

import gr.scriptum.dao.PermissionDAO;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.eprotocol.service.ProtocolBookService;
import gr.scriptum.eprotocol.util.Permission;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Component
public class ScriptumAuthenticationProvider implements AuthenticationProvider {

	private static Log log = LogFactory.getLog(ScriptumAuthenticationProvider.class);
	
	@Autowired
	private UserDetailsService  scriptumUserDetailsService;
	
	@Autowired
	private ProtocolBookService protocolBookService;
	
	@Autowired
	private PermissionDAO permissionDAO;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        ScriptumUser user = (ScriptumUser) scriptumUserDetailsService.loadUserByUsername(username);

        if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
            throw new BadCredentialsException("error.login.badCredentials");
        }
 
        if (!password.equals(user.getPassword())) {
        	throw new BadCredentialsException("error.login.badCredentials");
        }
        
        if(user.getUser().getIsDisabled()) {
        	throw new BadCredentialsException("error.login.userDisabled");
        }
        
        if(user.getDepartment()==null) {
        	throw new BadCredentialsException("error.login.invalidUserDepartment");
        }

        gr.scriptum.domain.Permission searchPermission = new gr.scriptum.domain.Permission();
		searchPermission.setName(Permission.SEARCH_BOOK.toString());
		searchPermission = permissionDAO.findByExample(searchPermission).get(0);

        List<ProtocolBook> assignedBooks = protocolBookService.findByAssignedUser(user.getUser(), searchPermission);
        if(assignedBooks.isEmpty()) {
        	throw new BadCredentialsException("error.login.noSearchBooksAssignedToUser");
        }
 
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
 
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
        
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
