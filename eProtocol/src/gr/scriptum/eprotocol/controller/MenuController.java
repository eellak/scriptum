/**
 * 
 */
package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.DiavgeiaDecisionTypeDAO;
import gr.scriptum.dao.DiavgeiaSubjectGroupDAO;
import gr.scriptum.domain.DiavgeiaDecisionType;
import gr.scriptum.domain.DiavgeiaSubjectGroup;
import gr.scriptum.eprotocol.diavgeia.DiavgeiaUpdaterConfig;
import gr.scriptum.eprotocol.diavgeia.DiavgeiaUpdaterImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Messagebox;

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

	public void onClick$diavgeiaMnItm() throws InterruptedException {
		DiavgeiaUpdaterConfig config = new DiavgeiaUpdaterConfig();
		DiavgeiaUpdaterImpl diavgeiaUpdater = new DiavgeiaUpdaterImpl(config);

		try {

			diavgeiaUpdater.updateFromDiavgeia();

		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("error"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		ArrayList<DiavgeiaDecisionType> decisionTypes = diavgeiaUpdater
				.getEidiApofaseon();
		ArrayList<DiavgeiaSubjectGroup> subjectGroups = diavgeiaUpdater
				.getThematikesEnotites();

		DiavgeiaDecisionTypeDAO diavgeiaDecisionTypeDAO = new DiavgeiaDecisionTypeDAO();
		DiavgeiaSubjectGroupDAO diavgeiaSubjectGroupDAO = new DiavgeiaSubjectGroupDAO();

		List<DiavgeiaDecisionType> existingDecisionTypes = diavgeiaDecisionTypeDAO
				.findAll();
		for (DiavgeiaDecisionType existingDecisionType : existingDecisionTypes) {
			diavgeiaDecisionTypeDAO.delete(existingDecisionType);
		}

		List<DiavgeiaSubjectGroup> existingSubjectGroups = diavgeiaSubjectGroupDAO
				.findAll();
		for (DiavgeiaSubjectGroup existingDiavgeiaSubjectGroup : existingSubjectGroups) {
			diavgeiaSubjectGroupDAO.delete(existingDiavgeiaSubjectGroup);
		}

		for (DiavgeiaDecisionType decisionType : decisionTypes) {
			diavgeiaDecisionTypeDAO.makePersistent(decisionType);
		}

		for (DiavgeiaSubjectGroup diavgeiaSubjectGroup : subjectGroups) {
			diavgeiaSubjectGroupDAO.makePersistent(diavgeiaSubjectGroup);
		}

		Messagebox.show(Labels.getLabel("success"),
				Labels.getLabel("success.title"), Messagebox.OK,
				Messagebox.INFORMATION);
	}
}
