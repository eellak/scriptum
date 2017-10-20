package gr.scriptum.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the project_user database table.
 * 
 */
@Embeddable
public class ProjectUserId implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer projectId;
	private Integer usersId;

	public ProjectUserId() {
	}

	@Column(name="project_id", insertable=false, updatable=false, unique=true, nullable=false)
	public Integer getProjectId() {
		return this.projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name="users_id", insertable=false, updatable=false, unique=true, nullable=false)
	public Integer getUsersId() {
		return this.usersId;
	}
	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProjectUserId)) {
			return false;
		}
		ProjectUserId castOther = (ProjectUserId)other;
		return 
			(this.projectId == castOther.projectId)
			&& (this.usersId == castOther.usersId);
	}

	public int hashCode() {
		final Integer prime = 31;
		Integer hash = 17;
		hash = hash * prime + this.projectId;
		hash = hash * prime + this.usersId;
		
		return hash;
	}
}