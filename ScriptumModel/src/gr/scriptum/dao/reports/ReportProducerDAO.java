package gr.scriptum.dao.reports;

import java.util.List;
import gr.scriptum.domain.Users;

/**
 * @author Michael Mountrakis mountrakis@uit.gr
 * Date: Dec 1, 2011
 * Project: SCRIPTUM http://www.scriptum.gr
 * http://www.uit.gr
 */
public interface ReportProducerDAO {
	
	public Integer      countReportRows(Users user);
	public List         createReport(Users user,Integer firstResult, Integer maxResults);

}
