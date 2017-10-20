package gr.scriptum.domain;

import static javax.persistence.GenerationType.*;
import static javax.persistence.TemporalType.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;

@Entity
@Table(name = "CorrespondentGroup")
public class CorrespondentGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String name;
	private String code;
	private String description;
	@Enumerated(EnumType.STRING)
	private CorrespondentDirection applicableTo;
	private Boolean active = Boolean.TRUE;
	@Temporal(TIMESTAMP)
	private Date createDt;
	@Temporal(TIMESTAMP)
	private Date updateDt;
	private Integer createUserId;
	private Integer updateUserId;
	@OneToMany(mappedBy = "correspondentGroup", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProtocolCorrespondent> protocolCorrespondents;

	public CorrespondentGroup() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long param) {
		this.id = param;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String param) {
		this.description = param;
	}

	public CorrespondentDirection getApplicableTo() {
		return applicableTo;
	}

	public void setApplicableTo(CorrespondentDirection param) {
		this.applicableTo = param;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date param) {
		this.createDt = param;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date param) {
		this.updateDt = param;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer param) {
		this.createUserId = param;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer param) {
		this.updateUserId = param;
	}

	public Set<ProtocolCorrespondent> getProtocolCorrespondents() {
	    return protocolCorrespondents;
	}

	public void setProtocolCorrespondents(Set<ProtocolCorrespondent> param) {
	    this.protocolCorrespondents = param;
	}

}