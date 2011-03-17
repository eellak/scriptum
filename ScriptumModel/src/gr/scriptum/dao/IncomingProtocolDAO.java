/**
 * 
 */
package gr.scriptum.dao;

import gr.scriptum.domain.Contact;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.IncomingProtocol;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * @author aanagnostopoulos
 * 
 */
public class IncomingProtocolDAO extends ProtocolDAO<IncomingProtocol, Integer> {

	private static Log log = LogFactory.getLog(IncomingProtocolDAO.class);

	@Override
	protected Criteria buildSearchCriteria(String protocolNumber, Date from,
			Date to, String subject, String keywords,
			DistributionMethod distributionMethod, Contact contact) {
		Criteria crit = super.buildSearchCriteria(protocolNumber, from, to,
				subject, keywords, distributionMethod, contact);

		if (contact != null) {
			crit.add(Restrictions.eq("contact", contact));
		}
		return crit;

	}

}
