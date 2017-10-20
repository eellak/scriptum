/**
 * 
 */
package gr.scriptum.eprotocol.util;

import java.util.List;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class EntitySelection<T extends Object> {

	private String term;

	private Boolean active = Boolean.FALSE;
	
	private List<T> entites;
	
	private Integer pageSize;
	
	private Integer totalSize;
	
	private Integer activePage;
	
	private T selectedEntity;

	public EntitySelection() {
		// TODO Auto-generated constructor stub
	}
	
	public EntitySelection(Integer pageSize) {
		super();
		this.pageSize = pageSize;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<T> getEntites() {
		return entites;
	}

	public void setEntites(List<T> entites) {
		this.entites = entites;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	public Integer getActivePage() {
		return activePage;
	}

	public void setActivePage(Integer activePage) {
		this.activePage = activePage;
	}

	public T getSelectedEntity() {
		return selectedEntity;
	}

	public void setSelectedEntity(T selectedEntity) {
		this.selectedEntity = selectedEntity;
	}
	
}
