/**
 * 
 */
package gr.scriptum.eprotocol.csv;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gr.scriptum.domain.CorrespondentGroup;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class CorrespondentGroupConverter extends BaseCSVConverter implements ICSVConverter<CorrespondentGroup> {

	@Override
	public File exportTo(List<CorrespondentGroup> instances, String filename) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<CorrespondentGroup> importFrom(File file) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<CorrespondentGroup> importFrom(List<String> lines) {
		throw new UnsupportedOperationException();
	}

}
