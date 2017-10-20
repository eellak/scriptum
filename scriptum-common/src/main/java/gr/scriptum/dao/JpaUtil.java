package gr.scriptum.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

	private static EntityManagerFactory factory = null;

	public static EntityManagerFactory getEntityManagerFactory() {
		if(factory!=null) {
			return factory;
		}
		factory = Persistence.createEntityManagerFactory("ScriptumModelJPA");
		return factory;
	}
}