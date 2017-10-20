package gr.scriptum.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import gr.scriptum.domain.ProtocolDependency;
import gr.scriptum.domain.ProtocolCorrespondent;


/**
 * The persistent class for the distribution_method database table.
 * 
 */
@Entity
@Table(name="distribution_method")
@NamedQuery(name="DistributionMethod.findAll", query="SELECT d FROM DistributionMethod d")
public class DistributionMethod implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String code;
	private Date createDt;
	private String createUser;
	private String description;
	private Date updateTs;
	private String updateUser;
	private Boolean requiresDescription;
	private Boolean  autoFillRoutingDate;
	private Set<Protocol> protocols;
	private Set<ProtocolDependency> protocolDependencies;
	private Set<ProtocolCorrespondent> protocolCorrespondents;

	public DistributionMethod() {
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
		DistributionMethod other = (DistributionMethod) obj;
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


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public Boolean getRequiresDescription() {
		return requiresDescription;
	}


	public void setRequiresDescription(Boolean requiresDescription) {
		this.requiresDescription = requiresDescription;
	}

	
	public Boolean getAutoFillRoutingDate() {
		return autoFillRoutingDate;
	}


	public void setAutoFillRoutingDate(Boolean autoFillRoutingDate) {
		this.autoFillRoutingDate = autoFillRoutingDate;
	}


	//bi-directional many-to-one association to Protocol
	@OneToMany(mappedBy="distributionMethod")
	public Set<Protocol> getProtocols() {
		return this.protocols;
	}

	public void setProtocols(Set<Protocol> protocols) {
		this.protocols = protocols;
	}

	public Protocol addProtocol(Protocol protocol) {
		getProtocols().add(protocol);
		protocol.setDistributionMethod(this);

		return protocol;
	}

	public Protocol removeProtocol(Protocol protocol) {
		getProtocols().remove(protocol);
		protocol.setDistributionMethod(null);

		return protocol;
	}


	@OneToMany(mappedBy = "distributionMethod")
	public Set<ProtocolDependency> getProtocolDependencies() {
	    return protocolDependencies;
	}


	public void setProtocolDependencies(Set<ProtocolDependency> param) {
	    this.protocolDependencies = param;
	}


	@OneToMany(mappedBy = "distributionMethod")
	public Set<ProtocolCorrespondent> getProtocolCorrespondents() {
	    return protocolCorrespondents;
	}


	public void setProtocolCorrespondents(Set<ProtocolCorrespondent> param) {
	    this.protocolCorrespondents = param;
	}

}