package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.ContactDAO;
import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.dao.IncomingProtocolDAO;
import gr.scriptum.dao.OutgoingProtocolDAO;
import gr.scriptum.dao.ProtocolBookDAO;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.IncomingProtocol;
import gr.scriptum.domain.OutgoingProtocol;
import gr.scriptum.domain.OutgoingRecipient;
import gr.scriptum.domain.OutgoingRecipientId;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolNode;
import gr.scriptum.domain.OutgoingRecipient.RecipientType;
import gr.scriptum.eprotocol.util.Callback;
import gr.scriptum.eprotocol.util.IConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

public class IndexController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8244204080897544229L;

	private static Log log = LogFactory.getLog(IndexController.class);

	public static final String PAGE = "index.zul";

	public static final String PARAM_SELECTED_TAB = "tab";

	/* Components */
	Window indexWin;
	Tabbox indexTbx;

	Tab incomingTb;
	Tab outgoingTb;
	Tab searchTb;

	Listbox incomingLstbx;
	Listheader incomingUpdateTsLsthdr;
	Paging incomingPgng;

	Listbox outgoingLstbx;
	Paging outgoingPgng;

	Radiogroup directionRdgrp;
	Listbox searchIncomingLstbx;
	Paging searchIncomingPgng;
	Listbox searchOutgoingLstbx;
	Paging searchOutgoingPgng;
	Bandbox contactBndbx;
	Bandbox toContactBndbx;

	/* data binding */

	/* common */
	private List<ProtocolBook> activeProtocolBooks = null;

	/* incoming */
	private IncomingProtocol incomingProtocol = null;
	private List<IncomingProtocol> incomingProtocols = null;
	private IncomingProtocol selectedIncomingProtocol = null;
	private Date incomingDateFrom = null;
	private Date incomingDateTo = null;

	/* outgoing */
	private OutgoingProtocol outgoingProtocol = null;
	private List<OutgoingProtocol> outgoingProtocols = null;
	private OutgoingProtocol selectedOutgoingProtocol = null;
	private Date outgoingDateFrom = null;
	private Date outgoingDateTo = null;

	/* search */
	private String protocolNumber = null;
	private Date searchDateFrom = null;
	private Date searchDateTo = null;
	private String subject = null;
	private String keywords = null;
	private List<DistributionMethod> distributionMethods = null;
	private DistributionMethod distributionMethod = null;
	private List<Contact> contacts = null;
	private Contact contact = null;
	private List<Contact> toContacts = null;
	private Contact toContact = null;
	private List<IncomingProtocol> searchIncomingProtocols = null;
	private List<OutgoingProtocol> searchOutgoingProtocols = null;
	private IncomingProtocol searchSelectedIncomingProtocol = null;
	private OutgoingProtocol searchSelectedOutgoingProtocol = null;
	private List<ProtocolBook> protocolBooks = null;

	private void initIncoming() {
		incomingProtocol = new IncomingProtocol();
		incomingDateFrom = null;
		incomingDateTo = null;
	}

	private void initOutgoing() {
		outgoingProtocol = new OutgoingProtocol();
		outgoingDateFrom = null;
		outgoingDateTo = null;
	}

	private void initSearch() {
		protocolNumber = null;
		searchDateFrom = null;
		searchDateTo = null;
		subject = null;
		distributionMethod = null;
		keywords = null;
		contacts = new ArrayList<Contact>();
		contact = null;
		toContacts = new ArrayList<Contact>();
		toContact = null;
	}

	private void searchIncomingPending(Integer startIndex) {
		boolean includeDeletedProtocols = false;
		if (getUserInSessionRole().equals(IConstants.ROLE_ADMIN)) {
			includeDeletedProtocols = true;
		}
		incomingProtocol = (IncomingProtocol) trimStringProperties(incomingProtocol);
		IncomingProtocolDAO incomingProtocolDAO = new IncomingProtocolDAO();
		// set up paging by counting records first
		Integer totalSize = incomingProtocolDAO.countSearch(null,
				incomingDateFrom, incomingDateTo,
				incomingProtocol.getSubject(), null, null, null, true,
				includeDeletedProtocols, activeProtocolBooks);
		incomingPgng.setTotalSize(totalSize);
		int pageSize = incomingPgng.getPageSize();

		// figure out which header to sort by
		Listheader header = getSortingListheader(incomingLstbx);
		List<Order> sortBy = getSortBy(header);

		incomingProtocols = incomingProtocolDAO.search(null, incomingDateFrom,
				incomingDateTo, incomingProtocol.getSubject(), null, null,
				null, true, includeDeletedProtocols, activeProtocolBooks,
				startIndex, pageSize, sortBy.toArray(new Order[0]));

	}

	private void searchOutgoingPending(Integer startIndex) {
		boolean includeDeletedProtocols = false;
		if (getUserInSessionRole().equals(IConstants.ROLE_ADMIN)) {
			includeDeletedProtocols = true;
		}
		outgoingProtocol = (OutgoingProtocol) trimStringProperties(outgoingProtocol);
		OutgoingProtocolDAO outgoingProtocolDAO = new OutgoingProtocolDAO();
		// set up paging by counting records first
		Integer totalSize = outgoingProtocolDAO.countSearch(null,
				outgoingDateFrom, outgoingDateTo,
				outgoingProtocol.getSubject(), null, null, null, true,
				includeDeletedProtocols, activeProtocolBooks);
		outgoingPgng.setTotalSize(totalSize);
		int pageSize = outgoingPgng.getPageSize();

		// figure out which header to sort by
		Listheader header = getSortingListheader(outgoingLstbx);
		List<Order> sortBy = getSortBy(header);

		outgoingProtocols = outgoingProtocolDAO.search(null, outgoingDateFrom,
				outgoingDateTo, outgoingProtocol.getSubject(), null, null,
				null, true, includeDeletedProtocols, activeProtocolBooks,
				startIndex, pageSize, sortBy.toArray(new Order[0]));
	}

	private void searchIncoming(Integer startIndex) {
		boolean includeDeletedProtocols = false;
		if (getUserInSessionRole().equals(IConstants.ROLE_ADMIN)) {
			includeDeletedProtocols = true;
		}
		IncomingProtocolDAO incomingProtocolDAO = new IncomingProtocolDAO();
		// set up paging by counting records first
		Integer totalSize = null;
		totalSize = incomingProtocolDAO.countSearch(protocolNumber,
				searchDateFrom, searchDateTo, subject, keywords,
				distributionMethod, contact, false, includeDeletedProtocols,
				protocolBooks);
		searchIncomingPgng.setTotalSize(totalSize);
		int pageSize = searchIncomingPgng.getPageSize();

		// figure out which header to sort by
		Listheader header = getSortingListheader(searchIncomingLstbx);
		List<Order> sortBy = getSortBy(header);
		searchIncomingProtocols = incomingProtocolDAO.search(protocolNumber,
				searchDateFrom, searchDateTo, subject, keywords,
				distributionMethod, contact, false, includeDeletedProtocols,
				protocolBooks, startIndex, pageSize,
				sortBy.toArray(new Order[0]));
	}

	private void searchOutgoing(Integer startIndex) {
		boolean includeDeletedProtocols = false;
		if (getUserInSessionRole().equals(IConstants.ROLE_ADMIN)) {
			includeDeletedProtocols = true;
		}

		OutgoingProtocolDAO outgoingProtocolDAO = new OutgoingProtocolDAO();
		// set up paging by counting records first
		Integer totalSize = outgoingProtocolDAO.countSearch(protocolNumber,
				searchDateFrom, searchDateTo, subject, keywords,
				distributionMethod, toContact, false, includeDeletedProtocols,
				protocolBooks);
		searchOutgoingPgng.setTotalSize(totalSize);
		int pageSize = searchOutgoingPgng.getPageSize();

		// figure out which header to sort by
		Listheader header = getSortingListheader(searchOutgoingLstbx);
		List<Order> sortBy = getSortBy(header);

		searchOutgoingProtocols = outgoingProtocolDAO.search(protocolNumber,
				searchDateFrom, searchDateTo, subject, keywords,
				distributionMethod, toContact, false, includeDeletedProtocols,
				protocolBooks, startIndex, pageSize,
				sortBy.toArray(new Order[0]));

	}

	public IndexController() {
		super();
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		page.setAttribute(this.getClass().getSimpleName(), this);

		DistributionMethodDAO distributionMethodDAO = new DistributionMethodDAO();
		distributionMethods = distributionMethodDAO.findAll();

		ProtocolBookDAO protocolBookDAO = new ProtocolBookDAO();
		activeProtocolBooks = protocolBookDAO.findActiveBooks();
//		protocolBooks = protocolBookDAO.findAll();

		initIncoming();
		initOutgoing();
		initSearch();

		String tab = execution.getParameter(PARAM_SELECTED_TAB);

		if (tab != null) {
			if (tab.equals(incomingTb.getId())) {
				Listheader header = getSortingListheader(incomingLstbx);
				searchIncomingPending(0);
				indexTbx.setSelectedTab(incomingTb);
			} else if (tab.equals(outgoingTb.getId())) {
				Listheader header = getSortingListheader(outgoingLstbx);
				searchOutgoingPending(0);
				indexTbx.setSelectedTab(outgoingTb);
			} else if (tab.equals(searchTb.getId())) {
				initSearch();
				indexTbx.setSelectedTab(searchTb);
			} else {
				Listheader header = getSortingListheader(incomingLstbx);
				searchIncomingPending(0);
			}
		} else {
			Listheader header = getSortingListheader(incomingLstbx);
			searchIncomingPending(0);
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
	 */
	public void onSort(Event event) {
		Event sortEvent = ((ForwardEvent) event).getOrigin();
		// prevent default sorting from getting called
		sortEvent.stopPropagation();
		// setup sorting
		Listheader header = (Listheader) sortEvent.getTarget();
		Listbox parent = (Listbox) header.getParent().getParent();
		setSortingListheader(header);

		if (parent.equals(incomingLstbx)) {
			searchIncomingPending(0);
			incomingPgng.setActivePage(0);
		} else if (parent.equals(outgoingLstbx)) {
			searchOutgoingPending(0);
			outgoingPgng.setActivePage(0);
		} else if (parent.equals(searchIncomingLstbx)) {
			searchIncoming(0);
			searchIncomingPgng.setActivePage(0);
		} else if (parent.equals(searchOutgoingLstbx)) {
			searchOutgoing(0);
			searchOutgoingPgng.setActivePage(0);
		}

		getBinder(indexWin).loadAll();
	}

	/* incoming tab related */
	public void onSelect$incomingTb(SelectEvent event) {
		initIncoming();
		searchIncomingPending(0);
		getBinder(indexWin).loadAll();
	}

	public void onPaging$incomingPgng(PagingEvent event) {

		int activePage = event.getActivePage();
		int startIndex = activePage * incomingPgng.getPageSize();
		searchIncomingPending(startIndex);
		getBinder(indexWin).loadAll();

	}

	public void onSelect$incomingLstbx(SelectEvent event) {
		log.info(event);
		Integer id = selectedIncomingProtocol.getId();

		Executions.getCurrent().sendRedirect(
				IncomingController.PAGE + "?" + IConstants.PARAM_KEY_ID + "="
						+ id);
	}

	public void onClick$searchIncomingPendingBtn() throws InterruptedException {
		searchIncomingPending(0);
		getBinder(indexWin).loadAll();

		if (incomingProtocols.isEmpty()) {
			Messagebox.show(Labels.getLabel("search.notFound"),
					Labels.getLabel("search.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void onClick$clearIncomingPendingBtn() {
		initIncoming();
		getBinder(indexWin).loadAll();
	}

	public void onClick$newIncomingBtn() {
		Executions.getCurrent().sendRedirect(IncomingController.PAGE);
	}

	/* outgoing tab related */
	public void onSelect$outgoingTb(SelectEvent event) {
		initOutgoing();
		searchOutgoingPending(0);
		getBinder(indexWin).loadAll();
	}

	public void onPaging$outgoingPgng(PagingEvent event) {
		int activePage = event.getActivePage();
		int startIndex = activePage * outgoingPgng.getPageSize();
		searchOutgoingPending(startIndex);
		getBinder(indexWin).loadAll();

	}

	public void onSelect$outgoingLstbx(SelectEvent event) {
		Integer id = selectedOutgoingProtocol.getId();

		Executions.getCurrent().sendRedirect(
				OutgoingController.PAGE + "?" + IConstants.PARAM_KEY_ID + "="
						+ id);
	}

	public void onClick$searchOutgoingPendingBtn() throws InterruptedException {
		searchOutgoingPending(0);
		getBinder(indexWin).loadAll();

		if (outgoingProtocols.isEmpty()) {
			Messagebox.show(Labels.getLabel("search.notFound"),
					Labels.getLabel("search.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void onClick$clearOutgoingPendingBtn() {
		initOutgoing();
		getBinder(indexWin).loadAll();
	}

	public void onClick$newOutgoingBtn() {
		Executions.getCurrent().sendRedirect(OutgoingController.PAGE);
	}

	/* search tab related */

	public void onSelect$searchTb(SelectEvent event) {
		initSearch();
		getBinder(indexWin).loadAll();
	}

	public void onPaging$searchIncomingPgng(PagingEvent event) {
		int activePage = event.getActivePage();
		int startIndex = activePage * searchIncomingPgng.getPageSize();
		searchIncoming(startIndex);
		getBinder(indexWin).loadAll();
	}

	public void onSelect$searchIncomingLstbx(SelectEvent event) {
		Integer id = searchSelectedIncomingProtocol.getId();

		Executions.getCurrent().sendRedirect(
				IncomingController.PAGE + "?" + IConstants.PARAM_KEY_ID + "="
						+ id);
	}

	public void onPaging$searchOutgoingPgng(PagingEvent event) {
		int activePage = event.getActivePage();
		int startIndex = activePage * searchOutgoingPgng.getPageSize();
		searchOutgoing(startIndex);
		getBinder(indexWin).loadAll();
	}

	public void onSelect$searchOutgoingLstbx(SelectEvent event) {
		Integer id = searchSelectedOutgoingProtocol.getId();

		Executions.getCurrent().sendRedirect(
				OutgoingController.PAGE + "?" + IConstants.PARAM_KEY_ID + "="
						+ id);
	}

	public void onCheck$directionRdgrp(Event event) {
		log.info("CHECKED:" + directionRdgrp.getSelectedItem().getValue());
		getBinder(indexWin).loadAll();
	}

	public void onChanging$contactBndbx(InputEvent event) {
		String term = StringUtils.trimToNull(event.getValue());
		ContactDAO contactDAO = new ContactDAO();
		contacts = contactDAO.findByTerm(term, null, null);
		getBinder(indexWin).loadAll();
	}

	public void onOpen$contactBndbx(OpenEvent event) {
		if (!contacts.isEmpty()) {
			return;
		}
		ContactDAO contactDAO = new ContactDAO();
		contacts = contactDAO.findAll();
		getBinder(indexWin).loadAll();
	}

	public void onSelect$contactsLstbx(SelectEvent event) {
		// contactBndbx.setText(contact.getName() + " " + contact.getSurname());
		// contactBndbx.setText(getContactFullName());
		contactBndbx.close();
		getBinder(indexWin).loadAll();
	}

	public void onChanging$toContactBndbx(InputEvent event) {
		String term = StringUtils.trimToNull(event.getValue());
		ContactDAO contactDAO = new ContactDAO();
		toContacts = contactDAO.findByTerm(term, null, null);
		getBinder(indexWin).loadAll();
	}

	public void onOpen$toContactBndbx(OpenEvent event) {
		if (!toContacts.isEmpty()) {
			return;
		}
		ContactDAO contactDAO = new ContactDAO();
		toContacts = contactDAO.findAll();
		getBinder(indexWin).loadAll();
	}

	public void onSelect$toContactsLstbx(SelectEvent event) {
		// toContactBndbx.setText(getToContactFullName());
		toContactBndbx.close();
		getBinder(indexWin).loadAll();
	}

	public void onClick$searchBtn(Event event) throws InterruptedException {

		boolean found = false;

		if (getIsDirectionIncoming()) {
			searchIncoming(0);
			found = searchIncomingProtocols.isEmpty() ? false : true;
		}

		if (getIsDirectionOutgoing()) {
			searchOutgoing(0);
			found = searchOutgoingProtocols.isEmpty() ? false : true;

		}
		getBinder(indexWin).loadAll();
		if (!found) {
			Messagebox.show(Labels.getLabel("search.notFound"),
					Labels.getLabel("search.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void onClick$clearBtn(Event event) {
		initSearch();
		searchIncomingProtocols = null;
		searchIncomingPgng.setTotalSize(0);
		searchOutgoingProtocols = null;
		searchOutgoingPgng.setTotalSize(0);
		getBinder(indexWin).loadAll();
	}

	public boolean getIsDirectionIncoming() {
		if (directionRdgrp.getSelectedItem().getValue().equals("0")) {
			return true;
		}
		return false;
	}

	public boolean getIsDirectionOutgoing() {
		if (directionRdgrp.getSelectedItem().getValue().equals("1")) {
			return true;
		}
		return false;
	}

	public String getContactFullName() {
		if (contact == null) {
			return "";
		}
		return (contact.getName() != null ? contact.getName() : "") + " "
				+ (contact.getSurname() != null ? contact.getSurname() : "")
				+ " (" + contact.getCompany().getName() + ")";
	}

	public void setContactFullName(String contactFullName) {

	}

	public String getToContactFullName() {
		if (toContact == null) {
			return "";
		}
		return (toContact.getName() != null ? toContact.getName() : "")
				+ " "
				+ (toContact.getSurname() != null ? toContact.getSurname() : "")
				+ " (" + toContact.getCompany().getName() + ")";
	}

	public void setToContactFullName(String contactFullName) {

	}

	public List<IncomingProtocol> getIncomingProtocols() {
		return incomingProtocols;
	}

	public void setIncomingProtocols(List<IncomingProtocol> incomingProtocols) {
		this.incomingProtocols = incomingProtocols;
	}

	public List<OutgoingProtocol> getOutgoingProtocols() {
		return outgoingProtocols;
	}

	public void setOutgoingProtocols(List<OutgoingProtocol> outgoingProtocols) {
		this.outgoingProtocols = outgoingProtocols;
	}

	public IncomingProtocol getIncomingProtocol() {
		return incomingProtocol;
	}

	public void setIncomingProtocol(IncomingProtocol incomingProtocol) {
		this.incomingProtocol = incomingProtocol;
	}

	public OutgoingProtocol getOutgoingProtocol() {
		return outgoingProtocol;
	}

	public void setOutgoingProtocol(OutgoingProtocol outgoingProtocol) {
		this.outgoingProtocol = outgoingProtocol;
	}

	public IncomingProtocol getSelectedIncomingProtocol() {
		return selectedIncomingProtocol;
	}

	public void setSelectedIncomingProtocol(
			IncomingProtocol selectedIncomingProtocol) {
		this.selectedIncomingProtocol = selectedIncomingProtocol;
	}

	public OutgoingProtocol getSelectedOutgoingProtocol() {
		return selectedOutgoingProtocol;
	}

	public void setSelectedOutgoingProtocol(
			OutgoingProtocol selectedOutgoingProtocol) {
		this.selectedOutgoingProtocol = selectedOutgoingProtocol;
	}

	public Date getIncomingDateFrom() {
		return incomingDateFrom;
	}

	public void setIncomingDateFrom(Date dateFrom) {
		this.incomingDateFrom = dateFrom;
	}

	public Date getIncomingDateTo() {
		return incomingDateTo;
	}

	public void setIncomingDateTo(Date dateTo) {
		this.incomingDateTo = dateTo;
	}

	public Date getOutgoingDateFrom() {
		return outgoingDateFrom;
	}

	public void setOutgoingDateFrom(Date outgoingDateFrom) {
		this.outgoingDateFrom = outgoingDateFrom;
	}

	public Date getOutgoingDateTo() {
		return outgoingDateTo;
	}

	public void setOutgoingDateTo(Date outgoingDateTo) {
		this.outgoingDateTo = outgoingDateTo;
	}

	public String getProtocolNumber() {
		return protocolNumber;
	}

	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = StringUtils.trimToNull(protocolNumber);
	}

	public Date getSearchDateFrom() {
		return searchDateFrom;
	}

	public void setSearchDateFrom(Date searchDateFrom) {
		this.searchDateFrom = searchDateFrom;
	}

	public Date getSearchDateTo() {
		return searchDateTo;
	}

	public void setSearchDateTo(Date searchDateTo) {
		this.searchDateTo = searchDateTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = StringUtils.trimToNull(subject);
	}

	public List<DistributionMethod> getDistributionMethods() {
		return distributionMethods;
	}

	public void setDistributionMethods(
			List<DistributionMethod> distributionMethods) {
		this.distributionMethods = distributionMethods;
	}

	public DistributionMethod getDistributionMethod() {
		return distributionMethod;
	}

	public void setDistributionMethod(DistributionMethod distributionMethod) {
		this.distributionMethod = distributionMethod;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = StringUtils.trimToNull(keywords);
	}

	public List<IncomingProtocol> getSearchIncomingProtocols() {
		return searchIncomingProtocols;
	}

	public void setSearchIncomingProtocols(
			List<IncomingProtocol> searchIncomingProtocols) {
		this.searchIncomingProtocols = searchIncomingProtocols;
	}

	public List<OutgoingProtocol> getSearchOutgoingProtocols() {
		return searchOutgoingProtocols;
	}

	public void setSearchOutgoingProtocols(
			List<OutgoingProtocol> searchOutgoingProtocols) {
		this.searchOutgoingProtocols = searchOutgoingProtocols;
	}

	public IncomingProtocol getSearchSelectedIncomingProtocol() {
		return searchSelectedIncomingProtocol;
	}

	public void setSearchSelectedIncomingProtocol(
			IncomingProtocol searchSelectedIncomingProtocol) {
		this.searchSelectedIncomingProtocol = searchSelectedIncomingProtocol;
	}

	public OutgoingProtocol getSearchSelectedOutgoingProtocol() {
		return searchSelectedOutgoingProtocol;
	}

	public void setSearchSelectedOutgoingProtocol(
			OutgoingProtocol searchSelectedOutgoingProtocol) {
		this.searchSelectedOutgoingProtocol = searchSelectedOutgoingProtocol;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public List<Contact> getToContacts() {
		return toContacts;
	}

	public void setToContacts(List<Contact> toContacts) {
		this.toContacts = toContacts;
	}

	public Contact getToContact() {
		return toContact;
	}

	public void setToContact(Contact toContact) {
		this.toContact = toContact;
	}

	public List<ProtocolBook> getActiveProtocolBooks() {
		return activeProtocolBooks;
	}

	public void setActiveProtocolBooks(List<ProtocolBook> activeProtocolBooks) {
		this.activeProtocolBooks = activeProtocolBooks;
	}

	public List<ProtocolBook> getProtocolBooks() {
		return protocolBooks;
	}

	public void setProtocolBooks(List<ProtocolBook> protocolBooks) {
		this.protocolBooks = protocolBooks;
	}

}
