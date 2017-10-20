/**
 * 
 */
package gr.scriptum.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class DatabaseValidationTest {

	@Test
	public final void test() {
		Map<String,String> properties= new HashMap<String, String>();
		properties.put("hibernate.hbm2ddl.auto", "validate");
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ScriptumModelJPA", properties);
		EntityManager entityManager = null;
		try {
			entityManager = entityManagerFactory.createEntityManager();
		} catch (Exception e) {
			fail(e.getMessage());
		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
				entityManagerFactory.close();
			}
		}
	}
}
