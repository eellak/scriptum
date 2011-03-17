package gr.scriptum.eprotocol.controller;

import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.security.ScriptumUser;
import gr.scriptum.eprotocol.ws.OkmDispatcherConfig;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.impl.api.InputElement;

public class BaseController extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9035911802277835130L;

	private static final Class<?>[] inputElements = { Bandbox.class,
			Combobox.class, Datebox.class, Decimalbox.class, Doublebox.class,
			Intbox.class, Longbox.class, Spinner.class, Textbox.class,
			Timebox.class };

	private static Log log = LogFactory.getLog(BaseController.class);

	/***
	 * Given a component, recursively traverses the child component hierarchy,
	 * forcing validation on all input type components (Textboxes, Dateboxes
	 * etc.). Adds all validation errors found to an exception list.
	 * 
	 * @param root
	 *            The component to start traversing from
	 * @param errors
	 *            The list of errors found so far
	 * @param component
	 *            if present, specific components to validate, all
	 *            direct/indirect children of the root component
	 * @return A list of zk validation exceptions
	 */
	private List<WrongValueException> validateFields(Component root,
			List<WrongValueException> errors, Component... component) {

		List<Component> children = root.getChildren();

		for (Component child : children) {

			if (ArrayUtils.contains(inputElements, child.getClass())) {
				Method method = null;
				try {
					method = child.getClass().getMethod("getValue", null);
				} catch (Exception e) {
					log.error(e);
				}

				if (method != null) {
					try {
						method.invoke(child, new Object[] {});
					} catch (InvocationTargetException e) {
						// log.warn(e.getCause());
						if (e.getCause() instanceof WrongValueException) {
							if (component.length != 0) {
								for (Component comp : component) {
									if (child.equals(comp)) {
										errors.add((WrongValueException) e
												.getCause());
										break;
									}
								}
							} else {
								errors.add((WrongValueException) e.getCause());
							}
						}
					} catch (Exception e) {
						log.error(e);
					}
				}
			}

			validateFields((Component) child, errors, component);
		}
		return errors;
	}

	/**
	 * Given a root component, recursively clears all validation messages on any
	 * InputElement child components.
	 * 
	 * @param root
	 *            The component to start traversing from
	 */
	protected void clearValidationMessages(Component root) {

		List<Component> children = root.getChildren();

		for (Component child : children) {
			if (child instanceof InputElement) {
				((InputElement) child).clearErrorMessage(true);
			}

			clearValidationMessages(child);
		}
	}

	/**
	 * Performs the data bind.
	 * 
	 * @return
	 */
	protected AnnotateDataBinder getBinder(Component comp) {
		if (comp == null) {
			return ((AnnotateDataBinder) page.getAttribute("binder"));
		}
		return ((AnnotateDataBinder) comp.getAttribute("binder"));
	}

	/**
	 * Perform validation on all InputElement type components by examining the
	 * validation of all children controls. Normaly is called on <i>this</i> to
	 * validate the page.
	 * 
	 * @param controller
	 * @return
	 * @throws Exception
	 */
	// protected List<WrongValueException> validateFields(BaseController
	// controller)
	// throws Exception {
	// // force validation on text fields
	// Field[] fields = controller.getClass().getDeclaredFields();
	// List<WrongValueException> errors = new ArrayList<WrongValueException>();
	// for (Field field : fields) {
	// if (ArrayUtils.contains(inputElements, field.getType())) {
	// Textbox object = (Textbox) field.get(controller); // TODO: avoid
	// // explicitly
	// // casting
	// // to
	// // Textbox
	// try {
	// object.getValue();
	// } catch (WrongValueException e) {
	// log.debug(e);
	// errors.add(e);
	// }
	// }
	// }
	// return errors;
	// }

	/**
	 * Given a root component, recursively traverses the child component
	 * hierarchy, forcing validation on all input type components (Textboxes,
	 * Dateboxes etc). When finished, throws a WrongValuesException, containing
	 * all WrongValueException instances thrown by components failing
	 * validation.
	 * 
	 * @param root
	 *            The root component to begin traversing from
	 * @param component
	 *            if present, an explicit list of components to validate, all
	 *            direct/indirect children of the root component
	 */
	protected void validateFields(Component root, Component... component) {
		List<WrongValueException> errors = new ArrayList<WrongValueException>();
		errors = validateFields(root, errors, component);
		if (!errors.isEmpty()) {
			getBinder(root).loadAll();
			throw new WrongValuesException(
					errors.toArray(new WrongValueException[0]));
		}
	}

	protected OkmProtocolDispatcherImpl getOkmDispatcher() {
		OkmDispatcherConfig config = new OkmDispatcherConfig();
		return new OkmProtocolDispatcherImpl(config);
	}

	protected Users getUserInSession() {
		// Object attribute = session.getAttribute(IConstants.KEY_USER);
		// if (attribute == null) {
		// return null;
		// }
		// return (Users) attribute;

		ScriptumUser scriptumUser = (ScriptumUser) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return scriptumUser.getUser();
	}

	protected String getOkmToken() {
		// Object attribute = session.getAttribute(IConstants.KEY_OKM_TOKEN);
		// if (attribute == null) {
		// return null;
		// }
		// return (String) attribute;
		ScriptumUser scriptumUser = (ScriptumUser) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return scriptumUser.getOkmToken();
	}

	protected void setSortingListheader(Listheader listHeader) {
		String direction = null;
		if (listHeader.getSortDirection().equals("ascending")) {
			direction = "descending";
		} else if (listHeader.getSortDirection().equals("natural")) {
			direction = "ascending";
		} else if (listHeader.getSortDirection().equals("descending")) {
			direction = "ascending";
		}
		Listbox parentLstbx = (Listbox) listHeader.getParent().getParent();
		for (Object child : parentLstbx.getListhead().getChildren()) {
			Listheader header = (Listheader) child;
			header.setSortDirection(header != listHeader ? "natural"
					: direction);
		}
	}

	protected Listheader getSortingListheader(Listbox listbox) {
		for (Object child : listbox.getListhead().getChildren()) {
			Listheader header = (Listheader) child;
			if (!header.getSortDirection().equals("natural")) {
				return header;
			}
		}
		return null;
	}

	protected String getIp() {
		return Executions.getCurrent().getRemoteAddr();
	}
}
