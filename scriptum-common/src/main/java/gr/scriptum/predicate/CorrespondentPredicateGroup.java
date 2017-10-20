/**
 * 
 */
package gr.scriptum.predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class CorrespondentPredicateGroup {

	public enum JunctionType {
		AND, OR;
	}
	
	private List<CorrespondentPredicate> correspondentPredicates;
	private JunctionType junctionType;
	
	public CorrespondentPredicateGroup(CorrespondentPredicate correspondentPredicate) {
		super();
		this.correspondentPredicates = new ArrayList<CorrespondentPredicate>();
		this.correspondentPredicates.add(correspondentPredicate);
		this.junctionType = JunctionType.AND;
	}
	
	public CorrespondentPredicateGroup(List<CorrespondentPredicate> correspondentPredicates,
			JunctionType junctionType) {
		super();
		this.correspondentPredicates = correspondentPredicates;
		this.junctionType = junctionType;
	}
	public List<CorrespondentPredicate> getCorrespondentPredicates() {
		return correspondentPredicates;
	}
	public void setCorrespondentPredicates(List<CorrespondentPredicate> correspondentPredicates) {
		this.correspondentPredicates = correspondentPredicates;
	}
	public CorrespondentPredicateGroup.JunctionType getJunctionType() {
		return junctionType;
	}
	public void setJunctionType(CorrespondentPredicateGroup.JunctionType junctionType) {
		this.junctionType = junctionType;
	}
	
}
