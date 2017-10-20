package gr.scriptum.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import gr.scriptum.domain.Users;
import gr.scriptum.domain.ApplicationLog.Operation;
import gr.scriptum.domain.ApplicationLog.Severity;

import javax.persistence.ManyToOne;
import gr.scriptum.domain.Protocol;

/**
 * The persistent class for the application_log database table.
 * 
 */
@Entity
@Table(name = "application_log")
@NamedQuery(name = "ApplicationLog.findAll", query = "SELECT a FROM ApplicationLog a")
public class ApplicationLog implements Serializable {
	
	public enum Severity{
		SUCCESS, FAILURE;
	}

	public enum Operation{
		DELETE_PROTOCOL, DISSASOCIATE_PROTOCOL, ADD_DOCUMENT, DELETE_DOCUMENT, EDIT_PROTOCOL;
	}

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String appuser;
	private String class_;
	private Date created;
	private String level;
	private String message;
	private String msgData;
	private String system;
	private Operation operation;
	private Severity severity;
	private Users user;
	private Protocol protocol;
	public ApplicationLog() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length = 16)
	public String getAppuser() {
		return this.appuser;
	}

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	@Column(name = "class", length = 64)
	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(length = 32)
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Column(length = 256)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "msg_data", length = 512)
	public String getMsgData() {
		return this.msgData;
	}

	public void setMsgData(String msgData) {
		this.msgData = msgData;
	}

	@Column(length = 32)
	public String getSystem() {
		return this.system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	@Enumerated(EnumType.STRING)
	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation param) {
		this.operation = param;
	}

	@Enumerated(EnumType.STRING)
	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity param) {
		this.severity = param;
	}

	@ManyToOne
	public Users getUser() {
	    return user;
	}

	public void setUser(Users param) {
	    this.user = param;
	}

	@ManyToOne
	public Protocol getProtocol() {
	    return protocol;
	}

	public void setProtocol(Protocol param) {
	    this.protocol = param;
	}

}