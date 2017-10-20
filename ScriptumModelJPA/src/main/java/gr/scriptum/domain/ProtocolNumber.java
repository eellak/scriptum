package gr.scriptum.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;


/**
 * The persistent class for the protocol_number database table.
 * 
 */
@Entity
@Table(name="protocol_number")
@NamedQuery(name="ProtocolNumber.findAll", query="SELECT p FROM ProtocolNumber p")
public class ProtocolNumber implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum ProtocolNumberType {
		INCOMING, OUTGOING, COMMON;
	};

	private Integer id;
	private Date createDt;
	private Long number;
	private Integer series;
	private Integer type;
	private Date updateTs;
	private Long version;
	private Integer year;
	private ProtocolBook protocolBook;

	public ProtocolNumber() {
	}

	@Transient
	public ProtocolNumberType getProNumberType() {
		return ProtocolNumberType.values()[type.intValue()];
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


	public Long getNumber() {
		return this.number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}


	public Integer getSeries() {
		return this.series;
	}

	public void setSeries(Integer series) {
		this.series = series;
	}


	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_ts")
	public Date getUpdateTs() {
		return this.updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}


	@Column(nullable=false)
	@Version
	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}


	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}


	//bi-directional many-to-one association to ProtocolBook
	@ManyToOne
	@JoinColumn(name="protocol_book_id", nullable=false)
	public ProtocolBook getProtocolBook() {
		return this.protocolBook;
	}

	public void setProtocolBook(ProtocolBook protocolBook) {
		this.protocolBook = protocolBook;
	}

}