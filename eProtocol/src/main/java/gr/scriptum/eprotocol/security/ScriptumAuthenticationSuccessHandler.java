/**
 * 
 */
package gr.scriptum.eprotocol.security;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.vm.PasswordChangeVM;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Component
public class ScriptumAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	protected Log log = LogFactory.getLog(this.getClass());
	 
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    @Autowired
    private ParameterDAO parameterDAO;
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
      HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }
 
    protected void handle(HttpServletRequest request, 
      HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
 
        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
 
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
 
    /** Builds the target URL according to the logic defined in the main class Javadoc. */
    protected String determineTargetUrl(Authentication authentication) {
        
    	Integer updateInterval = parameterDAO.getAsInteger(IConstants.PARAM_USER_PASSWORD_EXPIRE_INTERVAL);
    	if(updateInterval==null) {
    		throw new IllegalAccessError("Parameter not set:"+IConstants.PARAM_USER_PASSWORD_EXPIRE_INTERVAL);
    	}

    	ScriptumUser principal = (ScriptumUser) authentication.getPrincipal();
        Users user = principal.getUser();

        Date controlDate = user.getUpdatePasswordDate()==null ? user.getCreateDt() : user.getUpdatePasswordDate();
        log.debug("logged in user:"+user.getUsername());
//        log.debug("last update date: "+controlDate);
        Calendar updateCalendar = Calendar.getInstance();
        updateCalendar.setTime(controlDate);
//        updateCalendar = DateUtils.truncate(updateCalendar, Calendar.MONTH);
        updateCalendar.add(Calendar.MONTH, updateInterval);
        log.debug("control date: "+updateCalendar.getTime());
        Calendar todayCalendar = Calendar.getInstance();
//        todayCalendar = DateUtils.truncate(todayCalendar, Calendar.DATE);
        log.debug("today date: "+todayCalendar.getTime());
        
        if(todayCalendar.after(updateCalendar)) {
        	log.debug("user password expired, prompting change");
        	return "/"+PasswordChangeVM.PAGE;
        }else {
        	log.debug("user password still valid, entering application");
        	return "/index.zul";
        }
        
    }
 
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
 
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
