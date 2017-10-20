/**
 * 
 */
package gr.scriptum.external;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gr.scriptum.api.external.Entity;
import gr.scriptum.api.external.ExternalSearcher;
import gr.scriptum.api.external.Entity.EntityType;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos
 *         </a>
 *
 */
public class PostgreSQLSearcher implements ExternalSearcher {

	private static Log log = LogFactory.getLog(PostgreSQLSearcher.class);

	private Connection getConnection() {

		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cspace", "postgres", "postgres");
			return connection;
		} catch (Exception e) {
			return connection;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gr.scriptum.api.external.ExternalSearcher#findById(java.lang.Object)
	 */
	@Override
	public Entity findById(Object id) {
		throw new UnsupportedOperationException("Not yet implemented!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gr.scriptum.api.external.ExternalSearcher#countByTerm(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public Long countByTerm(String term, Object... optionalParameters) {

		Connection connection = getConnection();
		if (connection == null) {
			throw new RuntimeException("Error getting JDBC connection");
		}

		Long count = null;
		PreparedStatement preparedStatement = null;
		try {
			String selectSQL = "select count(*) from cspace_user where user_email like ? escape '!'";
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, "%" + term + "%");
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			count = rs.getLong(1);

		} catch (SQLException e) {
			log.error(e);
			throw new RuntimeException("Error executing query");
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				log.error(e);
				throw new RuntimeException("Error releasing resources");
			}
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gr.scriptum.api.external.ExternalSearcher#findByTerm(java.lang.String,
	 * java.lang.Integer, java.lang.Integer, java.lang.Object[])
	 */
	@Override
	public List<Entity> findByTerm(String term, Integer offset, Integer size, Object... optionalParameters) {

		Connection connection = getConnection();
		if (connection == null) {
			throw new RuntimeException("Error getting JDBC connection");
		}

		List<Entity> entities = null;
		PreparedStatement preparedStatement = null;
		try {
			String selectSQL = "select user_id, user_firstname, user_lastname, user_email from cspace_user"
					+ " where user_email like ? escape '!'"
					+ " order by user_id limit ? offset ?";
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, "%" + term + "%");
			preparedStatement.setInt(2, size);
			preparedStatement.setInt(3, offset);
			ResultSet rs = preparedStatement.executeQuery();
			entities = new ArrayList<Entity>();
			while (rs.next()) {
				Entity entity = new Entity();
				entity.setType(EntityType.INDIVIDUAL);
				Long userid = rs.getLong("user_id");
				entity.setId(userid);
				entity.setName(rs.getString("user_firstname") + " " + rs.getString("user_lastname"));
				entity.setTitle(rs.getString("user_email"));
				entities.add(entity);
			}

		} catch (SQLException e) {
			log.error(e);
			throw new RuntimeException("Error executing query");
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				log.error(e);
				throw new RuntimeException("Error releasing resources");
			}
		}
		log.debug("Number of entities found:"+entities.size());
		return entities;
	}

}
