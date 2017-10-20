package gr.scriptum.predicate;

import org.hibernate.criterion.Criterion;

import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;
import gr.scriptum.domain.ProtocolNode.Direction;

public class CorrespondentPredicate {
	
	private ProtocolCorrespondent correspondent;
	private Direction protocolDirection;
	private CorrespondentDirection direction;
	private Criterion[] additionalCriteria;
	
	public CorrespondentPredicate() {
		// TODO Auto-generated constructor stub
	}
	
	public CorrespondentPredicate(ProtocolCorrespondent correspondent) {
		super();
		this.correspondent = correspondent;
	}

	public ProtocolCorrespondent getCorrespondent() {
		return correspondent;
	}

	public void setCorrespondent(ProtocolCorrespondent correspondent) {
		this.correspondent = correspondent;
	}


	public Direction getProtocolDirection() {
		return protocolDirection;
	}

	public void setProtocolDirection(Direction protocolDirection) {
		this.protocolDirection = protocolDirection;
	}

	public CorrespondentDirection getDirection() {
		return direction;
	}

	public void setDirection(CorrespondentDirection direction) {
		this.direction = direction;
	}

	public Criterion[] getAdditionalCriteria() {
		return additionalCriteria;
	}

	public void setAdditionalCriteria(Criterion[] additionalCriteria) {
		this.additionalCriteria = additionalCriteria;
	}
	
}
