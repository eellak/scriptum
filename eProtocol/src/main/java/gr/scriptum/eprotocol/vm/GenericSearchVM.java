/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import gr.scriptum.dao.GenericDAO;
import gr.scriptum.eprotocol.csv.BaseCSVConverter;
import gr.scriptum.eprotocol.csv.ICSVConverter;
import gr.scriptum.eprotocol.util.Callback;
import gr.scriptum.eprotocol.util.IConstants;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos
 *         </a>
 *
 */
public abstract class GenericSearchVM<T, DAO extends GenericDAO, CONVERTER extends BaseCSVConverter> extends BaseVM {

	private static Log log = LogFactory.getLog(GenericSearchVM.class);

	@Wire("#entitiesLstbx")
	Listbox entitiesLstbx = null;

	private Class<T> entityClass = null;

	private Class<DAO> daoClass = null;

	private Class<CONVERTER> converterClass = null;

	private T entity = null;

	private ICSVConverter<T> converter = null;

	private List<T> entities = null;

	private T selected = null;

	private Integer totalSize;

	private Integer pageSize = PAGE_SIZE_LARGE;
	
	private Integer activePage = null;

	@SuppressWarnings("unchecked")
	private T initEntity() throws Exception {
		return (T) Class.forName(entityClass.getName()).newInstance();
	}

	@SuppressWarnings("unchecked")
	private DAO initDAO() throws Exception {
		return (DAO) SpringUtil.getApplicationContext().getBean(daoClass);
	}

	@SuppressWarnings("unchecked")
	private ICSVConverter<T> initCONVERTER() throws Exception {
		return (ICSVConverter<T>) Class.forName(converterClass.getName()).newInstance();
	}

	@SuppressWarnings("unchecked")
	private void search(Integer startIndex) throws Exception {
		DAO dao = initDAO();

		entity = (T) trimStringProperties(entity);

		// set up paging by counting records first
		totalSize = dao.countByExample(entity, MatchMode.START, null);

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
		entities = dao.findByExample(entity, MatchMode.START, null, sortBy, startIndex, pageSize);
	}

	protected Object getEntityId(T entity) throws Exception {
		Method method = null;
		try {
			method = entity.getClass().getMethod("getId", new Class<?>[] {});
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

		Object id = null;
		if (method != null) {
			try {
				id =  method.invoke(entity, new Object[] {});
			} catch (Exception e) {
				log.error(e);
				throw e;
			}
		}
		return id;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		try {
			search(0);
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Init
	public void init() throws Exception {

		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

		daoClass = (Class<DAO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];

		converterClass = (Class<CONVERTER>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[2];

		entity = initEntity();
		

	}

	public abstract String getEntityPage();

	@Command
	@NotifyChange({ "entity", "entities", "totalSize", "activePage" })
	public void searchEntities() throws Exception {
		activePage = 0;
		search(0);
		if (entities.isEmpty()) {
			Messagebox.show(Labels.getLabel("search.notFound"), Labels.getLabel("search.title"), Messagebox.OK,
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
	@NotifyChange({ "entity", "entities", "totalSize", "activePage" })
	@Command
	public void sort(@ContextParam(ContextType.TRIGGER_EVENT) SortEvent sortEvent) throws Exception {
		// prevent default sorting from getting called
		sortEvent.stopPropagation();
		// setup sorting
		Listheader header = (Listheader) sortEvent.getTarget();
		setSortingListheader(header);

		search(0);
	}

	@Command
	@NotifyChange({ "entity", "entities", "totalSize", "activePage" })
	public void page() throws Exception {
		int startIndex = activePage * pageSize;
		search(startIndex);
	}

	@Command
	@NotifyChange("entity")
	public void clearSearch() throws Exception {
		entity = initEntity();
	}

	@Command
	@NotifyChange("selected")
	public void selectEntity(@ContextParam(ContextType.VIEW) Component view) throws Exception {
		Object id = getEntityId(selected);

		// create modal window for selected entity
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_KEY_ID, id);
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshEntities"));
		Window entityWin = (Window) Executions.createComponents(getEntityPage(), view, params);
		entityWin.setClosable(true);
		entityWin.doModal();

		// clear seleted entity
		selected = null;
	}

	
	@Command
	public void addNewEntity(@ContextParam(ContextType.VIEW) Component view) {
		// create modal window for selected entity
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshEntities"));
		Window entityWin = (Window) Executions.createComponents(getEntityPage(), view, params);
		entityWin.setClosable(true);
		entityWin.doModal();
	}

	@GlobalCommand
	@NotifyChange({ "entity", "entities", "totalSize", "activePage" })
	public void refreshEntities() throws Exception {
		activePage=0;
		search(0);
	}

	@SuppressWarnings("unchecked")
	public void onUpload$importBtn(UploadEvent event) throws Exception {
		Media media = event.getMedia();
		if (media.isBinary()) {
			Messagebox.show(Labels.getLabel("search.import.invalidFile"), Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		List<String> lines = new ArrayList<String>();

		if (media.inMemory()) {
			log.info("in memory");
			String[] tokens = StringUtils.split(media.getStringData(), BaseCSVConverter.LINE_DELIM);
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
				Messagebox.show(Labels.getLabel("search.import.invalidFile"), Labels.getLabel("error.title"),
						Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}

		converter = initCONVERTER();
		List<T> imported = converter.importFrom(lines);

		search(0);
//		getBinder(win).loadAll();

		Messagebox.show(Labels.getLabel("search.import.success", new String[] { String.valueOf(imported.size()) }),
				Labels.getLabel("success.title"), Messagebox.OK, Messagebox.INFORMATION);

	}

	public void onClick$exportBtn() throws Exception {

		DAO dao = initDAO();
		List<T> results = dao.findAll();

		converter = initCONVERTER();
		File file = converter.exportTo(results, "export_" + entityClass.getSimpleName() + ".csv");
		Filedownload.save(file, "text/csv");
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

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getActivePage() {
		return activePage;
	}

	public void setActivePage(Integer activePage) {
		this.activePage = activePage;
	}

}
