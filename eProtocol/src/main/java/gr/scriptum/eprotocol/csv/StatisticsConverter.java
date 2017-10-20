/**
 * 
 */
package gr.scriptum.eprotocol.csv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class StatisticsConverter extends BaseCSVConverter implements ICSVConverter<Object[]> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see gr.scriptum.eprotocol.csv.ICSVConverter#exportTo(java.util.List,
	 * java.lang.String)
	 */
	@Override
	public File exportTo(List<Object[]> instances, String filename) throws IOException {
		File file = new File(filename);
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter out = new OutputStreamWriter(fos, ENCODING);
		try {

			for (Object[] instance : instances) {
				for (int i = 0; i < instance.length-1; i++) {
					out.write(StringUtils.trimToEmpty(instance[i].toString()));
					out.write(COL_DELIM);
				}
				out.write(StringUtils.trimToEmpty(instance[instance.length-1].toString()));
				out.write(LINE_DELIM);
			}
			
		} finally {
			out.close();
		}
		return file;

	}

	public File exportTo(List<Object[]> instances, String filename, String encoding) throws IOException {
		File file = new File(filename);
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter out = new OutputStreamWriter(fos, encoding);
		try {

			for (Object[] instance : instances) {
				for (int i = 0; i < instance.length-1; i++) {
					out.write(StringUtils.trimToEmpty(instance[i].toString()));
					out.write(COL_DELIM);
				}
				out.write(StringUtils.trimToEmpty(instance[instance.length-1].toString()));
				out.write(LINE_DELIM);
			}
			
		} finally {
			out.close();
		}
		return file;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gr.scriptum.eprotocol.csv.ICSVConverter#importFrom(java.io.File)
	 */
	@Override
	public List<Object[]> importFrom(File file) throws IOException {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gr.scriptum.eprotocol.csv.ICSVConverter#importFrom(java.util.List)
	 */
	@Override
	public List<Object[]> importFrom(List<String> lines) {
		throw new UnsupportedOperationException();
	}

}
