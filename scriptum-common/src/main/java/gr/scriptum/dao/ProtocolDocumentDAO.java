/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.ProtocolDocument;

/**
 * @author aanagnostopoulos
 * 
 */
@Repository
public class ProtocolDocumentDAO extends GenericDAO<ProtocolDocument, Integer> {

	public static final String KEYWORDS_DELIMITER = ",";

	@Transactional(readOnly=true)
	public List<ProtocolDocument> findByOkmUuid(String okmUuid) {
		Query query = getSession().createQuery("from ProtocolDocument pd where pd.okmUuid = :okmUuid");
		query.setParameter("okmUuid", okmUuid);
		return query.list();
	}

	@Transactional(readOnly=true)
	public List<ProtocolDocument> findByOkmUuids(List<String> okmUuids) {
		Query query = getSession().createQuery("from ProtocolDocument pd where pd.okmUuid in :okmUuids");
		query.setParameterList("okmUuids", okmUuids);
		return query.list();
	}

}
