# InvoiceBillingSystem

This Invoice Billing System, developed in Java using JDBC, offers efficient billing management with key functionalities like customer and items management, invoice creation, balance updates, and sales reporting. It allows for adding and managing customers, creating and updating invoices with accurate calculations of totals and discounts, and manages pricing more efficiently. Users can also view and update customer balances and generate detailed sales reports.


**TABLE DESIGN:**


**Customers Table:** 

CustomerID (Primary Key), Name, MobileNumber, Balance

**Invoices Table:**

InvoiceID (Primary Key), CustomerID (Foreign Key), Date, SubTotal, Discount, Total, Paid Amount
 
**Items Table:**  

ItemID (Primary Key), ItemName, Unit, Rate

**InvoiceDetails Table:**  

DetailID (Primary Key), InvoiceID (Foreign Key), ItemID (Foreign Key), Quantity, Price


**CLASSES AND METHODS USED:**

**GUI Classes** 

MainGUI class: To handle all the GUI classes within it.

CreateInvoiceGUI: User Interface for creating Invoice and Generating Invoice Bill.

viewInvoiceGUI: User Interface to view the Generated bill.

createItem: User Interface for adding and updating items list in daatabase.

**Main Class**

Main: The entry point of the application. Provides a menu for user interaction.

**InvoiceBilling Class**

createInvoice: Handles the creation of a new invoice, including customer validation, adding items, and calculating totals.

createItem: Manages the creation of new items or updates existing items.

viewAllInvoices: Displays all invoices with customer details.

viewInvoiceByID: Shows detailed information of a specific invoice.

viewCustomerBalance: Fetches and displays the balance of a customer based on their mobile number.

viewItemSales: Summarizes the sales of each item.

**Invoice Class**

Invoice: Represents an invoice with customer details, date, and discount.

addInvoiceRecord: Adds a new invoice record to the database.

createInvoiceDetail: Adds item details to an invoice.

updateInvoiceDetail: Updates item details in an invoice.

deleteInvoiceDetail: Deletes item details from an invoice.

updateInvoiceTotal: Updates the total amount of an invoice after applying discount.

updatePaidAmount: Updates the total amount paid by the customer.


**Customer Class**

Customer: Represents a customer with attributes like ID, name, mobile number, and balance.

getCustomerByMobileNumber: Fetches customer details by mobile number.

addCustomer: Adds a new customer to the database.

updateCustomerBalance: Updates the customer's balance.

isValidMobileNumber: Validates the mobile number format.

isValidName: Validates the customer name format.


**Item Class**

Item: Represents an item with attributes like ID, name, unit, and rate.

getItemIDbyItemName: Fetches item details by item name.

addItem: Adds a new item to the database.

updateItemRecord: Updates item rate in the database.

getItemRate: Fetches the rate of an item.

**DataBaseConnection Class**

getConnection: Establishes and returns a connection to the database.



**Module Description :**

This project implements an invoice billing system in Java, utilizing MySQL for database management and JSwing for User Interface. It provides functionalities to create invoices, manage customers and items, and view sales data. Key features include:

**Customer Management:** Adding new customers, retrieving customer details by mobile number, and updating customer balances.

**Item Management:** Adding new items, updating item rates, and retrieving item details.

**Invoice Management:** Creating invoices, adding, updating, and deleting invoice details (items), calculating subtotal, applying discounts, and updating invoice totals.

**Sales Reporting:** Viewing all invoices, viewing invoices by ID, checking customer balances, and viewing item sales statistics.

The project is structured using object-oriented principles with classes for customers, items, invoices, and database connections. It includes error handling for database operations and input validations for customer names and mobile numbers.
			
