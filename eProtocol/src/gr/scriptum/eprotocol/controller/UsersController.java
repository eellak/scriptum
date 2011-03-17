/**
 * 
 */
package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.csv.UserConverter;

/**
 * @author aanagnostopoulos
 * 
 */
public class UsersController extends GenericSearchController<Users, UsersDAO, UserConverter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8215460004294422710L;

	@Override
	public String getEntityPage() {
		return UserController.PAGE;
	}

}
