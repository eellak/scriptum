/**
 * 
 */
package gr.scriptum.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolNumber;
import gr.scriptum.domain.ProtocolNumber.ProtocolNumberType;

/**
 * @author aanagnostopoulos
 * 
 */
@Repository
public class ProtocolBookDAO extends GenericDAO<ProtocolBook, Integer> {

	@Autowired
	private ProtocolNumberDAO protocolNumberDAO;
	
	@Transactional(readOnly=true)
	public List<ProtocolBook> findActiveBooks() {
		ProtocolBook protocolBook = new ProtocolBook();
		protocolBook.setIsActive(ProtocolBook.IS_ACTIVE);
		List<Order> sortBy = new ArrayList<Order>();
		sortBy.add(Order.desc("isPreferred"));
		return findByExample(protocolBook, MatchMode.ANYWHERE, sortBy, null,
				null);

	}

	@Override
	@Transactional(readOnly=true)
	public boolean isDeletable(Integer id) {

		ProtocolBook book = findById(id, false);
		if (!book.getProtocols().isEmpty()
				|| !book.getProtocols().isEmpty()) {
			return false;
		}
		return true;
	}

//	@Override
//	@Transactional
//	public ProtocolBook makePersistent(ProtocolBook entity) {
//		ProtocolBook protocolBook = super.makePersistent(entity);
//		
//		//create relevant Protocol Number
////		ProtocolNumberDAO protocolNumberDAO =  new ProtocolNumberDAO();
////		protocolNumberDAO.setSessionFactory(getSessionFactory());
////		ProtocolNumber protocolNumber = protocolNumberDAO.createProtocolNumber(protocolBook);
////		protocolBook.getProtocolNumbers().add(protocolNumber);
//		return protocolBook;
//	}
	
//	@Override
//	@Transactional
//	public void deleteById(Integer id) {
//
//		ProtocolBook book = findById(id, false);
//
//		// delete relevant ProtocolNumbers
//		protocolNumberDAO.setSessionFactory(getSessionFactory());
//		for (ProtocolNumber protocolNumber : book.getProtocolNumbers()) {
//			protocolNumberDAO.delete(protocolNumber);
//		}
//		// delete book itself
//		super.delete(book);
//	}
}
