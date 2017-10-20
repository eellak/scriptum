/**
 * 
 */
package gr.scriptum.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Id;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author EPSILON
 *
 */
public class EntityUtil {

	private EntityUtil() {
		
	}
	
	private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
	    fields.addAll(Arrays.asList(type.getDeclaredFields()));

	    if (type.getSuperclass() != null) {
	        fields = getAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}

	public static Object getEntityId(Object entity)
			throws Exception {

		List<Field> declaredFields = getAllFields(new ArrayList<Field>(), entity.getClass());
		for (Field field : declaredFields) {
			Id idAnnotation = field.getAnnotation(Id.class);
			if (idAnnotation != null) {
				return PropertyUtils.getProperty(entity, field.getName());
			}
		}
		
		Method[] declaredMethods = entity.getClass().getDeclaredMethods();
		for (Method method : declaredMethods) {
			Id idAnnotation = method.getAnnotation(Id.class);
			if (idAnnotation != null) {
				return MethodUtils.invokeMethod(entity, method.getName(), null);
			}
		}
		
		return null;
	}

	public static Field getIdField(Class<?> entityClass) {

		Field[] declaredFields = entityClass.getDeclaredFields();
		for (Field field : declaredFields) {
			Id idAnnotation = field.getAnnotation(Id.class);
			if (idAnnotation != null) {
				return field;
			}
		}
		return null;
	}

}
