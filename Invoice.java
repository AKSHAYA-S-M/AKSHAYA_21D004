package invoiceBilling;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Invoice {
	private int invoiceId;
    private Customer customer;
    private LocalDate date;
    private double discount;

    public Invoice(Customer customer, LocalDate date, double discount) {
        this.customer = customer;
        this.date = date;
        this.discount = discount;
    }
    
    public int getId() {
    	return invoiceId; 
    }
    public void setId(int invoiceId) { 
    	this.invoiceId = invoiceId; 
    }
    public Customer getCustomer() { 
    	return customer; 
    }
    public void setCustomer(Customer customer) {
    	this.customer = customer; 
    }
    //public double getInitialBalance() { return initialBalance; }
    //public void setInitialBalance(double initialBalance) { this.initialBalance = initialBalance; }
    public LocalDate getDate() {
        return date;
    }
    public double getDiscount() { 
    	return discount; 
    }
    public void setDiscount(double discount) { 
    	this.discount = discount;
    }
    
    public static void addInvoiceRecord(Connection connection, Invoice invoice, LocalDate date) throws SQLException {
        String sql = "INSERT INTO Invoices (CustomerID, Date, Discount) VALUES (?, ?, ?)";
        try (var pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, invoice.customer.getId());
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.setDouble(3, invoice.customer.getBalance());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    invoice.invoiceId = rs.getInt(1);
                }
            }
        }
    }
    
    public static void createInvoiceDetail(Connection connection, int invoiceId, int itemId, int quantity, double price) throws SQLException {
        String query = "INSERT INTO InvoiceDetails (InvoiceID, ItemID, Quantity, Price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, invoiceId);
            stmt.setInt(2, itemId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.executeUpdate();
        }
    }
    
    public static void updateInvoiceDetail(Connection connection, int invoiceId, int itemId, int quantity, double price) throws SQLException {
        String query = "UPDATE InvoiceDetails SET Quantity = ?,Price=? WHERE InvoiceID = ? AND ItemID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setDouble(2, price);
            stmt.setInt(3, invoiceId);
            stmt.setInt(4, itemId);
            stmt.executeUpdate();
        }
    }
    
    public static void deleteInvoiceDetail(Connection connection, int invoiceId, int itemId) throws SQLException {
        String query = "DELETE FROM InvoiceDetails WHERE InvoiceID = ? AND ItemID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, invoiceId);
            stmt.setInt(2, itemId);
            stmt.executeUpdate();
        }
    }
    
    public static double updateInvoiceTotal(Connection connection, int invoiceId, double discount) throws SQLException {
        String query = "UPDATE Invoices SET SubTotal=(SELECT SUM(Price) FROM InvoiceDetails WHERE InvoiceID = ?),Total = (SELECT SUM(Price) FROM InvoiceDetails WHERE InvoiceID = ?) - ? WHERE InvoiceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
        	stmt.setInt(1, invoiceId);
            stmt.setInt(2, invoiceId);
            stmt.setDouble(3, discount);
            stmt.setInt(4, invoiceId);
            stmt.executeUpdate();

            String totalQuery = "SELECT Total FROM Invoices WHERE InvoiceID = ?";
            try (PreparedStatement totalStmt = connection.prepareStatement(totalQuery)) {
                totalStmt.setInt(1, invoiceId);
                ResultSet rs = totalStmt.executeQuery();
                if (rs.next()) {
                    return rs.getDouble("Total");
                }
            }
        }
        return 0;
    }
    
}
