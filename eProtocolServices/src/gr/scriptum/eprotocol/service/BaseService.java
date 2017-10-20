/**
 * 
 */
package gr.scriptum.eprotocol.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.domain.Parameter;
import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.ws.OkmDispatcherConfig;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestLogin;
import gr.scriptum.eprotocol.ws.RequestLogout;
import gr.scriptum.eprotocol.ws.Response;
import gr.scriptum.eprotocol.ws.ResponseLogin;
import gr.scriptum.exception.OpenKMException;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public abstract class BaseService {

	private static Log log = LogFactory.getLog(BaseService.class);
	
	@Autowired
	protected ParameterDAO parameterDAO;
	
	@Transactional(readOnly = true)
	public OkmProtocolDispatcherImpl getOkmDispatcher() {

		String okmAuthPortAddress = parameterDAO.getAsString(IConstants.PARAM_OKM_AUTH_PORT_ADDRESS);
		String okmDocumentPortAddress = parameterDAO.getAsString(IConstants.PARAM_OKM_DOCUMENT_PORT_ADDRESS);
		String okmFolderPortAddress = parameterDAO.getAsString(IConstants.PARAM_OKM_FOLDER_PORT_ADDRESS);
		String okmSearchPortAddress = parameterDAO.getAsString(IConstants.PARAM_OKM_SEARCH_PORT_ADDRESS);
		OkmDispatcherConfig config = new OkmDispatcherConfig(okmAuthPortAddress, okmDocumentPortAddress,
				okmFolderPortAddress, okmSearchPortAddress);
		
		return new OkmProtocolDispatcherImpl(config);
	}

	@Deprecated
	public String getOkmToken(Users user) throws OpenKMException {
		OkmProtocolDispatcherImpl service = getOkmDispatcher();

		RequestLogin loginReq = new RequestLogin();
		loginReq.setUsername(user.getUsername());
		loginReq.setPassword(user.getPassword());

		ResponseLogin respLogin = service.login(loginReq);
		if (respLogin.isError()) {
			log.error("OKM authentication error for username:" + user.getUsername());
			throw new OpenKMException("OKM authentication error for username:" + user.getUsername());
		}
		log.info("OpenKM login OK for username:" + user.getUsername() + ", token:" + respLogin.getOkmToken());

		return respLogin.getOkmToken();
	}

	public String getOkmToken() throws OpenKMException {
		Parameter okmUsernameParameter = parameterDAO.findByName(IConstants.PARAM_OKM_USERNAME);
		Parameter okmPasswordParameter = parameterDAO.findByName(IConstants.PARAM_OKM_PASSWORD);
		
		OkmProtocolDispatcherImpl service = getOkmDispatcher();

		RequestLogin loginReq = new RequestLogin();
		loginReq.setUsername(okmUsernameParameter.getValue());
		loginReq.setPassword(okmPasswordParameter.getValue());

		ResponseLogin respLogin = service.login(loginReq);
		if (respLogin.isError()) {
			log.error("OKM authentication error for username:" + okmUsernameParameter.getValue());
			throw new OpenKMException("OKM authentication error for username:" + okmUsernameParameter.getValue());
		}
		log.info("OpenKM login OK for username:" + okmUsernameParameter.getValue() + ", token:" + respLogin.getOkmToken());

		return respLogin.getOkmToken();
	}

	public void clearOkmToken(Users user, String okmToken) throws OpenKMException {
		// logout from OpenKM
		OkmProtocolDispatcherImpl service = getOkmDispatcher();
		RequestLogout requestLogout = new RequestLogout(okmToken);

		Response logoutResponse = service.logout(requestLogout);
		if (logoutResponse.isError()) {
			log.error("OpenKM logout failed for username:" + user.getUsername() + ", token:" + okmToken);
			log.error(logoutResponse.toString());
			throw new OpenKMException(
					"OpenKM logout failed for username:" + user.getUsername() + ", token:" + okmToken);
		}
		log.info("OpenKM logout OK for username:" + user.getUsername() + ", token:" + okmToken);
	}
}
