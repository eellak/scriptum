/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.Users;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DistributionMethodVM extends GenericEntityVM<DistributionMethod, Integer, DistributionMethodDAO> {

	public static final String PAGE = "distributionMethod.zul";

	@WireVariable
	private DistributionMethodDAO distributionMethodDAO;

	@Init(superclass = true)
	public void initVM() throws Exception {
		if (entity.getId() == null) {
			entity.setRequiresDescription(Boolean.FALSE);
			entity.setAutoFillRoutingDate(Boolean.FALSE);
		}
	}

	@Override
	@Command
	@NotifyChange("entity")
	public void saveEntity(@ContextParam(ContextType.VIEW) Window entityWin) throws Exception {

		validateFields(entityWin);

		DistributionMethod example = new DistributionMethod();
		example.setCode(entity.getCode());

		if (entity.getId() == null) {
			// new entity, check if code exists
			Integer countByExample = distributionMethodDAO.countByExample(example);
			if (countByExample > 0) {
				Messagebox.show(Labels.getLabel("distributionMethodPage.codeAlreadyExists"),
						Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
				return;
			}
		} else {
			// existing entity, check if code exists and belongs to different
			// entity
			List<DistributionMethod> existingEntities = distributionMethodDAO.findByExample(example, null, null);
			if (existingEntities.size() > 0) {
				for (DistributionMethod existingEntity : existingEntities) {
					distributionMethodDAO.evict(existingEntity);
					if (!existingEntity.getId().equals(entity.getId())) {
						Messagebox.show(Labels.getLabel("distributionMethodPage.codeAlreadyExists"),
								Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
			}
		}
		super.saveEntity(entityWin);
	}

}
