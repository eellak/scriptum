package gr.scriptum.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import gr.scriptum.domain.DistributionMethod;
import javax.persistence.ManyToOne;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolDocument;

@Entity
@Table(name = "ProtocolDependency")
public class ProtocolDependency implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	@ManyToOne
	private DistributionMethod distributionMethod;
	@ManyToOne
	private Protocol protocol;
	@ManyToOne
	private ProtocolDocument protocolDocument;

	public ProtocolDependency() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long param) {
		this.id = param;
	}

	public DistributionMethod getDistributionMethod() {
	    return distributionMethod;
	}

	public void setDistributionMethod(DistributionMethod param) {
	    this.distributionMethod = param;
	}

	public Protocol getProtocol() {
	    return protocol;
	}

	public void setProtocol(Protocol param) {
	    this.protocol = param;
	}

	public ProtocolDocument getProtocolDocument() {
	    return protocolDocument;
	}

	public void setProtocolDocument(ProtocolDocument param) {
	    this.protocolDocument = param;
	}

}