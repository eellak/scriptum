/**
 * 
 */
package gr.scriptum.eprotocol.wserver;

import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import gr.scriptum.dao.PermissionDAO;
import gr.scriptum.dao.ProtocolDAO;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.Protocol_;
import gr.scriptum.domain.UserAssignment;
import gr.scriptum.domain.Users;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.domain.ProtocolNode.Direction;
import gr.scriptum.eprotocol.service.ProtocolService;
import gr.scriptum.eprotocol.util.Permission;
import gr.scriptum.predicate.CorrespondentPredicate;
import gr.scriptum.predicate.CorrespondentPredicateGroup;
import gr.scriptum.predicate.CorrespondentPredicateGroup.JunctionType;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Service
public class ProtocolRetriever extends ProtocolMessageParser {

	private static Log log = LogFactory.getLog(ProtocolRetriever.class);

	@Transactional(readOnly = true)
	public List<ProtocolInfo> search(ProtocolQuery query, WebServiceContext wsContext, Validator validator)
			throws Exception {

		Users user = authenticate(wsContext);
		if (user == null) {
			throw new Exception("Invalid credentials");
		}

		// collect user permissions
		Set<gr.scriptum.domain.Permission> userPermissions = getPermissions(user);

		// validate payload constraints
		Set<ConstraintViolation<ProtocolQuery>> constraintViolations = validator.validate(query);
		if (!constraintViolations.isEmpty()) {
			ProtocolWebServiceFaultBean faultBean = getFaultBean(constraintViolations);
			throw new ProtocolWebServiceFault(faultBean);
		}

		// check if user can search protocols
		boolean userCanSearch = userPermissions.stream()
				.anyMatch(p -> p.getName().equals(Permission.SEARCH_BOOK.toString()));
		if (!userCanSearch) {
			throw new Exception("Invalid permissions");
		}

		// fetch protocol book
		ProtocolBook protocolBook = getProtocolBook(query.getBook());
		if (protocolBook == null) {
			throw new Exception("book:invalid");
		}

		// check if user has proper permissions on book
		gr.scriptum.domain.Permission searchBookPermission = new gr.scriptum.domain.Permission();
		searchBookPermission.setName(Permission.SEARCH_BOOK.toString());
		PermissionDAO permissionDAO = ContextLoader.getCurrentWebApplicationContext().getBean(PermissionDAO.class);
		searchBookPermission = permissionDAO.findByExample(searchBookPermission).get(0);
		List<UserAssignment> bookAssignments = getBookAssignments(user, protocolBook, searchBookPermission);
		if (bookAssignments.isEmpty()) {
			throw new Exception("book:invalid permissions");
		}

		// fetch user department (trust client side that user belongs to
		// supplied department)
		Department department = null;
		if (query.getDepartment() != null) {
			department = getDepartment(query.getDepartment());
			if (department == null) {
				throw new Exception("department: invalid");
			}
		}

		ProtocolDAO protocolDAO = ContextLoader.getCurrentWebApplicationContext().getBean(ProtocolDAO.class);

		List<ProtocolBook> protocolBooks = new ArrayList<>();
		protocolBooks.add(protocolBook);
		Direction direction = null;
		List<CorrespondentPredicateGroup> predicateGroups = new ArrayList<>();
		List<CorrespondentPredicate> departmentCorrespondentPredicates = new ArrayList<>();
		ProtocolCorrespondent departmentCorrespondent = null;
		if (department != null) {
			departmentCorrespondent = new ProtocolCorrespondent(null, null, CorrespondentType.DEPARTMENT);
			departmentCorrespondent.setDepartment(department);
		}

		if (departmentCorrespondent != null) {
			// include protocols (both incoming & outgoing) created by supplied
			// department
			CorrespondentPredicate creatorPredicate = new CorrespondentPredicate(departmentCorrespondent);
			creatorPredicate.setProtocolDirection(query.getDirection());
			creatorPredicate.setDirection(CREATOR);
			departmentCorrespondentPredicates.add(creatorPredicate);

			if (query.getDirection() == null || query.getDirection().equals(Direction.INCOMING)) {
				// also include protocols addressed to supplied department

				CorrespondentPredicate recipientPredicate = new CorrespondentPredicate(departmentCorrespondent);
				recipientPredicate.setProtocolDirection(Direction.OUTGOING);
				recipientPredicate.setDirection(RECIPIENT);
				departmentCorrespondentPredicates.add(recipientPredicate);

				CorrespondentPredicate internalRecipientPredicate = new CorrespondentPredicate(departmentCorrespondent);
				internalRecipientPredicate.setProtocolDirection(Direction.INCOMING);
				internalRecipientPredicate.setDirection(INTERNAL_RECIPIENT);
				departmentCorrespondentPredicates.add(internalRecipientPredicate);
			}
		} else {
			direction = query.getDirection();
		}

		CorrespondentPredicateGroup predicateGroup = new CorrespondentPredicateGroup(departmentCorrespondentPredicates,
				JunctionType.OR);
		predicateGroups.add(predicateGroup);

		List<Protocol> protocols = protocolDAO.search(direction, query.getProtocolYear(), query.getProtocolNumber(),
				null, null, null, null, null, null, null, null, null, null, null, null, null, false, false,
				protocolBooks, null, null, predicateGroups, null, Order.asc(Protocol_.protocolNumber.getName()));

		protocols.forEach(p -> p.getProtocolCorrespondents().forEach(ProtocolCorrespondent::getType));
		return protocols.stream().map(ProtocolInfo::new).collect(Collectors.toCollection(ArrayList::new));
	}
}
