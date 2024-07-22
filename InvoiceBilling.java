package invoiceBilling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class InvoiceBilling {

    public static void createInvoice() {
        Scanner scanner = new Scanner(System.in);
        try (Connection connection = DataBaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            String mobileNumber;
            do {
                System.out.print("Enter Customer's Mobile Number: ");
                mobileNumber = scanner.next();
                if (!Customer.isValidMobileNumber(mobileNumber)) {
                    System.out.println("Invalid mobile number format. Please enter a valid 10-digit number.");
                }
            } while (!Customer.isValidMobileNumber(mobileNumber));

            Customer customer = Customer.getCustomerByMobileNumber(connection, mobileNumber);
            int customerId;
            double initialBalance = 0;

            if (customer != null) {
                customerId = customer.getId();
                initialBalance = customer.getBalance();
            } else {
                String name;
                do {
                    System.out.print("Enter Customer Name: ");
                    name = scanner.next();
                    if (!Customer.isValidName(name)) {
                        System.out.println("Enter a valid name.");
                    }
                } while (!Customer.isValidName(name));

                customer = new Customer(name, mobileNumber, initialBalance);
                Customer.addCustomer(connection, customer);
                customerId = customer.getId();
            }

            LocalDate currentDate = LocalDate.now();
            System.out.print("Enter Discount: ");
            double discount = scanner.nextDouble();
            Invoice invoice = new Invoice(customer, currentDate, discount);
            Invoice.addInvoiceRecord(connection, invoice, currentDate);
            int invoiceId = invoice.getId();

            while (true) {
                System.out.print("1. Add Item\n2. Update Item\n3. Delete Item\n4. Finish\nChoose an option: ");
                int option = scanner.nextInt();
                if (option == 4) break;
                System.out.print("Enter Item Name: ");
                String itemName = scanner.next();
                Item item = Item.getItemIDbyItemName(connection, itemName);
                if (item == null) {
                    System.out.print("Item Name entered is not valid\n");
                } else {
                    int itemId = item.getId();
                    switch (option) {
                        case 1:
                            System.out.print("Enter Quantity: ");
                            int quantity = scanner.nextInt();
                            double price = Item.getItemRate(connection, itemName) * quantity;
                            Invoice.createInvoiceDetail(connection, invoiceId, itemId, quantity, price);
                            break;
                        case 2:
                            System.out.print("Enter Quantity to Update: ");
                            int newQuantity = scanner.nextInt();
                            double newPrice = Item.getItemRate(connection, itemName) * newQuantity;
                            Invoice.updateInvoiceDetail(connection, invoiceId, itemId, newQuantity, newPrice);
                            break;
                        case 3:
                            Invoice.deleteInvoiceDetail(connection, invoiceId, itemId);
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                }
            }

            double totalAmount = Invoice.updateInvoiceTotal(connection, invoiceId, discount);
            double amount=totalAmount+ initialBalance;
            System.out.println("AMOUNT TO BE PAID: " + amount);
            System.out.print("Enter Paid Amount: ");
            double paidAmount = scanner.nextDouble();
            Invoice.updatePaidAmount(connection,paidAmount,invoiceId);
            double finalBalance = initialBalance + totalAmount - paidAmount;
            Customer.updateCustomerBalance(connection, customerId, finalBalance);

            connection.commit();

            System.out.println("Invoice created successfully.");
            System.out.println("Initial Balance: " + initialBalance);
            InvoiceBilling.viewInvoiceByID(invoiceId);
            System.out.println("Paid Amount: " + paidAmount);
            System.out.println("Balance Due: " + finalBalance); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createItem() {
        Scanner scanner = new Scanner(System.in);
        try (Connection connection = DataBaseConnection.getConnection()) {
            System.out.print("Enter Item Name: ");
            String itemName = scanner.next();
            Item item = Item.getItemIDbyItemName(connection, itemName);
            if (item == null) {
                System.out.print("Enter Unit: ");
                String itemUnit = scanner.next();
                System.out.print("Enter Rate: ");
                double itemRate = scanner.nextDouble();
                item = new Item(itemName, itemUnit, itemRate);
                Item.addItem(connection, item);
                System.out.println("Item created successfully");
            } else {
                System.out.println("This Item is already created and ItemID is " + item.getId());
                System.out.println("Enter 1 to UPDATE item rate, 0 to exit: ");
                int value = scanner.nextInt();
                if (value == 1) {
                    System.out.print("Enter Rate: ");
                    double itemRate = scanner.nextDouble();
                    Item.updateItemRecord(connection, item.getId(), itemRate);
                    System.out.println("Item Details Updated Successfully");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewAllInvoices() {
        try (Connection connection = DataBaseConnection.getConnection()) {
            String query = "SELECT i.InvoiceID, c.Name, i.Date, i.Total, i.PaidAmount FROM Invoices i JOIN Customers c ON i.CustomerID = c.CustomerID";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                System.out.printf("%-10s %-20s %-10s %-10s %-10s\n", "InvoiceID", "Customer Name", "Date", "Total","PaidAmount");
                while (rs.next()) {
                    System.out.printf("%-10d %-20s %-10s %-10.2f %-10.2f\n", rs.getInt("InvoiceID"), rs.getString("Name"), rs.getDate("Date"), rs.getDouble("Total"),rs.getDouble("PaidAmount"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewInvoiceByID(int invoiceId) {
        try (Connection connection = DataBaseConnection.getConnection()) {
            String query1 = "SELECT i.InvoiceID, c.Name, i.Date FROM Invoices i JOIN Customers c ON i.CustomerID = c.CustomerID WHERE i.InvoiceID = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query1)) {
                stmt.setInt(1, invoiceId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.printf("InvoiceID: %d\nCustomer Name: %s\nDate: %s\n",
                    		rs.getInt("InvoiceID"), rs.getString("Name"), rs.getDate("Date"));
                } else {
                    System.out.println("Invoice not found.");
                }
            }

            String detailQuery = "SELECT d.Quantity, i.ItemName, i.Unit, i.Rate, d.Price FROM InvoiceDetails d JOIN Items i ON d.ItemID = i.ItemID WHERE d.InvoiceID = ?";
            try (PreparedStatement stmt = connection.prepareStatement(detailQuery)) {
                stmt.setInt(1, invoiceId);
                ResultSet rs = stmt.executeQuery();
                System.out.printf("%-20s %-10s %-10s %-10s %-10s\n", "Item Name", "Quantity", "Unit", "Rate", "Price");
                while (rs.next()) {
                    System.out.printf("%-20s %-10d %-10s %-10.2f %-10.2f\n",
                            rs.getString("ItemName"), rs.getInt("Quantity"), rs.getString("Unit"), rs.getDouble("Rate"), rs.getDouble("Price"));
                }
            }
            
            String query2 = "SELECT i.SubTotal, i.Discount, i.Total ,i.PaidAmount FROM Invoices i JOIN Customers c ON i.CustomerID = c.CustomerID WHERE i.InvoiceID = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query2)) {
                stmt.setInt(1, invoiceId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.printf("SubTotal: %.2f\nDiscount: %.2f\nTotal: %.2f\nPaidAmount: %.2f\n",
                            rs.getDouble("SubTotal"), rs.getDouble("Discount"), rs.getDouble("Total"),rs.getDouble("PaidAmount"));
                } 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewCustomerBalance() {
        Scanner scanner = new Scanner(System.in);
        try (Connection connection = DataBaseConnection.getConnection()) {
            System.out.print("Enter Customer's Mobile Number: ");
            String mobileNumber = scanner.next();
            Customer customer = Customer.getCustomerByMobileNumber(connection, mobileNumber);
            if (customer != null) {
                System.out.println("Customer's Balance Due: " + customer.getBalance());
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewItemSales() {
        try (Connection connection = DataBaseConnection.getConnection()) {
            String query = "SELECT i.ItemName, SUM(d.Quantity) AS TotalQuantity, SUM(d.Price) AS TotalSales FROM InvoiceDetails d JOIN Items i ON d.ItemID = i.ItemID GROUP BY i.ItemName";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                System.out.printf("%-20s %-10s %-10s\n", "Item Name", "Total Quantity", "Total Sales");
                while (rs.next()) {
                    System.out.printf("%-20s %-10d %-10.2f\n", rs.getString("ItemName"), rs.getInt("TotalQuantity"), rs.getDouble("TotalSales"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
