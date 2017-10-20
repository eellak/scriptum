package gr.scriptum.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="Users.findAll", query="SELECT u FROM Users u")
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String address;
	private Date createDt;
	private String createUser;
	private String email;
	private Boolean isDisabled;
	private String name;
	private String password;
	private String surname;
	private Date updateTs;
	private String updateUser;
	private String username;
	private String middleName;
	private String code;
	private Date updatePasswordDate;
	private Set<Project> projects;
	private Set<ProjectTask> projectTasks1;
	private Set<ProjectTask> projectTasks2;
	private Set<ProjectUser> projectUsers;
	private Set<Role> roles;
	private Set<TaskMessage> taskMessages1;
	private Set<TaskMessage> taskMessages2;
	private Set<UserHierarchy> userHierarchies;
	private Set<UserAssignment> userAssignments;
	private Set<ApplicationLog> applicationLogs;
	private Set<ProtocolCorrespondent> protocolCorrespondents;

	public Users() {
		this.isDisabled = Boolean.FALSE;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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


	@Column(length=256)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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


	@Column(length=256)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name="is_disabled")
	public Boolean getIsDisabled() {
		return this.isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}


	@Column(length=32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(nullable=false, length=45)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Column(length=32)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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


	@Column(length=16)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Date getUpdatePasswordDate() {
		return updatePasswordDate;
	}


	public void setUpdatePasswordDate(Date updatePasswordDate) {
		this.updatePasswordDate = updatePasswordDate;
	}


	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="user")
	public Set<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		project.setUser(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setUser(null);

		return project;
	}


	//bi-directional many-to-one association to ProjectTask
	@OneToMany(mappedBy="user1")
	public Set<ProjectTask> getProjectTasks1() {
		return this.projectTasks1;
	}

	public void setProjectTasks1(Set<ProjectTask> projectTasks1) {
		this.projectTasks1 = projectTasks1;
	}

	public ProjectTask addProjectTasks1(ProjectTask projectTasks1) {
		getProjectTasks1().add(projectTasks1);
		projectTasks1.setUser1(this);

		return projectTasks1;
	}

	public ProjectTask removeProjectTasks1(ProjectTask projectTasks1) {
		getProjectTasks1().remove(projectTasks1);
		projectTasks1.setUser1(null);

		return projectTasks1;
	}


	//bi-directional many-to-one association to ProjectTask
	@OneToMany(mappedBy="user2")
	public Set<ProjectTask> getProjectTasks2() {
		return this.projectTasks2;
	}

	public void setProjectTasks2(Set<ProjectTask> projectTasks2) {
		this.projectTasks2 = projectTasks2;
	}

	public ProjectTask addProjectTasks2(ProjectTask projectTasks2) {
		getProjectTasks2().add(projectTasks2);
		projectTasks2.setUser2(this);

		return projectTasks2;
	}

	public ProjectTask removeProjectTasks2(ProjectTask projectTasks2) {
		getProjectTasks2().remove(projectTasks2);
		projectTasks2.setUser2(null);

		return projectTasks2;
	}


	//bi-directional many-to-one association to ProjectUser
	@OneToMany(mappedBy="user")
	public Set<ProjectUser> getProjectUsers() {
		return this.projectUsers;
	}

	public void setProjectUsers(Set<ProjectUser> projectUsers) {
		this.projectUsers = projectUsers;
	}

	public ProjectUser addProjectUser(ProjectUser projectUser) {
		getProjectUsers().add(projectUser);
		projectUser.setUser(this);

		return projectUser;
	}

	public ProjectUser removeProjectUser(ProjectUser projectUser) {
		getProjectUsers().remove(projectUser);
		projectUser.setUser(null);

		return projectUser;
	}


	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
			name="user_to_role"
			, joinColumns={
				@JoinColumn(name="users_id", nullable=false)
				}
			, inverseJoinColumns={
				@JoinColumn(name="role_id", nullable=false)
				}
			)

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	//bi-directional many-to-one association to TaskMessage
	@OneToMany(mappedBy="user1")
	public Set<TaskMessage> getTaskMessages1() {
		return this.taskMessages1;
	}

	public void setTaskMessages1(Set<TaskMessage> taskMessages1) {
		this.taskMessages1 = taskMessages1;
	}

	public TaskMessage addTaskMessages1(TaskMessage taskMessages1) {
		getTaskMessages1().add(taskMessages1);
		taskMessages1.setUser1(this);

		return taskMessages1;
	}

	public TaskMessage removeTaskMessages1(TaskMessage taskMessages1) {
		getTaskMessages1().remove(taskMessages1);
		taskMessages1.setUser1(null);

		return taskMessages1;
	}


	//bi-directional many-to-one association to TaskMessage
	@OneToMany(mappedBy="user2")
	public Set<TaskMessage> getTaskMessages2() {
		return this.taskMessages2;
	}

	public void setTaskMessages2(Set<TaskMessage> taskMessages2) {
		this.taskMessages2 = taskMessages2;
	}

	public TaskMessage addTaskMessages2(TaskMessage taskMessages2) {
		getTaskMessages2().add(taskMessages2);
		taskMessages2.setUser2(this);

		return taskMessages2;
	}

	public TaskMessage removeTaskMessages2(TaskMessage taskMessages2) {
		getTaskMessages2().remove(taskMessages2);
		taskMessages2.setUser2(null);

		return taskMessages2;
	}


	//bi-directional many-to-one association to UserHierarchy
	@OneToMany(mappedBy="user")
	public Set<UserHierarchy> getUserHierarchies() {
		return this.userHierarchies;
	}

	public void setUserHierarchies(Set<UserHierarchy> userHierarchies) {
		this.userHierarchies = userHierarchies;
	}

	public UserHierarchy addUserHierarchy(UserHierarchy userHierarchy) {
		getUserHierarchies().add(userHierarchy);
		userHierarchy.setUser(this);

		return userHierarchy;
	}

	public UserHierarchy removeUserHierarchy(UserHierarchy userHierarchy) {
		getUserHierarchies().remove(userHierarchy);
		userHierarchy.setUser(null);

		return userHierarchy;
	}


	@OneToMany(mappedBy = "user")
	public Set<UserAssignment> getUserAssignments() {
	    return userAssignments;
	}


	public void setUserAssignments(Set<UserAssignment> param) {
	    this.userAssignments = param;
	}


	@OneToMany(mappedBy = "user")
	public Set<ApplicationLog> getApplicationLogs() {
	    return applicationLogs;
	}


	public void setApplicationLogs(Set<ApplicationLog> param) {
	    this.applicationLogs = param;
	}


	@OneToMany(mappedBy = "user")
	public Set<ProtocolCorrespondent> getProtocolCorrespondents() {
	    return protocolCorrespondents;
	}


	public void setProtocolCorrespondents(Set<ProtocolCorrespondent> param) {
	    this.protocolCorrespondents = param;
	}

}