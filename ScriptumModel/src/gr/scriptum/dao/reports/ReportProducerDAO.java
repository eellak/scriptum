package gr.scriptum.dao.reports;

import java.util.List;
import gr.scriptum.domain.Users;

public interface ReportProducerDAO {
	
	public Integer      countReportRows(Users user);
	public List         createReport(Users user,Integer firstResult, Integer maxResults);

}
