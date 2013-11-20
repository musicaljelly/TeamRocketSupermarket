package com.teamrocket.supermarket.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

public class SuperMarketFrame extends JFrame {

	Connection connection;

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
		productTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		productTable.setFillsViewportHeight(true);
		TableColumn col = new TableColumn();
		productTable.addColumn(new TableColumn());
		
		
		userTypeList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Statement statement;
				JComboBox<String> userlist = (JComboBox) event.getSource();
				// Index will be from 0 to 3
				int index = userlist.getSelectedIndex();

				userIDList.removeAllItems();
				switch (index) {
				case 0:
					// case where Visitor is selected
					break;
				case 1:
					userIDList.addItem("");
					// case where Member is selected
					try {
						statement = connection.createStatement();
						ResultSet rs = statement.executeQuery("SELECT * FROM member");

						// Add 'empty' field for default
						while (rs.next()) {
							userIDList.addItem((String) rs.getString("NAME"));
						}

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("SQLException. Something went wrong when running the query.");
					}
					// userIDList.setSelectedIndex(0);
					break;
				case 2:
					// case where Employee is selected
					userIDList.addItem("");
					try {
						statement = connection.createStatement();
						ResultSet rs = statement.executeQuery("SELECT * FROM employee");

						// Add 'empty' field for default
						while (rs.next()) {
							// TODO: add check to only add in employees
							userIDList.addItem((String) rs.getString("NAME"));
						}

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("SQLException. Something went wrong when running the query.");
					}
					// userIDList.setSelectedIndex(0);
					break;
				case 3:
					// case where Employer is selected
					userIDList.addItem("");
					try {
						statement = connection.createStatement();
						ResultSet rs = statement.executeQuery("SELECT * FROM employee");

						// Add 'empty' field for default
						while (rs.next()) {
							// TODO: add check to only add in employers
							userIDList.addItem((String) rs.getString("NAME"));
						}

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("SQLException. Something went wrong when running the query.");
					}
					// userIDList.setSelectedIndex(0);
					break;
				default:
					// Shouldn't run
				}
			}
		});

		userIDList.addActionListener(new ActionListener() {
			int typeIndex = userTypeList.getSelectedIndex();

			public void actionPerformed(ActionEvent event) {
				JComboBox<String> idlist = (JComboBox) event.getSource();
				// Index will be from 0 to 3
				int id = idlist.getSelectedIndex();

				switch (typeIndex) {
				case 1:
					// TODO Member query
					break;
				case 2:
					// TODO Employee query
					break;
				case 3:
					// TODO Employer query
					break;
				default:
				}
			}
		});

		panel.add(userTypeList);
		panel.add(userIDList);
		JScrollPane productScrollPane = new JScrollPane(productTable);
		
		panel.add(productScrollPane);
	}

}
