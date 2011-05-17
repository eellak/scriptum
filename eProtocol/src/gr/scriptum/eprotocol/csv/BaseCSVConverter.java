/**
 * 
 */
package gr.scriptum.eprotocol.csv;

import gr.scriptum.domain.Company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author aanagnostopoulos
 * 
 */
public abstract class BaseCSVConverter{

	public static final String COL_DELIM = ";";

	public static final String LINE_DELIM = "\n";
	
	public static final String ENCODING = "UTF-8";

	protected List<String> readFileLines(File file) throws IOException {

		List<String> lines = new ArrayList<String>();

		BufferedReader bufRdr = new BufferedReader(new FileReader(file));
		String line = null;

		// read each line of text file
		while ((line = bufRdr.readLine()) != null) {
			lines.add(line);
		}

		// close the file
		bufRdr.close();

		return lines;
	}

}

