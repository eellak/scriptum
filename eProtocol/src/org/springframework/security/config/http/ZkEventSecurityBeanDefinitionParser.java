/* ZkEventSecurityBeanDefinitionParser.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Oct 6, 2008 11:57:20 AM, Created by henrichen
}}IS_NOTE

Copyright (C) 2008 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under GPL Version 2.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.springframework.security.config.http;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.BeanIds;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.RegexUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.zkoss.spring.security.config.ZkBeanIds;
import org.zkoss.spring.security.config.ZkDesktopReuseFilter;
import org.zkoss.spring.security.config.ZkElements;
import org.zkoss.spring.security.config.ZkEventProcessDefinitionSourceBeanDefinitionParser;
import org.zkoss.spring.security.intercept.zkevent.EventProcessKey;
import org.zkoss.spring.security.intercept.zkevent.ZkEventProcessDefinitionSourceImpl;
import org.zkoss.spring.security.intercept.zkevent.ZkEventProcessInterceptor;
import org.zkoss.spring.security.ui.ZkAccessDeniedHandler;
import org.zkoss.spring.security.ui.ZkDisableSessionInvalidateFilter;
import org.zkoss.spring.security.ui.ZkEnableSessionInvalidateFilter;
import org.zkoss.spring.security.ui.ZkError403Filter;
import org.zkoss.spring.security.ui.ZkExceptionTranslationFilter;
import org.zkoss.spring.security.ui.ZkLoginOKFilter;
import org.zkoss.spring.security.ui.webapp.ZkAuthenticationEntryPoint;

/**
 * Sets up ZK Event security: filter stack and protected ZK Event in ZK event
 * processing.
 * 
 * @author henrichen
 * @since 1.0
 */
