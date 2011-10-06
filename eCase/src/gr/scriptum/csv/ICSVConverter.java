/**
 * 
 */
package gr.scriptum.csv;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author aanagnostopoulos
 *
 */
public interface ICSVConverter<T> {

	File exportTo(List<T> instances, String filename) throws IOException;
	
	List<T> importFrom(File file) throws IOException;
	
	List<T> importFrom(List<String> lines);
}
