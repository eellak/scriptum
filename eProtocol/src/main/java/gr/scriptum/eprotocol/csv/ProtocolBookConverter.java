/**
 * 
 */
package gr.scriptum.eprotocol.csv;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gr.scriptum.domain.ProtocolBook;

/**
 * @author aanagnostopoulos
 * 
 */
public class ProtocolBookConverter extends BaseCSVConverter implements
		ICSVConverter<ProtocolBook> {

	@Override
	public File exportTo(List<ProtocolBook> instances, String filename)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProtocolBook> importFrom(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProtocolBook> importFrom(List<String> lines) {
		// TODO Auto-generated method stub
		return null;
	}

}
