/**
 * 
 */
package gr.scriptum.csv;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gr.scriptum.domain.Users;

/**
 * @author aanagnostopoulos
 *
 */
public class UserConverter extends BaseCSVConverter implements ICSVConverter<Users> {

	@Override
	public File exportTo(List<Users> instances, String filename)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> importFrom(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> importFrom(List<String> lines) {
		// TODO Auto-generated method stub
		return null;
	}

}
