/**
 * 
 */
package gr.scriptum.eprotocol.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gr.scriptum.dao.CompanyDAO;
import gr.scriptum.dao.CompanyTypeDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.CompanyType;
import gr.scriptum.domain.DocumentType;

/**
 * @author aanagnostopoulos
 * 
 */
public class CompanyConverter extends BaseCSVConverter implements
		ICSVConverter<Company> {

	private static Log log = LogFactory.getLog(CompanyConverter.class);

	@Override
	public File exportTo(List<Company> instances, String filename)
			throws IOException {
		File file = new File(filename);
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter out = new OutputStreamWriter(fos, ENCODING); 
//		Writer out = new BufferedWriter(new FileWriter(file));
		try {

			for (Company company : instances) {

				out.write(StringUtils.trimToEmpty(company.getName()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getCompanyType()
						.getName()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getVatNo()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getIrs()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getStreet()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getStreetNo()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getCity()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getPrefecture()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getPostcode()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getTelephone()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getMobile()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getFax()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getEmail()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getWeb()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(company.getWebService()));
				out.write(COL_DELIM);

				out.write(company.getIsGoverment().toString());
				out.write(LINE_DELIM);

			}

		} finally {
			out.close();
		}
		return file;

	}

	@Override
	public List<Company> importFrom(File file) throws IOException {
		List<String> lines = readFileLines(file);
		return importFrom(lines);
	}

	@Override
	public List<Company> importFrom(List<String> lines) {

		List<Company> companies = new ArrayList<Company>();
		List<CompanyType> companyTypes = new ArrayList<CompanyType>();

		CompanyDAO companyDAO = new CompanyDAO();
		CompanyTypeDAO companyTypeDAO = new CompanyTypeDAO();

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			String[] tokens = StringUtils.splitPreserveAllTokens(line,
					COL_DELIM);
			String name = tokens[0];

			// check if already added by this method
			boolean found = false;
			for (Company company : companies) {
				if (company.getName().equals(name)) {
					found = true;
					break;
				}
			}
			if (found) {
				log.warn((i+1) + "/" + lines.size() + "- Name already added:"
						+ name);
				continue; // skip to next node

			}
			// check if already exists in database
			Company example = new Company();
			example.setName(name);
			Integer hits = companyDAO.countByExample(example);
			if (hits > 0) {
				log.warn((i+1) + "/" + lines.size() + "- Name already exists:"
						+ name);
				continue; // skip to next node
			}

			CompanyType assignedCompanyType = null;
			String companyTypeName = tokens[1];
			// check if company type already added by this method
			for (CompanyType companyType : companyTypes) {
				if (companyType.getName().equals(companyTypeName)) {
					assignedCompanyType = companyType; // use
					break;
				}
			}

			if (assignedCompanyType == null) { // search local database
				CompanyType companyTypeExample = new CompanyType();
				companyTypeExample.setName(companyTypeName);
				List<CompanyType> results = companyTypeDAO
						.findByExample(companyTypeExample);
				if (results.isEmpty()) { // create new company type

					companyTypeDAO.makePersistent(companyTypeExample);
					companyTypes.add(companyTypeExample);
					assignedCompanyType = companyTypeExample; // use

				} else { // use existing (assume only one hit result be returned)
					assignedCompanyType = results.get(0);
				}
			}

			Company company = new Company();
			company.setName(name);
			company.setCompanyType(assignedCompanyType);
			company.setVatNo(tokens[2]);
			company.setIrs(tokens[3]);
			company.setStreet(tokens[4]);
			company.setStreetNo(tokens[5]);
			company.setCity(tokens[6]);
			company.setPrefecture(tokens[7]);
			company.setPostcode(tokens[8]);
			company.setTelephone(tokens[9]);
			company.setMobile(tokens[10]);
			company.setFax(tokens[11]);
			company.setEmail(tokens[12]);
			company.setWeb(tokens[13]);
			company.setWebService(tokens[14]);
			company.setIsGoverment(Boolean.valueOf(tokens[15]));

			companyDAO.makePersistent(company);
			companies.add(company);

		}
		
		return companies;

	}

}
