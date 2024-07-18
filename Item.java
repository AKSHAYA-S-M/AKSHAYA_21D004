package invoiceBilling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Item {
	private int itemId;
    private String name;
    private String unit;
    private double rate;

    public Item(String name, String unit, double rate) {
        this.name = name;
        this.unit = unit;
        this.rate = rate;
    }
  
    public int getId() { 
    	return itemId; 
    }
    public void setId(int itemId) { 
    	this.itemId = itemId; 
    }
    public String getName() { 
    	return name; 
    }
    public void setName(String name) { 
    	this.name = name; 
    }
    public String getUnit() {
    	return unit; 
    }
    public void setUnit(String unit) {
    	this.unit = unit; 
    }
    public double getRate() {
    	return rate; 
    }
    public void setRate(double rate) {
    	this.rate = rate; 
    }
    
    public static Item getItemIDbyItemName(Connection connection, String itemName) throws SQLException {
        String sql = "SELECT * FROM Items WHERE ItemName = ?";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, itemName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item(rs.getString("ItemName"), rs.getString("Unit"),rs.getDouble("Rate"));
                    item.itemId = rs.getInt("ItemID");
                    return item;
                } else {
                    return null;
                }
            }
        }
    }
    
    public static void addItem(Connection connection, Item item) throws SQLException {
        String sql = "INSERT INTO Items (ItemName, Unit, Rate) VALUES (?, ?, ?)";
        try (var pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getUnit());
            pstmt.setDouble(3, item.getRate());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    item.itemId = rs.getInt(1);
                }
            }
        }
    }
    
    public static double updateItemRecord(Connection connection, int item_id, double rate) throws SQLException {
    	String query = "UPDATE Items SET Rate = ? WHERE ItemID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, rate);
            stmt.setInt(2, item_id);
            stmt.executeUpdate();
        }
        return 0;
    }
    
    public static double getItemRate(Connection connection, String itemName) throws SQLException {
        Item item = getItemIDbyItemName(connection, itemName);
        if (item != null) {
            return item.getRate();
        } else {
            throw new SQLException("Item not found");
        }
    }
}
