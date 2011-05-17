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

import gr.scriptum.dao.DocumentTypeDAO;
import gr.scriptum.domain.DocumentType;

/**
 * @author aanagnostopoulos
 * 
 */
public class DocumentTypeConverter extends BaseCSVConverter implements
		ICSVConverter<DocumentType> {

	private static Log log = LogFactory.getLog(DocumentTypeConverter.class);

	@Override
	public File exportTo(List<DocumentType> instances, String filename)
			throws IOException {

		File file = new File(filename);
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter out = new OutputStreamWriter(fos, ENCODING); 
//		Writer out = new BufferedWriter(new FileWriter(file));
		try {

			for (DocumentType documentType : instances) {

				out.write(StringUtils.trimToEmpty(documentType.getName()));
				out.write(COL_DELIM);

				out.write(StringUtils.trimToEmpty(documentType.getDescription()));
				out.write(COL_DELIM);

				out.write(documentType.getDays() != null ? StringUtils
						.trimToEmpty(documentType.getDays().toString()) : "");
				out.write(LINE_DELIM);

			}

		} finally {
			out.close();
		}
		return file;

	}

	@Override
	public List<DocumentType> importFrom(File file) throws IOException {
		List<String> lines = readFileLines(file);
		return importFrom(lines);
	}

	@Override
	public List<DocumentType> importFrom(List<String> lines) {

		List<DocumentType> documentTypes = new ArrayList<DocumentType>();
		DocumentTypeDAO documentTypeDAO = new DocumentTypeDAO();

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			String[] tokens = StringUtils.splitPreserveAllTokens(line,
					COL_DELIM);
			String name = tokens[0];

			// check if already added by this method
			boolean found = false;
			for (DocumentType documentType : documentTypes) {
				if (documentType.getName().equals(name)) {
					found = true;
					break;
				}
			}
			if (found) {
				log.warn((i+1) + "/" + lines.size() + "- Name already added:"
						+ name);
				continue; //skip to next node
			}

			// check if already exists in database
			DocumentType example = new DocumentType();
			example.setName(name);
			Integer hits = documentTypeDAO.countByExample(example);
			if (hits > 0) {
				log.warn((i+1) + "/" + lines.size() + "- Name already exists:"
						+ name);
				continue; //skip to next node
			}

			DocumentType documentType = new DocumentType();
			documentType.setName(name);
			documentType.setDescription(tokens[1]);
			try {
				Integer days = Integer.valueOf(tokens[2]);
				documentType.setDays(days);
			} catch (NumberFormatException e) {
				log.warn((i+1) + "/" + lines.size() + "- Invalid days:" + tokens[2]);
			}

			documentTypeDAO.makePersistent(documentType);
			documentTypes.add(documentType);
		}

		return documentTypes;

	}

}
