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

	
	public class OracleSearcher implements ExternalSearcher {
	//public class OracleSearcher implements ExternalSearcher {

		private static Log log = LogFactory.getLog(PostgreSQLSearcher.class);

		private Connection getConnection() {

			Connection connection = null;
			try {
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
//				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:oracle:thin:@10.1.7.150:1521:A","protscript","protscript");
//				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/efta", "root", "root");
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
				String selectSQL = "SELECT count(*) FROM OAEE.PROT_D01_ASFALISMENOS where D01_ASF_EAM = ?";
				preparedStatement = connection.prepareStatement(selectSQL);
				preparedStatement.setString(1, term);
//				preparedStatement.setString(1, term+"%");
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
				String selectSQL = "SELECT * FROM OAEE.PROT_D01_ASFALISMENOS where D01_ASF_EAM = ?";
				preparedStatement = connection.prepareStatement(selectSQL);
				preparedStatement.setString(1, term);
//				preparedStatement.setString(1, term+"%");
				ResultSet rs = preparedStatement.executeQuery();
				entities = new ArrayList<Entity>();
				while (rs.next()) {
					log.info(rs.toString());
					Entity entity = new Entity();
					entity.setType(EntityType.INDIVIDUAL);
					Long userid = rs.getLong("D01_ASF_EAM");
					entity.setId(userid);
					entity.setName(rs.getString("D01_ASF_TMHMA"));
					entity.setDescription(rs.getString("D01_ASF_DESCR"));
//					entity.setTitle(rs.getString("D03_EMP_UNIT_CODE"));
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
			return entities;
		}

	}
