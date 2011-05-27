/**
 * 
 */
package gr.scriptum.eprotocol.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.CheckEvent;

/**
 * @author aanagnostopoulos
 * 
 */
public class MenuController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1424573014985790006L;

	private static Log log = LogFactory.getLog(MenuController.class);

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		log.info("do after compose");
	}

	public void onClick$diavgeiaMnItm() {
		log.info("CLICK!");
		// TODO: implement diavgeia settings update
	}
}
