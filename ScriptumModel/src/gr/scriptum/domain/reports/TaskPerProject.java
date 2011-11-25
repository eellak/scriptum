package gr.scriptum.domain.reports;

public class TaskPerProject {
	Integer projectId;
	Integer taskId;
	String taskName;
	String projectName;
	String taskDispatcher;
	String taskState;

	public TaskPerProject() {

	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTaskDispatcher() {
		return taskDispatcher;
	}

	public void setTaskDispatcher(String taskDispatcher) {
		this.taskDispatcher = taskDispatcher;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

}
