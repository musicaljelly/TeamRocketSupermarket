package com.teamrocket.supermarket.gui;

import java.awt.CardLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class SuperMarketFrame extends JFrame {

	final String[] userTypes = { "Visitor", "Member", "Employee", "Manager" };

	Connection connection;

	List<Integer> currentIDs = new ArrayList<Integer>();

	JPanel productTablePanel;
	JPanel memberTablePanel;
	JPanel transactionTablePanel;

	JPanel visitorPanel;
	JPanel memberPanel;
	JPanel employeePanel;
	JPanel managerPanel;

	JTable productTable;
	JTable memberTable;
	JTable transactionTable;

	final JButton accDetailsButton;
	
	public SuperMarketFrame(Connection conn) {
		super("Supermarket Application");
		this.connection = conn;

		final JPanel boxPanel = new JPanel();
		boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.PAGE_AXIS));

		final JPanel headerPanel = new JPanel();

		final JPanel tablePanel = new JPanel(new CardLayout());
		
		accDetailsButton = new JButton("Account Details");

		visitorPanel = new JPanel();
		memberPanel = new JPanel();
		employeePanel = new JPanel();
		managerPanel = new JPanel();

		tablePanel.add(visitorPanel, userTypes[0]);
		tablePanel.add(memberPanel, userTypes[1]);
		tablePanel.add(employeePanel, userTypes[2]);
		tablePanel.add(managerPanel, userTypes[3]);

		boxPanel.add(headerPanel);
		boxPanel.add(tablePanel);

		getContentPane().add(boxPanel);

		final CardLayout cardLayout = (CardLayout) tablePanel.getLayout();
		cardLayout.show(tablePanel, userTypes[0]);

		// Create the product table
		productTable = new JTable();
		productTable.setFillsViewportHeight(true);

		productTablePanel = new JPanel();
		productTablePanel.setLayout(new BoxLayout(productTablePanel, BoxLayout.PAGE_AXIS));
		productTablePanel.add(new JLabel("Products"));
		productTablePanel.add(new JScrollPane(productTable));

		// Create the member table
		memberTable = new JTable();
		memberTable.setPreferredScrollableViewportSize(new Dimension(600, 250));
		memberTable.setFillsViewportHeight(true);

		memberTablePanel = new JPanel();
		memberTablePanel.setPreferredSize(new Dimension(600, 250));
		memberTablePanel.setLayout(new BoxLayout(memberTablePanel, BoxLayout.PAGE_AXIS));
		memberTablePanel.add(new JLabel("Members"));
		memberTablePanel.add(new JScrollPane(memberTable));

		// Create the transaction table
		transactionTable = new JTable();
		transactionTable.setPreferredScrollableViewportSize(new Dimension(600, 250));
		transactionTable.setFillsViewportHeight(true);

		transactionTablePanel = new JPanel();
		transactionTablePanel.setPreferredSize(new Dimension(600, 250));
		transactionTablePanel.setLayout(new BoxLayout(transactionTablePanel, BoxLayout.PAGE_AXIS));
		
		JPanel transactionsLabelAndButton = new JPanel();
		transactionsLabelAndButton.add(new JLabel("Transactions                                                                    "));
		
		JButton groupByDateButton = new JButton("Group By Date");
		groupByDateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO add sql filter query stuff thingies
				
			}
		});
		transactionsLabelAndButton.add(groupByDateButton);
		transactionTablePanel.add(transactionsLabelAndButton);
		transactionTablePanel.add(new JScrollPane(transactionTable));

		// Create and add the user type and user ID lists
		final JComboBox<String> userTypeList = new JComboBox<String>(userTypes);
		final JComboBox<String> userIDList = new JComboBox<String>();
		userTypeList.setSelectedIndex(0);

		userTypeList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JComboBox<String> userlist = (JComboBox) event.getSource();
				// Index will be from 0 to 3
				int index = userlist.getSelectedIndex();

				setUserType(index, userIDList, tablePanel);
			}
		});

		userIDList.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				int typeIndex = userTypeList.getSelectedIndex();

				JComboBox<String> idlist = (JComboBox) event.getSource();
				int userIdIndex = idlist.getSelectedIndex();

				try {
					populateProductTable(typeIndex);

					if (userIdIndex > 0) {
						populateMemberTable(typeIndex);
						populateTransactionTable(typeIndex, currentIDs.get(userIdIndex));
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		accDetailsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int userType = userTypeList.getSelectedIndex();
				JDialog dialog;
				
				if (userType == 0) {
					// "Do nothing"
				} else if (userType == 1) {
					dialog = new JDialog();
					// Create new table for account details
					JTable t = new JTable();
					// TODO: add stuff to table
					dialog.add(t);
					dialog.setVisible(true);
						
				} else if (userType == 2) {
					dialog = new JDialog();
					// Create new table for account details
					JTable t = new JTable();
					// TODO: add stuff to table
					dialog.add(t);
					dialog.setVisible(true);

				} else if (userType == 3) {
					dialog = new JDialog();
					// Create new table for account details
					JTable t = new JTable();
					// TODO: add stuff to table
					dialog.add(t);
					dialog.setVisible(true);
				}

			}
		});

		headerPanel.add(userTypeList);
		headerPanel.add(userIDList);
		headerPanel.add(accDetailsButton);

		setUserType(0, userIDList, tablePanel);

		try {
			populateProductTable(userTypeList.getSelectedIndex());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Hit an SQLException during the initial population of the product table.");
		}
	}

	public void setUserType(int userType, JComboBox<String> userIDList, JPanel tablePanel) {
		userIDList.removeAllItems();

		transactionTable.removeAll();

		Statement statement;

		currentIDs.clear();
		currentIDs.add(-1);

		CardLayout cardLayout = (CardLayout) tablePanel.getLayout();
		cardLayout.show(tablePanel, userTypes[userType]);

		try {
			statement = connection.createStatement();

			userIDList.addItem("");

			accDetailsButton.setVisible(true);
			productTable.setPreferredScrollableViewportSize(new Dimension(1200, 250));
			
			if (userType == 0) {
				accDetailsButton.setVisible(false);
				productTable.setPreferredScrollableViewportSize(new Dimension(1200, 550));
				visitorPanel.removeAll();
				visitorPanel.add(productTablePanel);

			} else if (userType == 1) {
				memberPanel.removeAll();
				ResultSet rs = statement.executeQuery("SELECT * FROM member");
				while (rs.next()) {
					userIDList.addItem(rs.getString("name"));
					currentIDs.add(rs.getInt("mid"));
				}

				JPanel memberBoxPanel = new JPanel();
				memberBoxPanel.setPreferredSize((new Dimension(1210, 600)));
				memberBoxPanel.setLayout(new BoxLayout(memberBoxPanel, BoxLayout.PAGE_AXIS));
				memberBoxPanel.add(productTablePanel);
				memberBoxPanel.add(transactionTablePanel);
				memberPanel.add(memberBoxPanel);

			} else if (userType == 2) {
				employeePanel.removeAll();
				ResultSet rs = statement.executeQuery("SELECT * FROM employee WHERE isEmployer=0");
				while (rs.next()) {
					userIDList.addItem(rs.getString("NAME"));
					currentIDs.add(rs.getInt("eid"));
				}

				JPanel transactionAndMemberPanel = new JPanel();
				transactionAndMemberPanel.add(memberTablePanel);
				transactionAndMemberPanel.add(transactionTablePanel);

				JPanel employeeBoxPanel = new JPanel();
				employeeBoxPanel.setPreferredSize((new Dimension(1210, 600)));
				employeeBoxPanel.setLayout(new BoxLayout(employeeBoxPanel, BoxLayout.PAGE_AXIS));
				// Add buttons to product Panel, for employees to update and remove items
				JPanel buttonPanel = new JPanel();

				JButton discardButton = new JButton("Discard Changes");
				discardButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO: delete row(s) from DB
						JOptionPane.showConfirmDialog(null, "Are you sure you want to discard changes?", "Confirm",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					}
				});
				buttonPanel.add(discardButton);

				JButton updateButton = new JButton("Update");
				updateButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO: read table and update to DB
						JOptionPane.showConfirmDialog(null, "Are you sure you want to update?", "Confirm",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					}
				});
				buttonPanel.add(updateButton);

				JButton deleteButton = new JButton("Delete");
				deleteButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO: delete row(s) from DB
						JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Confirm",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					}
				});
				buttonPanel.add(deleteButton);

				employeeBoxPanel.add(productTablePanel);
				employeeBoxPanel.add(buttonPanel);
				employeeBoxPanel.add(transactionAndMemberPanel);
				employeePanel.add(employeeBoxPanel);

			} else if (userType == 3) {
				managerPanel.removeAll();
				ResultSet rs = statement.executeQuery("SELECT * FROM employee WHERE isEmployer=1");
				while (rs.next()) {
					userIDList.addItem(rs.getString("NAME"));
					currentIDs.add(rs.getInt("eid"));
				}

				JPanel transactionAndMemberPanel = new JPanel();
				transactionAndMemberPanel.add(memberTablePanel);
				transactionAndMemberPanel.add(transactionTablePanel);

				// Add buttons to product Panel, for employees to update and remove items
				JPanel buttonPanel = new JPanel();

				JButton discardButton = new JButton("Discard Changes");
				discardButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO: delete row(s) from DB
						JOptionPane.showConfirmDialog(null, "Are you sure you want to discard changes?", "Confirm",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					}
				});
				buttonPanel.add(discardButton);

				JButton updateButton = new JButton("Update");
				updateButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO: read table and update to DB
						JOptionPane.showConfirmDialog(null, "Are you sure you want to update?", "Confirm",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					}
				});
				buttonPanel.add(updateButton);

				JButton deleteButton = new JButton("Delete");
				deleteButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO: delete row(s) from DB
						JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Confirm",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					}
				});
				buttonPanel.add(deleteButton);

				JPanel managerBoxPanel = new JPanel();
				managerBoxPanel.setPreferredSize((new Dimension(1210, 600)));
				managerBoxPanel.setLayout(new BoxLayout(managerBoxPanel, BoxLayout.PAGE_AXIS));
				managerBoxPanel.add(productTablePanel);
				managerBoxPanel.add(buttonPanel);
				managerBoxPanel.add(transactionAndMemberPanel);
				managerPanel.add(managerBoxPanel);

			} else {
				// Execution should never reach this point
			}

			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void populateProductTable(int userType) throws SQLException {
		Statement productStatement = connection.createStatement();
		ResultSet allProducts = productStatement.executeQuery("SELECT * FROM Product");

		productTable.removeAll();

		// For visitors and members
		if (userType == 0 || userType == 1) {
			@SuppressWarnings("serial")
			DefaultTableModel tableModel = new DefaultTableModel(new Object[] { "Name", "Price ($)", "In Stock" }, 0) {
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
				((DefaultTableModel) productTable.getModel()).addRow(newRow);
			}

			// For employees and managers
		} else if (userType == 2 || userType == 3) {
			productTable.removeAll();
			productTable.setModel(new DefaultTableModel(new Object[] { "PID", "Name", "Price", "Member Price", "Cost",
					"Stock", "Expiry Date" }, 0));
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
				((DefaultTableModel) productTable.getModel()).addRow(newRow);
			}
		} else {
			// Execution should never reach this point
		}

		productStatement.close();
	}

	public void clearMemberTable() {
		memberTable.removeAll();
		memberTable.setModel(new DefaultTableModel(new Object[] { "MID", "Name", "Home Address", "Phone Number",
				"Email Address" }, 0));
	}

	public void populateMemberTable(int userType) throws SQLException {

		if (userType != 2 && userType != 3) {
			return;
		}

		Statement memberStatement = connection.createStatement();
		ResultSet allMembers = memberStatement.executeQuery("SELECT * FROM Member");

		clearMemberTable();

		if (userType == 2) {
			@SuppressWarnings("serial")
			DefaultTableModel tableModel = new DefaultTableModel(new Object[] { "MID", "Name", "Home Address",
					"Phone Number", "Email Address" }, 0) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			memberTable.setModel(tableModel);

		} else if (userType == 3) {
			memberTable.setModel(new DefaultTableModel(new Object[] { "MID", "Name", "Home Address", "Phone Number",
					"Email Address" }, 0));
		}

		while (allMembers.next()) {
			Object[] newRow = new Object[5];
			newRow[0] = allMembers.getInt("mid");
			newRow[1] = allMembers.getString("name");
			newRow[2] = allMembers.getString("homeaddress");
			newRow[3] = allMembers.getString("phonenumber");
			newRow[4] = allMembers.getString("emailaddress");
			((DefaultTableModel) memberTable.getModel()).addRow(newRow);
		}

	}

	public void clearTransactionTable() {
		transactionTable.removeAll();
		transactionTable.setModel(new DefaultTableModel(new Object[] {}, 0));
	}

	public void populateTransactionTable(int userType, int userID) throws SQLException {
		if (userType == 0) {
			return;
		}

		Statement transactionStatement = connection.createStatement();

		if (userType == 1) {
			@SuppressWarnings("serial")
			DefaultTableModel tableModel = new DefaultTableModel(new Object[] { "Date", "Type", "Product Name",
					"Quantity", "Amount" }, 0) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			transactionTable.setModel(tableModel);

			String query = "SELECT * " + "FROM " + "    (SELECT * " + "    FROM Transaction "
					+ "    INNER JOIN Product " + "    ON Product.pid = Transaction.pid) " + "WHERE mid = " + userID;

			ResultSet transactions = transactionStatement.executeQuery(query);

			while (transactions.next()) {
				Object[] newRow = new Object[5];
				newRow[0] = transactions.getDate("tdate");
				newRow[1] = transactions.getString("type");
				newRow[2] = transactions.getString("name");
				newRow[3] = transactions.getInt("quantity");
				newRow[4] = transactions.getFloat("amount");
				((DefaultTableModel) transactionTable.getModel()).addRow(newRow);
			}

		} else if (userType == 2 || userType == 3) {
			@SuppressWarnings("serial")
			DefaultTableModel tableModel = new DefaultTableModel(new Object[] { "TID", "Date", "Type", "PID",
					"Product Name", "Quantity", "Amount", "Authorized By", "Card Name", "Card #", "Card Expiry Date" },
					0) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			transactionTable.setModel(tableModel);

			String viewQuery = "CREATE OR REPLACE VIEW joinedcardtransactions AS "
					+ "SELECT * "
					+ "FROM Transaction "
					+ "LEFT OUTER JOIN (SELECT tid AS cctid, cardnum, cardname, cardexpiry FROM CreditCardTransaction) "
					+ "ON tid = cctid";

			String query = "SELECT * " + "FROM " + "    (SELECT * " + "    FROM joinedcardtransactions "
					+ "    INNER JOIN Product " + "    ON Product.pid = joinedcardtransactions.pid) " + "WHERE eid = "
					+ userID;

			String deleteQuery = "DROP VIEW joinedcardtransactions";

			transactionStatement.executeQuery(viewQuery);
			ResultSet transactions = transactionStatement.executeQuery(query);

			while (transactions.next()) {
				Object[] newRow = new Object[11];
				newRow[0] = transactions.getInt("tid");
				newRow[1] = transactions.getDate("tdate");
				newRow[2] = transactions.getString("type");
				newRow[3] = transactions.getInt("pid");
				newRow[4] = transactions.getString("name");
				newRow[5] = transactions.getInt("quantity");
				newRow[6] = transactions.getFloat("amount");
				newRow[7] = transactions.getInt("eid");

				if (transactions.getObject("cardname") == null || transactions.getObject("cardnum") == null
						|| transactions.getObject("cardexpiry") == null) {
					newRow[8] = "N/A";
					newRow[9] = "N/A";
					newRow[10] = "N/A";
				} else {
					newRow[8] = transactions.getString("cardname");
					newRow[9] = transactions.getInt("cardnum");
					newRow[10] = transactions.getDate("cardexpiry");
				}

				((DefaultTableModel) transactionTable.getModel()).addRow(newRow);
			}

			transactionStatement.executeQuery(deleteQuery);

		}

	}

}
