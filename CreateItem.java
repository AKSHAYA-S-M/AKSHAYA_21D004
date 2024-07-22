package invoiceBilling;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CreateItem {

	private JFrame frmCreateItem;
	private JTextField textItemName;
	private JTextField textItemUnit;
	private JTextField textItemRate;
	private JTextField textItemID;
	private JTextArea textItem;
	private JLabel lblItemID;
	private int itemID;
	private JTextArea textItemStatus;
	
	public static void Frame4(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateItem window = new CreateItem();
					window.frmCreateItem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
 
	public CreateItem() {
		initialize();
	}
 
	private void initialize() {
		frmCreateItem = new JFrame();
		frmCreateItem.setTitle("Create Item");
		frmCreateItem.setBounds(100, 100, 458, 317);
		frmCreateItem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCreateItem.getContentPane().setLayout(null);
		

		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleItemName();
			}
		});
		btnEnter.setBounds(304, 62, 89, 23);
		frmCreateItem.getContentPane().add(btnEnter);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleAddItem();
			}
		});
		btnAddItem.setBounds(80, 212, 110, 23);
		frmCreateItem.getContentPane().add(btnAddItem);
		
		JButton btnUpdateItem = new JButton("Update Item");
		btnUpdateItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleUpdateItem();
			}
		});
		btnUpdateItem.setBounds(200, 212, 110, 23);
		frmCreateItem.getContentPane().add(btnUpdateItem);
		
		JLabel lblItemName = new JLabel("Item Name");
		lblItemName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblItemName.setBounds(56, 60, 100, 23);
		frmCreateItem.getContentPane().add(lblItemName);
		
		JLabel lblItemUnit = new JLabel("Item Unit");
		lblItemUnit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblItemUnit.setBounds(56, 139, 74, 23);
		frmCreateItem.getContentPane().add(lblItemUnit);
		
		JLabel lblItemRate = new JLabel("Item Rate");
		lblItemRate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblItemRate.setBounds(56, 173, 89, 23);
		frmCreateItem.getContentPane().add(lblItemRate);
		
		textItemName = new JTextField();
		textItemName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textItemName.setBounds(155, 63, 139, 20);
		frmCreateItem.getContentPane().add(textItemName);
		textItemName.setColumns(10);
		
		textItemUnit = new JTextField();
		textItemUnit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textItemUnit.setBounds(155, 142, 139, 20);
		frmCreateItem.getContentPane().add(textItemUnit);
		textItemUnit.setColumns(10);
		
		textItemRate = new JTextField();
		textItemRate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textItemRate.setBounds(155, 176, 139, 20);
		frmCreateItem.getContentPane().add(textItemRate);
		textItemRate.setColumns(10);
		
	    lblItemID = new JLabel("Item ID");
		lblItemID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblItemID.setBounds(56, 105, 74, 23);
		frmCreateItem.getContentPane().add(lblItemID);
		
		textItemID = new JTextField();
		textItemID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textItemID.setBounds(155, 105, 139, 20);
		frmCreateItem.getContentPane().add(textItemID);
		textItemID.setColumns(10); 
		
	    textItem = new JTextArea();
		textItem.setFont(new Font("Calibri", Font.PLAIN, 13));
		textItem.setOpaque(false);
		textItem.setForeground(Color.BLACK);
		textItem.setBackground(new Color(0, 0, 0));
		textItem.setBounds(142, 83, 198, 22);
		frmCreateItem.getContentPane().add(textItem);
		
		textItemStatus = new JTextArea();
		textItemStatus.setFont(new Font("Calibri", Font.PLAIN, 13));
		textItemStatus.setOpaque(false);
		textItemStatus.setForeground(Color.BLACK);
		textItemStatus.setBackground(new Color(0, 0, 0));
		textItemStatus.setBounds(90, 239, 198, 23);
		frmCreateItem.getContentPane().add(textItemStatus);
		
	}
	
	private void handleItemName() {
		try (Connection connection = DataBaseConnection.getConnection()) { 
			String itemName=textItemName.getText();
            Item item = Item.getItemIDbyItemName(connection, itemName);
            if (item == null) {
            	textItem.setText("New Item. Enter Details below.");
            	lblItemID.setVisible(false);
            	textItemID.setVisible(false); 
            } else {
            	textItem.setText("This Item is already created");
            	itemID=item.getId();
            	textItemID.setText(String.valueOf(itemID));
            	textItemID.setEditable(false);
            	String itemUnit=item.getUnit();
            	textItemUnit.setText(itemUnit);
            	textItemUnit.setEditable(false);
            	double itemRate=item.getRate();
            	textItemRate.setText(String.valueOf(itemRate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	private void handleAddItem() {
		try (Connection connection = DataBaseConnection.getConnection()) { 
			String itemName=textItemName.getText();
            Item item = Item.getItemIDbyItemName(connection, itemName);
            if (item == null) {
            	lblItemID.setVisible(true);
            	textItemID.setVisible(true);
            	String itemUnit=textItemUnit.getText();
            	double itemRate=Double.parseDouble(textItemRate.getText());
            	item = new Item(itemName, itemUnit, itemRate);
                Item.addItem(connection, item);
                itemID=item.getId(); 
            	textItemStatus.setText("Item Added.Item ID:"+itemID);
            	textItemName.setText("");
            	textItemID.setText("");
            	textItemRate.setText("");
            	textItemUnit.setText("");
            	textItem.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	private void handleUpdateItem() {
		try (Connection connection = DataBaseConnection.getConnection()) { 
			String itemName=textItemName.getText();
            Item item = Item.getItemIDbyItemName(connection, itemName);
            double itemRate=Double.parseDouble(textItemRate.getText());
            Item.updateItemRecord(connection, item.getId(), itemRate);
            textItemStatus.setText("Item Updated");
        	textItemID.setEditable(true);
        	textItemUnit.setEditable(true);
            textItemName.setText("");
        	textItemID.setText("");
        	textItemRate.setText("");
        	textItemUnit.setText("");
        	textItem.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
