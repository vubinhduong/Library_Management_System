package Controller;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
	public static java.sql.Connection main(String[] args) throws SQLException {
		
		java.sql.Connection conn = null;
		String url = "jdbc:sqlserver://localhost;databaseName=QLTV";
		String username = "sa";
		String password = "duong123";
		
		try {	
			conn = (java.sql.Connection) DriverManager.getConnection(url, username, password);
			System.out.println("Connect successfully!");
		} catch (Exception e) {
			System.out.println("ERROR CONNECTION");
			e.printStackTrace();
		}
		return conn;
//		String sql = "INSERT INTO Account VALUES ('Acc002', N'Nguyễn Văn Nam', '1999-5-7', '0397253940');";	
//		String sql2 = "DELETE FROM Account WHERE AccId = 'Acc002'";
//		Statement st = conn.createStatement();
//		st.execute(sql2);
//		conn.close();
	}
}
