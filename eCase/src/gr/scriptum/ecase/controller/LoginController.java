/**
 * 
 */
package gr.scriptum.ecase.controller;

import gr.scriptum.controller.BaseController;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Users;
import gr.scriptum.ecase.util.IConstants;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestLogin;
import gr.scriptum.eprotocol.ws.ResponseLogin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 * @author aanagnostopoulos
 * 
 */
public class LoginController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3341552957311678854L;

	private static Log log = LogFactory.getLog(LoginController.class);
	
	Window loginWin;

	private Users user = null;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		user = new Users();
		
		//clean user session
		session.removeAttribute(IConstants.KEY_USER);
		session.removeAttribute(IConstants.KEY_OKM_TOKEN);
		page.setAttribute(this.getClass().getSimpleName(), this);
	}

	public void onClick$loginBtn() throws InterruptedException {

		validateFields(loginWin);

		user.setIsDisabled(false);
		UsersDAO usersDAO = new UsersDAO();
		List<Users> users = usersDAO.findByExample(user, new String[] {});
		if (users.isEmpty() || users.size() > 1) {
			Messagebox.show(Labels.getLabel("loginPage.error"), Labels
					.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
			return;
		}
		user = users.get(0);
		session.setAttribute(IConstants.KEY_USER, user);

		// login to OpenKM and get token
		OkmProtocolDispatcherImpl service = getOkmDispatcher();

		RequestLogin loginReq = new RequestLogin();
		loginReq.setUsername(user.getUsername());
		loginReq.setPassword(user.getPassword());

		ResponseLogin respLogin = service.login(loginReq);
		if (respLogin.isError()) {
			log.error(respLogin.toString());
			Messagebox.show(Labels.getLabel("loginPage.error"), Labels
					.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
			return;
		}

		log.info(respLogin.getOkmToken());
		session.setAttribute(IConstants.KEY_OKM_TOKEN, respLogin.getOkmToken());

		Executions.getCurrent().sendRedirect("index.zul");

	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}
