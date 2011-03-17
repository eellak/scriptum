/**
 * 
 */
package gr.scriptum.eprotocol.security;

import gr.scriptum.eprotocol.ws.OkmDispatcherConfig;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestLogout;
import gr.scriptum.eprotocol.ws.Response;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

		// logout from OpenKM
		OkmDispatcherConfig config = new OkmDispatcherConfig();
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
		super.handle(request, response, authentication);
	}

}
