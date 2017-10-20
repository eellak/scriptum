/**
 * 
 */
package gr.scriptum.eprotocol.service;

import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection.*;
import static gr.scriptum.domain.ProtocolNode.Direction.*;
import static java.lang.Boolean.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.dao.ProtocolDAO;
import gr.scriptum.dao.ProtocolDAO.StatisticDirection;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.domain.ProtocolCorrespondent_;
import gr.scriptum.domain.ProtocolNode.Direction;
import gr.scriptum.domain.Protocol_;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.predicate.CorrespondentPredicate;
import gr.scriptum.predicate.CorrespondentPredicateGroup;
import junit.framework.Assert;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Service
public class ReportsService {

	@Autowired
	private ProtocolDAO protocolDAO;

	@Autowired
	private DistributionMethodDAO distributionMethodDAO;

	@Transactional(readOnly = true)
	public List<Protocol> getCreatorReport(Department department, Date from, Date to, boolean incoming,
			boolean outgoing, Integer numberFrom, Integer numberTo, List<ProtocolBook> protocolBooks,
			List<Order> sortBy) {

		List<CorrespondentPredicate> correspondentPredicatesList = new ArrayList<CorrespondentPredicate>();

		ProtocolCorrespondent creatorCorrespondent = new ProtocolCorrespondent(null, null,
				CorrespondentType.DEPARTMENT);
		creatorCorrespondent.setDepartment(department);
		CorrespondentPredicate creatorCorrespondentPredicate = new CorrespondentPredicate(creatorCorrespondent);
		if (incoming ^ outgoing) {
			creatorCorrespondentPredicate.setProtocolDirection(incoming ? INCOMING : OUTGOING);
		}
		creatorCorrespondentPredicate.setDirection(CorrespondentDirection.CREATOR);
		correspondentPredicatesList.add(creatorCorrespondentPredicate);
		CorrespondentPredicateGroup predicateGroup = new CorrespondentPredicateGroup(correspondentPredicatesList,
				CorrespondentPredicateGroup.JunctionType.OR);
		List<CorrespondentPredicateGroup> predicateGroups = new ArrayList<CorrespondentPredicateGroup>();
		predicateGroups.add(predicateGroup);
		List<Criterion> additionalCriteria = new ArrayList<>();
		if (numberFrom != null) {
			additionalCriteria.add(Restrictions.ge(Protocol_.protocolNumber.getName(), numberFrom));
		}
		if (numberTo != null) {
			additionalCriteria.add(Restrictions.le(Protocol_.protocolNumber.getName(), numberTo));
		}
		return protocolDAO.search(null, null, null, null, null, null, from, to, null, null, null, null, null, null,
				null, null, false, true, protocolBooks, null, null, predicateGroups,
				additionalCriteria.toArray(new Criterion[0]), sortBy.toArray(new Order[0]));
	}

	@Transactional(readOnly = true)
	public List<Protocol> getRecipientReport(Department department, Date from, Date to, boolean incoming,
			boolean outgoing, Date deliveryDateFrom, Date deliveryDateTo, List<ProtocolBook> protocolBooks,
			List<Order> sortBy) {
		List<CorrespondentPredicate> correspondentPredicatesList = new ArrayList<CorrespondentPredicate>();

		Calendar deliveryDateToCalendar = Calendar.getInstance();
		deliveryDateToCalendar.setTime(deliveryDateTo);
		deliveryDateToCalendar.add(Calendar.DAY_OF_MONTH, 1);

		Criterion[] deliveryDateCriteria = new Criterion[] {
				Restrictions.ge(ProtocolCorrespondent_.deliveryDate.getName(), deliveryDateFrom),
				Restrictions.lt(ProtocolCorrespondent_.deliveryDate.getName(), deliveryDateToCalendar.getTime()) };
		if (outgoing) {
			ProtocolCorrespondent recipientCorrespondent = new ProtocolCorrespondent(null, null,
					CorrespondentType.DEPARTMENT);
			recipientCorrespondent.setDepartment(department);
			CorrespondentPredicate recipientCorrespondentPredicate = new CorrespondentPredicate(recipientCorrespondent);
			recipientCorrespondentPredicate.setProtocolDirection(Direction.OUTGOING);
			recipientCorrespondentPredicate.setDirection(RECIPIENT);

			recipientCorrespondentPredicate.setAdditionalCriteria(deliveryDateCriteria);
			correspondentPredicatesList.add(recipientCorrespondentPredicate);
		}
		if (incoming) {
			ProtocolCorrespondent internalRecipientCorrespondent = new ProtocolCorrespondent(null, null,
					CorrespondentType.DEPARTMENT);
			internalRecipientCorrespondent.setDepartment(department);
			CorrespondentPredicate internalRecipientCorrespondentPredicate = new CorrespondentPredicate(
					internalRecipientCorrespondent);
			internalRecipientCorrespondentPredicate.setProtocolDirection(Direction.INCOMING);
			internalRecipientCorrespondentPredicate.setDirection(INTERNAL_RECIPIENT);
			internalRecipientCorrespondentPredicate.setAdditionalCriteria(deliveryDateCriteria);
			correspondentPredicatesList.add(internalRecipientCorrespondentPredicate);
		}
		CorrespondentPredicateGroup predicateGroup = new CorrespondentPredicateGroup(correspondentPredicatesList,
				CorrespondentPredicateGroup.JunctionType.OR);
		List<CorrespondentPredicateGroup> predicateGroups = new ArrayList<CorrespondentPredicateGroup>();
		predicateGroups.add(predicateGroup);
		return protocolDAO.search(null, null, null, null, null, null, from, to, null, null, null, null, null, null,
				null, null, false, true, protocolBooks, null, null, predicateGroups, null,
				sortBy.toArray(new Order[0]));
	}

