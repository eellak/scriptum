/**
 * 
 */
package gr.scriptum.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import gr.scriptum.domain.ProtocolBook;

/**
 * @author aanagnostopoulos
 * 
 */
public class ProtocolBookDAO extends GenericDAO<ProtocolBook, Integer> {

	public List<ProtocolBook> findActiveBooks() {
		ProtocolBook protocolBook = new ProtocolBook();
		protocolBook.setIsActive(ProtocolBook.IS_ACTIVE);
		List<Order> sortBy = new ArrayList<Order>();
		sortBy.add(Order.desc("isPreferred"));
		return findByExample(protocolBook, MatchMode.ANYWHERE, sortBy, null,
				null);

	}
}
