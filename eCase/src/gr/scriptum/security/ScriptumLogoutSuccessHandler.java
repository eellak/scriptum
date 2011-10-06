/**
 * 
 */
package gr.scriptum.security;

import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.ecase.util.IConstants;
import gr.scriptum.eprotocol.ws.OkmDispatcherConfig;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestLogout;
import gr.scriptum.eprotocol.ws.Response;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author aanagnostopoulos
 * 
 */
public class ScriptumLogoutSuccessHandler extends
		AbstractAuthenticationTargetUrlRequestHandler implements
		LogoutSuccessHandler {

	private static Log log = LogFactory
			.getLog(ScriptumLogoutSuccessHandler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.web.authentication.logout.LogoutSuccessHandler
	 * #onLogoutSuccess(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
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

		// logout from OpenKM
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
		ScriptumUser scriptumUser = (ScriptumUser) authentication
				.getPrincipal();
		RequestLogout requestLogout = new RequestLogout(
				scriptumUser.getOkmToken());

		Response logoutResponse = service.logout(requestLogout);
		if (logoutResponse.isError()) {
			log.error("OpenKM logout failed for username:"
					+ scriptumUser.getUsername() + ", token:"
					+ scriptumUser.getOkmToken());
			log.error(response.toString());
		}
		
		tx.commit();
		log.info("Commited JTA transaction:" + tx);
		
		} catch (Exception e) {
			log.error(e);
			try {
				tx.rollback();
				log.info("Rolled back JTA transaction:" + tx);
			} catch (Exception e1) {
				log.error(e1);
				throw new ServletException(e1);
			}
		}
		super.handle(request, response, authentication);
	}

}
