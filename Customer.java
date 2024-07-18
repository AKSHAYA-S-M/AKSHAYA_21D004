package invoiceBilling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
	private int customerId;
    private String name;
    private String mobile;
    private double balance;

    public Customer(String name, String mobileNumber, double balance) {
        this.name = name;
        this.mobile = mobileNumber;
        this.balance = balance;
    }

    public int getId() {
    	return customerId; 
    }
    public void setId(int customerId) {
    	this.customerId = customerId; 
    }
    public String getName() { 
    	return name; 
    }
    public void setName(String name) { 
    	this.name = name; 
    }
    public String getMobileNumber() {
    	return mobile; 
    }
    public void setMobile(String mobile) {
    	this.mobile = mobile; 
    }
    public double getBalance() { 
    	return balance; 
    }
    public void setBalance(double balance) {
    	this.balance = balance; 
    }
    
    public static Customer getCustomerByMobileNumber(Connection connection, String mobileNumber) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE MobileNumber = ?";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, mobileNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer(rs.getString("Name"), rs.getString("MobileNumber"),rs.getDouble("Balance"));
                    customer.customerId = rs.getInt("CustomerID");
                    return customer;
                } else {
                    return null;
                }
            }
        }
    }
    
    public static void addCustomer(Connection connection, Customer customer) throws SQLException {
        String sql = "INSERT INTO Customers (Name, MobileNumber, Balance) VALUES (?, ?, ?)";
        try (var pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getMobileNumber());
            pstmt.setDouble(3, customer.getBalance());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    customer.customerId = rs.getInt(1);
                }
            }
        }
    }
    
    public static double getCustomerBalance(Connection connection, String mobileNumber) throws SQLException {
        Customer customer = getCustomerByMobileNumber(connection, mobileNumber);
        if (customer != null) {
            return customer.getBalance();
        } else {
            throw new SQLException("Customer not found");
        }
    }
    
    public static void updateCustomerBalance(Connection connection, int customerId, double finalBalance) throws SQLException {
        String query = "UPDATE Customers SET Balance = ? WHERE CustomerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, finalBalance);
            stmt.setInt(2, customerId);
            stmt.executeUpdate();
        }
    }
    
    public static boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber.matches("\\d{10}"); // Validates 10-digit numbers
    }
    public static boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+"); // Allows only alphabetic characters
    }  
    
}
