/**
 * 
 */
package gr.scriptum.eprotocol.security;

import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Role;
import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.ws.OkmDispatcherConfig;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestLogin;
import gr.scriptum.eprotocol.ws.ResponseLogin;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author aanagnostopoulos
 * 
 */
public class ScriptumUserDetailsService implements UserDetailsService {

	private static Log log = LogFactory
			.getLog(ScriptumUserDetailsService.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		UserTransaction tx = null;
		try {
			tx = (UserTransaction) new InitialContext()
					.lookup("java:comp/UserTransaction");
			log.info("Looked up JTA transaction:" + tx);

		} catch (NamingException e) {
			log.error(e);
			throw new AuthenticationServiceException(
					"Error looking up JTA transaction", e);
		}

		try {
			tx.begin();
			log.info("Began JTA transaction:" + tx);

			// login against local database
			Users user = new Users();
			user.setUsername(username);
			UsersDAO usersDAO = new UsersDAO();

			List<Users> users = usersDAO.findByExample(user, new String[] {});
			if (users.isEmpty()) {
				throw new UsernameNotFoundException(username);
			}
			if (users.size() > 1) {
				throw new AuthenticationServiceException(
						"Multiple users found for username:" + username);
			}

			user = users.get(0);
			Role role = user.getRoles().toArray(new Role[0])[0]; // force
																	// hibernate
																	// to fetch
																	// roles
			// login to OpenKM and get token
			ParameterDAO parameterDAO = new ParameterDAO();
			String okmAuthPortAddress = parameterDAO
					.getAsString(IConstants.PARAM_OKM_AUTH_PORT_ADDRESS);
			String okmDocumentPortAddress = parameterDAO
					.getAsString(IConstants.PARAM_OKM_DOCUMENT_PORT_ADDRESS);
			String okmFolderPortAddress = parameterDAO
					.getAsString(IConstants.PARAM_OKM_FOLDER_PORT_ADDRESS);
			String okmSearchPortAddress = parameterDAO
					.getAsString(IConstants.PARAM_OKM_SEARCH_PORT_ADDRESS);

			OkmDispatcherConfig config = new OkmDispatcherConfig(
					okmAuthPortAddress, okmDocumentPortAddress,
					okmFolderPortAddress, okmSearchPortAddress);

			OkmProtocolDispatcherImpl service = new OkmProtocolDispatcherImpl(
					config);

			RequestLogin loginReq = new RequestLogin();
			loginReq.setUsername(user.getUsername());
			loginReq.setPassword(user.getPassword());

			ResponseLogin respLogin = service.login(loginReq);
			if (respLogin.isError()) {
				throw new BadCredentialsException(
						"OKM authentication error for username:" + username);
			}
			log.info("OpenKM login OK for username:" + username + ", token:"
					+ respLogin.getOkmToken());

			tx.commit();
			log.info("Commited JTA transaction:" + tx);

			/*
			 * A 'weird' situation might arise at this point, since Spring
			 * doesn't provide the password to this method, allowing successful
			 * login to OpenKM, while the local database authentication will
			 * fail. No problems will arise, however, because Spring
			 * authentication will immediately fail upon return from this
			 * method.
			 */
			return new ScriptumUser(user, respLogin.getOkmToken());

		} catch (Exception e) {
			log.error(e);
			try {
				tx.rollback();
				log.info("Rolled back JTA transaction:" + tx);
			} catch (Exception e1) {
				log.error(e1);
				throw new AuthenticationServiceException(
						"Error Rolling back JTA transaction", e1);
			}
			if (e instanceof AuthenticationException) {
				throw (AuthenticationException) e;
			} else {
				throw new AuthenticationServiceException(
						"Error during JTA transaction", e);
			}
		}

	}

}
