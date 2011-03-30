/**
 * 
 */
package gr.scriptum.dao;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gr.scriptum.domain.Contact;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.OutgoingProtocol;

/**
 * @author aanagnostopoulos
 * 
 */
public class OutgoingProtocolDAO extends ProtocolDAO<OutgoingProtocol, Integer> {

	private static Log log = LogFactory.getLog(OutgoingProtocolDAO.class);

	@Override
	protected Criteria buildSearchCriteria(String protocolNumber, Date from,
			Date to, String subject, String keywords,
			DistributionMethod distributionMethod, Contact contact,
			boolean includePending) {
		Criteria crit = super.buildSearchCriteria(protocolNumber, from, to,
				subject, keywords, distributionMethod, contact, includePending);

		if (contact != null) {
			Criteria recipientCriteria = crit
					.createCriteria("outgoingRecipients");
			recipientCriteria.add(Restrictions.eq("id.contact", contact));
		}

		return crit;

	}

}
