/**
 * 
 */
package gr.scriptum.eprotocol.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gr.scriptum.dao.CompanyDAO;
import gr.scriptum.dao.CompanyTypeDAO;
import gr.scriptum.dao.ContactDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.CompanyType;
import gr.scriptum.domain.Contact;

/**
 * @author aanagnostopoulos
 * 
 */
public class ContactConverter extends BaseCSVConverter implements
		ICSVConverter<Contact> {

	private static Log log = LogFactory.getLog(ContactConverter.class);

	@Override
	public File exportTo(List<Contact> instances, String filename)
			throws IOException {
		File file = new File(filename);
		Writer out = new BufferedWriter(new FileWriter(file));
		try {

			for (Contact contact : instances) {

				out.write(StringUtils.trimToEmpty(contact.getSurname()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getName()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getMiddlename()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getCompany()
						.getName()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getCompany()
						.getCompanyType().getName()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getCompany()
						.getIsGoverment().toString()));
				out.write(COL_DELIM);
				
				out.write(StringUtils.trimToEmpty(contact.getStreet()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getStreetNo()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getCity()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getPrefecture()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getPostcode()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getTelephone()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getMobile()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getFax()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getEmail()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(contact.getWeb()));
				out.write(LINE_DELIM);

			}

		} finally {
			out.close();
		}
		return file;

	}

	@Override
	public List<Contact> importFrom(File file) throws IOException {
		List<String> lines = readFileLines(file);
		return importFrom(lines);
	}

	@Override
	public List<Contact> importFrom(List<String> lines) {

		List<Contact> contacts = new ArrayList<Contact>();
		List<Company> companies = new ArrayList<Company>();
		List<CompanyType> companyTypes = new ArrayList<CompanyType>();

		ContactDAO contactDAO = new ContactDAO();
		CompanyDAO companyDAO = new CompanyDAO();
		CompanyTypeDAO companyTypeDAO = new CompanyTypeDAO();

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			String[] tokens = StringUtils.splitPreserveAllTokens(line,
					COL_DELIM);

			String surname = tokens[0];
			String name = tokens[1];
			String middlename = tokens[2];

			// check if already added by this method
			boolean found = false;
			for (Contact contact : contacts) {
				if (contact.getSurname().equals(surname)
						&& contact.getName().equals(name)
						&& contact.getMiddlename().equals(middlename)) {
					found = true;
					break;
				}
			}

			if (found) {
				log.warn((i + 1) + "/" + lines.size()
						+ "- Contact already added:" + name);
				continue; // skip to next node
			}

			// check if already exists in database
			Contact example = new Contact();
			example.setSurname(surname);
			example.setName(name);
			example.setMiddlename(middlename);
			Integer hits = contactDAO.countByExample(example);
			if (hits > 0) {
				log.warn((i + 1) + "/" + lines.size()
						+ "- Contact already exists:" + name);
				continue; // skip to next node
			}

			Company assignedCompany = null;
			String companyName = tokens[3];
			// check if company already added by this method
			for (Company company : companies) {
				if (company.getName().equals(companyName)) {
					assignedCompany = company; // use
					break;
				}
			}

			if (assignedCompany == null) { // search local database
				Company companyExample = new Company();
				companyExample.setName(companyName);
				List<Company> results = companyDAO
						.findByExample(companyExample);
				if (results.isEmpty()) { // create new company

					CompanyType assignedCompanyType = null;
					String companyTypeName = tokens[4];
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
						List<CompanyType> companyTypeResults = companyTypeDAO
								.findByExample(companyTypeExample);
						if (companyTypeResults.isEmpty()) { // create new
															// company type

							companyTypeDAO.makePersistent(companyTypeExample);
							companyTypes.add(companyTypeExample);
							assignedCompanyType = companyTypeExample; // use

						} else { // use existing (assume only one hit result be
									// returned)
							assignedCompanyType = companyTypeResults.get(0);
						}
					}

					Company company = new Company();
					company.setName(companyName);
					company.setCompanyType(assignedCompanyType);
					company.setIsGoverment(Boolean.valueOf(tokens[5]));

					companyDAO.makePersistent(company);
					companies.add(company);
					assignedCompany = company; // use

				} else {// use existing (assume only one hit result be returned)
					assignedCompany = results.get(0);
				}
			}

			Contact contact = new Contact();
			contact.setSurname(surname);
			contact.setName(name);
			contact.setMiddlename(middlename);
			contact.setCompany(assignedCompany);
			contact.setStreet(tokens[6]);
			contact.setStreetNo(tokens[7]);
			contact.setCity(tokens[8]);
			contact.setPrefecture(tokens[9]);
			contact.setPostcode(tokens[10]);
			contact.setTelephone(tokens[11]);
			contact.setMobile(tokens[12]);
			contact.setFax(tokens[13]);
			contact.setEmail(tokens[14]);
			contact.setWeb(tokens[15]);

			contactDAO.makePersistent(contact);
			contacts.add(contact);
		}

		return contacts;
	}

}
