/**
 * 
 */
package gr.scriptum.eprotocol.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.dao.CorrespondentGroupDAO;
import gr.scriptum.domain.CorrespondentGroup;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.Users;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Service
public class CorrespondentGroupService {

	@Autowired
	private CorrespondentGroupDAO correspondentGroupDAO;
	
	@Transactional
	public CorrespondentGroup create(CorrespondentGroup correspondentGroup, Users user) {
		correspondentGroup.setCreateDt(new Date());
		correspondentGroup.setCreateUserId(user.getId());
		return correspondentGroupDAO.makePersistent(correspondentGroup);
	}
	
	@Transactional
	public CorrespondentGroup update(CorrespondentGroup correspondentGroup, Users user) {
		correspondentGroup.setUpdateDt(new Date());
		correspondentGroup.setUpdateUserId(user.getId());
		return correspondentGroupDAO.merge(correspondentGroup);
	}
	
}
