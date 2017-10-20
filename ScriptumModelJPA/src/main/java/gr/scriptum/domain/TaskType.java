package gr.scriptum.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the task_type database table.
 * 
 */
@Entity
@Table(name="task_type")
@NamedQuery(name="TaskType.findAll", query="SELECT t FROM TaskType t")
public class TaskType implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date createDt;
	private String createUser;
	private Integer dayPeriod;
	private String description;
	private Integer isPeriodic;
	private String name;
	private Date updateTs;
	private String updateUser;
	private Set<ProjectTask> projectTasks;

	public TaskType() {
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
	@Column(name="create_dt")
	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}


	@Column(name="create_user", length=16)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	@Column(name="day_period")
	public Integer getDayPeriod() {
		return this.dayPeriod;
	}

	public void setDayPeriod(Integer dayPeriod) {
		this.dayPeriod = dayPeriod;
	}


	@Column(length=512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(name="is_periodic")
	public Integer getIsPeriodic() {
		return this.isPeriodic;
	}

	public void setIsPeriodic(Integer isPeriodic) {
		this.isPeriodic = isPeriodic;
	}


	@Column(length=32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_ts")
	public Date getUpdateTs() {
		return this.updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}


	@Column(name="update_user", length=16)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}


	//bi-directional many-to-one association to ProjectTask
	@OneToMany(mappedBy="taskType")
	public Set<ProjectTask> getProjectTasks() {
		return this.projectTasks;
	}

	public void setProjectTasks(Set<ProjectTask> projectTasks) {
		this.projectTasks = projectTasks;
	}

	public ProjectTask addProjectTask(ProjectTask projectTask) {
		getProjectTasks().add(projectTask);
		projectTask.setTaskType(this);

		return projectTask;
	}

	public ProjectTask removeProjectTask(ProjectTask projectTask) {
		getProjectTasks().remove(projectTask);
		projectTask.setTaskType(null);

		return projectTask;
	}

}