/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import gr.scriptum.dao.ProtocolDAO;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.eprotocol.service.ProtocolService;
import gr.scriptum.eprotocol.util.Callback;
import gr.scriptum.eprotocol.util.IConstants;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BatchRouteVM extends BaseVM {

	private static Log log = LogFactory.getLog(BatchRouteVM.class);

	public static final String PAGE = "batchRoute.zul";

	public static final String PARAM_BATCH_ROUTE_PROTOCOLS = "BATCH_ROUTE_PROTOCOLS";

	public static final String PARAM_BATCH_ROUTE_DEPARTMENTS = "BATCH_ROUTE_DEPARTMENTS";

	public static final String PARAM_BATCH_ROUTE_RECIPIENTS = "BATCH_ROUTE_RECIPIENTS";

	public static final String PARAM_PROTOCOL = "PROTOCOL";

	@WireVariable
	private ProtocolDAO protocolDAO;

	@WireVariable
	private ProtocolService protocolService;

	/* data binding fields */
	private Date routingDate;

	private List<ProtocolCorrespondent> batchRouteRecipients;

	private List<ProtocolCorrespondent> selectedBatchRouteRecipients;

	/* non accessible fields */
	private Protocol protocol;

	private List<Protocol> batchRouteProtocols;

	private List<Department> batchRouteDepartments;

	private Callback callback = null;

	@Init
	public void init(@ContextParam(ContextType.EXECUTION) Execution execution) {

		// setup callback, if any
		// Execution execution = Executions.getCurrent();
		Map<?, ?> arg = execution.getArg();
		callback = (Callback) arg.get(IConstants.PARAM_CALLBACK);
		protocol = (Protocol) arg.get(PARAM_PROTOCOL);
		batchRouteProtocols = (List<Protocol>) arg.get(PARAM_BATCH_ROUTE_PROTOCOLS);
		batchRouteDepartments = (List<Department>) arg.get(PARAM_BATCH_ROUTE_DEPARTMENTS);
		batchRouteRecipients = (List<ProtocolCorrespondent>) arg.get(PARAM_BATCH_ROUTE_RECIPIENTS);
		if(batchRouteRecipients!=null) {
			selectedBatchRouteRecipients = new ArrayList<ProtocolCorrespondent>();
			selectedBatchRouteRecipients.addAll(batchRouteRecipients);
		}
		// init routing date to today
		routingDate = new Date();
	}

	@Command
	public void confirmBatchRoute(@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (protocol != null) {
				// route single protocol
				protocolService.routeProtocol(protocol, selectedBatchRouteRecipients, routingDate, getUserInSession());
			} else {
				// batch route protocols
				protocolService.batchRouteProtocols(batchRouteProtocols, batchRouteDepartments, routingDate, getUserInSession());
			}

			Messagebox.show(Labels.getLabel("success"), Labels.getLabel("success.title"), Messagebox.OK,
					Messagebox.INFORMATION);

			if (callback != null) {
				// notify caller (if any)
				BindUtils.postGlobalCommand(null, null, callback.getEvent(), null);
			}

		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("error"), Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
			return;
		} finally {
			view.detach();
		}
	}

	public Date getRoutingDate() {
		return routingDate;
	}

	public void setRoutingDate(Date routingDate) {
		this.routingDate = routingDate;
	}

	public List<ProtocolCorrespondent> getBatchRouteRecipients() {
		return batchRouteRecipients;
	}

	public void setBatchRouteRecipients(List<ProtocolCorrespondent> batchRouteRecipients) {
		this.batchRouteRecipients = batchRouteRecipients;
	}

	public List<ProtocolCorrespondent> getSelectedBatchRouteRecipients() {
		return selectedBatchRouteRecipients;
	}

	public void setSelectedBatchRouteRecipients(List<ProtocolCorrespondent> selectedBatchRouteRecipients) {
		this.selectedBatchRouteRecipients = selectedBatchRouteRecipients;
	}

}
