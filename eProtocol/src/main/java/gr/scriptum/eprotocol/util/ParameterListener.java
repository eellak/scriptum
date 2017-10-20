/**
 * 
 */
package gr.scriptum.eprotocol.util;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppCleanup;
import org.zkoss.zk.ui.util.WebAppInit;
import org.zkoss.zkplus.spring.SpringUtil;

import gr.scriptum.dao.ParameterDAO;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos
 *         </a>
 *
 */
public class ParameterListener implements WebAppInit, WebAppCleanup {

	private static Log log = LogFactory.getLog(ParameterListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zkoss.zk.ui.util.WebAppCleanup#cleanup(org.zkoss.zk.ui.WebApp)
	 */
	@Override
	public void cleanup(WebApp wapp) throws Exception {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zkoss.zk.ui.util.WebAppInit#init(org.zkoss.zk.ui.WebApp)
	 */
	@Override
	public void init(WebApp wapp) throws Exception {
		// TODO Auto-generated method stub
		log.info("Initializing application");
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(wapp.getServletContext());

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = applicationContext
				.getBean(LocalContainerEntityManagerFactoryBean.class);
		EntityManager entityManager = entityManagerFactoryBean.getNativeEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();

		log.info("Initializing application parameters");
		ParameterDAO parameterDAO = new ParameterDAO();
		parameterDAO.setEntityManager(entityManager);
		parameterDAO.setEntityManager(entityManager);
		Boolean isDossierFunctionalityEnabled = parameterDAO
				.getAsBoolean(IConstants.PARAM_ENABLE_DOSSIER_FUNCTIONALITY);
		// disable dossier funcitonality by default
		log.info(IConstants.PARAM_ENABLE_DOSSIER_FUNCTIONALITY + ":" + isDossierFunctionalityEnabled);
		wapp.setAttribute(IConstants.PARAM_ENABLE_DOSSIER_FUNCTIONALITY,
				isDossierFunctionalityEnabled == null ? false : isDossierFunctionalityEnabled);

		Boolean isPendingFunctionalityEnabled = parameterDAO
				.getAsBoolean(IConstants.PARAM_ENABLE_PENDING_FUNCTIONALITY);
		// disable pending functionality by default
		log.info(IConstants.PARAM_ENABLE_PENDING_FUNCTIONALITY + ":" + isPendingFunctionalityEnabled);
		wapp.setAttribute(IConstants.PARAM_ENABLE_PENDING_FUNCTIONALITY,
				isPendingFunctionalityEnabled == null ? false : isPendingFunctionalityEnabled);

		Boolean isDocumentUploadFunctionalityEnabled = parameterDAO
				.getAsBoolean(IConstants.PARAM_ENABLE_DOCUMENT_UPLOAD_FUNCTIONALITY);
		// enable document upload functionality by default
		log.info(IConstants.PARAM_ENABLE_DOCUMENT_UPLOAD_FUNCTIONALITY + ":" + isDocumentUploadFunctionalityEnabled);
		wapp.setAttribute(IConstants.PARAM_ENABLE_DOCUMENT_UPLOAD_FUNCTIONALITY,
				isDocumentUploadFunctionalityEnabled == null ? true : isDocumentUploadFunctionalityEnabled);

		if (isDocumentUploadFunctionalityEnabled) {
			Integer contentSearchResultsLimit = parameterDAO
					.getAsInteger(IConstants.PARAM_CONTENT_SEARCH_RESULTS_LIMIT);
			if (contentSearchResultsLimit == null) {
				throw new Exception(
						"Value not set for parameter:" + IConstants.PARAM_CONTENT_SEARCH_RESULTS_LIMIT.toString());
			}
			wapp.setAttribute(IConstants.PARAM_CONTENT_SEARCH_RESULTS_LIMIT, contentSearchResultsLimit);
		}

		entityManager.getTransaction().commit();
		entityManager.close();
	}

}
