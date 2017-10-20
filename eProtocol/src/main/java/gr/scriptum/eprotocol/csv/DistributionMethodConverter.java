/**
 * 
 */
package gr.scriptum.eprotocol.csv;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gr.scriptum.domain.DistributionMethod;

/**
 * @author aanagnostopoulos
 *
 */
public class DistributionMethodConverter extends BaseCSVConverter implements
		ICSVConverter<DistributionMethod> {

	@Override
	public File exportTo(List<DistributionMethod> instances, String filename)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DistributionMethod> importFrom(File file)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DistributionMethod> importFrom(List<String> lines) {
		// TODO Auto-generated method stub
		return null;
	}

}
