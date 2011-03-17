/**
 * 
 */
package gr.scriptum.eprotocol.csv;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gr.scriptum.domain.CompanyType;

/**
 * @author aanagnostopoulos
 *
 */
public class CompanyTypeConverter extends BaseCSVConverter implements
		ICSVConverter<CompanyType> {

	@Override
	public File exportTo(List<CompanyType> instances, String filename)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompanyType> importFrom(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompanyType> importFrom(List<String> lines) {
		// TODO Auto-generated method stub
		return null;
	}

}
