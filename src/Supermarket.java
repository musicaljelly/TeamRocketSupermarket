import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.teamrocket.supermarket.gui.SuperMarketFrame;



public class Supermarket {
	
	static Connection connection = null;
	
	public static void initConnection() {

	    try {
	    	
	    	DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_a1e8", "a35683119");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Something went horribly wrong.");
			
		}
	}
	
	public static void main(String[] args) {
		
		initConnection();
		
		Statement statement;
		
		try {
			
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM member");
			while (rs.next()) {
				System.out.println(rs.getString("NAME"));
			}
			
//			statement.close();
//			connection.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("SQLException. Something went wrong when running the query.");
			
		}
		
		SuperMarketFrame frame = new SuperMarketFrame(connection);
		
		frame.pack();
		frame.setVisible(true);
	}

}
