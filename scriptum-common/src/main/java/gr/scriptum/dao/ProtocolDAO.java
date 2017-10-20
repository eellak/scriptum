/**
 * 
 */
package gr.scriptum.dao;

import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.Department;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent_;
import gr.scriptum.domain.ProtocolDocument_;
import gr.scriptum.domain.ProtocolNode.Direction;
import gr.scriptum.domain.Protocol_;
import gr.scriptum.predicate.CorrespondentPredicate;
import gr.scriptum.predicate.CorrespondentPredicateGroup;

/**
 * @author aanagnostopoulos
 * 
 */
@Repository
public class ProtocolDAO extends GenericDAO<Protocol, Integer> {

	private static Log log = LogFactory.getLog(ProtocolDAO.class);

	public enum StatisticDirection {
		INCOMING, OUTGOING, RECIPIENT, ALL;
	}

	private Criteria buildSearchCriteria(Direction direction, Integer protocolYear, Integer protocolNumber,
			String incomingProtocolNumber, Date incomingDateFrom, Date incomingDateTo, Date from, Date to,
			String subject, String comments, String keywords, Integer attachedNumber, String attachedDescription,
			DistributionMethod distributionMethod, String distributionMethodDetails, DocumentType protocolDocumentType,
			boolean includePending, boolean includeDeleted, List<ProtocolBook> protocolBooks,
			List<CorrespondentPredicateGroup> correspondentPredicateGroups, Criterion[] additionalProtocolCriteria) {

		Criteria crit = getSession().createCriteria(getPersistentClass());

		if (!includeDeleted) {
			// exclude deleted protocols
			Disjunction notDeletedDisjunction = Restrictions.disjunction();
			notDeletedDisjunction.add(Restrictions.eq(Protocol_.isDeleted.getName(), false));
			notDeletedDisjunction.add(Restrictions.isNull(Protocol_.isDeleted.getName()));
			crit.add(notDeletedDisjunction);
		}

		if (direction != null) {
			crit.add(Restrictions.eq(Protocol_.direction.getName(), direction));
		}

		if (protocolYear != null) {
			crit.add(Restrictions.eq(Protocol_.protocolYear.getName(), protocolYear));
		}

		if (protocolNumber != null) {

			Integer protocolNumberParsed = null;
			try {
				protocolNumberParsed = Integer.valueOf(protocolNumber);
			} catch (NumberFormatException e) {
				log.warn(e);
			}

			if (protocolNumberParsed == null) {
				// make sure no results are returned
				crit.add(Restrictions.isNull(Protocol_.id.getName()));
			} else {
				crit.add(Restrictions.eq(Protocol_.protocolNumber.getName(), protocolNumberParsed));
			}
		}

		if (!includePending) {
			// ensure search is performed in submitted protocols
			crit.add(Restrictions.isNotNull(Protocol_.protocolNumber.getName()));
		}

		if (incomingProtocolNumber != null) {
			crit.add(Restrictions.eqOrIsNull(Protocol_.incomingProtocolNumber.getName(), incomingProtocolNumber));
		}

		if (incomingDateFrom != null) {
			crit.add(Restrictions.ge(Protocol_.incomingDate.getName(), incomingDateFrom));
		}

		if (incomingDateTo != null) {
			/*
			 * add one day to make date search more user-friendly (since the
			 * user tends to think in terms of <= , when it comes to dates)
			 */
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(incomingDateTo);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			crit.add(Restrictions.lt(Protocol_.incomingDate.getName(), calendar.getTime()));
		}

		if (from != null) {
			crit.add(Restrictions.ge(Protocol_.protocolDate.getName(), from));
		}

		if (to != null) {
			/*
			 * add one day to make date search more user-friendly (since the
			 * user tends to think in terms of <= , when it comes to dates)
			 */
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			crit.add(Restrictions.lt(Protocol_.protocolDate.getName(), calendar.getTime()));
		}

		if (subject != null) {
			crit.add(Restrictions.like(Protocol_.subject.getName(), subject, MatchMode.ANYWHERE));
		}

		if (comments != null) {
			crit.add(Restrictions.like(Protocol_.comments.getName(), comments, MatchMode.ANYWHERE));
		}

		if (distributionMethod != null) {
			crit.add(Restrictions.eq(Protocol_.distributionMethod.getName(), distributionMethod));
		}

		if (distributionMethodDetails != null) {
			crit.add(Restrictions.like(Protocol_.distributionMethodDetails.getName(), distributionMethodDetails,
					MatchMode.ANYWHERE));
		}

		if (protocolDocumentType != null) {
			crit.add(Restrictions.eq(Protocol_.documentType.getName(), protocolDocumentType));
		}

		if (keywords != null) {
			Criteria documentCriteria = crit.createCriteria(Protocol_.protocolDocuments.getName());
			String[] tokens = keywords.split(ProtocolDocumentDAO.KEYWORDS_DELIMITER);
			Disjunction disjunction = Restrictions.disjunction();
			for (String token : tokens) {
				disjunction.add(
						Restrictions.like(ProtocolDocument_.documentKeywords.getName(), token, MatchMode.ANYWHERE));
			}
			documentCriteria.add(disjunction);
		}

		if (attachedNumber != null) {
			crit.add(Restrictions.eq(Protocol_.attachedNumber.getName(), attachedNumber));
		}

		if (attachedDescription != null) {
			crit.add(Restrictions.like(Protocol_.attachedDescription.getName(), attachedDescription,
					MatchMode.ANYWHERE));
		}

		if (protocolBooks != null && !protocolBooks.isEmpty()) {
			crit.add(Restrictions.in(Protocol_.protocolBook.getName(), protocolBooks));
			// Disjunction disjunction = Restrictions.disjunction();
			// disjunction.add(Restrictions.in("protocolBook", protocolBooks));
			// disjunction.add(Restrictions.isNull("protocolBook"));
			// crit.add(disjunction);
		}

		if (additionalProtocolCriteria != null) {
			for (Criterion additionalCriterion : additionalProtocolCriteria) {
				crit.add(additionalCriterion);
			}
		}

		if (correspondentPredicateGroups != null) {
			for (CorrespondentPredicateGroup predicateGroup : correspondentPredicateGroups) {
				Junction junctionBetweenCorrespondentPredicates = null;
				if (predicateGroup.getJunctionType().equals(CorrespondentPredicateGroup.JunctionType.OR)) {
					junctionBetweenCorrespondentPredicates = Restrictions.disjunction();
				} else {
					junctionBetweenCorrespondentPredicates = Restrictions.conjunction();
				}
				crit.add(junctionBetweenCorrespondentPredicates);

				List<CorrespondentPredicate> correspondentPredicates = predicateGroup.getCorrespondentPredicates();
				for (CorrespondentPredicate correspondentPredicate : correspondentPredicates) {
					ProtocolCorrespondent correspondent = correspondentPredicate.getCorrespondent();
					DetachedCriteria correspondentsCriteria = DetachedCriteria.forClass(ProtocolCorrespondent.class);
					correspondentsCriteria
							.setProjection(Projections.property(ProtocolCorrespondent_.protocol.getName()));
					junctionBetweenCorrespondentPredicates.add(
							Subqueries.propertyIn(Protocol_.protocolCorrespondents.getName(), correspondentsCriteria));
					if (correspondentPredicate.getProtocolDirection() != null) {
						DetachedCriteria correspondentProtocolCrit = correspondentsCriteria
								.createCriteria(ProtocolCorrespondent_.protocol.getName());
						correspondentProtocolCrit.add(Restrictions.eq(Protocol_.direction.getName(),
								correspondentPredicate.getProtocolDirection()));
					}

					Conjunction conjunction = Restrictions.conjunction();
					correspondentsCriteria.add(conjunction);

					if (correspondentPredicate.getDirection() != null) {
						conjunction.add(Restrictions.eq(ProtocolCorrespondent_.direction.getName(),
								correspondentPredicate.getDirection()));
						switch (correspondentPredicate.getDirection()) {
						case SENDER:
						case TRANSACTOR:
						case INTERNAL_RECIPIENT:
						case AUTHOR:
						case RECIPIENT:
							if (correspondent != null) {
								switch (correspondent.getType()) {
								case ACTIVE_MEMBER:
									Map<String, Object> propertyNameValues = new LinkedHashMap<>();
									propertyNameValues.put(ProtocolCorrespondent_.type.getName(), ACTIVE_MEMBER);
									propertyNameValues.put(ProtocolCorrespondent_.code.getName(),
											correspondent.getCode());
									propertyNameValues.put(ProtocolCorrespondent_.description.getName(),
											correspondent.getDescription());
									conjunction.add(Restrictions.allEq(propertyNameValues));
									break;
								case INACTIVE_MEMBER:
									propertyNameValues = new LinkedHashMap<>();
									propertyNameValues.put(ProtocolCorrespondent_.type.getName(), INACTIVE_MEMBER);
									propertyNameValues.put(ProtocolCorrespondent_.code.getName(),
											correspondent.getCode());
									propertyNameValues.put(ProtocolCorrespondent_.description.getName(),
											correspondent.getDescription());
									conjunction.add(Restrictions.allEq(propertyNameValues));
									break;
								case EMPLOYEE:
									propertyNameValues = new LinkedHashMap<>();
									propertyNameValues.put(ProtocolCorrespondent_.type.getName(), EMPLOYEE);
									propertyNameValues.put(ProtocolCorrespondent_.contact.getName(),
											correspondent.getContact());
									if (correspondent.getDepartment() != null) {
										// be more specific,by also setting the
										// department
										propertyNameValues.put(ProtocolCorrespondent_.department.getName(),
												correspondent.getDepartment());
									}
									conjunction.add(Restrictions.allEq(propertyNameValues));
									break;
								case DEPARTMENT:
									conjunction.add(Restrictions.eq(ProtocolCorrespondent_.type.getName(), DEPARTMENT));
									conjunction.add(Restrictions.eq(ProtocolCorrespondent_.department.getName(),
											correspondent.getDepartment()));
									break;
								case COMPANY:
									conjunction.add(Restrictions.eq(ProtocolCorrespondent_.type.getName(), COMPANY));
									conjunction.add(Restrictions.eq(ProtocolCorrespondent_.company.getName(),
											correspondent.getCompany()));
									break;
								case CONTACT:
									conjunction.add(Restrictions.eq(ProtocolCorrespondent_.type.getName(), CONTACT));
									conjunction.add(Restrictions.eq(ProtocolCorrespondent_.contact.getName(),
											correspondent.getContact()));
									break;
								case OTHER:
									conjunction.add(Restrictions.eq(ProtocolCorrespondent_.type.getName(), OTHER));
									conjunction.add(Restrictions.eq(ProtocolCorrespondent_.description.getName(),
											correspondent.getDescription()));
									break;
								default:
									continue;
								}
							}
							break;
						case CREATOR:
							conjunction.add(Restrictions.isNull(ProtocolCorrespondent_.type.getName()));
							if (correspondent == null) {
								break;
								// throw new IllegalArgumentException(
								// "Correspondent must not be null for 'CREATOR'
								// Predicate");
							}
							if (correspondent.getUser() != null) {
								conjunction.add(Restrictions.eq(ProtocolCorrespondent_.user.getName(),
										correspondent.getUser()));
							}
							if (correspondent.getDepartment() != null) {
								conjunction.add(Restrictions.eq(ProtocolCorrespondent_.department.getName(),
										correspondent.getDepartment()));
							}
							break;
						case ASSIGNEE:
							conjunction.add(
									Restrictions.eq(ProtocolCorrespondent_.type.getName(), correspondent.getType()));
							if (correspondent.getDepartment() != null) {
								conjunction.add(Restrictions.eq(ProtocolCorrespondent_.department.getName(),
										correspondent.getDepartment()));
							}
							if (correspondent.getContact() != null) {
								conjunction.add(Restrictions.eq(ProtocolCorrespondent_.contact.getName(),
										correspondent.getContact()));
							}
							break;
						default:
							break;
						}
					}

					// add any other, arbitrary, criteria
					if (correspondentPredicate.getAdditionalCriteria() != null) {
						Criterion[] additionalCriteria = correspondentPredicate.getAdditionalCriteria();
						for (Criterion additionalCriterion : additionalCriteria) {
							// special handling for association equality
							// criteria
							if (additionalCriterion instanceof SimpleExpression) {
								SimpleExpression expression = (SimpleExpression) additionalCriterion;
								if (expression.getPropertyName().contains(".")) {
									String propertyName = expression.getPropertyName();
									String associationPath = propertyName.substring(0, propertyName.indexOf("."));
									DetachedCriteria alias = correspondentsCriteria.createAlias(associationPath,
											associationPath);
									alias.add(expression);
								} else {
									conjunction.add(additionalCriterion);
								}
							} else {
								conjunction.add(additionalCriterion);
							}
						}
					}
				}
			}
		}
		return crit;
	}