public class ZkEventSecurityBeanDefinitionParser implements
		BeanDefinitionParser {
	public static final String ATT_PATH_TYPE = "path-type";
	public static final String DEF_PATH_TYPE_ANT = "ant";
	public static final String OPT_PATH_TYPE_REGEX = "regex";

	// <intercept-event>
	public static final String ATT_PATH = "path";
	public static final String ATT_ZK_EVENT = "event";
	public static final String ATT_ACCESS_CONFIG = "access";

	// <zk-event>
	public static final String ATT_LOGIN_TEMPLATE = "login-template";
	public static final String ATT_LOGIN_TEMPLATE_CLOSE_DELAY = "login-template-close-delay";
	public static final String ATT_ERROR_TEMPLATE = "error-template";

	// <form-login>, for the default login template window
	public static final String ATT_TITLE = "title";
	public static final String ATT_WIDTH = "width";
	public static final String ATT_HEIGHT = "height";
	public static final String ATT_LOGIN_PAGE = "login-page";
	public static final String ATT_LOGIN_OK_URL = "login-ok-url";
	public static final String ATT_AUTHENTICATION_FAILURE_URL = "authentication-failure-url";
	public static final String ATT_FORCE_HTTPS = "force-https";

	public BeanDefinition parse(Element element, ParserContext pc) {
		// Filter for ZK desktop reuse (used in http -> https)
		addHttpFilter(pc, ZkBeanIds.ZK_DESKTOP_REUSE_FILTER,
				ZkDesktopReuseFilter.class);

		// Tell ZK engine not to invalidate ZK Session when http session
		// invalidated
		addHttpFilter(pc, ZkBeanIds.ZK_DISABLE_SESSION_INVALIDATE_FILTER,
				ZkDisableSessionInvalidateFilter.class);
		addHttpFilter(pc, ZkBeanIds.ZK_ENABLE_SESSION_INVALIDATE_FILTER,
				ZkEnableSessionInvalidateFilter.class);

		// zkLoginOKFilter
		registerLoginOKFilter(element, pc);

		// zkError403Filter
		registerZkError403Filter(element, pc);

		// zkExceptionTranslationFilter
		registerExceptionTranslationFilter(element, pc);

		// zkEventProcessInterceptor
		registerZkEventProcessInterceptor(element, pc);

		return null;
	}

	private RootBeanDefinition addHttpFilter(ParserContext pc, String beanid,
			Class cls) {
		final RootBeanDefinition rbd = new RootBeanDefinition(cls);
		pc.getRegistry().registerBeanDefinition(beanid, rbd);
		// ConfigUtils.addHttpFilter(pc, new RuntimeBeanReference(beanid));
		return rbd;
	}

	/**
	 * Registers LoginOkFilter that handles Ajax popup login display.
	 * 
	 * @param element
	 * @param pc
	 */
	@SuppressWarnings("unchecked")
	private void registerLoginOKFilter(Element element, ParserContext pc) {
		final BeanDefinitionBuilder builder = BeanDefinitionBuilder
				.rootBeanDefinition(ZkLoginOKFilter.class);

		RootBeanDefinition formLoginFilter = null;
		formLoginFilter = getStandardFilter(pc,
				UsernamePasswordAuthenticationFilter.class.getName());

		if (formLoginFilter != null) {
			PropertyValue dtUrl = formLoginFilter.getPropertyValues()
					.getPropertyValue("authenticationSuccessHandler");
			if (dtUrl != null) {
				final Object authSuccessHandler = dtUrl.getValue();
				if (authSuccessHandler != null
						&& authSuccessHandler instanceof RootBeanDefinition) {
					RootBeanDefinition successHandler = (RootBeanDefinition) authSuccessHandler;
					final String defaultTargetUrl = (String) successHandler
							.getPropertyValues()
							.getPropertyValue("defaultTargetUrl").getValue();
					if (StringUtils.hasText(defaultTargetUrl)) {
						builder.addPropertyValue("defaultTargetUrl",
								defaultTargetUrl);
					}
				}
			}
			PropertyValue afUrl = formLoginFilter.getPropertyValues()
					.getPropertyValue("authenticationFailureHandler");
			if (afUrl != null) {
				final Object authFailureHandler = afUrl.getValue();
				if (authFailureHandler != null
						&& authFailureHandler instanceof RootBeanDefinition) {
					RootBeanDefinition failureHandler = (RootBeanDefinition) authFailureHandler;
					final String authenticationFailureUrl = (String) failureHandler
							.getPropertyValues()
							.getPropertyValue("defaultFailureUrl").getValue();
					if (StringUtils.hasText(authenticationFailureUrl)) {
						builder.addPropertyValue("authenticationFailureUrl",
								authenticationFailureUrl);
					}
				}
			}
		}
		pc.getRegistry().registerBeanDefinition(ZkBeanIds.ZK_LOGIN_OK_FILTER,
				builder.getBeanDefinition());
	}

	/**
	 * Returns standard filter definition as created by main http bean
	 * definition parser
	 * 
	 * @param pc
	 * @param filterClassName
	 * @return
	 */
	private RootBeanDefinition getStandardFilter(ParserContext pc,
			String filterClassName) {
		RootBeanDefinition filterChainProxy = (RootBeanDefinition) pc
				.getRegistry().getBeanDefinition(BeanIds.FILTER_CHAIN_PROXY);
		PropertyValue v = filterChainProxy.getPropertyValues()
				.getPropertyValue("filterChainMap");
		RootBeanDefinition standardFilter = null;
		if (v != null) {
			ManagedMap m = (ManagedMap) v.getValue();
			Set k = m.keySet();
			for (Iterator iterator = k.iterator(); iterator.hasNext();) {
				/*
				 * aanagnostopoulos: known Spring bug: cast to
				 * RootBeanDefinition fails. Must
				 * cast to Object instead.
				 */
				// RootBeanDefinition rbd = (RootBeanDefinition)
				// iterator.next();
				Object rbd = iterator.next();
				ManagedList ml = (ManagedList) m.get(rbd);
				for (Iterator iterator2 = ml.iterator(); iterator2.hasNext();) {
					Object object = iterator2.next();
					if (object instanceof RootBeanDefinition) {
						RootBeanDefinition bean = (RootBeanDefinition) object;
						String beanClassName = bean.getBeanClassName();
						if (beanClassName.equals(filterClassName)) {
							standardFilter = bean;
							break;
						}
					} else if (object instanceof RuntimeBeanReference) {
						String beanRefBeanName = ((RuntimeBeanReference) object)
								.getBeanName();
						if (beanRefBeanName.indexOf(filterClassName) != -1) {
							standardFilter = (RootBeanDefinition) pc
									.getRegistry().getBeanDefinition(
											beanRefBeanName);
							break;
						}
					}
				}
			}
		}
		return standardFilter;
	}

	/**
	 * 
	 * @param element
	 * @param pc
	 */
	private void registerZkError403Filter(Element element, ParserContext pc) {
		final BeanDefinitionBuilder builder = BeanDefinitionBuilder
				.rootBeanDefinition(ZkError403Filter.class);
		RootBeanDefinition exceptionTranslationFilter = getStandardFilter(pc,
				ExceptionTranslationFilter.class.getName());
		if (exceptionTranslationFilter != null) {
			PropertyValue pv = exceptionTranslationFilter.getPropertyValues()
					.getPropertyValue("accessDeniedHandler");
			if (pv != null) {
				Object handler = (RootBeanDefinition) pv.getValue();
				if (handler != null && handler instanceof RootBeanDefinition) {
					builder.addPropertyValue("accessDeniedHandler", handler);
				}
			}
		}
		pc.getRegistry().registerBeanDefinition(ZkBeanIds.ZK_ERROR_403_FILTER,
				builder.getBeanDefinition());
	}

	private void registerExceptionTranslationFilter(Element element,
			ParserContext pc) {
		// zkAccessDeniedHandler
		final BeanDefinitionBuilder builder = BeanDefinitionBuilder
				.rootBeanDefinition(ZkExceptionTranslationFilter.class);
		AccessDeniedHandler accessDeniedHandler = new ZkAccessDeniedHandler();
		builder.addPropertyValue("accessDeniedHandler", accessDeniedHandler);

		// zkAuthenticationEntryPoint
		ZkAuthenticationEntryPoint entryPoint = new ZkAuthenticationEntryPoint();
		String closeDelay = element
				.getAttribute(ATT_LOGIN_TEMPLATE_CLOSE_DELAY);
		if (StringUtils.hasText(closeDelay)) {
			int seconds = new Integer(closeDelay).intValue();
			entryPoint.setLoginOKDelay(seconds);
		}
		setEntryPointAttrs(entryPoint, element, pc);
		builder.addPropertyValue("authenticationEntryPoint", entryPoint);

		// zkExceptionTranslationFilter
		pc.getRegistry().registerBeanDefinition(
				ZkBeanIds.ZK_EXCEPTION_TRANSLATION_FILTER,
				builder.getBeanDefinition());
	}

	private void setEntryPointAttrs(ZkAuthenticationEntryPoint entryPoint,
			Element rootElm, ParserContext pc) {
		final Element element = DomUtils.getChildElementByTagName(rootElm,
				ZkElements.FORM_LOGIN);

		// authenticationEntryPoint

		RootBeanDefinition exceptionTranslationFilter = getStandardFilter(pc,
				ExceptionTranslationFilter.class.getName());
		RootBeanDefinition formEntryPoint = null;
		if (exceptionTranslationFilter != null) {
			PropertyValue pv = exceptionTranslationFilter.getPropertyValues()
					.getPropertyValue("authenticationEntryPoint");
			if (pv != null) {
				formEntryPoint = (RootBeanDefinition) pv.getValue();
			}
		}

		if (element == null) {
			// Get login-page url and forceHttps from default form entry login
			// point bean (created if <http> element contains <form-login />)
			if (formEntryPoint != null) {
				String loginFormUrl = (String) formEntryPoint
						.getPropertyValues().getPropertyValue("loginFormUrl")
						.getValue();
				if (StringUtils.hasText(loginFormUrl)) {
					entryPoint.setLoginFormUrl(loginFormUrl);
					entryPoint.setLoginFailUrl(loginFormUrl);
				}
				PropertyValue pv = formEntryPoint.getPropertyValues()
						.getPropertyValue("forceHttps");
				if (pv != null) {
					String httpForceHttps = (String) pv.getValue();
					if (StringUtils.hasText(httpForceHttps)) {
						entryPoint.setForceHttps("true".equals(httpForceHttps));
					}
				}

			}
			return;
		}

		// login-ok-url
		final String loginOKUrl = element.getAttribute(ATT_LOGIN_OK_URL);
		WebConfigUtils.validateHttpRedirect(loginOKUrl, pc,
				pc.extractSource(element));
		if (StringUtils.hasText(loginOKUrl)) {
			entryPoint.setLoginOKUrl(loginOKUrl);
		}

		// authentication-failure-url
		final String loginFailUrl = element
				.getAttribute(ATT_AUTHENTICATION_FAILURE_URL);
		WebConfigUtils.validateHttpRedirect(loginFailUrl, pc,
				pc.extractSource(element));
		if (StringUtils.hasText(loginFailUrl)) {
			entryPoint.setLoginFailUrl(loginFailUrl);
		}

		// title
		final String title = element.getAttribute(ATT_TITLE);
		if (StringUtils.hasText(title)) {
			entryPoint.setLoginTemplateArg("title", title);
		}

		// width
		final String width = element.getAttribute(ATT_WIDTH);
		if (StringUtils.hasText(width)) {
			entryPoint.setLoginTemplateArg("width", width);
		}

		// height
		final String height = element.getAttribute(ATT_HEIGHT);
		if (StringUtils.hasText(height)) {
			entryPoint.setLoginTemplateArg("height", height);
		}

		// login-page
		// if not defined, use login-page defined in form-login from
		// http/FORM_LOGIN_ENTRY_POINT
		final String loginPage = element.getAttribute(ATT_LOGIN_PAGE);
		if (StringUtils.hasText(loginPage)) {
			entryPoint.setLoginFormUrl(loginPage);
			if (!StringUtils.hasText(loginFailUrl)) {
				entryPoint.setLoginFailUrl(loginPage + "?login_error=true");
			}
		} else {
			if (formEntryPoint != null) {
				String loginFormUrl = (String) formEntryPoint
						.getPropertyValues().getPropertyValue("loginFormUrl")
						.getValue();
				if (StringUtils.hasText(loginFormUrl)) {
					entryPoint.setLoginFormUrl(loginFormUrl);
					if (!StringUtils.hasText(loginFailUrl)) {
						entryPoint.setLoginFailUrl(loginFormUrl);
					}
				}
			}
		}

		// force-https
		// if not defined, use forceHttps defined if http/FORM_LOGIN_ENTRY_POINT
		// exists
		final String forceHttps = element.getAttribute(ATT_FORCE_HTTPS);
		if (StringUtils.hasText(forceHttps)) {
			entryPoint.setForceHttps("true".equals(forceHttps));
		} else {
			if (formEntryPoint != null) {
				PropertyValue pv = formEntryPoint.getPropertyValues()
						.getPropertyValue("forceHttps");
				if (pv != null) {
					String httpForceHttps = (String) pv.getValue();
					if (StringUtils.hasText(httpForceHttps)) {
						entryPoint.setForceHttps("true".equals(httpForceHttps));
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param element
	 * @param pc
	 */
	private void registerZkEventProcessInterceptor(Element element,
			ParserContext pc) {
		final BeanDefinitionBuilder builder = BeanDefinitionBuilder
				.rootBeanDefinition(ZkEventProcessInterceptor.class);
		final List eventElms = DomUtils.getChildElementsByTagName(element,
				ZkElements.INTERCEPT_EVENT);
		final LinkedHashMap<EventProcessKey, Collection<ConfigAttribute>> requestMap = ZkEventProcessDefinitionSourceBeanDefinitionParser
				.parseInterceptEventsForZkEventProcessMap(eventElms, pc);

		RootBeanDefinition interceptor = getStandardFilter(pc,
				FilterSecurityInterceptor.class.getName());

		Object accessDecisionManager = interceptor.getPropertyValues()
				.getPropertyValue("accessDecisionManager").getValue();
		Object authenticationManager = interceptor.getPropertyValues()
				.getPropertyValue("authenticationManager").getValue();

		final UrlMatcher matcher = createUrlMatcher(element);
		builder.addPropertyValue("accessDecisionManager", accessDecisionManager);
		builder.addPropertyValue("authenticationManager", authenticationManager);
		builder.addPropertyValue("objectDefinitionSource",
				new ZkEventProcessDefinitionSourceImpl(matcher, requestMap));
		pc.getRegistry().registerBeanDefinition(
				ZkBeanIds.ZK_EVENT_PROCESS_INTERCEPTOR,
				builder.getBeanDefinition());
	}

	public static UrlMatcher createUrlMatcher(Element element) {
		String pathType = element.getAttribute(ATT_PATH_TYPE);
		if (!StringUtils.hasText(pathType)) {
			pathType = DEF_PATH_TYPE_ANT;
		}

		if (pathType.equals(OPT_PATH_TYPE_REGEX)) {
			final UrlMatcher matcher = new RegexUrlPathMatcher();
			((RegexUrlPathMatcher) matcher).setRequiresLowerCaseUrl(false);
			return matcher;
		} else {
			final UrlMatcher matcher = new AntUrlPathMatcher();
			((AntUrlPathMatcher) matcher).setRequiresLowerCaseUrl(false);
			return matcher;
		}
	}
}
