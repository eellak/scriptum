package gr.scriptum.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.DistributionMethod_;

@Repository
public class DistributionMethodDAO extends GenericDAO<DistributionMethod, Integer> {

	@Override
	public List<DistributionMethod> findAll() {
		DistributionMethod distributionMethod = new DistributionMethod();
		List<Order> sortBy = new ArrayList<Order>();
		sortBy.add(Order.asc(DistributionMethod_.description.getName()));
		return findByExample(distributionMethod, null, sortBy, null,
				null);
	}
}
