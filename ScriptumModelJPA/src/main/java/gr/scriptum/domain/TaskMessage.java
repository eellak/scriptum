package gr.scriptum.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the task_message database table.
 * 
 */
@Entity
@Table(name="task_message")
@NamedQuery(name="TaskMessage.findAll", query="SELECT t FROM TaskMessage t")
public class TaskMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date createdTs;
	private String message;
	private String subject;
	private ProjectTask projectTask;
	private Users user1;
	private Users user2;

	public TaskMessage() {
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
	@Column(name="created_ts")
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}


	@Column(length=1024)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	@Column(length=256)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	//bi-directional many-to-one association to ProjectTask
	@ManyToOne
	@JoinColumn(name="project_task_id", nullable=false)
	public ProjectTask getProjectTask() {
		return this.projectTask;
	}

	public void setProjectTask(ProjectTask projectTask) {
		this.projectTask = projectTask;
	}


	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_receiver_id", nullable=false)
	public Users getUser1() {
		return this.user1;
	}

	public void setUser1(Users user1) {
		this.user1 = user1;
	}


	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_sender_id", nullable=false)
	public Users getUser2() {
		return this.user2;
	}

	public void setUser2(Users user2) {
		this.user2 = user2;
	}

}