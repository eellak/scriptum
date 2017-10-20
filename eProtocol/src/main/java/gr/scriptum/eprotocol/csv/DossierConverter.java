/**
 * 
 */
package gr.scriptum.eprotocol.csv;

import gr.scriptum.domain.Dossier;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author aanagnostopoulos
 * 
 */
public class DossierConverter extends BaseCSVConverter implements
		ICSVConverter<Dossier> {

	@Override
	public File exportTo(List<Dossier> instances, String filename)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Dossier> importFrom(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Dossier> importFrom(List<String> lines) {
		// TODO Auto-generated method stub
		return null;
	}

}
