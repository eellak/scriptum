package gr.scriptum.controller;

import gr.scriptum.csv.BaseCSVConverter;
import gr.scriptum.csv.ICSVConverter;
import gr.scriptum.dao.GenericDAO;
import gr.scriptum.ecase.util.IConstants;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

public class GenericSearchController<T, DAO extends GenericDAO, CONVERTER extends BaseCSVConverter>
		extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2902850984266563103L;

	private static Log log = LogFactory.getLog(GenericSearchController.class);

	/* components */
	Window win = null;
	Listbox entitiesLstbx = null;
	Paging pgng = null;

	// private String ENTITY_PAGE = "";

	private Class<T> entityClass = null;

	private Class<DAO> daoClass = null;

	private Class<CONVERTER> converterClass = null;

	private T entity = null;

	private ICSVConverter<T> converter = null;

	private List<T> entities = null;

	private T selected = null;

	@SuppressWarnings("unchecked")
	private T initEntity() throws Exception {
		return (T) Class.forName(entityClass.getName()).newInstance();
	}

	@SuppressWarnings("unchecked")
	private DAO initDAO() throws Exception {
		return (DAO) Class.forName(daoClass.getName()).newInstance();
	}

	@SuppressWarnings("unchecked")
	private ICSVConverter<T> initCONVERTER() throws Exception {
		return (ICSVConverter<T>) Class.forName(converterClass.getName())
				.newInstance();
	}

	@SuppressWarnings("unchecked")
	private void search(Integer startIndex) throws Exception {
		DAO dao = initDAO();
		
		entity = (T) trimStringProperties(entity);
		
		// set up paging by counting records first
		Integer totalSize = dao.countByExample(entity, MatchMode.START, null);
		pgng.setTotalSize(totalSize);
		int pageSize = pgng.getPageSize();

		// figure out which header to sort by
		List<Order> sortBy = new LinkedList<Order>();
		Listheader header = getSortingListheader(entitiesLstbx);
		if (header != null) {
			Order order = null;
			if (header.getSortDirection().equals("ascending")) {
				order = Order.asc(header.getValue().toString());
			} else {
				order = Order.desc(header.getValue().toString());
			}
			sortBy.add(order);
		}
		entities = dao.findByExample(entity, MatchMode.START, null, sortBy,
				startIndex, pageSize);
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

	public GenericSearchController() {

		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		daoClass = (Class<DAO>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1];

		converterClass = (Class<CONVERTER>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[2];

	}

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		page.setAttribute(this.getClass().getSimpleName(), this);
		log.debug(this.getClass().getSimpleName());

		entity = initEntity();
		search(0);
	}

	public void onClick$searchBtn() throws Exception {
		search(0);
		getBinder(win).loadAll();

		if (entities.isEmpty()) {
			Messagebox.show(Labels.getLabel("search.notFound"),
					Labels.getLabel("search.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	/**
	 * Custom sorting event listener, overriding default sorting mechanism.
	 * Instead, database sorting is used. The property to be sorted by gets
	 * picked up from the 'value' attribute of the Listheader triggering the
	 * event. The sorting order (asc,desc) used the Listheader's sortDirection
	 * attribute.
	 * 
	 * @param event
	 *            The sorting event
	 * @throws Exception
	 */
	public void onSort(Event event) throws Exception {
		Event sortEvent = ((ForwardEvent) event).getOrigin();
		// prevent default sorting from getting called
		sortEvent.stopPropagation();
		// setup sorting
		Listheader header = (Listheader) sortEvent.getTarget();
		Listbox parent = (Listbox) header.getParent().getParent();
		setSortingListheader(header);

		search(0);
		pgng.setActivePage(0);
		getBinder(win).loadAll();
	}

	public void onPaging$pgng(PagingEvent event) throws Exception {
		int activePage = event.getActivePage();
		int startIndex = activePage * pgng.getPageSize();
		search(startIndex);
		getBinder(win).loadAll();
	}

	public void onClick$clearBtn() throws Exception {
		entity = initEntity();
		getBinder(win).loadAll();
	}

	public void onSelect$entitiesLstbx() throws Exception {
		Integer id = getEntityId(selected);
		Executions.getCurrent().sendRedirect(
				getEntityPage() + "?" + IConstants.PARAM_KEY_ID + "=" + id);
	}

	public void onClick$newBtn() {
		Executions.getCurrent().sendRedirect(getEntityPage());
	}

	@SuppressWarnings("unchecked")
	public void onUpload$importBtn(UploadEvent event)
			throws Exception {
		Media media = event.getMedia();
		if (media.isBinary()) {
			Messagebox.show(Labels.getLabel("search.import.invalidFile"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		List<String> lines = new ArrayList<String>();

		if (media.inMemory()) {
			log.info("in memory");
			String[] tokens = StringUtils.split(
					media.getStringData(), BaseCSVConverter.LINE_DELIM);
			for (String token : tokens) {
				lines.add(token);
			}

		} else {
			log.info("in file");
			Reader reader = media.getReaderData();
			try {
				lines.addAll(IOUtils.readLines(reader));

			} catch (IOException e) {
				log.error(e);
				Messagebox.show(Labels.getLabel("search.import.invalidFile"),
						Labels.getLabel("error.title"), Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}
		
		converter = initCONVERTER();
		List<T> imported = converter.importFrom(lines);

		search(0);
		getBinder(win).loadAll();
		
		Messagebox.show(
				Labels.getLabel("search.import.success",
						new String[] { String.valueOf(imported.size()) }),
				Labels.getLabel("success.title"), Messagebox.OK,
				Messagebox.INFORMATION);

	}

	public void onClick$exportBtn() throws Exception {

		DAO dao = initDAO();
		List<T> results = dao.findAll();

		converter = initCONVERTER();
		File file = converter.exportTo(results,
				"export_" + entityClass.getSimpleName() + ".csv");
		Filedownload.save(file, "text/csv");
	}

	public String getEntityPage() {
		return "";
	}

	/**
	 * @return the entity
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * @return the entities
	 */
	public List<T> getEntities() {
		return entities;
	}

	/**
	 * @return the selected
	 */
	public T getSelected() {
		return selected;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}

	/**
	 * @param entities
	 *            the entities to set
	 */
	public void setEntities(List<T> entities) {
		this.entities = entities;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(T selected) {
		this.selected = selected;
	}
}
