package hr.fer.zemris.java.web.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton class used to provide connection used to connect to the database.
 * 
 * @author Kristijan Biscanic
 * 
 */
public final class ConnectionFactory {

	/**
	 * One and only instance to this class.
	 */
	private static final ConnectionFactory INSTANCE = new ConnectionFactory();
	/**
	 * Connection to the database.
	 */
	private Connection connection = null;

	/**
	 * Private constructor.
	 */
	private ConnectionFactory() {

		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException x) {
			System.err.println("Error with database drivers.");
		}

		String dbName = "votingDB";
		String connectionURL = "jdbc:derby://localhost:1527/" + dbName;

		Properties dbProperties = new Properties();

		dbProperties.setProperty("user", "ivica");
		dbProperties.setProperty("password", "ivo");

		try {
			connection = DriverManager.getConnection(connectionURL,
					dbProperties);

		} catch (SQLException sqlex) {
			System.err.println("Error connecting to database");
			sqlex.printStackTrace();
		}

	}

	/**
	 * Method used to retrieve connection from this factory.
	 * 
	 * @return Connection to the database.
	 */
	public static Connection getConnection() {
		return INSTANCE.connection;
	}

}
