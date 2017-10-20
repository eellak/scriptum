/**
 * 
 */
package gr.scriptum.dao;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.OptimisticLockException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.StaleObjectStateException;
import org.hibernate.StaleStateException;
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
public class ProtocolNumberDAO extends GenericDAO<ProtocolNumber, Integer> {

	private static Log log = LogFactory.getLog(ProtocolNumberDAO.class);

	@Transactional(readOnly = true)
	public ProtocolNumber getNextNumber(ProtocolNumberType type, ProtocolBook protocolBook) {

		Query query = getSession().createQuery(
				"from ProtocolNumber pn where pn.id = (select max(pn2.id) from ProtocolNumber pn2 where pn2.type =:type and pn2.protocolBook =:book)");
		query.setParameter("type", type.ordinal());
		query.setParameter("book", protocolBook);
		ProtocolNumber number = (ProtocolNumber) query.uniqueResult();

		// auto reset number every new year
		Calendar updateCalendar = Calendar.getInstance();
		updateCalendar.setTime(number.getUpdateTs());

		Calendar nowCalendar = Calendar.getInstance();
		// row below is purely for testing purposes

		if (nowCalendar.get(Calendar.YEAR) > updateCalendar.get(Calendar.YEAR)) {
			// year has changed, reset number
			number.setNumber(Long.valueOf(1));
		} else {
			// same year, increment number
			Long value = number.getNumber();
			number.setNumber(value + 1);
		}

		number.setUpdateTs(new Date());

		try {
			update(number);
		} catch (StaleStateException | OptimisticLockException e) {
			log.error("Caught hibernate/jpa stale state exception during protocol number update",e);
			throw e;
		}

		return number;
	}

	@Transactional
	public ProtocolNumber createProtocolNumber(ProtocolBook protocolBook) {
		ProtocolNumber protocolNumber = new ProtocolNumber();
		protocolNumber.setProtocolBook(protocolBook);
		protocolNumber.setVersion(Long.valueOf(1));
		protocolNumber.setNumber(Long.valueOf(0));
		protocolNumber.setType(ProtocolNumberType.COMMON.ordinal());
		Date now = new Date();
		protocolNumber.setCreateDt(now);
		protocolNumber.setUpdateTs(now);
		return super.makePersistent(protocolNumber);
	}

}
