/**
 * 
 */
package gr.scriptum.api.external;

import java.util.List;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public interface ExternalSearcher {

	Entity findById(Object id);
	
	Long countByTerm(String term, Object... optionalParameters);
	
	List<Entity> findByTerm(String term, Integer offset, Integer size, Object... optionalParameters);
	
}