	@Transactional(readOnly = true)
	public Integer countSearch(Direction direction, Integer protocolYear, Integer protocolNumber,
			String incomingProtocolNumber, Date incomingDateFrom, Date incomingDateTo, Date from, Date to,
			String subject, String comments, String keywords, Integer attachedNumber, String attachedDescription,
			DistributionMethod distributionMethod, String distributionMethodDetails, DocumentType protocolDocumentType,
			boolean includePending, boolean includeDeleted, List<ProtocolBook> protocolBooks,
			List<CorrespondentPredicateGroup> correspondentPredicateGroups, Criterion[] additionalCriteria) {

		Criteria crit = buildSearchCriteria(direction, protocolYear, protocolNumber, incomingProtocolNumber,
				incomingDateFrom, incomingDateTo, from, to, subject, comments, keywords, attachedNumber,
				attachedDescription, distributionMethod, distributionMethodDetails, protocolDocumentType,
				includePending, includeDeleted, protocolBooks, correspondentPredicateGroups, additionalCriteria);
		crit.setProjection(Projections.rowCount());
		Integer count = ((Long) crit.uniqueResult()).intValue();
		log.debug("Rows counted (Protocol) : " + count);
		return count;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Protocol> search(Direction direction, Integer protocolYear, Integer protocolNumber,
			String incomingProtocolNumber, Date incomingDateFrom, Date incomingDateTo, Date from, Date to,
			String subject, String comments, String keywords, Integer attachedNumber, String attachedDescription,
			DistributionMethod distributionMethod, String distributionMethodDetails, DocumentType protocolDocumentType,
			boolean includePending, boolean includeDeleted, List<ProtocolBook> protocolBooks, Integer firstResult,
			Integer maxResults, List<CorrespondentPredicateGroup> correspondentPredicateGroups,
			Criterion[] additionalCriteria, Order... sortBy) {

		Criteria crit = buildSearchCriteria(direction, protocolYear, protocolNumber, incomingProtocolNumber,
				incomingDateFrom, incomingDateTo, from, to, subject, comments, keywords, attachedNumber,
				attachedDescription, distributionMethod, distributionMethodDetails, protocolDocumentType,
				includePending, includeDeleted, protocolBooks, correspondentPredicateGroups, additionalCriteria);

		//lazy fetch performs better in some cases (e.g. when protocol correspondents are not involved in a query
		crit.setFetchMode(Protocol_.dossier.getName(), FetchMode.SELECT);
		crit.setFetchMode(Protocol_.protocolBook.getName(), FetchMode.SELECT);
		crit.setFetchMode(Protocol_.documentType.getName(), FetchMode.SELECT);
		crit.setFetchMode(Protocol_.distributionMethod.getName(), FetchMode.SELECT);
		
		for (Order order : sortBy) {
			crit.addOrder(order);
		}

		if (firstResult != null) {
			crit.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			crit.setMaxResults(maxResults);
		}

		List<Protocol> results = crit.list();
		log.debug("Rows fetched (Protocol) : " + results.size());
		return results;
	}

	public List<Object[]> countByCriteria(ProtocolBook protocolBook, Department department, Date from, Date to,
			boolean groupDates, DocumentType documentType, StatisticDirection statisticDirection) {
		StringBuffer sb = new StringBuffer();
		// select
		sb.append("select d.name, d.code");
		if (groupDates) {
			sb.append(",YEAR(p.protocol_date), MONTH(p.protocol_date)");
		}
		if (documentType != null) {
			sb.append(",dt.id as dt_name");
		}
		if (statisticDirection != null) {
			sb.append(",pc.direction");
		}
		sb.append(", count(distinct protocol_id)");

		// from
		sb.append(" from ProtocolCorrespondent pc inner join protocol p on p.id = pc.protocol_id"
				+ " inner join department d on d.id = pc.department_id"
				+ " inner join DepartmentType t on d.departmentType_id = t.id");
		if (documentType != null) {
			sb.append(" inner join document_type dt on dt.id = p.documentType_id");
		}
		// where
		sb.append(" where p.protocol_book_id=" + protocolBook.getId());
		String ids = Arrays.stream(DepartmentDAO.PROTOCOL_DEPARTMENT_TYPES).map(String::valueOf)
				.collect(Collectors.joining(","));
		sb.append(" and t.id in (" + ids + ")");
		if (department != null) {
			sb.append(" and pc.department_id=" + department.getId());
		} else {
			sb.append(" and pc.department_id is not null");
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (from != null) {
			sb.append(" and p.protocol_date >= '" + df.format(from) + "'");
		}
		if (to != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			sb.append(" and p.protocol_date < '" + df.format(calendar.getTime()) + "'");
		}
		if (documentType != null && documentType.getId() != null) {
			sb.append(" and p.documentType_id=" + documentType.getId());
		}
		if (statisticDirection != null) {
			switch (statisticDirection) {
			case INCOMING:
				sb.append(" and p.direction='INCOMING' and pc.direction='CREATOR'");
				break;
			case OUTGOING:
				sb.append(" and p.direction='OUTGOING' and pc.direction='CREATOR'");
				break;
			case RECIPIENT:
				sb.append(" and pc.direction in ('RECIPIENT', 'INTERNAL_RECIPIENT')");
				break;
			case ALL:
				sb.append(" and pc.direction in ('CREATOR','RECIPIENT', 'INTERNAL_RECIPIENT')");
				break;
			default:
				break;
			}
		}

		// grouping
		sb.append(" group by pc.department_id");
		if (groupDates) {
			sb.append(",YEAR(p.protocol_date), MONTH(p.protocol_date)");
		}
		if (documentType != null) {
			sb.append(",p.documentType_id");
		}
		if (statisticDirection != null) {
			sb.append(",pc.direction");
		}
		String queryString = sb.toString();
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
		return sqlQuery.list();
	}

	@Transactional(readOnly = true)
	public Integer countIncomingAsRecipient(Department department, List<ProtocolBook> protocolBooks,
			Date from, Date to, boolean isRoutingDatePresent) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Protocol p"
				+ " join p.protocolCorrespondents pc"
				+ " where (p.isDeleted = false or p.isDeleted is null)"
				+ " and p.protocolNumber is not null"
				+ (from != null ? " and p.protocolDate >= :incomingDateFrom" : "")
				+ (to != null ? " and p.protocolDate < :incomingDateTo" : "")
				+ " and p.protocolBook in(:protocol_books) "
				+ " and("
				+ " (p.direction = 'OUTGOING'"
				+ " and pc.direction = 'RECIPIENT' "
				+ " and pc.type = 'DEPARTMENT' "
				+ " and pc.department = :department"
				+ " and pc.deliveryDate is null"
				+ (isRoutingDatePresent ? " and pc.routingDate is not null" : " and pc.routingDate is null")
				+ ")"
				+ " or"
				+ " (p.direction = 'INCOMING'"
				+ " and pc.direction = 'INTERNAL_RECIPIENT'"
				+ " and pc.type = 'DEPARTMENT'"
				+ " and pc.department = :department"
				+ " and pc.deliveryDate is null"
				+ (isRoutingDatePresent ? " and pc.routingDate is not null" : " and pc.routingDate is null")
				+ ")"
				+ ")");
		
		Query query = getSession().createQuery(sb.toString());

		if(from!=null) {
			query.setParameter("incomingDateFrom", from);
		}
		if(to!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			query.setParameter("incomingDateTo", calendar.getTime());
		}
		query.setParameterList("protocol_books",protocolBooks);
		query.setParameter("department", department);
		
		return ((Number)query.uniqueResult()).intValue();
	}


