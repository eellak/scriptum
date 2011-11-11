/**
 * 
 */
package gr.scriptum.ecase.controller;

import gr.scriptum.controller.GenericSearchController;
import gr.scriptum.csv.TaskTypeConverter;
import gr.scriptum.dao.TaskTypeDAO;
import gr.scriptum.domain.TaskType;

/**
 * @author aanagnostopoulos
 * 
 */
public class TaskTypesController extends
		GenericSearchController<TaskType, TaskTypeDAO, TaskTypeConverter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7759527294133275979L;

	@Override
	public String getEntityPage() {
		return TaskTypeController.PAGE;
	}

}
