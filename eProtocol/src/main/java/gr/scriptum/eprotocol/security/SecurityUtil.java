/* SecurityUtil.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Sep 17, 2009 10:48:23 AM, Created by henrichen
}}IS_NOTE

Copyright (C) 2009 Potix Corporation. All Rights Reserved.
Partial Copyright 2004, 2005, 2006 Acegi Technology Pty Limited

{{IS_RIGHT
	This program is distributed under GPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
	
	This implementation is based on codes of the Spring Security taglibs 
	written by Ben Alex, Thomas Champagne, Francois Beausoleil, et al.

	As what Apache License required, we have to include following license 
	statement.
	
	-----
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
 
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.

}}IS_RIGHT
*/

package gr.scriptum.eprotocol.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.UiException;

/**
 * Utility class for ZK spring security.
 * @author henrichen
 */
public class SecurityUtil {
    private static AclService _aclService;
    private static ApplicationContext _applicationContext;
    private static ObjectIdentityRetrievalStrategy _objectIdentityRetrievalStrategy;
    private static SidRetrievalStrategy _sidRetrievalStrategy;
    private static PermissionFactory permissionFactory;

	/**
	 * Return true if the current Authentication has one of the specified 
	 * permissions to the presented	domain object instance.
	 *
	 * @param hasPermission A comma separated list of integers, each 
	 *  representing a required bit mask permission from a subclass of 
	 * {@link org.springframework.security.acl.basic.AbstractBasicAclEntry}.
	 * @param domainObject The actual domain object instance for which permissions
	 *	are being evaluated.
	 * @return true if current Authentication has one of the specified permission
	 *  to the presented domain object instance.
	 */
	public static boolean isAccessible(String hasPermission, Object domainObject) {
		
		if (hasPermission == null || "".equals(hasPermission)) {
			return false;
		}
		initializeIfRequired();
        
        final List<Permission> requiredPermissions = parsePermissions(hasPermission);

        Object resolvedDomainObject = domainObject;

        if (resolvedDomainObject == null) {
            // Of course they have access to a null object!
        	return true;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            //SecurityContextHolder did not return a non-null Authentication object, so skipping tag body
            return false;
        }

        List<Sid> sids = _sidRetrievalStrategy.getSids(SecurityContextHolder.getContext().getAuthentication());
        ObjectIdentity oid = _objectIdentityRetrievalStrategy.getObjectIdentity(resolvedDomainObject);

        // Obtain aclEntrys applying to the current Authentication object
        try {
            final Acl acl = _aclService.readAclById(oid, sids);
            return acl.isGranted(requiredPermissions, sids, false);
        } catch (NotFoundException nfe) {
            return false;
        }
	}
	
	/**
	 * Return true if the authenticated principal is granted NONE of the roles 
	 * specified in authorities.
	 * 
	 * @param authorities A comma separated list of roles which the user must 
	 * have been granted NONE.
	 * @return true if the authenticated principal is granted authorities 
	 * of NONE the specified roles.
	 */
	public static boolean isNoneGranted(String authorities) {
		if (null == authorities || "".equals(authorities)) {
			return false;
		}
        final Collection<? extends GrantedAuthority> granted = getPrincipalAuthorities();
        final Set grantedCopy = retainAll(granted, parseAuthoritiesString(authorities));
        return grantedCopy.isEmpty();
	}
	
	/**
	 * Return true if the authenticated principal is granted ALL of the roles 
	 * specified in authorities.
	 * 
	 * @param authorities A comma separated list of roles which the user must have  
	 * been granted ALL.
	 * @return true true if the authenticated principal is granted authorities 
	 * of ALL the specified roles.
	 */
	public static boolean isAllGranted(String authorities) {
		if (null == authorities || "".equals(authorities)) {
			return false;
		}
        final Collection<? extends GrantedAuthority> granted = getPrincipalAuthorities();
        boolean isAllGranted = granted.containsAll(parseAuthoritiesString(authorities)); 
        return isAllGranted;
	}
	
