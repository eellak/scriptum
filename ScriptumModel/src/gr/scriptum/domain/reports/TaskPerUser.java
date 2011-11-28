package gr.scriptum.domain.reports;

/**
 * @author Mike Mountrakis mountrakis@uit.gr
 */
public class TaskPerUser {
	String userName;
	String nameSurname;
	Integer taskNumber = 0;
	String taskState;

	public TaskPerUser() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public String getNameSurname() {
		return nameSurname;
	}

	public void setNameSurname(String nameSurname) {
		this.nameSurname = nameSurname;
	}

}
