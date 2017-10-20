/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import gr.scriptum.dao.CompanyDAO;
import gr.scriptum.dao.CompanyTypeDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.CompanyType;
import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.security.SecurityUtil;
import gr.scriptum.eprotocol.util.Permission;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CompanyVM extends GenericEntityVM<Company, Integer, CompanyDAO> {

	public static final String PAGE = "company.zul";

	private List<CompanyType> companyTypes = null;

	@WireVariable
	private CompanyDAO companyDAO;

	@Init(superclass = true)
	public void initVM() throws Exception {

		CompanyTypeDAO companyTypeDAO = SpringUtil.getApplicationContext().getBean(CompanyTypeDAO.class);
		companyTypes = companyTypeDAO.findAll();
		if (entity.getId() == null) {
			entity.setIsGoverment(Boolean.FALSE);
		}
	}

	@Override
	@Command
	@NotifyChange("entity")
	public void saveEntity(@ContextParam(ContextType.VIEW) Window entityWin) throws Exception {

		entity.setName(StringUtils.trimToEmpty(entity.getName()));
		
		validateFields(entityWin);
		
		if (entity.getCode() != null) {
			Company example = new Company();
			example.setCode(entity.getCode());
			if (entity.getId() == null) {
				// new company, check if code exists
				Integer countByExample = companyDAO.countByExample(example);
				if (countByExample > 0) {
					Messagebox.show(Labels.getLabel("companyPage.codeAlreadyExists"), Labels.getLabel("error.title"),
							Messagebox.OK, Messagebox.ERROR);
					return;
				}
			} else {
				// existing user, check if code exists and belongs to different
				// user
				List<Company> existingCompanies = companyDAO.findByExample(example, null, null);
				if (existingCompanies.size() > 0) {
					for (Company existingCompany : existingCompanies) {
						companyDAO.evict(existingCompany);
						if (!existingCompany.getId().equals(entity.getId())) {
							Messagebox.show(Labels.getLabel("companyPage.codeAlreadyExists"),
									Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
							return;
						}
					}
				}
			}
		}
		
		Company example = new Company();
		example.setName(entity.getName());
		if (entity.getId() == null) {
			// new company, check if name exists
			Integer countByExample = companyDAO.countByExample(example);
			if (countByExample > 0) {
				Messagebox.show(Labels.getLabel("companyPage.nameAlreadyExists"), Labels.getLabel("error.title"),
						Messagebox.OK, Messagebox.ERROR);
				return;
			}
		} else {
			// existing user, check if name exists and belongs to different company
			List<Company> existingCompanies = companyDAO.findByExample(example, null, null);
			if (existingCompanies.size() > 0) {
				for (Company existingCompany : existingCompanies) {
					companyDAO.evict(existingCompany);
					if (!existingCompany.getId().equals(entity.getId())) {
						Messagebox.show(Labels.getLabel("companyPage.nameAlreadyExists"),
								Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
			}
		}		
		
		super.saveEntity(entityWin);
	}

	public boolean isSaveEnabled() throws Exception {
		if (isEntityNotCreated()) {
			return true;
		}
		return SecurityUtil.isAllGranted(Permission.EDIT_COMPANY.toString());
	}

	public boolean isDeleteEnabled() throws Exception {
		if (isEntityNotCreated()) {
			return false;
		}
		return SecurityUtil.isAllGranted(Permission.EDIT_COMPANY.toString());
	}

	public List<CompanyType> getCompanyTypes() {
		return companyTypes;
	}

	public void setCompanyTypes(List<CompanyType> companyTypes) {
		this.companyTypes = companyTypes;
	}

}
