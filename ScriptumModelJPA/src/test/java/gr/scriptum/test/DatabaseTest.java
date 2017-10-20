package gr.scriptum.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DatabaseTest {

    private static Log log = LogFactory.getLog(DatabaseTest.class);

    public static void main(String[] args) {

        try {
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ScriptumModelJPA");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			entityManager.close();
			entityManagerFactory.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
        System.exit(0);

    }
}