	public List<Protocol> getRoutingReport(Department department, Date from, Date to, Date routingDateFrom,
			Date routingDateTo, List<Order> sortBy) {

		DistributionMethod example = new DistributionMethod();
		example.setAutoFillRoutingDate(TRUE);
		List<DistributionMethod> autoFilledRoutingDateDistributionMethods = distributionMethodDAO
				.findByExample(example);

		Calendar routingDateToCalendar = Calendar.getInstance();
		routingDateToCalendar.setTime(routingDateTo);
		routingDateToCalendar.add(Calendar.DAY_OF_MONTH, 1);

		List<CorrespondentPredicate> correspondentPredicates = new ArrayList<CorrespondentPredicate>();

		ProtocolCorrespondent correspondent = new ProtocolCorrespondent(null, null, CorrespondentType.DEPARTMENT);
		correspondent.setDepartment(department);
		Criterion[] additionalCorrespondentCriteria = new Criterion[] {
				Restrictions.ge(ProtocolCorrespondent_.routingDate.getName(), routingDateFrom),
				Restrictions.lt(ProtocolCorrespondent_.routingDate.getName(), routingDateToCalendar.getTime()),
				Restrictions.isNull(ProtocolCorrespondent_.deliveryDate.getName()),
				Restrictions.not(Restrictions.in(ProtocolCorrespondent_.distributionMethod.getName(),
						autoFilledRoutingDateDistributionMethods)) };

		CorrespondentPredicate recipientPredicate = new CorrespondentPredicate(correspondent);
		recipientPredicate.setProtocolDirection(Direction.OUTGOING);
		recipientPredicate.setDirection(RECIPIENT);
		recipientPredicate.setAdditionalCriteria(additionalCorrespondentCriteria);
		correspondentPredicates.add(recipientPredicate);

		CorrespondentPredicate internalRecipientPredicate = new CorrespondentPredicate(correspondent);
		internalRecipientPredicate.setProtocolDirection(Direction.INCOMING);
		internalRecipientPredicate.setDirection(INTERNAL_RECIPIENT);
		internalRecipientPredicate.setAdditionalCriteria(additionalCorrespondentCriteria);
		correspondentPredicates.add(internalRecipientPredicate);

		CorrespondentPredicateGroup predicateGroup = new CorrespondentPredicateGroup(correspondentPredicates,
				CorrespondentPredicateGroup.JunctionType.OR);
		List<CorrespondentPredicateGroup> predicateGroups = new ArrayList<CorrespondentPredicateGroup>();
		predicateGroups.add(predicateGroup);

		return protocolDAO.search(null, null, null, null, null, null, from, to, null, null, null, null, null, null,
				null, null, false, false, null, null, null, predicateGroups, null, sortBy.toArray(new Order[0]));

	}

