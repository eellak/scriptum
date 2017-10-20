/**
 * 
 */
package gr.scriptum.eprotocol.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.dao.ApplicationLogDAO;
import gr.scriptum.domain.ApplicationLog;
import gr.scriptum.domain.ApplicationLog.Operation;
import gr.scriptum.domain.ApplicationLog.Severity;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.Users;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Service
public class LoggingService {

	@Autowired
	private ApplicationLogDAO applicationLogDAO;

	@Transactional
	public ApplicationLog success(Operation operation, Users user, Protocol protocol) {
		return logOperation(operation, Severity.SUCCESS, user, protocol, null);
	}

	@Transactional
	public ApplicationLog success(Operation operation, Users user, Protocol protocol, String comments) {
		return logOperation(operation, Severity.SUCCESS, user, protocol, comments);
	}

	@Transactional
	public ApplicationLog failure(Operation operation, Users user, Protocol protocol, String comments) {
		return logOperation(operation, Severity.FAILURE, user, protocol, comments);
	}

	@Transactional
	public ApplicationLog logOperation(Operation operation, Severity severity, Users user, Protocol protocol,
			String comments) {
		ApplicationLog applicationLog = new ApplicationLog();
		applicationLog.setOperation(operation);
		applicationLog.setSeverity(severity);
		applicationLog.setUser(user);
		applicationLog.setProtocol(protocol);
		applicationLog.setAppuser(user.getUsername());
		applicationLog.setCreated(new Date());
		applicationLog.setMessage(comments);
		return applicationLogDAO.makePersistent(applicationLog);
	}
}
