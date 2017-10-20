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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.CorrespondentGroup;

@Entity
@Table(name = "ProtocolCorrespondent")
public class ProtocolCorrespondent implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum CorrespondentType {
		ACTIVE_MEMBER, INACTIVE_MEMBER, EMPLOYEE, DEPARTMENT, COMPANY, CONTACT, OTHER
	}

	public enum CorrespondentDirection {
		SENDER, TRANSACTOR, CREATOR, INTERNAL_RECIPIENT, AUTHOR, RECIPIENT, ASSIGNEE;
	}

	public enum CorrespondentAction {
		TO, CC;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private CorrespondentDirection direction;

	@Enumerated(EnumType.STRING)
	private CorrespondentType type;

	@Column(columnDefinition = "TEXT")
	private String description;

	private String code;

	private String vatNumber;

	private String ssn;

	@Enumerated(EnumType.STRING)
	private CorrespondentAction action;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dispatchDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date routingDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date attachedDeliveryDate;
	
	Integer createUserId;
	Integer updateUserId;
	@Temporal(TemporalType.TIMESTAMP)
	Date createDt;
	@Temporal(TemporalType.TIMESTAMP)
	Date updateTs;

	@Transient
	private Boolean dirty;
	
	@ManyToOne
	private Protocol protocol;

	@ManyToOne
	private Department department;

	@ManyToOne
	private Contact contact;

	@ManyToOne
	private Company company;

	@ManyToOne
	private Users user;

	@ManyToOne
	private DistributionMethod distributionMethod;

	@ManyToOne
	private CorrespondentGroup correspondentGroup;

	public ProtocolCorrespondent() {
	}

	public ProtocolCorrespondent(Protocol protocol, CorrespondentDirection direction, CorrespondentType type) {
		this.protocol = protocol;
		this.direction = direction;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public CorrespondentDirection getDirection() {
		return direction;
	}

	public void setDirection(CorrespondentDirection direction) {
		this.direction = direction;
	}

	public CorrespondentType getType() {
		return type;
	}

	public void setType(CorrespondentType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public CorrespondentAction getAction() {
		return action;
	}

	public void setAction(CorrespondentAction action) {
		this.action = action;
	}

	public Date getDispatchDate() {
		return dispatchDate;
	}

	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}

	public Date getRoutingDate() {
		return routingDate;
	}

	public void setRoutingDate(Date routingDate) {
		this.routingDate = routingDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getAttachedDeliveryDate() {
		return attachedDeliveryDate;
	}

	public void setAttachedDeliveryDate(Date attachedDeliveryDate) {
		this.attachedDeliveryDate = attachedDeliveryDate;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}

	public Boolean getDirty() {
		return dirty;
	}

	public void setDirty(Boolean dirty) {
		this.dirty = dirty;
	}

	public void setId(Long param) {
		this.id = param;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol param) {
		this.protocol = param;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department param) {
		this.department = param;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact param) {
		this.contact = param;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company param) {
		this.company = param;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users param) {
		this.user = param;
	}

	public DistributionMethod getDistributionMethod() {
		return distributionMethod;
	}

	public void setDistributionMethod(DistributionMethod param) {
		this.distributionMethod = param;
	}

	public CorrespondentGroup getCorrespondentGroup() {
	    return correspondentGroup;
	}

	public void setCorrespondentGroup(CorrespondentGroup param) {
	    this.correspondentGroup = param;
	}

}