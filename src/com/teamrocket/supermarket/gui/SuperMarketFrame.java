package com.teamrocket.supermarket.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class SuperMarketFrame extends JFrame {

	Connection connection;
	
	List<Integer> currentIDs = new ArrayList<Integer>();

	public SuperMarketFrame(Connection conn) {
		super("Supermarket Application");
		this.connection = conn;

		final JPanel panel = new JPanel();
		getContentPane().add(panel);

		String[] userTypes = { "Visitor", "Member", "Employee", "Employer" };

		final JComboBox<String> userTypeList = new JComboBox<String>(userTypes);
		final JComboBox<String> userIDList = new JComboBox<String>();
		userTypeList.setSelectedIndex(0);

		final JTable productTable = new JTable();
		productTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
		productTable.setFillsViewportHeight(true);
		TableColumn col = new TableColumn();
		productTable.addColumn(new TableColumn());
		
		userTypeList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JComboBox<String> userlist = (JComboBox) event.getSource();
				// Index will be from 0 to 3
				int index = userlist.getSelectedIndex();

				setUserType(index, userIDList);
			}
		});

		userIDList.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				int typeIndex = userTypeList.getSelectedIndex();
				
				JComboBox<String> idlist = (JComboBox) event.getSource();
				int userIdIndex = idlist.getSelectedIndex();
				
				try {
					populateProductTable(typeIndex, productTable);
					
					if (userIdIndex > 0) {
						populateMemberTable(typeIndex, currentIDs.get(userIdIndex));
						populateTransactionTable(typeIndex, currentIDs.get(userIdIndex));
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(userTypeList);
		panel.add(userIDList);
		JScrollPane productScrollPane = new JScrollPane(productTable);
		
		panel.add(productScrollPane);
		
		try {
			populateProductTable(userTypeList.getSelectedIndex(), productTable);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Hit an SQLException during the initial population of the product table.");
		}
	}
	
	
	public void setUserType(int userType, JComboBox<String> userIDList) {
		userIDList.removeAllItems();
		
		Statement statement;
		
		currentIDs.clear();
		currentIDs.add(-1);
		
		try {
			statement = connection.createStatement();
			
			userIDList.addItem("");
			
			if (userType == 0) {
				
			} else if (userType == 1) {
				ResultSet rs = statement.executeQuery("SELECT * FROM member");
				while (rs.next()) {
					userIDList.addItem(rs.getString("NAME"));
					currentIDs.add(rs.getInt("mid"));
				}
				
			} else if (userType == 2) {
				ResultSet rs = statement.executeQuery("SELECT * FROM employee WHERE isEmployer=0");
				while (rs.next()) {
					userIDList.addItem(rs.getString("NAME"));
					currentIDs.add(rs.getInt("eid"));
				}
				
			} else if (userType == 3) {
				ResultSet rs = statement.executeQuery("SELECT * FROM employee WHERE isEmployer=1");
				while (rs.next()) {
					userIDList.addItem(rs.getString("NAME"));
					currentIDs.add(rs.getInt("eid"));
				}
				
			} else {
				// Execution should never reach this point
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void populateProductTable(int userType, JTable productTable) throws SQLException {
		Statement productStatement = connection.createStatement();
		ResultSet allProducts = productStatement.executeQuery("SELECT * FROM Product");
		
		productTable.removeAll();
		
		// For visitors and members
		if (userType == 0 || userType == 1) {
			@SuppressWarnings("serial")
			DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Name", "Price", "In Stock"}, 0) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			productTable.removeAll();
			productTable.setModel(tableModel);
			while (allProducts.next()) {
				Object[] newRow = new Object[3];
				newRow[0] = allProducts.getString("name");
				
				boolean hasMemberPrice = allProducts.getObject("memberprice") != null;
				
				if (userType == 0 || !hasMemberPrice) {
					newRow[1] = allProducts.getFloat("price");
				} else if (userType == 1) {
					newRow[1] = allProducts.getFloat("memberprice");
				} else {
					System.out.println("Something went horribly wrong in populateProductTable");
				}
				
				int stock = allProducts.getInt("stock");
				if (stock > 0) {
					newRow[2] = "In Stock";
				} else {
					newRow[2] = "Out of Stock";
				}
				((DefaultTableModel)productTable.getModel()).addRow(newRow);
			}
			
		// For employees and employers
		} else if (userType == 2 || userType == 3) {
			productTable.removeAll();
			productTable.setModel(new DefaultTableModel(new Object[]{"PID", "Name", "Price", "Member Price", "Cost", "Stock", "Expiry Date"}, 0));
			while (allProducts.next()) {
				Object[] newRow = new Object[7];
				newRow[0] = allProducts.getInt("pid");
				newRow[1] = allProducts.getString("name");
				newRow[2] = allProducts.getFloat("price");
				
				if (allProducts.getObject("memberprice") != null) {
					newRow[3] = allProducts.getFloat("memberprice");
				} else {
					newRow[3] = allProducts.getFloat("price");
				}
				
				newRow[4] = allProducts.getFloat("cost");
				newRow[5] = allProducts.getInt("stock");
				newRow[6] = allProducts.getDate("expiry");
				((DefaultTableModel)productTable.getModel()).addRow(newRow);
			}
		} else {
			// Execution should never reach this point
		}
	}

	
	public void populateMemberTable(int userType, int userID) {
		
	}
	
	
	public void populateTransactionTable(int userType, int userID) {
		
	}
	
}
