package gr.scriptum.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the diavgeia_decision_type database table.
 * 
 */
@Entity
@Table(name="diavgeia_decision_type")
@NamedQuery(name="DiavgeiaDecisionType.findAll", query="SELECT d FROM DiavgeiaDecisionType d")
public class DiavgeiaDecisionType implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date createDt;
	private String description;
	private String supported;
	private String uid;

	public DiavgeiaDecisionType() {
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


	@Column(columnDefinition="TEXT")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(length=256)
	public String getSupported() {
		return this.supported;
	}

	public void setSupported(String supported) {
		this.supported = supported;
	}


	@Column(length=256)
	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}