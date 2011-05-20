/**
 * 
 */
package gr.scriptum.dao;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.StaleObjectStateException;

import gr.scriptum.domain.ProtocolNumber;
import gr.scriptum.domain.ProtocolNumber.ProtocolNumberType;

/**
 * @author aanagnostopoulos
 * 
 */
public class ProtocolNumberDAO extends GenericDAO<ProtocolNumber, Integer> {

	private static Log log = LogFactory.getLog(ProtocolNumberDAO.class);

	public ProtocolNumber getNextNumber(ProtocolNumberType type) {

		Query query = getSession()
				.createQuery(
						"from ProtocolNumber pn where pn.id = (select max(pn2.id) from ProtocolNumber pn2 where pn2.type =:type)");
		query.setParameter("type", type.ordinal());
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

}
