/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.Resource;
import gr.scriptum.domain.Resource.ResourceType;
import gr.scriptum.exception.DataCorruptionException;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Repository
public class ResourceDAO extends GenericDAO<Resource, Integer> {

	@Transactional(readOnly = true)
	public Resource findEntityResource(Class<?> entityType, Object entityId) {
		Resource resource = new Resource();
		resource.setType(ResourceType.ENTITY);
		resource.setKeyCode(entityType.getSimpleName());
		resource.setValue(entityId.toString());
		resource.setActive(Boolean.TRUE);

		List<Resource> resources = findByExample(resource);
		if (resources.isEmpty()) {
			return null;
		}
		if (resources.size() != 1) {
			throw new DataCorruptionException("Found more than one entity resources:" + resources.size());
		}
		return resources.get(0);
	}

}