	/**
	 * Return true if the authenticated principal is granted ANY of the roles 
	 * specified in authorities.
	 * 
	 * @param authorities A comma separated list of roles which the user must have  
	 * been granted ANY.
	 * @return true true if the authenticated principal is granted authorities 
	 * of ALL the specified roles.
	 */
	public static boolean isAnyGranted(String authorities) {
		if (null == authorities || "".equals(authorities)) {
			return false;
		}
        final Collection<? extends GrantedAuthority> granted = getPrincipalAuthorities();
        final Set grantedCopy = retainAll(granted, parseAuthoritiesString(authorities));
        return !grantedCopy.isEmpty();
	}
	
	/**
	 * Return the current Authentication object.
	 */
	public static Authentication getAuthentication() {
	    if ((SecurityContextHolder.getContext() != null)
	            && (SecurityContextHolder.getContext() instanceof SecurityContext)) {
	        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null && auth.getPrincipal() != null) {
	        	return auth;
	        }
	    }
        return null;
	}
	
	/**
	 * Return the evaluated result per the given property of the current 
	 * Authentication object.
	 * 
	 * @param property Property of the Authentication object which would be 
	 * evaluated. Supports nested properties. For example if the principal 
	 * object is an instance of UserDetails, the property "principal.username" 
	 * will return the username. Alternatively, using "name" will call getName 
	 * method on the Authentication object directly.
	 * 
	 * @return the evaluated result of the current Authentication object per the
	 * given property.
	 */
	public static Object getAuthentication(String property) {
		// determine the value by...
		if (property != null) {
		    if ((SecurityContextHolder.getContext() == null)
		            || !(SecurityContextHolder.getContext() instanceof SecurityContext)
		            || (SecurityContextHolder.getContext().getAuthentication() == null)) {
		        return null;
		    }
		
		    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		    if (auth.getPrincipal() == null) {
		        return null;
		    }
		
		    try {
		        BeanWrapperImpl wrapper = new BeanWrapperImpl(auth);
		        return wrapper.getPropertyValue(property);
		    } catch (BeansException e) {
		        throw new UiException(e);
		    }
		}
		return null;
	}
	
	private static List<Permission> parsePermissions(String permissionsString) {
        final Set<Permission> permissions = new HashSet<Permission>();
        final StringTokenizer tokenizer;
        tokenizer = new StringTokenizer(permissionsString, ",", false);

        while (tokenizer.hasMoreTokens()) {
            String permission = tokenizer.nextToken();
            try {
                permissions.add(permissionFactory.buildFromMask(Integer.valueOf(permission)));
            } catch (NumberFormatException nfe) {
                // Not an integer mask. Try using a name
                permissions.add(permissionFactory.buildFromName(permission));
            }
        }
            return new ArrayList<Permission>(permissions);
	}
	
	private static void initializeIfRequired() {
        if (_applicationContext != null) {
            return;
        }

        _applicationContext = SpringUtil.getApplicationContext();

        Map map = new HashMap();
        ApplicationContext context = _applicationContext;

        while (context != null) {
            map.putAll(context.getBeansOfType(AclService.class));
            context = context.getParent();
        }

        if (map.size() != 1) {
            throw new UiException(
                "Found incorrect number of AclService instances in application context - you must have only have one!");
        }

        _aclService = (AclService) map.values().iterator().next();

        map = _applicationContext.getBeansOfType(SidRetrievalStrategy.class);

        if (map.size() == 0) {
            _sidRetrievalStrategy = new SidRetrievalStrategyImpl();
        } else if (map.size() == 1) {
            _sidRetrievalStrategy = (SidRetrievalStrategy) map.values().iterator().next();
        } else {
            throw new UiException("Found incorrect number of SidRetrievalStrategy instances in application "
                    + "context - you must have only have one!");
        }

        map = _applicationContext.getBeansOfType(ObjectIdentityRetrievalStrategy.class);

        if (map.size() == 0) {
            _objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();
        } else if (map.size() == 1) {
            _objectIdentityRetrievalStrategy = (ObjectIdentityRetrievalStrategy) map.values().iterator().next();
        } else {
            throw new UiException("Found incorrect number of ObjectIdentityRetrievalStrategy instances in "
                    + "application context - you must have only have one!");
        }
        
        map = _applicationContext.getBeansOfType(PermissionFactory.class);

        if (map.size() == 0) {
            permissionFactory = new DefaultPermissionFactory();
        } else if (map.size() == 1) {
        	permissionFactory = (PermissionFactory) map.values().iterator().next();
        } else {
            throw new UiException("Found incorrect number of PermissionFactory instances in "
                    + "application context - you must have only have one!");
        }
    }
	
	private static Set authoritiesToRoles(Collection c) {
        final Set target = new HashSet();
        for (Iterator iterator = c.iterator(); iterator.hasNext();) {
            GrantedAuthority authority = (GrantedAuthority) iterator.next();

            if (null == authority.getAuthority()) {
                throw new IllegalArgumentException(
                    "Cannot process GrantedAuthority objects which return null from getAuthority() - attempting to process "
                    + authority.toString());
            }

            target.add(authority.getAuthority());
        }

        return target;
    }

	private static Collection<? extends GrantedAuthority> getPrincipalAuthorities() {
	    Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
	
	    if (null == currentUser) {
	        return Collections.emptyList();
	    }
	
	    if ((null == currentUser.getAuthorities()) || (currentUser.getAuthorities().size() < 1)) {
	        return Collections.emptyList();
	    }
	
	    Collection<? extends GrantedAuthority> granted = currentUser.getAuthorities();
	
	    
	    return granted;
	}

    private static Collection<GrantedAuthority> parseAuthoritiesString(String authorizationsString) {
        final Collection<GrantedAuthority> requiredAuthorities = new ArrayList<GrantedAuthority>();
        final String[] roles = authorizationsString.split(",");

        for (int i = 0; i < roles.length; i++) {
            String role = roles[i].trim();
            /* modified by aanagnostopoulos: replaced obsolete GrantedAuthorityImpl with Spring 4.x's equivalent */
            requiredAuthorities.add(new SimpleGrantedAuthority(role));
            /* END aanagnostopoulos */
        }

        return requiredAuthorities;
    }

    /**
     * Find the common authorities between the current authentication's {@link GrantedAuthority} and the ones
     * that have been specified in the tag's ifAny, ifNot or ifAllGranted attributes.<p>We need to manually
     * iterate over both collections, because the granted authorities might not implement {@link
     * Object#equals(Object)} and {@link Object#hashCode()} in the same way as {@link GrantedAuthorityImpl}, thereby
     * invalidating {@link Collection#retainAll(java.util.Collection)} results.</p>
     * <p>
     * <strong>CAVEAT</strong>:  This method <strong>will not</strong> work if the granted authorities
     * returns a <code>null</code> string as the return value of {@link
     * org.springframework.security.GrantedAuthority#getAuthority()}.
     * </p>
     * <p>Reported by rawdave, on Fri Feb 04, 2005 2:11 pm in the Spring Security forum.</p>
     *
     * @param granted The authorities granted by the authentication. May be any implementation of {@link
     *        GrantedAuthority} that does <strong>not</strong> return <code>null</code> from {@link
     *        org.springframework.security.GrantedAuthority#getAuthority()}.
     * @param required A {@link Set} of {@link GrantedAuthorityImpl}s that have been built using ifAny, ifAll or
     *        ifNotGranted.
     *
     * @return A set containing only the common authorities between <var>granted</var> and <var>required</var>.
     */
    private static Set retainAll(final Collection<? extends GrantedAuthority> granted, final Collection<GrantedAuthority> required) {
        Set grantedRoles = authoritiesToRoles(granted);
        Set requiredRoles = authoritiesToRoles(required);
        grantedRoles.retainAll(requiredRoles);

        return rolesToAuthorities(grantedRoles, granted);
    }

    private static Set rolesToAuthorities(Set grantedRoles, Collection granted) {
        Set target = new HashSet();

        for (Iterator iterator = grantedRoles.iterator(); iterator.hasNext();) {
            String role = (String) iterator.next();

            for (Iterator grantedIterator = granted.iterator(); grantedIterator.hasNext();) {
                GrantedAuthority authority = (GrantedAuthority) grantedIterator.next();

                if (authority.getAuthority().equals(role)) {
                    target.add(authority);

                    break;
                }
            }
        }

        return target;
    }
}
