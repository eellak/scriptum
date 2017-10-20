package gr.scriptum.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the dossier database table.
 * 
 */
@Entity
@Table(name="dossier")
@NamedQuery(name="Dossier.findAll", query="SELECT d FROM Dossier d")
public class Dossier implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Integer IS_ACTIVE = 1;
	
	public static final Integer IS_NOT_ACTIVE= 0;

	private Integer id;
	private Date createDt;
	private String createUser;
	private Integer isActive;
	private Integer isPreferred;
	private String name;
	private Integer protocolYear;
	private Date updateTs;
	private String updateUser;
	private Set<Protocol> protocols;

	public Dossier() {
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


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_dt")
	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}


	@Column(name="create_user", length=32)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	@Column(name="is_active")
	public Integer getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}


	@Column(name="is_preferred")
	public Integer getIsPreferred() {
		return this.isPreferred;
	}

	public void setIsPreferred(Integer isPreferred) {
		this.isPreferred = isPreferred;
	}


	@Column(length=256)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(name="protocol_year")
	public Integer getProtocolYear() {
		return this.protocolYear;
	}

	public void setProtocolYear(Integer protocolYear) {
		this.protocolYear = protocolYear;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_ts")
	public Date getUpdateTs() {
		return this.updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}


	@Column(name="update_user", length=32)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	//bi-directional many-to-one association to Protocol
	@OneToMany(mappedBy="dossier")
	public Set<Protocol> getProtocols() {
		return this.protocols;
	}

	public void setProtocols(Set<Protocol> protocols) {
		this.protocols = protocols;
	}

	public Protocol addProtocol(Protocol protocol) {
		getProtocols().add(protocol);
		protocol.setDossier(this);

		return protocol;
	}

	public Protocol removeProtocol(Protocol protocol) {
		getProtocols().remove(protocol);
		protocol.setDossier(null);

		return protocol;
	}

}