	@Transactional(readOnly = true)
	public List<Protocol> getAssigneeReport(Department department, Department departmentAssignee,
			UserHierarchy employeeAssignee, Date from, Date to, boolean incoming, boolean outgoing,
			Date deliveryDateFrom, Date deliveryDateTo, List<ProtocolBook> protocolBooks, List<Order> sortBy) {
		List<CorrespondentPredicate> correspondentPredicatesList = new ArrayList<CorrespondentPredicate>();

		Calendar deliveryDateToCalendar = Calendar.getInstance();
		deliveryDateToCalendar.setTime(deliveryDateTo);
		deliveryDateToCalendar.add(Calendar.DAY_OF_MONTH, 1);

		Criterion[] deliveryDateCriteria = new Criterion[] {
				Restrictions.ge(ProtocolCorrespondent_.deliveryDate.getName(), deliveryDateFrom),
				Restrictions.lt(ProtocolCorrespondent_.deliveryDate.getName(), deliveryDateToCalendar.getTime()) };
		if (outgoing) {
			ProtocolCorrespondent recipientCorrespondent = new ProtocolCorrespondent(null, null,
					CorrespondentType.DEPARTMENT);
			recipientCorrespondent.setDepartment(department);
			CorrespondentPredicate recipientCorrespondentPredicate = new CorrespondentPredicate(recipientCorrespondent);
			recipientCorrespondentPredicate.setProtocolDirection(Direction.OUTGOING);
			recipientCorrespondentPredicate.setDirection(RECIPIENT);

			recipientCorrespondentPredicate.setAdditionalCriteria(deliveryDateCriteria);
			correspondentPredicatesList.add(recipientCorrespondentPredicate);
		}
		if (incoming) {
			ProtocolCorrespondent internalRecipientCorrespondent = new ProtocolCorrespondent(null, null,
					CorrespondentType.DEPARTMENT);
			internalRecipientCorrespondent.setDepartment(department);
			CorrespondentPredicate internalRecipientCorrespondentPredicate = new CorrespondentPredicate(
					internalRecipientCorrespondent);
			internalRecipientCorrespondentPredicate.setProtocolDirection(Direction.INCOMING);
			internalRecipientCorrespondentPredicate.setDirection(INTERNAL_RECIPIENT);
			internalRecipientCorrespondentPredicate.setAdditionalCriteria(deliveryDateCriteria);
			correspondentPredicatesList.add(internalRecipientCorrespondentPredicate);
		}
		CorrespondentPredicateGroup predicateGroup = new CorrespondentPredicateGroup(correspondentPredicatesList,
				CorrespondentPredicateGroup.JunctionType.OR);
		List<CorrespondentPredicateGroup> predicateGroups = new ArrayList<CorrespondentPredicateGroup>();
		predicateGroups.add(predicateGroup);

		ProtocolCorrespondent assigneeCorrespondent = new ProtocolCorrespondent();
		Assert.assertTrue("Only one of department/employee assignees can be present",
				departmentAssignee != null ^ employeeAssignee != null);

		if (departmentAssignee != null) {
			assigneeCorrespondent.setType(CorrespondentType.DEPARTMENT);
			assigneeCorrespondent.setDepartment(departmentAssignee);
		} else if (employeeAssignee != null) {
			assigneeCorrespondent.setType(CorrespondentType.EMPLOYEE);
			assigneeCorrespondent.setDepartment(employeeAssignee.getDepartment());
			assigneeCorrespondent.setContact(employeeAssignee.getContact());
		}
		CorrespondentPredicate assigneePredicate = new CorrespondentPredicate(assigneeCorrespondent);
		assigneePredicate.setDirection(ASSIGNEE);
		CorrespondentPredicateGroup assigneeGroup = new CorrespondentPredicateGroup(assigneePredicate);
		predicateGroups.add(assigneeGroup);

		return protocolDAO.search(null, null, null, null, null, null, from, to, null, null, null, null, null, null,
				null, null, false, false, protocolBooks, null, null, predicateGroups, null,
				sortBy.toArray(new Order[0]));
	}

	@Transactional(readOnly = true)
	public List<Object[]> getProtocolCountByCriteria(ProtocolBook protocolBook, Date from, Date to, boolean groupDates,
			Department department, DocumentType documentType, StatisticDirection statisticDirection) {
		return protocolDAO.countByCriteria(protocolBook, department, from, to, groupDates, documentType,
				statisticDirection);
	}
}
