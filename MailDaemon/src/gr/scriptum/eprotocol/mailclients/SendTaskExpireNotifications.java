package gr.scriptum.eprotocol.mailclients;


import java.util.List;

import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.ProjectTaskDAO;
import gr.scriptum.domain.ProjectTask;

import gr.uit.mthreads.MWork;

public class SendTaskExpireNotifications implements MWork {

	private static Log log = LogFactory
			.getLog(SendTaskExpireNotifications.class);

	private MailDispatcherConfig mailDispatcherConfig;

	private SendTaskExpireNotifications() {
	}

	public SendTaskExpireNotifications(
			MailDispatcherConfig mailDispatcherConfig){
		this();
		this.mailDispatcherConfig = mailDispatcherConfig;
	}

	private static void debug(String s) {
		log.info(s);
	}

	public void initializeWork() {
		debug("initializeWork()" + Thread.currentThread().getId());
	}

	public void performWork() {
		debug("performWork()" + Thread.currentThread().getId());

		UserTransaction tx = null;
		try {

			tx = (UserTransaction) new InitialContext()
					.lookup("UserTransaction"); // Since the thread is created
												// outside of WorkManager, the
												// java:comp/ prefix doesn't
												// always work, hence the use of
												// 'UserTransaction'

			if (tx == null) {
				return;
			}
			tx.begin();
		} catch (Exception e) {
			log.error(e);
			return;
		}

		try {

			// login to OpenKM and get token
			ParameterDAO parameterDAO = new ParameterDAO();
			String serviceEnabled = parameterDAO.getAsString("NOTIFICATION_SERVICE_ENABLED");
			Integer defaultDays = parameterDAO.getAsInteger("NOTIFICATION_SERVICE_DAYS_TO_WARN");
			
			if (serviceEnabled.equalsIgnoreCase("true")) {

				// Get the mail Dispatcher object to grab the mails
				ImapProtocolDispatcherImpl disp = new ImapProtocolDispatcherImpl(
						mailDispatcherConfig);

				ProjectTaskDAO projectTaskDao = new ProjectTaskDAO();
				List<ProjectTask> tasks = projectTaskDao.findTaskAboutToExpire(defaultDays);
				for (ProjectTask task : tasks)
					disp.sendOutgoingTask(task);

			}

			tx.commit();

		} catch (Exception e) {
			log.error(e);
			try {
				tx.rollback();
			} catch (Exception e1) {
				log.error(e1);
			}
		}

	}

	public void finilizeWork() {
		debug("finilizeWork()" + Thread.currentThread().getId());
	}
}
