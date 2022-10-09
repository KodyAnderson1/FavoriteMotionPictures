package UWF.Group4;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.derby.jdbc.EmbeddedDriver;

public class DatabaseConnection {
	private static Connection connection;
	private static Driver derbyEmbeddedDriver;
	private static String jdbcURL;

	DatabaseConnection() {}

	public static void init() {
		jdbcURL = "jdbc:derby:trainsdb;create=true";
		

		try {
			derbyEmbeddedDriver = new EmbeddedDriver();
			DriverManager.registerDriver(derbyEmbeddedDriver);
			connection = DriverManager.getConnection(jdbcURL);
		} catch (SQLException e) {
			System.out.println(e);
		}

	}
	
	@SuppressWarnings("exports")
	public static Connection getConnection() throws SQLException { return connection; }
	
	public static void shutDownConnection() {
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException ex) {
			if (((ex.getErrorCode() == 50000) && ("XJ015".equals(ex.getSQLState())))) {
				System.out.println("Derby shut down normally");
			} else {
				System.err.println("Derby did not shut down normally");
				System.err.println(ex.getMessage());
			}
		}
	}

}
