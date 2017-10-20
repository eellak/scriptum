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
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import gr.scriptum.dao.DocumentTypeDAO;
import gr.scriptum.dao.ProtocolBookDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.DocumentType.ApplicableType;
import gr.scriptum.domain.ProtocolBook;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DocumentTypeVM extends GenericEntityVM<DocumentType, Integer, DocumentTypeDAO> {

	public static final String PAGE = "documentType.zul";

	private List<ProtocolBook> protocolBooks;

	private ApplicableType[] applicableTypes = ApplicableType.values();
	
	@WireVariable
	private DocumentTypeDAO documentTypeDAO;

	@Init(superclass = true)
	public void initVM() {
		ProtocolBookDAO protocolBookDAO = SpringUtil.getApplicationContext().getBean(ProtocolBookDAO.class);
		protocolBooks = protocolBookDAO.findAll();
		if (entity.getId() == null) {
			entity.setAllowOutgoingFromIncoming(Boolean.FALSE);
		}
	}

	@Override
	@Command
	@NotifyChange("entity")
	public void saveEntity(@ContextParam(ContextType.VIEW) Window entityWin) throws Exception {
		validateFields(entityWin);
		
		DocumentType example = new DocumentType();
		example.setCode(entity.getCode());
		if(entity.getId()==null) {
			//new documentType, check if code exists
			Integer countByExample = documentTypeDAO.countByExample(example);
			if(countByExample>0) {
				Messagebox.show(Labels.getLabel("documentTypePage.codeAlreadyExists"), Labels.getLabel("error.title"), Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}else {
			//existing user, check if username exists and belongs to different user 
			List<DocumentType> existingCompanies = documentTypeDAO.findByExample(example, null, null);
			if(existingCompanies.size()>0) {
				for (DocumentType existingCompany : existingCompanies) {
					documentTypeDAO.evict(existingCompany);
					if(!existingCompany.getId().equals(entity.getId())) {
						Messagebox.show(Labels.getLabel("documentTypePage.codeAlreadyExists"), Labels.getLabel("error.title"), Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}
			}
		}

		super.saveEntity(entityWin);
	}

	public List<ProtocolBook> getProtocolBooks() {
		return protocolBooks;
	}

	public void setProtocolBooks(List<ProtocolBook> protocolBooks) {
		this.protocolBooks = protocolBooks;
	}

	public ApplicableType[] getApplicableTypes() {
		return applicableTypes;
	}

}
