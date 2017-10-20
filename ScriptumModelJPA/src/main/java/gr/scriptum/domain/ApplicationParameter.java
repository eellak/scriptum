package gr.scriptum.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the application_parameter database table.
 * 
 */
@Entity
@Table(name="application_parameter")
@NamedQuery(name="ApplicationParameter.findAll", query="SELECT a FROM ApplicationParameter a")
public class ApplicationParameter implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer applicationParameterType;
	private Date createDt;
	private String createUser;
	private String defaultValue;
	private String domain;
	private Integer lastValue;
	private String parameterValue;
	private Date updateTs;
	private String updateUser;

	public ApplicationParameter() {
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


	@Column(name="application_parameter_type")
	public Integer getApplicationParameterType() {
		return this.applicationParameterType;
	}

	public void setApplicationParameterType(Integer applicationParameterType) {
		this.applicationParameterType = applicationParameterType;
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


	@Column(name="default_value", length=512)
	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}


	@Column(length=256)
	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}


	@Column(name="last_value")
	public Integer getLastValue() {
		return this.lastValue;
	}

	public void setLastValue(Integer lastValue) {
		this.lastValue = lastValue;
	}


	@Column(name="parameter_value", length=512)
	public String getParameterValue() {
		return this.parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
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

}