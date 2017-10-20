package gr.scriptum.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the task_result database table.
 * 
 */
@Entity
@Table(name="task_result")
@NamedQuery(name="TaskResult.findAll", query="SELECT t FROM TaskResult t")
public class TaskResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String description;
	private String name;
	private Set<ProjectTask> projectTasks;

	public TaskResult() {
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


	@Column(length=512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(length=32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	//bi-directional many-to-one association to ProjectTask
	@OneToMany(mappedBy="taskResult")
	public Set<ProjectTask> getProjectTasks() {
		return this.projectTasks;
	}

	public void setProjectTasks(Set<ProjectTask> projectTasks) {
		this.projectTasks = projectTasks;
	}

	public ProjectTask addProjectTask(ProjectTask projectTask) {
		getProjectTasks().add(projectTask);
		projectTask.setTaskResult(this);

		return projectTask;
	}

	public ProjectTask removeProjectTask(ProjectTask projectTask) {
		getProjectTasks().remove(projectTask);
		projectTask.setTaskResult(null);

		return projectTask;
	}

}