package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.GenericDAO;
import gr.scriptum.eprotocol.util.Callback;
import gr.scriptum.eprotocol.util.IConstants;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class GenericEntityController<T, DAO extends GenericDAO> extends
		BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3892462100035973445L;

	private static Log log = LogFactory.getLog(GenericEntityController.class);

	Window win = null;

	private Class<T> entityClass = null;

	protected T entity = null;

	private Class<DAO> daoClass = null;

	private Callback callback = null;

	@SuppressWarnings("unchecked")
	protected T initEntity() throws Exception {
		return (T) Class.forName(entityClass.getName()).newInstance();
	}

	@SuppressWarnings("unchecked")
	protected DAO initDAO() throws Exception {
		return (DAO) Class.forName(daoClass.getName()).newInstance();
	}

	@SuppressWarnings("unchecked")
	protected void save() throws Exception {
		
		Integer id = getEntityId(entity);

		DAO dao = initDAO();
		if (id == null) { // new entity, create
			dao.makePersistent(entity);
		} else {
			dao.update(entity);
		}

	}
	
	@SuppressWarnings("unchecked")
	protected void delete() throws Exception {

		Integer id = getEntityId(entity);

		DAO dao = initDAO();
		dao.deleteById(id);

//		Messagebox.show(Labels.getLabel("delete.success"),
//				Labels.getLabel("success.title"), Messagebox.OK,
//				Messagebox.INFORMATION);
//
//		entity = initEntity();
//		getBinder(win).loadAll();
//		clearValidationMessages(win);
	}

	protected Integer getEntityId(T entity) throws Exception {
		Method method = null;
		try {
			method = entity.getClass().getMethod("getId", new Class<?>[] {});
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

		Integer id = null;
		if (method != null) {
			try {
				id = (Integer) method.invoke(entity, new Object[] {});
			} catch (Exception e) {
				log.error(e);
				throw e;
			}
		}
		return id;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);

		callback = (Callback) arg.get(IConstants.PARAM_CALLBACK); // setup
																	// callback,
																	// if any
		page.setAttribute(this.getClass().getSimpleName(), this);

		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		entity = initEntity();

		daoClass = (Class<DAO>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1];
		DAO dao = initDAO();

		String idString = execution.getParameter(IConstants.PARAM_KEY_ID);
		if (idString != null) {
			entity = (T) dao.findById(Integer.valueOf(idString), false);
			if (entity == null) {
				Messagebox.show(Labels.getLabel("fetch.notFound"),
						Labels.getLabel("fetch.title"), Messagebox.OK,
						Messagebox.ERROR);
				entity = null;
			}
		}

	}

	public void onClick$newBtn() throws Exception {
		entity = initEntity();
		getBinder(win).loadAll();
		clearValidationMessages(win);
	}

	@SuppressWarnings("unchecked")
	public void onClick$saveBtn() throws Exception {

		validateFields(win);

		save();
		
		getBinder(win).loadAll();

		if (callback != null) { // notify caller
			Events.postEvent(callback.getEvent(), callback.getCaller(), entity);
			win.onClose();
			return;
		}

		Messagebox.show(Labels.getLabel("save.OK"),
				Labels.getLabel("save.title"), Messagebox.OK,
				Messagebox.INFORMATION);

	}

	public void onClick$deleteBtn() throws Exception {

		Integer id = getEntityId(entity);

		DAO dao = initDAO();
		if (!dao.isDeletable(id)) {

			Messagebox.show(Labels.getLabel("delete.notDeletable"),
					Labels.getLabel("delete.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		log.info("Deleting");
		try {
			boolean delete = false;
			Messagebox.show(Labels.getLabel("delete.confirm"),
					Labels.getLabel("delete.title"), Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION,
					new EventListener() {
						@Override
						public void onEvent(Event event) throws Exception {
							if (((Integer) event.getData()).intValue() == Messagebox.YES) {
								delete();
								
								Messagebox.show(Labels.getLabel("delete.success"),
										Labels.getLabel("success.title"), Messagebox.OK,
										Messagebox.INFORMATION);

								entity = initEntity();
								getBinder(win).loadAll();
								clearValidationMessages(win);

							}
						}
					});
		} catch (InterruptedException e) {
			// swallow
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
