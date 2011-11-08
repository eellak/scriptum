/**
 * 
 */
package gr.scriptum.csv;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gr.scriptum.domain.Parameter;

/**
 * @author aanagnostopoulos
 *
 */
public class ParameterConverter extends BaseCSVConverter implements
		ICSVConverter<Parameter> {

	@Override
	public File exportTo(List<Parameter> instances, String filename)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parameter> importFrom(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parameter> importFrom(List<String> lines) {
		// TODO Auto-generated method stub
		return null;
	}

}
