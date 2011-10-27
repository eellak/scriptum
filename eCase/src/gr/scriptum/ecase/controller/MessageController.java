/**
 * 
 */
package gr.scriptum.ecase.controller;

import gr.scriptum.controller.GenericEntityController;
import gr.scriptum.dao.ProjectTaskDAO;
import gr.scriptum.dao.TaskMessageDAO;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.ProjectTask;
import gr.scriptum.domain.TaskMessage;
import gr.scriptum.domain.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

/**
 * @author aanagnostopoulos
 * 
 */
public class MessageController extends
		GenericEntityController<TaskMessage, TaskMessageDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -237189705044033643L;

	private static Log log = LogFactory.getLog(MessageController.class);

	public static final String PARAM_KEY_TASK = "t";

	public static final String PARAM_KEY_RECIPIENT = "r";

	public static final String PAGE = "message.zul";

	/* components */
	Paging recipientsPgng;
	Bandbox recipientBndbx;

	/* data binding */
	private List<ProjectTask> projectTasks = null;
	private List<Users> recipients = null;
	private String term = null;

	private void initTaskMessage() throws InterruptedException {

		projectTasks = new ArrayList<ProjectTask>();
		recipients = new ArrayList<Users>();

		// Fetch related task, if any
		String taskIdString = execution.getParameter(PARAM_KEY_TASK);
		if (taskIdString != null) {

			Integer taskId = null;
			try {
				taskId = taskIdString != null ? Integer.valueOf(taskIdString)
						: null;
			} catch (NumberFormatException e) {
				log.error(e);
			}

			if (taskId == null) {
				Messagebox.show(Labels.getLabel("fetch.notFound"),
						Labels.getLabel("fetch.title"), Messagebox.OK,
						Messagebox.ERROR);
				entity = null;
				return;
			}

			ProjectTaskDAO projectTaskDAO = new ProjectTaskDAO();
			ProjectTask projectTask = projectTaskDAO.findById(taskId, false);

			if (projectTask == null) {
				Messagebox.show(Labels.getLabel("fetch.notFound"),
						Labels.getLabel("fetch.title"), Messagebox.OK,
						Messagebox.ERROR);
				entity = null;
				return;

			}

			// check if currently logged-on user is allowed to send messages
			// related to this task
			Integer dispatcherId = projectTask.getUsersByUserDispatcherId()
					.getId();
			Integer creatorId = projectTask.getUsersByUserCreatorId().getId();
			Integer userId = getUserInSession().getId();

			if (userId.intValue() != dispatcherId.intValue()
					&& userId.intValue() != creatorId.intValue()) {
				Messagebox.show(Labels.getLabel("error.notAllowed"),
						Labels.getLabel("error.title"), Messagebox.OK,
						Messagebox.ERROR);
				entity = null;
				return;
			}

			((TaskMessage) entity).setProjectTask(projectTask);
			projectTasks.add(projectTask);
			((TaskMessage) entity).setUsersByUserSenderId(getUserInSession());
		}

		// fetch recipient, if any
		String recipientIdString = execution.getParameter(PARAM_KEY_RECIPIENT);
		if (recipientIdString != null) {
			Integer recipientId = null;
			try {
				recipientId = Integer.valueOf(recipientIdString);
			} catch (NumberFormatException e) {
				log.error(e);
			}

			if (recipientId == null) {
				Messagebox.show(Labels.getLabel("fetch.notFound"),
						Labels.getLabel("fetch.title"), Messagebox.OK,
						Messagebox.ERROR);
				entity = null;
				return;
			}

			UsersDAO usersDAO = new UsersDAO();
			Users recipient = usersDAO.findById(recipientId, false);

			if (recipient == null) {
				Messagebox.show(Labels.getLabel("fetch.notFound"),
						Labels.getLabel("fetch.title"), Messagebox.OK,
						Messagebox.ERROR);
				entity = null;
				return;
			}

			((TaskMessage) entity).setUsersByUserReceiverId(recipient);
			recipients.add(recipient);
		}
	}

	private void searchRecipients(Integer startIndex) {
		UsersDAO usersDAO = new UsersDAO();

		// set up paging by counting records first
		Integer totalSize = usersDAO.countByTerm(term);
		recipientsPgng.setTotalSize(totalSize);
		int pageSize = recipientsPgng.getPageSize();

		recipients = usersDAO.findByTerm(term, startIndex, pageSize);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initTaskMessage();
	}

	public void onChanging$recipientBndbx(InputEvent event) {
		term = StringUtils.trimToNull(event.getValue());

		searchRecipients(0);
		getBinder(win).loadAll();
	}

	public void onOpen$recipientBndbx(OpenEvent event) {
		if (!recipients.isEmpty()) {
			return;
		}
		term = "";
		searchRecipients(0);

		getBinder(win).loadAll();
	}

	public void onPaging$recipientsPgng(PagingEvent event) {
		int activePage = event.getActivePage();
		int startIndex = activePage * recipientsPgng.getPageSize();
		searchRecipients(startIndex);
		getBinder(win).loadAll();
	}

	public void onSelect$recipientsLstbx(SelectEvent event) {
		// populateContactBndbx();
		recipientBndbx.close();
		getBinder(win).loadAll();
	}

	@Override
	public void onClick$saveBtn() throws Exception {
		if (entity != null) {
			((TaskMessage) entity).setCreatedTs(new Date());
		}
		super.onClick$saveBtn();
	}

	public void onClick$replyBtn() {

		Executions.getCurrent().sendRedirect(
				PAGE
						+ "?"
						+ PARAM_KEY_TASK
						+ "="
						+ ((TaskMessage) entity).getProjectTask().getId()
						+ "&"
						+ PARAM_KEY_RECIPIENT
						+ "="
						+ ((TaskMessage) entity).getUsersByUserSenderId()
								.getId());

	}

	public void onClick$cancelBtn() {
		Clients.evalJavaScript("window.history.back()");
	}

	public String getRecipientFullName() {

		Users recipient = ((TaskMessage) entity).getUsersByUserReceiverId();
		if (recipient == null) {
			return "";
		}
		return (recipient.getName() != null ? recipient.getName() : "")
				+ " "
				+ (recipient.getSurname() != null ? recipient.getSurname() : "");
	}

	public String getTaskFullName() {

		ProjectTask projectTask = ((TaskMessage) entity).getProjectTask();
		if (projectTask == null) {
			return "";
		}
		return (projectTask.getId() != null ? projectTask.getId() : "") + " - "
				+ (projectTask.getName() != null ? projectTask.getName() : "");
	}

	public List<ProjectTask> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(List<ProjectTask> projectTasks) {
		this.projectTasks = projectTasks;
	}

	public List<Users> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<Users> recipients) {
		this.recipients = recipients;
	}
}