	@Transactional(readOnly = true)
	public List<Protocol> findIncomingAsRecipient(Department department, List<ProtocolBook> protocolBooks,
			Date from, Date to, boolean isRoutingDatePresent, Integer firstResult,
			Integer maxResults, Order... sortBy) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Protocol p"
				+ " join p.protocolCorrespondents pc"
				+ " where (p.isDeleted = false or p.isDeleted is null)"
				+ " and p.protocolNumber is not null"
				+ (from != null ? " and p.protocolDate >= :incomingDateFrom" : "")
				+ (to != null ? " and p.protocolDate < :incomingDateTo" : "")
				+ " and p.protocolBook in(:protocol_books) "
				+ " and("
				+ " (p.direction = 'OUTGOING'"
				+ " and pc.direction = 'RECIPIENT' "
				+ " and pc.type = 'DEPARTMENT' "
				+ " and pc.department = :department"
				+ " and pc.deliveryDate is null"
				+ (isRoutingDatePresent ? " and pc.routingDate is not null" : " and pc.routingDate is null")
				+ ")"
				+ " or"
				+ " (p.direction = 'INCOMING'"
				+ " and pc.direction = 'INTERNAL_RECIPIENT'"
				+ " and pc.type = 'DEPARTMENT'"
				+ " and pc.department = :department"
				+ " and pc.deliveryDate is null"
				+ (isRoutingDatePresent ? " and pc.routingDate is not null" : " and pc.routingDate is null")
				+ ")"
				+ ")");
		
		
		List<Order> sortByList = Arrays.asList(sortBy);
		String sortByString = sortByList.stream().map(o -> ("p."+ o.getPropertyName()+ (o.isAscending() ? " asc" : " desc"))).collect(Collectors.joining(", "));
		if(!sortByString.isEmpty()) {
			sb.append(" order by ");
			sb.append(sortByString);
		}
		
		Query query = getSession().createQuery(sb.toString());

		if(from!=null) {
			query.setParameter("incomingDateFrom", from);
		}
		if(to!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			query.setParameter("incomingDateTo", calendar.getTime());
		}
		query.setParameterList("protocol_books",protocolBooks);
		query.setParameter("department", department);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	@Transactional(readOnly = true)
	public Integer countIncomingAsCreator(Department department, List<ProtocolBook> protocolBooks,
			Date from, Date to) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Protocol p"
				+ " join p.protocolCorrespondents pc"
				+ " where (p.isDeleted = false or p.isDeleted is null)"
				+ " and p.protocolNumber is not null"
				+ (from != null ? " and p.protocolDate >= :incomingDateFrom" : "")
				+ (to != null ? " and p.protocolDate < :incomingDateTo" : "")
				+ " and p.protocolBook in(:protocol_books) "
				+ " and"
				+ " (p.direction = 'INCOMING'"
				+ " and pc.direction = 'CREATOR' "
				+ " and pc.type is null"
				+ " and pc.department = :department"
				+ ")");
		
		Query query = getSession().createQuery(sb.toString());

		if(from!=null) {
			query.setParameter("incomingDateFrom", from);
		}
		if(to!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			query.setParameter("incomingDateTo", calendar.getTime());
		}
		query.setParameterList("protocol_books",protocolBooks);
		query.setParameter("department", department);
		
		return ((Number)query.uniqueResult()).intValue();
	}

	@Transactional(readOnly = true)
	public List<Protocol> findIncomingAsCreator(Department department, List<ProtocolBook> protocolBooks,
			Date from, Date to, Integer firstResult,
			Integer maxResults, Order... sortBy) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Protocol p"
				+ " join p.protocolCorrespondents pc"
				+ " where (p.isDeleted = false or p.isDeleted is null)"
				+ " and p.protocolNumber is not null"
				+ (from != null ? " and p.protocolDate >= :incomingDateFrom" : "")
				+ (to != null ? " and p.protocolDate < :incomingDateTo" : "")
				+ " and p.protocolBook in(:protocol_books) "
				+ " and"
				+ " (p.direction = 'INCOMING'"
				+ " and pc.direction = 'CREATOR' "
				+ " and pc.type is null"
				+ " and pc.department = :department"
				+ ")");
		
		List<Order> sortByList = Arrays.asList(sortBy);
		String sortByString = sortByList.stream().map(o -> ("p."+ o.getPropertyName()+ (o.isAscending() ? " asc" : " desc"))).collect(Collectors.joining(", "));
		if(!sortByString.isEmpty()) {
			sb.append(" order by ");
			sb.append(sortByString);
		}
		
		Query query = getSession().createQuery(sb.toString());

		if(from!=null) {
			query.setParameter("incomingDateFrom", from);
		}
		if(to!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			query.setParameter("incomingDateTo", calendar.getTime());
		}
		query.setParameterList("protocol_books",protocolBooks);
		query.setParameter("department", department);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	@Transactional(readOnly = true)
	public Integer countOutgoing(Department department, List<ProtocolBook> protocolBooks,
			Date from, Date to) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Protocol p"
				+ " join p.protocolCorrespondents pc"
				+ " where (p.isDeleted = false or p.isDeleted is null)"
				+ " and p.direction = 'OUTGOING'"
				+ " and p.protocolNumber is not null"
				+ (from != null ? " and p.protocolDate >= :incomingDateFrom" : "")
				+ (to != null ? " and p.protocolDate < :incomingDateTo" : "")
				+ " and p.protocolBook in(:protocol_books) "
				+ " and"
				+ " (p.direction = 'OUTGOING'"
				+ " and pc.direction = 'CREATOR' "
				+ " and pc.type is null"
				+ " and pc.department = :department"
				+ ")");
		
		Query query = getSession().createQuery(sb.toString());

		if(from!=null) {
			query.setParameter("incomingDateFrom", from);
		}
		if(to!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			query.setParameter("incomingDateTo", calendar.getTime());
		}
		query.setParameterList("protocol_books",protocolBooks);
		query.setParameter("department", department);
		
		return ((Number)query.uniqueResult()).intValue();
	}

	@Transactional(readOnly = true)
	public List<Protocol> findOutgoing(Department department, List<ProtocolBook> protocolBooks,
			Date from, Date to, Integer firstResult,
			Integer maxResults, Order... sortBy) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Protocol p"
				+ " join p.protocolCorrespondents pc"
				+ " where (p.isDeleted = false or p.isDeleted is null)"
				+ " and p.protocolNumber is not null"
				+ " and p.direction = 'OUTGOING'"
				+ (from != null ? " and p.protocolDate >= :incomingDateFrom" : "")
				+ (to != null ? " and p.protocolDate < :incomingDateTo" : "")
				+ " and p.protocolBook in(:protocol_books) "
				+ " and"
				+ " (p.direction = 'OUTGOING'"
				+ " and pc.direction = 'CREATOR' "
				+ " and pc.type is null"
				+ " and pc.department = :department"
				+ ")");
		
		List<Order> sortByList = Arrays.asList(sortBy);
		String sortByString = sortByList.stream().map(o -> ("p."+ o.getPropertyName()+ (o.isAscending() ? " asc" : " desc"))).collect(Collectors.joining(", "));
		if(!sortByString.isEmpty()) {
			sb.append(" order by ");
			sb.append(sortByString);
		}
		
		Query query = getSession().createQuery(sb.toString());

		if(from!=null) {
			query.setParameter("incomingDateFrom", from);
		}
		if(to!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			query.setParameter("incomingDateTo", calendar.getTime());
		}
		query.setParameterList("protocol_books",protocolBooks);
		query.setParameter("department", department);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

}
