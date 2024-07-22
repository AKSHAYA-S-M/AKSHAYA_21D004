package invoiceBilling;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class viewInvoiceGUI {

	private JFrame frmBill;
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private JTextArea txtCustomerID;
	private JTextArea txtCustomerName;
	private JTextArea txtMobileNumber;
	private JTextArea txtDate;
	private JTextArea txtInvoiceID;
	private JTextArea txtSubTotal;
	private JTextArea txtTotal;
	private JTextArea txtDiscount;
	private JTextArea txtPaidAmount;
	private JTextArea txtBalanceDue;
	private JLabel lblNewLabel_1;
	 
	
	public static void Frame1() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewInvoiceGUI window = new viewInvoiceGUI();
					window.frmBill.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} 
	 
	public viewInvoiceGUI() {
		initialize();
	}
 
	private void initialize() {
		frmBill = new JFrame();
		frmBill.setTitle("Bill");
		frmBill.setBounds(100, 100, 691, 519);
		frmBill.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBill.getContentPane().setLayout(null);
		
		JLabel lblcustomerID = new JLabel("Customer ID");
		lblcustomerID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblcustomerID.setBounds(27, 79, 114, 20);
		frmBill.getContentPane().add(lblcustomerID);
		
		JLabel lblcusomterName = new JLabel("Customer Name");
		lblcusomterName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblcusomterName.setBounds(27, 113, 114, 20);
		frmBill.getContentPane().add(lblcusomterName);
		
		JLabel lblmobile = new JLabel("Mobile Number");
		lblmobile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblmobile.setBounds(27, 148, 114, 20);
		frmBill.getContentPane().add(lblmobile);
		
	    txtCustomerID = new JTextArea();
		txtCustomerID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCustomerID.setOpaque(false);
		txtCustomerID.setForeground(Color.BLACK);
		txtCustomerID.setBackground(new Color(0, 0, 0));
		txtCustomerID.setBounds(147, 79, 194, 22);
		frmBill.getContentPane().add(txtCustomerID); 
		
	    txtCustomerName = new JTextArea();
	    txtCustomerName.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    txtCustomerName.setOpaque(false);
	    txtCustomerName.setForeground(Color.BLACK);
	    txtCustomerName.setBackground(new Color(0, 0, 0));
	    txtCustomerName.setBounds(147, 113, 194, 22);
		frmBill.getContentPane().add(txtCustomerName);
		
	    txtMobileNumber = new JTextArea();
		txtMobileNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtMobileNumber.setOpaque(false);
		txtMobileNumber.setForeground(Color.BLACK);
		txtMobileNumber.setBackground(new Color(0, 0, 0));
		txtMobileNumber.setBounds(147, 148, 198, 22);
		frmBill.getContentPane().add(txtMobileNumber);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDate.setBounds(27, 48, 90, 20);
		frmBill.getContentPane().add(lblDate);
		
		JLabel lblinvoiceID = new JLabel("Invoice ID");
		lblinvoiceID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblinvoiceID.setBounds(412, 113, 79, 20);
		frmBill.getContentPane().add(lblinvoiceID);
		
		txtDate = new JTextArea();
		txtDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDate.setOpaque(false);
		txtDate.setForeground(Color.BLACK);
		txtDate.setBackground(new Color(0, 0, 0));
		txtDate.setBounds(147, 48, 194, 22);
		frmBill.getContentPane().add(txtDate);

		txtInvoiceID = new JTextArea(); 
		txtInvoiceID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtInvoiceID.setBounds(517, 113, 115, 22);
		frmBill.getContentPane().add(txtInvoiceID);
		
		 tableModel = new DefaultTableModel(
	                new Object[][]{},
	                new String[]{"Item Name", "Quantity", "Unit", "Rate", "Price"}
	        );

	        table = new JTable(tableModel);
	        table.setBackground(new Color(255, 255, 255));
	        table.setBounds(74, 266, 337, 31); 
	        scrollPane = new JScrollPane(table);
	        scrollPane.setBounds(37, 179, 586, 151);
	        frmBill.getContentPane().add(scrollPane);
	        
	        JButton btnViewBill = new JButton("View Bill");
	        btnViewBill.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		int invoiceId = Integer.parseInt(txtInvoiceID.getText());
					updateTableData(invoiceId);
	        	}
	        });
	        btnViewBill.setBounds(543, 80, 89, 23);
	        frmBill.getContentPane().add(btnViewBill);
	        
	        JLabel lblNewLabel = new JLabel("Sub Total");
	        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        lblNewLabel.setBounds(396, 338, 89, 20);
	        frmBill.getContentPane().add(lblNewLabel);
	        
	        JLabel lblDiscount = new JLabel("Discount");
	        lblDiscount.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        lblDiscount.setBounds(396, 368, 95, 20);
	        frmBill.getContentPane().add(lblDiscount);
	        
	        JLabel lblTotal = new JLabel("Total");
	        lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        lblTotal.setBounds(396, 399, 89, 20);
	        frmBill.getContentPane().add(lblTotal);
	        
	        JLabel lblPaidAmount = new JLabel("Amount Paid");
	        lblPaidAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        lblPaidAmount.setBounds(43, 338, 95, 20);
	        frmBill.getContentPane().add(lblPaidAmount);
	        
	        JLabel lblFinalBalance = new JLabel("Balance Due");
	        lblFinalBalance.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        lblFinalBalance.setBounds(43, 368, 95, 20);
	        frmBill.getContentPane().add(lblFinalBalance);
	        
	        JTextArea textArea = new JTextArea();
	        textArea.setText("-----------------------------");
	        textArea.setOpaque(false);
	        textArea.setForeground(Color.BLACK);
	        textArea.setBackground(new Color(0, 0, 0));
	        textArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        textArea.setBounds(390, 382, 168, 22);
	        frmBill.getContentPane().add(textArea);
	        
	        txtSubTotal = new JTextArea();
	        txtSubTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        txtSubTotal.setOpaque(false);
	        txtSubTotal.setForeground(Color.BLACK);
	        txtSubTotal.setBackground(new Color(0, 0, 0));
	        txtSubTotal.setBounds(476, 338, 89, 20);
	        frmBill.getContentPane().add(txtSubTotal);
	        
	        txtTotal = new JTextArea();
	        txtTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        txtTotal.setOpaque(false);
	        txtTotal.setForeground(Color.BLACK);
	        txtTotal.setBackground(new Color(0, 0, 0));
	        txtTotal.setBounds(476, 399, 89, 20);
	        frmBill.getContentPane().add(txtTotal);
	        
	        txtDiscount = new JTextArea();
	        txtDiscount.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        txtDiscount.setOpaque(false);
	        txtDiscount.setForeground(Color.BLACK);
	        txtDiscount.setBackground(new Color(0, 0, 0));
	        txtDiscount.setBounds(476, 368, 89, 20);
	        frmBill.getContentPane().add(txtDiscount);
	        
	        txtPaidAmount = new JTextArea();
	        txtPaidAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        txtPaidAmount.setOpaque(false);
	        txtPaidAmount.setForeground(Color.BLACK);
	        txtPaidAmount.setBackground(new Color(0, 0, 0));
	        txtPaidAmount.setBounds(173, 338, 89, 20);
	        frmBill.getContentPane().add(txtPaidAmount);
	        
	        txtBalanceDue = new JTextArea();
	        txtBalanceDue.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        txtBalanceDue.setOpaque(false);
	        txtBalanceDue.setForeground(Color.BLACK);
	        txtBalanceDue.setBackground(new Color(0, 0, 0));
	        txtBalanceDue.setBounds(173, 368, 89, 20);
	        frmBill.getContentPane().add(txtBalanceDue);
	        
	        lblNewLabel_1 = new JLabel("AKS STORE");
	        lblNewLabel_1.setFont(new Font("Calisto MT", Font.BOLD, 20));
	        lblNewLabel_1.setBounds(258, 18, 148, 39);
	        frmBill.getContentPane().add(lblNewLabel_1);
	}
	
	public void updateTableData(int invoiceId) {
		SwingUtilities.invokeLater(() -> {
			try (Connection connection = DataBaseConnection.getConnection()) {
				String detailQuery = "SELECT d.Quantity, i.ItemName, i.Unit, i.Rate, d.Price FROM InvoiceDetails d JOIN Items i ON d.ItemID = i.ItemID WHERE d.InvoiceID = ?";
				PreparedStatement stmt = connection.prepareStatement(detailQuery);
				stmt.setInt(1, invoiceId);
				ResultSet rs = stmt.executeQuery(); 
				tableModel.setRowCount(0); 
				while (rs.next()) {
					String itemName = rs.getString("ItemName");
					int quantity = rs.getInt("Quantity");
					String unit = rs.getString("Unit");
					double rate = rs.getDouble("Rate");
					double price = rs.getDouble("Price"); 
					tableModel.addRow(new Object[]{itemName, quantity, unit, rate, price});
				} 
				rs.close();
				stmt.close(); 
				table.revalidate();
				table.repaint();
				
				String query1 = "SELECT c.CustomerID, c.Name, c.MobileNumber, c.Balance, i.Date FROM Invoices i JOIN Customers c ON i.CustomerID = c.CustomerID WHERE i.InvoiceID = ?";
	            try (PreparedStatement ps = connection.prepareStatement(query1)) {
	            	ps.setInt(1, invoiceId);
	                ResultSet rs1 = ps.executeQuery();
	                if (rs1.next()) {
	                	int customerId=rs1.getInt("CustomerID");
	                	String customerName=rs1.getString("Name");
	                	String mobile=rs1.getString("MobileNumber");
	                	double balance=rs1.getDouble("Balance");
	                    Date date=rs1.getDate("Date"); 
	                    txtCustomerID.setText(String.valueOf(customerId));
	                    txtCustomerName.setText(customerName);
	                    txtMobileNumber.setText(mobile);
	                    txtDate.setText(String.valueOf(date));
	                    txtBalanceDue.setText(String.valueOf(balance));
	                } else {
	                    JOptionPane.showMessageDialog(frmBill, "Invoice not found");
	                }
	            }
	            
	            String query2 = "SELECT i.SubTotal, i.Discount, i.Total, i.PaidAmount FROM Invoices i JOIN Customers c ON i.CustomerID = c.CustomerID WHERE i.InvoiceID = ?";
	            try (PreparedStatement ps1 = connection.prepareStatement(query2)) {
	            	ps1.setInt(1, invoiceId);
	                ResultSet rs2 = ps1.executeQuery();
	                if (rs2.next()) {
	                	double subTotal=rs2.getDouble("SubTotal");
	                	double discount=rs2.getDouble("Discount");
	                	double total=rs2.getDouble("Total"); 
	                	double paidAmount=rs2.getDouble("PaidAmount"); 
	                	txtSubTotal.setText(String.valueOf(subTotal));
	                	txtDiscount.setText(String.valueOf(discount));
	                	txtTotal.setText(String.valueOf(total)); 
	                	txtPaidAmount.setText(String.valueOf(paidAmount));
	                } 
	            }
	             

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
}