/**
 * 
 */
package gr.scriptum.csv;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gr.scriptum.domain.TaskType;

/**
 * @author aanagnostopoulos
 *
 */
public class TaskTypeConverter extends BaseCSVConverter implements
		ICSVConverter<TaskType> {

	@Override
	public File exportTo(List<TaskType> instances, String filename)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskType> importFrom(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskType> importFrom(List<String> lines) {
		// TODO Auto-generated method stub
		return null;
	}

}
