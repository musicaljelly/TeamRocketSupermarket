import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.teamrocket.supermarket.gui.SuperMarketFrame;



public class Supermarket {
	
	static Connection connection = null;
	
	public static void initConnection() {

	    try {
	    	
	    	DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	    	
	    	// Will's login
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_a1e8", "a35683119");
	    	
	    	// Lewis's login
	    	connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_a5b8", "a31288111");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Something went horribly wrong.");
			
		}
	}
	
	public static void main(String[] args) {
		
		initConnection();
		
		SuperMarketFrame frame = new SuperMarketFrame(connection);
		
		frame.pack();
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				try {
					connection.close();
					System.exit(0);
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Error while closing the connection upon program exit.");
				}
			}
		});
	}

}
