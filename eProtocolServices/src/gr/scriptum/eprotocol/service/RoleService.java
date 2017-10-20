/**
 * 
 */
package gr.scriptum.eprotocol.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.dao.RoleDAO;
import gr.scriptum.domain.Role;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Service
public class RoleService extends BaseService {

	@Autowired
	private RoleDAO roleDAO;

}
