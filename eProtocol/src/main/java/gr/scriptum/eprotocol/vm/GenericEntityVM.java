/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import gr.scriptum.dao.GenericDAO;
import gr.scriptum.eprotocol.util.Callback;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.util.EntityUtil;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class GenericEntityVM<T, ID extends Serializable, DAO extends GenericDAO<T, ID>> extends BaseVM {

	private static Log log = LogFactory.getLog(GenericEntityVM.class);

	private Class<T> entityClass = null;

	private Class<DAO> daoClass = null;
	
	private Class<ID> idClass = null;

	protected T entity = null;

	protected Callback callback = null;

	@SuppressWarnings("unchecked")
	protected T initEntity() throws Exception {
		return (T) Class.forName(entityClass.getName()).newInstance();
	}

	@SuppressWarnings("unchecked")
	protected DAO initDAO() throws Exception {
		return (DAO) SpringUtil.getApplicationContext().getBean(daoClass);
	}

	@SuppressWarnings("unchecked")
	protected void save() throws Exception {

		ID id = getEntityId(entity);

		DAO dao = initDAO();
		if (id == null) { // new entity, create
			dao.makePersistent(entity);
		} else {
			dao.update(entity);
		}

	}

	@SuppressWarnings("unchecked")
	protected void delete() throws Exception {

		ID id = getEntityId(entity);

		DAO dao = initDAO();
		dao.deleteById(id);

	}

	protected ID getEntityId(T entity) throws Exception {
		return (ID) EntityUtil.getEntityId(entity);
	}

	@Init
	public void init() throws Exception {

		callback = (Callback) Executions.getCurrent().getArg().get(IConstants.PARAM_CALLBACK); // setup
		// callback,
		// if any

		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		entity = initEntity();

		idClass = (Class<ID>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];

		daoClass = (Class<DAO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
		DAO dao = initDAO();

		String idString = Executions.getCurrent().getParameter(IConstants.PARAM_KEY_ID);
		if (idString == null && Executions.getCurrent().getArg().containsKey(IConstants.PARAM_KEY_ID)) {
			// check if param came from another component (i.e. by calling the
			// entity page as a modal window)
			idString = Executions.getCurrent().getArg().get(IConstants.PARAM_KEY_ID).toString();
		}
		if (idString != null) {
			Constructor<ID> constructor = idClass.getConstructor(new Class<?>[] { String.class });
			ID id = constructor.newInstance(idString);
			entity = dao.findById(id, false);

			if (entity == null) {
				Messagebox.show(Labels.getLabel("fetch.notFound"), Labels.getLabel("fetch.title"), Messagebox.OK,
						Messagebox.ERROR);
				entity = null;
			}
		}

	}

	@Command
	@NotifyChange("entity")
	public void addNewEntity() throws Exception {
		entity = initEntity();
		// clearValidationMessages(entityWin);
	}

	@Command
	@NotifyChange("entity")
	public void saveEntity(@ContextParam(ContextType.VIEW) Window entityWin) throws Exception {

		validateFields(entityWin);

		save();

		if (callback != null) {
			// notify caller (if any)
			if (callback.getCaller() != null) {
				// called by MVC controller
				Events.postEvent(callback.getEvent(), callback.getCaller(), entity);
			} else {
				// called by MVVM view model
				Map<String, Object> args = new HashMap<>();
				args.put("entity", entity);
				BindUtils.postGlobalCommand(null, null, callback.getEvent(), args);
			}
			entityWin.detach();
			return;
		}

		Messagebox.show(Labels.getLabel("save.OK"), Labels.getLabel("save.title"), Messagebox.OK,
				Messagebox.INFORMATION);

	}

	@Command
	@NotifyChange("entity")
	public void deleteEntity(@ContextParam(ContextType.VIEW) Window entityWin) throws Exception {

		ID id = getEntityId(entity);

		DAO dao = initDAO();
		if (!dao.isDeletable(id)) {

			Messagebox.show(Labels.getLabel("delete.notDeletable"), Labels.getLabel("delete.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		log.info("Deleting");
		Messagebox.show(Labels.getLabel("delete.confirm"), Labels.getLabel("delete.title"),
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EntityDeleteListener(entityWin));

	}

	private class EntityDeleteListener implements EventListener<Event> {

		private Window entityWin = null;

		public EntityDeleteListener(Window entityWin) {
			this.entityWin = entityWin;
		}

		@Override
		public void onEvent(Event event) throws Exception {
			if (((Integer) event.getData()).intValue() == Messagebox.YES) {
				delete();

				if (callback != null) {
					// notify caller (if any)
					if (callback.getCaller() != null) {
						// called by MVC controller
						Events.postEvent(callback.getEvent(), callback.getCaller(), entity);
					} else {
						// called by MVVM view model
						Map<String, Object> args = new HashMap<>();
						args.put("entity", entity);
						BindUtils.postGlobalCommand(null, null, callback.getEvent(), args);
					}
					entityWin.detach();
					return;
				}

				Messagebox.show(Labels.getLabel("delete.success"), Labels.getLabel("success.title"), Messagebox.OK,
						Messagebox.INFORMATION);

			}
		}
	}

	public boolean isLocked() {
		if (entity == null) {
			return true;
		}
		return false;
	}

	public boolean isEntityCreated() throws Exception {
		if (entity != null && getEntityId(entity) != null) {
			return true;
		}
		return false;
	}

	public boolean isEntityNotCreated() throws Exception {
		return !isEntityCreated();
	}

	/**
	 * @return the entity
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}

}
