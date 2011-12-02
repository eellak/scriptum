package gr.scriptum.domain.reports;

import java.util.Date;

public class TaskPerProject {
	Integer projectId;
	Integer taskId;
	String  taskName;
	String  projectName;
	String  taskDispatcher;
	String  taskState;

	//used from reportTasksWaitingExternalDAO
	Date   taskStartDt;
	String contactName;
	String contactSurname;
	String companyName;
	
	//used from ReportTaskNumberWaitingPerCompanyDAO
	Integer taskNumber;
	
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

	public Date getTaskStartDt() {
		return taskStartDt;
	}

	public void setTaskStartDt(Date taskStartDt) {
		this.taskStartDt = taskStartDt;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactSurname() {
		return contactSurname;
	}

	public void setContactSurname(String contactSurname) {
		this.contactSurname = contactSurname;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}

	@Override
	public String toString() {
		return "TaskPerProject [projectId=" + projectId + ", taskId=" + taskId
				+ ", taskName=" + taskName + ", projectName=" + projectName
				+ ", taskDispatcher=" + taskDispatcher + ", taskState="
				+ taskState + ", taskStartDt=" + taskStartDt + ", contactName="
				+ contactName + ", contactSurname=" + contactSurname
				+ ", companyName=" + companyName + ", taskNumber=" + taskNumber
				+ "]";
	}



	
}
