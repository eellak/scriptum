/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.service.UserService;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PasswordChangeVM extends BaseVM {

	public static final String PAGE = "passwordChange.zul";

	private static Log log = LogFactory.getLog(PasswordChangeVM.class);

	private String username;
	private String passwordOld;
	private String password;
	private String passwordRepeat;

	/* non accessible fields */
	private boolean expired = false;

	@WireVariable
	private UserService userService;

	@WireVariable
	private UsersDAO usersDAO;

	@Init
	public void init() {
		// if a user is found in session, then the VM was trigger by a password
		// expiration, otherwise it was manually invoked by the user
		expired = getUserInSession() != null;
		if (expired) {
			Messagebox.show(Labels.getLabel("passwordChangePage.expired"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Command
	public void changePassword(@ContextParam(ContextType.VIEW) Window win) throws Exception {

		validateFields(win);

		Users existingUser = null;
		List<Users> users = usersDAO.findByUsername(username, null, null);
		if (users.isEmpty()) {
			Messagebox.show(Labels.getLabel("passwordChangePage.error.invalidUsername"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}
		existingUser = users.get(0);

		if (!existingUser.getPassword().equals(passwordOld)) {
			Messagebox.show(Labels.getLabel("passwordChangePage.error.invalidOldPassword"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (!password.equals(passwordRepeat)) {
			Messagebox.show(Labels.getLabel("passwordChangePage.error.passwordMismatch"),
					Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (password.equals(passwordOld)) {
			Messagebox.show(Labels.getLabel("passwordChangePage.error.oldNewMatch"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if(existingUser.getCode()!=null && existingUser.getCode().equals(password)) {
			Messagebox.show(Labels.getLabel("passwordChangePage.error.codePasswordMatch"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		userService.changeUserPassword(existingUser, password);
		
		Messagebox.show(Labels.getLabel("passwordChangePage.success"), Labels.getLabel("success.title"),
				Messagebox.OK, Messagebox.INFORMATION, (e)->{Executions.sendRedirect("/logout");});
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordOld() {
		return passwordOld;
	}

	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

}
