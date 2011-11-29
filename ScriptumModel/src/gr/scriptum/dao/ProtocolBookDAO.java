/**
 * 
 */
package gr.scriptum.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolNumber;
import gr.scriptum.domain.ProtocolNumber.ProtocolNumberType;

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

	@Override
	public boolean isDeletable(Integer id) {

		ProtocolBook book = findById(id, false);
		if (!book.getIncomingProtocols().isEmpty()
				|| !book.getOutgoingProtocols().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public ProtocolBook makePersistent(ProtocolBook entity) {
		ProtocolBook protocolBook = super.makePersistent(entity);
		
		//create relevant Protocol Number
		ProtocolNumberDAO protocolNumberDAO =  new ProtocolNumberDAO();
		ProtocolNumber protocolNumber = protocolNumberDAO.createProtocolNumber(protocolBook);
		protocolBook.getProtocolNumbers().add(protocolNumber);
		return protocolBook;
	}
	
	@Override
	public void deleteById(Integer id) {

		ProtocolBook book = findById(id, false);

		// delete relevant ProtocolNumbers
		ProtocolNumberDAO protocolNumberDAO = new ProtocolNumberDAO();
		for (ProtocolNumber protocolNumber : book.getProtocolNumbers()) {
			protocolNumberDAO.delete(protocolNumber);
		}
		// delete book itself
		super.delete(book);
	}
}
