/**
 * 
 */
package gr.scriptum.eprotocol.security;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.UserHierarchyDAO;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.Role;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.ws.OkmDispatcherConfig;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestLogin;
import gr.scriptum.eprotocol.ws.ResponseLogin;

/**
 * @author aanagnostopoulos
 * 
 */
@Service("scriptumUserDetailsService")
public class ScriptumUserDetailsService implements UserDetailsService {

	private static Log log = LogFactory.getLog(ScriptumUserDetailsService.class);

	@Autowired
	private UsersDAO usersDAO;
	
	@Autowired
	private UserHierarchyDAO userHierarchyDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

			// login against local database
			Users user = new Users();
			user.setUsername(username);
			user.setIsDisabled(null);
			List<Users> users = usersDAO.findByExample(user, new String[] {});

			if(users.isEmpty()) {
				return null;
			}
			
			user = users.get(0);
			
			List<UserHierarchy> userHierarchies = userHierarchyDAO.findByUser(user);
			Department department =  null;
			if (!userHierarchies.isEmpty()) {
				department = userHierarchies.get(0).getDepartment();
			}
			return new ScriptumUser(user, department);

	}
}
