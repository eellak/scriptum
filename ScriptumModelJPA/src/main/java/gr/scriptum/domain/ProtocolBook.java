package gr.scriptum.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import gr.scriptum.domain.DocumentType;
import javax.persistence.ManyToMany;


/**
 * The persistent class for the protocol_book database table.
 * 
 */
@Entity
@Table(name="protocol_book")
@NamedQuery(name="ProtocolBook.findAll", query="SELECT p FROM ProtocolBook p")
public class ProtocolBook implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Integer IS_ACTIVE = 1;
	
	public static final Integer IS_NOT_ACTIVE= 0;

	public enum ProtocolBookType{
		PUBLIC, SECRET;
	}
	
	private Integer id;
	private ProtocolBookType type;
	private Date createDt;
	private String createUser;
	private Integer isActive;
	private Integer isIncoming;
	private Integer isPreferred;
	private String protocolNumber;
	private String protocolPath;
	private String protocolSeries;
	private Integer protocolYear;
	private Date updateTs;
	private String updateUser;
	private Set<Protocol> protocols;
	private Set<ProtocolNumber> protocolNumbers;

	private Set<DocumentType> documentTypes;
	public ProtocolBook() {
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
		ProtocolBook other = (ProtocolBook) obj;
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

	@Enumerated(EnumType.STRING)
	public ProtocolBookType getType() {
		return type;
	}

	public void setType(ProtocolBookType type) {
		this.type = type;
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


	@Column(name="is_incoming")
	public Integer getIsIncoming() {
		return this.isIncoming;
	}

	public void setIsIncoming(Integer isIncoming) {
		this.isIncoming = isIncoming;
	}


	@Column(name="is_preferred")
	public Integer getIsPreferred() {
		return this.isPreferred;
	}

	public void setIsPreferred(Integer isPreferred) {
		this.isPreferred = isPreferred;
	}


	@Column(name="protocol_number", length=45)
	public String getProtocolNumber() {
		return this.protocolNumber;
	}

	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = protocolNumber;
	}


	@Column(name="protocol_path", length=640)
	public String getProtocolPath() {
		return this.protocolPath;
	}

	public void setProtocolPath(String protocolPath) {
		this.protocolPath = protocolPath;
	}


	@Column(name="protocol_series", length=256)
	public String getProtocolSeries() {
		return this.protocolSeries;
	}

	public void setProtocolSeries(String protocolSeries) {
		this.protocolSeries = protocolSeries;
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
	@OneToMany(mappedBy="protocolBook")
	public Set<Protocol> getProtocols() {
		return this.protocols;
	}

	public void setProtocols(Set<Protocol> protocols) {
		this.protocols = protocols;
	}

	public Protocol addProtocol(Protocol protocol) {
		getProtocols().add(protocol);
		protocol.setProtocolBook(this);

		return protocol;
	}

	public Protocol removeProtocol(Protocol protocol) {
		getProtocols().remove(protocol);
		protocol.setProtocolBook(null);

		return protocol;
	}


	//bi-directional many-to-one association to ProtocolNumber
	@OneToMany(mappedBy="protocolBook")
	public Set<ProtocolNumber> getProtocolNumbers() {
		return this.protocolNumbers;
	}

	public void setProtocolNumbers(Set<ProtocolNumber> protocolNumbers) {
		this.protocolNumbers = protocolNumbers;
	}

	public ProtocolNumber addProtocolNumber(ProtocolNumber protocolNumber) {
		getProtocolNumbers().add(protocolNumber);
		protocolNumber.setProtocolBook(this);

		return protocolNumber;
	}

	public ProtocolNumber removeProtocolNumber(ProtocolNumber protocolNumber) {
		getProtocolNumbers().remove(protocolNumber);
		protocolNumber.setProtocolBook(null);

		return protocolNumber;
	}


	@ManyToMany(mappedBy = "protocolBooks")
	public Set<DocumentType> getDocumentTypes() {
	    return documentTypes;
	}


	public void setDocumentTypes(Set<DocumentType> param) {
	    this.documentTypes = param;
	}

}