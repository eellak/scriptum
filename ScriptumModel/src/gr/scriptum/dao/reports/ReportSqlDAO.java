package gr.scriptum.dao.reports;

import gr.scriptum.dao.GenericDAO;
import gr.scriptum.domain.Users;
import gr.scriptum.domain.reports.ReportSqlDomain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;


/**
 * @author Michael Mountrakis mountrakis@uit.gr
 * Date: Dec 5, 2011
 * Project: SCRIPTUM http://www.scriptum.gr
 * http://www.uit.gr
 */
public class ReportSqlDAO  extends GenericDAO<Object, Integer> 
implements ReportProducerDAO{

	private static Log log = LogFactory.getLog(ReportSqlDAO.class);
	String sqlQuery;
	ReportSqlDomain genericDomain = null;

	

	public void setSqlQuery(String sqlquery) {
		sqlQuery = sqlquery.replace(",", " , ");
		String words[] = sqlQuery.split(" ");
		
		
		ArrayList<String> fields = new ArrayList<String>();
		boolean nextIsField = false;
        for(int i=0; i<words.length; i++){
			//System.out.println("word   : [" + words[i]  + "]");
			
			if( nextIsField ){
			   // System.out.println("Field [" + words[i].replace(",", "") +"]");
			    fields.add(words[i].replace(",", ""));
			}
			
			if( words[i].equalsIgnoreCase("as")){
				nextIsField = true;
			}else if ( words[i].equalsIgnoreCase("from"))
				break;
			else
				nextIsField = false;
		}
        
        genericDomain = new ReportSqlDomain();
        genericDomain.setSelectedFields(fields);
	}
	
	
	
	@Override
	public Integer countReportRows(Users user) {
		log.info("countReportRows() started.");
		
		String select = "SELECT COUNT(*) AS rcount FROM (" + sqlQuery;
		select += " ) AS rset";      
		
		Query query = getSession().createSQLQuery(select);
		//query.setParameter("myUserId", user.getId());
		
		log.info("countReportRows() finished.");
		return ((BigInteger) query.uniqueResult()).intValue();
	}

	
	@Override
	public List<ReportSqlDomain> createReport(Users user, Integer firstResult,
			Integer maxResults) {
		log.info("createReport() started.");
        SQLQuery query = getSession().createSQLQuery(sqlQuery );
       
        String fields [] = genericDomain.getSelectedFields();
		for(int i=0; i<fields.length; i++){
			//System.out.println("Field [" + fields[i] +"]");
			query.addScalar( fields[i]);
		}
		
	    List<?> list = query.list();
		log.info("createReport() Report fetched : " + list.size());
		
		List<ReportSqlDomain> results = new ArrayList<ReportSqlDomain>();
		for (Object result : list) {
			Object[] row = (Object[]) result;
			ReportSqlDomain reportDomain = new ReportSqlDomain();
			reportDomain.setSelectedFields(fields);
			reportDomain.setFieldsValues(row);
			results.add(reportDomain);
		}

		log.info("createReport() finished.");
		return  results;

	}

	public String getSqlQuery() {
		return sqlQuery;
	}
		
		
}
