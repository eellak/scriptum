package gr.scriptum.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.Parameter;

@Repository
public class ParameterDAO extends GenericDAO<Parameter, Integer> {

	@Transactional(readOnly = true)
	public Parameter findByName(String name) {

		Parameter parameter = new Parameter();
		parameter.setName(name);
		List<Parameter> parameters = findByExample(parameter);
		if (parameters.isEmpty()) {
			return null;
		}
		return parameters.get(0);
	}

	@Transactional(readOnly = true)
	public List<Parameter> findByArea(String area) {

		Parameter parameter = new Parameter();
		parameter.setArea(area);
		return findByExample(parameter);

	}

	public String getAsString(String name) {
		Parameter parameter = findByName(name);
		if (parameter != null) {
			return parameter.getValue();
		}
		return null;
	}

	public Integer getAsInteger(String name) {
		Parameter parameter = findByName(name);
		if (parameter != null) {
			return Integer.valueOf(parameter.getValue());
		}
		return null;
	}

	public Boolean getAsBoolean(String name) {
		Parameter parameter = findByName(name);
		if (parameter != null) {
			return Boolean.valueOf(parameter.getValue());
		}
		return null;		
	}
}
