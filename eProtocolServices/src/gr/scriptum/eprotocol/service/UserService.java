/**
 * 
 */
package gr.scriptum.eprotocol.service;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.dao.RoleDAO;
import gr.scriptum.dao.UserAssignmentDAO;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Role;
import gr.scriptum.domain.UserAssignment;
import gr.scriptum.domain.Users;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Service
public class UserService extends BaseService {

	@Autowired
	private UsersDAO usersDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private UserAssignmentDAO userAssignmentDAO;
	
	@Transactional
	public Users create(Users user, Integer creatorId) {

//		//refresh roles from database
//		for (Role role : user.getRoles()) {
//			roleDAO.refresh(role);
//		}
		Date now = new Date();
		user.setCreateDt(now);
		user.setCreateUser(creatorId.toString());
		user.setUpdateTs(now);
		user.setUpdateUser(creatorId.toString());
		usersDAO.makePersistent(user);

		//persist permissions
		Set<UserAssignment> userAssignments = user.getUserAssignments();
		for (UserAssignment userAssignment : userAssignments) {
			userAssignmentDAO.makePersistent(userAssignment);
		}
		return user;
	}
	
	@Transactional
	public void update(Users user, Set<UserAssignment> userAssignmentsToBeDeleted, Integer updaterId) {

		Date now = new Date();
		user.setUpdateTs(now);
		user.setUpdateUser(updaterId.toString());
		
		//refresh roles from database
		for (Role role : user.getRoles()) {
			roleDAO.refresh(role);
		}
		
		usersDAO.merge(user);

		//create new assignments
		Set<UserAssignment> userAssignments = user.getUserAssignments();
		for (UserAssignment userAssignment : userAssignments) {
			if(userAssignment.getId()==null) {
				userAssignmentDAO.makePersistent(userAssignment);
			}
		}
		
		//delete assignments marked accordingly
		for (UserAssignment userAssignment : userAssignmentsToBeDeleted) {
			userAssignmentDAO.refresh(userAssignment);
			userAssignmentDAO.delete(userAssignment);
		}
	}
	
	@Transactional
	public void delete(Users user) {
		
		usersDAO.refresh(user);
		
		//delete associated assignments
		Set<UserAssignment> userAssignments = user.getUserAssignments();
		for (UserAssignment userAssignment : userAssignments) {
			userAssignmentDAO.delete(userAssignment);
		}
		
		//TODO: delete associated user hierarchies
		
		//delete user
		//TODO: user deletion may leave 'orphan' protocol rows 
		usersDAO.delete(user);
	}
	
	@Transactional
	public Users changeUserPassword(Users user, String password) {
		user.setPassword(password);
		user.setUpdatePasswordDate(new Date());
		return usersDAO.merge(user);
	}
}
