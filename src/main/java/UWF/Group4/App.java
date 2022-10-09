package UWF.Group4;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.derby.jdbc.EmbeddedDriver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	@Override
	public void start(Stage stage) {
		var javaVersion = SystemInfo.javaVersion();
		var javafxVersion = SystemInfo.javafxVersion();

		var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
		var scene = new Scene(new StackPane(label), 640, 480);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		Connection conn;

//        launch();

		try {
			DatabaseConnection.init();
			conn = DatabaseConnection.getConnection();
			String sqlTable = "CREATE TABLE test(id int primary key, name varchar(128))";
			Statement statement = conn.createStatement();
//			statement.executeUpdate(sqlTable);
//			statement.executeUpdate("insert into test values(1,'tom')");
//			statement.executeUpdate("insert into test values(2,'peter')");

//			statement.executeUpdate("DROP table test");

			ResultSet rs = statement.executeQuery("SELECT * FROM test");

			while (rs.next()) {
				System.out.printf("%d\t%s\n", rs.getInt("id"), rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

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

//		DatabaseConnection.shutDownConnection();
	}

}