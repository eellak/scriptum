package gr.scriptum.eprotocol.controller;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;

import gr.scriptum.dao.CompanyDAO;
import gr.scriptum.dao.CompanyTypeDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.CompanyType;

public class CompanyController extends
		GenericEntityController<Company, CompanyDAO> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8200233601222034212L;

	public static final String PAGE = "company.zul";
	
	private Checkbox isGovermentChkbx = null;
	
	private List<CompanyType> companyTypes = null;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		CompanyTypeDAO companyTypeDAO = new CompanyTypeDAO();
		companyTypes = companyTypeDAO.findAll();
		
		if(entity!=null && entity.getIsGoverment()!=null) {
			isGovermentChkbx.setChecked(entity.getIsGoverment());
		}
	}
	
	@Override
	public void onClick$newBtn() throws Exception {
		super.onClick$newBtn();
		isGovermentChkbx.setChecked(false);
	}
	
	@Override
	public void onClick$saveBtn() throws Exception {
		entity.setIsGoverment(isGovermentChkbx.isChecked());
		super.onClick$saveBtn();
	}

	public List<CompanyType> getCompanyTypes() {
		return companyTypes;
	}

	public void setCompanyTypes(List<CompanyType> companyTypes) {
		this.companyTypes = companyTypes;
	}
}
