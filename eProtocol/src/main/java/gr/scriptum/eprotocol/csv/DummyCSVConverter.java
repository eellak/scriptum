/**
 * 
 */
package gr.scriptum.eprotocol.csv;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class DummyCSVConverter extends BaseCSVConverter implements ICSVConverter<Object> {

	@Override
	public File exportTo(List<Object> instances, String filename) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Object> importFrom(File file) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Object> importFrom(List<String> lines) {
		throw new UnsupportedOperationException();
	}

}
