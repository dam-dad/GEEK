package dad.geek.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {

	private static Connection connection;
	private static Properties properties = new Properties();

	public static void conectarDB() {
		try {

			properties.load(DBManager.class.getResourceAsStream("/properties/conexiones.properties"));
			Class.forName(properties.getProperty("SQLDriver"));
			connection = DriverManager.getConnection(properties.getProperty("SQLHost"),
					properties.getProperty("SQLUsername"), properties.getProperty("SQLPassword"));

		} catch (IOException | ClassNotFoundException | SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
