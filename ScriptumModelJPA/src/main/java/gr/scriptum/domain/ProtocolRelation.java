/**
 * 
 */
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos
 *         </a>
 *
 */
@Entity
@Table(name = "protocol_relation", uniqueConstraints = @UniqueConstraint(columnNames = { "source_protocol_id",
		"target_protocol_id" }))
public class ProtocolRelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7281196901398394852L;

	public enum RelationType {
		RELATIVE, IDENTICAL_NUMBER, NEW_NUMBER;
	}

	private Integer id;
	private Protocol sourceProtocol;
	private Protocol targetProtocol;

	private RelationType relationType;
	private Integer createUserId;
	private Integer updateUserId;
	private Date createDt;
	private Date updateTs;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "source_protocol_id")
	public Protocol getSourceProtocol() {
		return sourceProtocol;
	}

	public void setSourceProtocol(Protocol sourceProtocol) {
		this.sourceProtocol = sourceProtocol;
	}

	@ManyToOne
	@JoinColumn(name = "target_protocol_id")
	public Protocol getTargetProtocol() {
		return targetProtocol;
	}

	public void setTargetProtocol(Protocol targetProtocol) {
		this.targetProtocol = targetProtocol;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	public RelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_dt", nullable = false)
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
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

	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sourceProtocol == null) ? 0 : sourceProtocol.hashCode());
		result = prime * result + ((targetProtocol == null) ? 0 : targetProtocol.hashCode());
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
		ProtocolRelation other = (ProtocolRelation) obj;
		if (sourceProtocol == null) {
			if (other.sourceProtocol != null)
				return false;
		} else if (!sourceProtocol.equals(other.sourceProtocol))
			return false;
		if (targetProtocol == null) {
			if (other.targetProtocol != null)
				return false;
		} else if (!targetProtocol.equals(other.targetProtocol))
			return false;
		return true;
	}

}
