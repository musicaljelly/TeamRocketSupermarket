import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.teamrocket.supermarket.gui.SuperMarketFrame;



public class Supermarket extends JFrame {
	
	static Connection connection = null;
	
	public static void initConnection() throws SQLException{

	    try {
	    	
	    	DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	    	
	    	// Will's login
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_a1e8", "a35683119");
	    	
	    	// Lewis's login
	    	connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_a5b8", "a31288111");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Failed to connect to Database");
			JOptionPane.showMessageDialog(null, "Failed to connect to database", "Failed to connect to database", JOptionPane.ERROR_MESSAGE);	
			

		}
	}
	
	
	public static void main(String[] args) throws SQLException {
		
		initConnection();
		
		SuperMarketFrame frame = new SuperMarketFrame(connection);
		
		//frame.pack();
		frame.setSize(1280, 720);
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
					JOptionPane.showMessageDialog(null, "Error while closing the connection upon program exit", "Error while closing the connection upon program exit", JOptionPane.ERROR_MESSAGE);	

				}
			}
		});
	}

}
