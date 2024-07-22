package invoiceBilling;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JInternalFrame;
import javax.swing.JList;

public class CreateInvoiceGUI {

    private JFrame frame;
    private JTextField txtMobileNumber;
    private JTextField txtCustomerName;
    private JTextField txtitemName;
    private JTextArea fetchCustomer;
    private JTextField txtDiscount;
    private JTextField txtItemQuantity;
    private JTextField txtpaidAmount;
    private JLabel itemQuantity;
    private JTextArea txtInvoiceID;
    private JTextArea txtItemStatus;
    private JLabel customerID;
    private int invoiceId;
    private int itemId;
    private String itemName;
    private int newQuantity;
    private JButton btnViewInvoice;
    private JTextArea txtInvoice;
    private double discount;
    private double initialBalance;
    private JTextArea textAreaAmount;
    private int customerId;
    private String customerName;
    private double totalAmount;
    private double amount;
    private double paidAmount;
    private double finalBalance;
    private JTextArea txtCustomerID;
    private JTextArea txtinitialBalance;
    private JTable table;
    private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
    
    public double getInitialBalance() {
        return initialBalance;
    }

    public double getAmount() {
        return amount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }
    
    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    } 
    
    public static void Frame2() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CreateInvoiceGUI window = new CreateInvoiceGUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CreateInvoiceGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
        frame.setTitle("Invoice Billing");
        frame.getContentPane().setForeground(new Color(0, 0, 0));
        frame.setBounds(100, 100, 863, 623);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null); 

        JLabel mobilenumber = new JLabel("Enter Mobile Number");
        mobilenumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
        mobilenumber.setBounds(44, 70, 187, 27);
        frame.getContentPane().add(mobilenumber);

        txtMobileNumber = new JTextField();
        txtMobileNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtMobileNumber.setBounds(223, 72, 330, 22);
        frame.getContentPane().add(txtMobileNumber);
        txtMobileNumber.setColumns(10);

        customerID = new JLabel("Customer ID");
        customerID.setFont(new Font("Tahoma", Font.PLAIN, 14));
        customerID.setBounds(44, 108, 165, 27);
        frame.getContentPane().add(customerID);

        JLabel customerName = new JLabel("Customer Name");
        customerName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        customerName.setBounds(44, 146, 187, 27);
        frame.getContentPane().add(customerName);

        txtCustomerName = new JTextField();
        txtCustomerName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtCustomerName.setBounds(223, 146, 330, 22);
        frame.getContentPane().add(txtCustomerName);
        txtCustomerName.setColumns(10);

        JLabel initialBalance = new JLabel("Initial Balance");
        initialBalance.setFont(new Font("Tahoma", Font.PLAIN, 14));
        initialBalance.setBounds(44, 184, 170, 27);
        frame.getContentPane().add(initialBalance);

        JLabel lblitemName = new JLabel("Enter Item");
        lblitemName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblitemName.setBounds(44, 289, 187, 27);
        frame.getContentPane().add(lblitemName);

        txtitemName = new JTextField();
        txtitemName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtitemName.setBounds(223, 291, 330, 22);
        frame.getContentPane().add(txtitemName);
        txtitemName.setColumns(10);

        fetchCustomer = new JTextArea();
        fetchCustomer.setEditable(false);
        fetchCustomer.setFont(new Font("Calibri", Font.PLAIN, 13));
        fetchCustomer.setOpaque(false);
        fetchCustomer.setForeground(Color.BLACK);
        fetchCustomer.setBackground(new Color(0, 0, 0));
        fetchCustomer.setBounds(223, 94, 330, 15);
        frame.getContentPane().add(fetchCustomer);

        JButton addItem = new JButton("Add Item");
        addItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                try {
                    itemName = txtitemName.getText();
                    int quantity = Integer.parseInt(txtItemQuantity.getText());
                    handleAddItem(itemName, quantity);
                    txtitemName.setText("");
                    txtItemQuantity.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid quantity format. Please enter a valid number.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
        addItem.setBounds(223, 360, 89, 27);
        frame.getContentPane().add(addItem);

        JButton updateItem = new JButton("Update Item");
        updateItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                try {
                    itemName = txtitemName.getText();
                    newQuantity = Integer.parseInt(txtItemQuantity.getText());
                    handleUpdateItem(itemName, newQuantity);
                    txtitemName.setText("");
                    txtItemQuantity.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid quantity format. Please enter a valid number.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
        updateItem.setBounds(322, 360, 115, 27);
        frame.getContentPane().add(updateItem);

        JButton deleteItem = new JButton("Delete Item");
        deleteItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                try { 
                    itemName = txtitemName.getText();
                    handleDeleteItem(itemName);
                    txtitemName.setText("");
                    txtItemQuantity.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
        deleteItem.setBounds(448, 360, 105, 27);
        frame.getContentPane().add(deleteItem);

        JButton enterBtn = new JButton("Enter");
        enterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String mobileNumber = txtMobileNumber.getText();
                if (!Customer.isValidMobileNumber(mobileNumber)) {
                    JOptionPane.showMessageDialog(frame, "Invalid mobile number format. Please enter a valid 10-digit number.");
                } else {
                    handleCustomer(mobileNumber);
                }
            }
        });
        enterBtn.setBounds(563, 74, 89, 23);
        frame.getContentPane().add(enterBtn);
        
        JLabel discountLabel = new JLabel("Discount");
        discountLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        discountLabel.setBounds(44, 222, 187, 27);
        frame.getContentPane().add(discountLabel);
        
        txtDiscount = new JTextField();
        txtDiscount.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtDiscount.setBounds(223, 222, 330, 22);
        frame.getContentPane().add(txtDiscount);
        txtDiscount.setColumns(10);
        
        itemQuantity = new JLabel("Quantity");
        itemQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
        itemQuantity.setBounds(44, 322, 187, 27);
        frame.getContentPane().add(itemQuantity);
        
        txtItemQuantity = new JTextField();
        txtItemQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtItemQuantity.setBounds(223, 327, 330, 22);
        frame.getContentPane().add(txtItemQuantity);
        txtItemQuantity.setColumns(10);
        
        JLabel amount = new JLabel("Amount to be Paid");
        amount.setFont(new Font("Tahoma", Font.PLAIN, 14));
        amount.setBounds(44, 410, 187, 27);
        frame.getContentPane().add(amount);
        
        JLabel amountPaid = new JLabel("Paid Amount");
        amountPaid.setFont(new Font("Tahoma", Font.PLAIN, 14));
        amountPaid.setBounds(44, 446, 187, 27);
        frame.getContentPane().add(amountPaid);
        
        textAreaAmount = new JTextArea();
        textAreaAmount.setEditable(false);
        textAreaAmount.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textAreaAmount.setBounds(223, 412, 330, 22);
        frame.getContentPane().add(textAreaAmount);
        
        txtpaidAmount = new JTextField();
        txtpaidAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtpaidAmount.setBounds(223, 448, 334, 22);
        frame.getContentPane().add(txtpaidAmount);
        txtpaidAmount.setColumns(10);
        
        JButton btnCreateInvoice = new JButton("Create Invoice");
        btnCreateInvoice.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		btnViewInvoice.setVisible(true);
        		handleInvoice();
        		txtInvoice.setText("Invoice Created successfully");
        		btnViewInvoice.setVisible(true);
        	}
        });
        btnCreateInvoice.setBounds(223, 492, 165, 38);
        frame.getContentPane().add(btnCreateInvoice); 
        
        txtInvoice = new JTextArea();
        txtInvoice.setEditable(false);
        txtInvoice.setBounds(223, 534, 380, 22);
        txtInvoice.setOpaque(false);
        txtInvoice.setForeground(Color.BLACK);
        txtInvoice.setBackground(new Color(0, 0, 0));
        frame.getContentPane().add(txtInvoice);
        
        JButton btnCheck = new JButton("Check");
        btnCheck.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		updateTotalAmount(); 
        	}
        });
        btnCheck.setBounds(563, 414, 89, 23);
        frame.getContentPane().add(btnCheck);
        
        JButton btnDone = new JButton("Done");
        btnDone.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		customerID.setVisible(true);
            	txtCustomerID.setVisible(true);
        		handleDone(); 	
        	}
        });
        btnDone.setBounds(223, 255, 89, 23);
        frame.getContentPane().add(btnDone);
        
        txtInvoiceID = new JTextArea();
        txtInvoiceID.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtInvoiceID.setBounds(322, 255, 231, 22);
        txtInvoiceID.setOpaque(false);
        txtInvoiceID.setForeground(Color.BLACK);
        txtInvoiceID.setBackground(new Color(0, 0, 0));
        frame.getContentPane().add(txtInvoiceID);
        
        txtItemStatus = new JTextArea();
        txtItemStatus.setEditable(false);
        txtItemStatus.setFont(new Font("Calibri", Font.PLAIN, 13));
        txtItemStatus.setBounds(223, 391, 330, 16);
        txtItemStatus.setOpaque(false);
        txtItemStatus.setForeground(Color.BLACK);
        txtItemStatus.setBackground(new Color(0, 0, 0));
        frame.getContentPane().add(txtItemStatus);
        
        btnViewInvoice = new JButton("View Invoice");
        btnViewInvoice.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { 
        		viewInvoiceGUI viewInvoiceGUI = new viewInvoiceGUI();
				//viewInvoiceGUI.updateTableData(invoiceId); 
				viewInvoiceGUI.Frame1(); 
				frame.setVisible(false);
        	}
        });
        
        btnViewInvoice.setVisible(false);
        btnViewInvoice.setBounds(398, 492, 165, 38);
        frame.getContentPane().add(btnViewInvoice);
        
        JLabel lblStoreName = new JLabel("AKS STORE");
        lblStoreName.setFont(new Font("Calisto MT", Font.BOLD, 20));
        lblStoreName.setBounds(299, 26, 138, 27);
        frame.getContentPane().add(lblStoreName);
        
        txtCustomerID = new JTextArea();
        txtCustomerID.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtCustomerID.setEditable(false);
        txtCustomerID.setBounds(223, 110, 330, 22);
        frame.getContentPane().add(txtCustomerID);
        
        txtinitialBalance = new JTextArea();
        txtinitialBalance.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtinitialBalance.setEditable(false);
        txtinitialBalance.setBounds(223, 186, 330, 22);
        frame.getContentPane().add(txtinitialBalance);
        
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Item Name", "Quantity"}
        );

        table = new JTable(tableModel);
        table.setBackground(new Color(255, 255, 255));
        table.setBounds(74, 266, 337, 31); 
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(584, 123, 231, 258);
        frame.getContentPane().add(scrollPane);
        
        JList list = new JList();
        list.setBounds(703, 463, 1, 1);
        frame.getContentPane().add(list);
         
    }

    private void handleCustomer(String mobileNumber) {
        try (Connection connection = DataBaseConnection.getConnection()) {
            Customer customer = Customer.getCustomerByMobileNumber(connection, mobileNumber); 
            initialBalance = 0;
            if (customer != null) {
            	fetchCustomer.setText("This Customer is Already registered");
                customerId = customer.getId();
                txtCustomerID.setText(String.valueOf(customerId));
                customerName = customer.getName();
                txtCustomerName.setText(customerName);
                initialBalance = customer.getBalance();
                txtinitialBalance.setText(String.valueOf(initialBalance));
            } else { 
            	fetchCustomer.setText("New Registration. Enter Details below.");
            	customerID.setVisible(false);
            	txtCustomerID.setVisible(false);
                txtinitialBalance.setText("0");
            }
              
         } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void handleDone() {
    	try (Connection connection = DataBaseConnection.getConnection()) {
    		String mobileNumber = txtMobileNumber.getText();
    		Customer customer = Customer.getCustomerByMobileNumber(connection, mobileNumber); 
    		if(customer==null) {
    			String name = txtCustomerName.getText(); 
                customer = new Customer(name, mobileNumber, initialBalance);
                Customer.addCustomer(connection, customer);
                customerId = customer.getId();
                txtCustomerID.setText(String.valueOf(customerId));
    		}
    		LocalDate currentDate = LocalDate.now();
            discount = Double.parseDouble(txtDiscount.getText());
            Invoice invoice = new Invoice(customer, currentDate, discount);
            Invoice.addInvoiceRecord(connection, invoice, currentDate);
            invoiceId = invoice.getId();  
            txtInvoiceID.setText("Invoice ID : "+invoiceId);
    	}catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void handleAddItem(String itemName,int quantity) {
    	try (Connection connection = DataBaseConnection.getConnection()) {  
            Item item = Item.getItemIDbyItemName(connection, itemName);
            if (item == null) {
                txtItemStatus.setText("Item not found");
                return;
            }
            itemId = item.getId(); 
    		double price = Item.getItemRate(connection, itemName) * quantity;
            Invoice.createInvoiceDetail(connection, invoiceId, itemId, quantity, price);
            tableModel.addRow(new Object[]{itemName, quantity});
            txtItemStatus.setText("Item Added Successfully");
    	}catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void handleUpdateItem(String itemName,int newQuantity) {
    	try (Connection connection = DataBaseConnection.getConnection()) {
    		Item item = Item.getItemIDbyItemName(connection, itemName);
    		if (item == null) {
                txtItemStatus.setText("Item not found");
                return;
            }
    		boolean itemFound = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
            	if (tableModel.getValueAt(i, 0).toString().equalsIgnoreCase(itemName)) {
                    tableModel.setValueAt(newQuantity, i, 1);
                    itemFound = true;
                    break;
                }
            } 
            itemId = item.getId(); 
    		double newPrice = Item.getItemRate(connection, itemName) * newQuantity;
            Invoice.updateInvoiceDetail(connection, invoiceId, itemId, newQuantity, newPrice);
            txtItemStatus.setText("Item Updated Successfully");
    	}catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void handleDeleteItem(String itemName) {
    	try (Connection connection = DataBaseConnection.getConnection()) {
    		Item item = Item.getItemIDbyItemName(connection, itemName);
    		if (item == null) {
                txtItemStatus.setText("Item not found");
                return;
            }
    		boolean itemFound = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
            	if (tableModel.getValueAt(i, 0).toString().equalsIgnoreCase(itemName)) {
                    tableModel.removeRow(i);
                    itemFound = true;
                    break;
                }
            } 
    		itemId = item.getId();
            Invoice.deleteInvoiceDetail(connection, invoiceId, itemId);
            txtItemStatus.setText("Item Deleted Successfully");
     	}catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateTotalAmount() {
    	 try (Connection connection = DataBaseConnection.getConnection()) {
    	        totalAmount = Invoice.updateInvoiceTotal(connection, invoiceId, discount);
    	        amount = totalAmount + initialBalance;
    	        textAreaAmount.setText(String.valueOf(amount));
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    }
    private void handleInvoice() {
   	 	try (Connection connection = DataBaseConnection.getConnection()) {
   	 		 paidAmount = Double.parseDouble(txtpaidAmount.getText());
   	 		 Invoice.updatePaidAmount(connection,paidAmount,invoiceId);
   	 		 finalBalance = initialBalance + totalAmount - paidAmount;
   	 		Customer.updateCustomerBalance(connection, customerId, finalBalance);
   	 		System.out.println("Invoice ID: "+invoiceId);
   	 	} catch (SQLException e) {
   	 		e.printStackTrace();
   	 	}
} 
}
