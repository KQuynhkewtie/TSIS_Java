package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {
	public static Connection getConnection() {
		String url = "jdbc:oracle:thin:@//localhost:1521/orclpdb4";
		String user = "TSISPharmacy";
		String pass = "tsispharmacy";

		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			return conn;
		} catch (SQLException e) {
			System.out.println("Lỗi kết nối Oracle: " + e.getMessage());
			return null;
		}
	}
}



