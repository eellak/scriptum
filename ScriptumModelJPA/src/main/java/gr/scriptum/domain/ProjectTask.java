package gr.scriptum.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the project_task database table.
 * 
 */
@Entity
@Table(name="project_task")
@NamedQuery(name="ProjectTask.findAll", query="SELECT p FROM ProjectTask p")
public class ProjectTask implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date closedDt;
	private String comments;
	private Date createDt;
	private String description;
	private Boolean dispatcherCloseable;
	private Date endDt;
	private Date expectedDt;
	private String name;
	private Integer reminderDays;
	private Integer reviewerScore;
	private Date startDt;
	private Integer taskNo;
	private Integer timesContactExternal;
	private Integer timesRevised;
	private Date updateTs;
	private Contact contact;
	private Project project;
	private ProjectTask projectTask;
	private Set<ProjectTask> projectTasks;
	private TaskPriority taskPriority;
	private TaskResult taskResult;
	private TaskState taskState;
	private TaskType taskType;
	private Users user1;
	private Users user2;
	private Set<TaskDocument> taskDocuments;
	private Set<TaskMessage> taskMessages;

	public ProjectTask() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="closed_dt")
	public Date getClosedDt() {
		return this.closedDt;
	}

	public void setClosedDt(Date closedDt) {
		this.closedDt = closedDt;
	}


	@Column(length=512)
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_dt")
	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}


	@Column(length=512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(name="dispatcher_closeable")
	public Boolean getDispatcherCloseable() {
		return this.dispatcherCloseable;
	}

	public void setDispatcherCloseable(Boolean dispatcherCloseable) {
		this.dispatcherCloseable = dispatcherCloseable;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_dt")
	public Date getEndDt() {
		return this.endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expected_dt")
	public Date getExpectedDt() {
		return this.expectedDt;
	}

	public void setExpectedDt(Date expectedDt) {
		this.expectedDt = expectedDt;
	}


	@Column(length=256)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(name="reminder_days")
	public Integer getReminderDays() {
		return this.reminderDays;
	}

	public void setReminderDays(Integer reminderDays) {
		this.reminderDays = reminderDays;
	}


	@Column(name="reviewer_score")
	public Integer getReviewerScore() {
		return this.reviewerScore;
	}

	public void setReviewerScore(Integer reviewerScore) {
		this.reviewerScore = reviewerScore;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_dt")
	public Date getStartDt() {
		return this.startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}


	@Column(name="task_no")
	public Integer getTaskNo() {
		return this.taskNo;
	}

	public void setTaskNo(Integer taskNo) {
		this.taskNo = taskNo;
	}


	@Column(name="times_contact_external")
	public Integer getTimesContactExternal() {
		return this.timesContactExternal;
	}

	public void setTimesContactExternal(Integer timesContactExternal) {
		this.timesContactExternal = timesContactExternal;
	}


	@Column(name="times_revised")
	public Integer getTimesRevised() {
		return this.timesRevised;
	}

	public void setTimesRevised(Integer timesRevised) {
		this.timesRevised = timesRevised;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_ts")
	public Date getUpdateTs() {
		return this.updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}


	//bi-directional many-to-one association to Contact
	@ManyToOne
	@JoinColumn(name="contact_id")
	public Contact getContact() {
		return this.contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}


	//bi-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="project_id")
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}


	//bi-directional many-to-one association to ProjectTask
	@ManyToOne
	@JoinColumn(name="parent_task_id")
	public ProjectTask getProjectTask() {
		return this.projectTask;
	}

	public void setProjectTask(ProjectTask projectTask) {
		this.projectTask = projectTask;
	}


	//bi-directional many-to-one association to ProjectTask
	@OneToMany(mappedBy="projectTask")
	public Set<ProjectTask> getProjectTasks() {
		return this.projectTasks;
	}

	public void setProjectTasks(Set<ProjectTask> projectTasks) {
		this.projectTasks = projectTasks;
	}

	public ProjectTask addProjectTask(ProjectTask projectTask) {
		getProjectTasks().add(projectTask);
		projectTask.setProjectTask(this);

		return projectTask;
	}

	public ProjectTask removeProjectTask(ProjectTask projectTask) {
		getProjectTasks().remove(projectTask);
		projectTask.setProjectTask(null);

		return projectTask;
	}


	//bi-directional many-to-one association to TaskPriority
	@ManyToOne
	@JoinColumn(name="task_priority_id", nullable=false)
	public TaskPriority getTaskPriority() {
		return this.taskPriority;
	}

	public void setTaskPriority(TaskPriority taskPriority) {
		this.taskPriority = taskPriority;
	}


	//bi-directional many-to-one association to TaskResult
	@ManyToOne
	@JoinColumn(name="task_result_id")
	public TaskResult getTaskResult() {
		return this.taskResult;
	}

	public void setTaskResult(TaskResult taskResult) {
		this.taskResult = taskResult;
	}


	//bi-directional many-to-one association to TaskState
	@ManyToOne
	@JoinColumn(name="task_state_id", nullable=false)
	public TaskState getTaskState() {
		return this.taskState;
	}

	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}


	//bi-directional many-to-one association to TaskType
	@ManyToOne
	@JoinColumn(name="task_type_id", nullable=false)
	public TaskType getTaskType() {
		return this.taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}


	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_dispatcher_id", nullable=false)
	public Users getUser1() {
		return this.user1;
	}

	public void setUser1(Users user1) {
		this.user1 = user1;
	}


	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_creator_id", nullable=false)
	public Users getUser2() {
		return this.user2;
	}

	public void setUser2(Users user2) {
		this.user2 = user2;
	}


	//bi-directional many-to-one association to TaskDocument
	@OneToMany(mappedBy="projectTask")
	public Set<TaskDocument> getTaskDocuments() {
		return this.taskDocuments;
	}

	public void setTaskDocuments(Set<TaskDocument> taskDocuments) {
		this.taskDocuments = taskDocuments;
	}

	public TaskDocument addTaskDocument(TaskDocument taskDocument) {
		getTaskDocuments().add(taskDocument);
		taskDocument.setProjectTask(this);

		return taskDocument;
	}

	public TaskDocument removeTaskDocument(TaskDocument taskDocument) {
		getTaskDocuments().remove(taskDocument);
		taskDocument.setProjectTask(null);

		return taskDocument;
	}


	//bi-directional many-to-one association to TaskMessage
	@OneToMany(mappedBy="projectTask")
	public Set<TaskMessage> getTaskMessages() {
		return this.taskMessages;
	}

	public void setTaskMessages(Set<TaskMessage> taskMessages) {
		this.taskMessages = taskMessages;
	}

	public TaskMessage addTaskMessage(TaskMessage taskMessage) {
		getTaskMessages().add(taskMessage);
		taskMessage.setProjectTask(this);

		return taskMessage;
	}

	public TaskMessage removeTaskMessage(TaskMessage taskMessage) {
		getTaskMessages().remove(taskMessage);
		taskMessage.setProjectTask(null);

		return taskMessage;
	}

}