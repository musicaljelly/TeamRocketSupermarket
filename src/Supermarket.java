import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.teamrocket.supermarket.gui.SuperMarketFrame;



public class Supermarket {
	
	static Connection connection = null;
	
	public static void initConnection() throws SQLException{

    	DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    	
    	// Not going to do anything fancy here with password security 
    	// like using a char array and overwriting the individual values
    	// or even masking the password field.
    	String username = "";
    	String password = "";
    	username = JOptionPane.showInputDialog("Database username:");
    	password = JOptionPane.showInputDialog("Database password:");
    	
    	
    	connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", username, password);
//    	For Lewis's use:  "ora_a5b8", "a31288111"
//    	For Will's use: "ora_a1e8", "a35683119"
    	
    	connection.setAutoCommit(false);
	}
	
	
	public static void main(String[] args) {
		try {
			initConnection();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Failed to connect to database", "Failed to connect to database", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		SuperMarketFrame frame = new SuperMarketFrame(connection);
		
		frame.setSize(1280, 720);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				try {
					connection.close();
					System.exit(0);
				} catch (SQLException e) {
					System.out.println("Error while closing the connection upon program exit.");
					JOptionPane.showMessageDialog(null, "Error while closing the connection upon program exit", "Error while closing the connection upon program exit", JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
	}

}
