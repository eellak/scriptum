/**
 * 
 */
package gr.scriptum.api.external;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos
 *         </a>
 *
 */
public class Entity {

	public enum EntityType {
		INDIVIDUAL, ORGANIZATION, DEPARTMENT
	}
	
	private Object id = null; 

	private EntityType type = null;
	
	private String name = null;
	
	private String title = null;
	
	private String description = null;

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
