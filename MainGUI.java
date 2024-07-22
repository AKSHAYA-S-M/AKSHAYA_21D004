package invoiceBilling;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class MainGUI {

	private JFrame frame;
 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
 
	public MainGUI() {
		initialize();
	}
 
	private void initialize() {
		frame = new JFrame("AKS Store");
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 442, 550);
		
		JButton btnCreateInvoice = new JButton("Create Invoice");
		btnCreateInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateInvoiceGUI ci=new CreateInvoiceGUI();
				ci.Frame2();
				frame.setVisible(false);
			}
		});
		btnCreateInvoice.setBounds(105, 70, 212, 47);
		frame.getContentPane().add(btnCreateInvoice);
		
		JButton btnViewInvoices = new JButton("View All Invoices");
		btnViewInvoices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewAllInvoices();
			}
		});
		btnViewInvoices.setBounds(105, 250, 212, 47);
		frame.getContentPane().add(btnViewInvoices);
		
		JButton btnItemSales = new JButton("View Item Sales");
		btnItemSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewItemSales();
			}
		});
		btnItemSales.setBounds(105, 310, 212, 47);
		frame.getContentPane().add(btnItemSales);
		
		JButton btnViewInvoiceByID = new JButton("View Invoice By ID");
		btnViewInvoiceByID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewInvoiceGUI view=new viewInvoiceGUI();
				view.Frame1();
				frame.setVisible(false);
			}
		});
		btnViewInvoiceByID.setBounds(105, 190, 212, 47);
		frame.getContentPane().add(btnViewInvoiceByID);
		
		JButton btnCheckBalance = new JButton("Check Balance");
		btnCheckBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewCustomerBalance();
			}
		});
		btnCheckBalance.setBounds(105, 130, 212, 47);
		frame.getContentPane().add(btnCheckBalance);
		
		JButton btnCreateItem = new JButton("Create Item");
		btnCreateItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateItem createItem=new CreateItem();
				createItem.Frame4();
				frame.setVisible(false);
			}
		});
		btnCreateItem.setBounds(105, 370, 212, 50);
		frame.getContentPane().add(btnCreateItem);
	}
	
    private void viewAllInvoices() {
        JFrame viewFrame = new JFrame("All Invoices");
        viewFrame.setBounds(100, 100, 600, 400);
        String[] columnNames = {"Invoice ID", "Customer Name", "Date", "Total","Paid Amount"};
        Object[][] data = {};
        try (Connection connection = DataBaseConnection.getConnection()) {
            String query = "SELECT i.InvoiceID, c.Name, i.Date, i.Total, i.PaidAmount FROM Invoices i JOIN Customers c ON i.CustomerID = c.CustomerID";
            try (PreparedStatement stmt = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                ResultSet rs = stmt.executeQuery(); 
                rs.last();
                int rowCount = rs.getRow();
                rs.beforeFirst();
                data = new Object[rowCount][5];
                int i = 0;
                while (rs.next()) {
                    data[i][0] = rs.getInt("InvoiceID");
                    data[i][1] = rs.getString("Name");
                    data[i][2] = rs.getDate("Date");
                    data[i][3] = rs.getDouble("Total");
                    data[i][4] = rs.getDouble("PaidAmount");
                    i++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        viewFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        viewFrame.setVisible(true);
    }
    
    private void viewItemSales() {
        JFrame viewFrame = new JFrame("View Item Sales");
        viewFrame.setBounds(100, 100, 600, 400);
        String[] columnNames = {"Item Name", "Total Quantity", "Total Sales"};
        Object[][] data = {};
        try (Connection connection = DataBaseConnection.getConnection()) {
            String query = "SELECT i.ItemName, SUM(d.Quantity) AS TotalQuantity, SUM(d.Price) AS TotalSales FROM InvoiceDetails d JOIN Items i ON d.ItemID = i.ItemID GROUP BY i.ItemName";
            try (PreparedStatement stmt = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                ResultSet rs = stmt.executeQuery(); 
                rs.last();
                int rowCount = rs.getRow();
                rs.beforeFirst();
                data = new Object[rowCount][3];
                int i = 0;
                while (rs.next()) {
                    data[i][0] = rs.getString("ItemName");  
                    data[i][1] = rs.getInt("TotalQuantity");  
                    data[i][2] = rs.getDouble("TotalSales");  
                    i++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        viewFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        viewFrame.setVisible(true);
    }
    
    public void viewCustomerBalance() {
        String mobileNumber = JOptionPane.showInputDialog(frame, "Enter Mobile Number:"); 
        while (!Customer.isValidMobileNumber(mobileNumber)) {
            JOptionPane.showMessageDialog(frame, "Invalid mobile number format. Please enter a valid 10-digit number.", "Error", JOptionPane.ERROR_MESSAGE);
            mobileNumber = JOptionPane.showInputDialog(frame, "Enter Mobile Number:");
        }
        try (Connection connection = DataBaseConnection.getConnection()) { 
            Customer customer = Customer.getCustomerByMobileNumber(connection, mobileNumber);
            String name=customer.getName();
            double balance = customer.getBalance(); 
            JOptionPane.showMessageDialog(frame, "Customer Name: " + name + "\nCustomer Balance: " + balance, "Customer Balance", JOptionPane.INFORMATION_MESSAGE);        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error retrieving balance", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
