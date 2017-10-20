/**
 * 
 */
package gr.scriptum.eprotocol.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.util.Assert;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class ScriptumAuthenticationStrategy extends ConcurrentSessionControlAuthenticationStrategy {

	private final SessionRegistry sessionRegistry;
	
	private Integer maximumUsers;
	
	public ScriptumAuthenticationStrategy(SessionRegistry sessionRegistry) {
		super(sessionRegistry);
		this.sessionRegistry = sessionRegistry;
	}

	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) {
		if(sessionRegistry.getAllPrincipals().size() >= maximumUsers) {
			throw new SessionAuthenticationException("error.login.maxUsers");
		}
		super.onAuthentication(authentication, request, response);
	}
	
	@Override
	protected void allowableSessionsExceeded(List<SessionInformation> sessions, int allowableSessions,
			SessionRegistry registry) throws SessionAuthenticationException {
		//destroy any other sessions for this user
		for (SessionInformation sessionInformation : sessions) {
			sessionInformation.expireNow();
		}
	}
	
	public void setMaximumUsers(Integer maximumUsers) {
		Assert.isTrue(
				maximumUsers != null,
				"MaximumLogins must not be null");
		this.maximumUsers = maximumUsers;
	}

}
