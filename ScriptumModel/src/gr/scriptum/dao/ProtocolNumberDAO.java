/**
 * 
 */
package gr.scriptum.dao;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.StaleObjectStateException;

import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolNumber;
import gr.scriptum.domain.ProtocolNumber.ProtocolNumberType;

/**
 * @author aanagnostopoulos
 * 
 */
public class ProtocolNumberDAO extends GenericDAO<ProtocolNumber, Integer> {

	private static Log log = LogFactory.getLog(ProtocolNumberDAO.class);

	public ProtocolNumber getNextNumber(ProtocolNumberType type,
			ProtocolBook protocolBook) {

		Query query = getSession()
				.createQuery(
						"from ProtocolNumber pn where pn.id = (select max(pn2.id) from ProtocolNumber pn2 where pn2.type =:type and pn2.protocolBook =:book)");
		query.setParameter("type", type.ordinal());
		query.setParameter("book", protocolBook);
		ProtocolNumber number = (ProtocolNumber) query.uniqueResult();

		Long value = number.getNumber();
		number.setNumber(value + 1);
		number.setUpdateTs(new Date());
		// try {
		// Thread.sleep(10000);
		// } catch (InterruptedException e1) {
		// log.error(e1);
		// }
		try {
			update(number);
		} catch (StaleObjectStateException e) {
			log.error(e);
			throw e;
		}

		return number;
	}

	public ProtocolNumber createProtocolNumber(ProtocolBook protocolBook) {
		ProtocolNumber protocolNumber = new ProtocolNumber();
		protocolNumber.setProtocolBook(protocolBook);
		protocolNumber.setVersion(new Long(1));
		protocolNumber.setNumber(new Long(0));
		protocolNumber.setType(ProtocolNumberType.COMMON.ordinal());
		Date now = new Date();
		protocolNumber.setCreateDt(now);
		protocolNumber.setUpdateTs(now);
		return super.makePersistent(protocolNumber);
	}

}
