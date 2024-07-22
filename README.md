# InvoiceBillingSystem

This Invoice Billing System, developed in Java using JDBC, offers efficient billing management with key functionalities like customer and items management, invoice creation, balance updates, and sales reporting. It allows for adding and managing customers, creating and updating invoices with accurate calculations of totals and discounts, and manages pricing more efficiently. Users can also view and update customer balances and generate detailed sales reports.


**TABLE DESIGN:**


**Customers Table:** CustomerID (Primary Key), Name, MobileNumber, Balance
+------------+---------------+--------------+---------+
| CustomerID | Name          | MobileNumber | Balance |
+------------+---------------+--------------+---------+
|          1 | Akshaya       | 7373611199   |   16.25 |
|          2 | Amritha       | 7373911199   |    3.40 |
|          3 | Seetha        | 9897868787   |    7.50 |
|          4 | Ajitha        | 6576875646   |    3.50 |
|          5 | Tharanya      | 4565768798   |    6.00 |
|          6 | Roshini       | 9089342312   |    8.50 |
|          7 | Manju         | 8796875676   |   70.00 |
|          8 | Sakkaravarthy | 9342312567   |    8.00 |
|          9 | Minerva       | 9675645342   |    0.00 |
|         10 | Hazel         | 9645786677   |   56.00 |
|         11 | Mazhar        | 8786765656   |    8.50 |
+------------+---------------+--------------+---------+


**Invoices Table:**

InvoiceID (Primary Key), CustomerID (Foreign Key), Date, SubTotal, Discount, Total

+-----------+------------+------------+----------+----------+--------+------------+
| InvoiceID | CustomerID | Date       | SubTotal | Discount | Total  | PaidAmount |
+-----------+------------+------------+----------+----------+--------+------------+
|         1 |          1 | 2024-07-22 |   175.00 |     8.75 | 166.25 |     150.00 |
|         2 |          2 | 2024-07-22 |    20.00 |     1.60 |  18.40 |      15.00 |
|         3 |          3 | 2024-07-22 |    50.00 |     2.50 |  47.50 |      40.00 |
|         4 |          4 | 2024-07-22 |   130.00 |     6.50 | 123.50 |     120.00 |
|         5 |          5 | 2024-07-22 |    80.00 |     4.00 |  76.00 |      70.00 |
|         6 |          6 | 2024-07-22 |    30.00 |     1.50 |  28.50 |      20.00 |
|         7 |          7 | 2024-07-22 |   300.00 |    30.00 | 270.00 |     200.00 |
|         8 |          8 | 2024-07-22 |   200.00 |    12.00 | 188.00 |     180.00 |
|         9 |          9 | 2024-07-22 |    20.00 |     1.00 |  19.00 |      19.00 |
|        10 |         10 | 2024-07-22 |   480.00 |    24.00 | 456.00 |     400.00 |
|        11 |         11 | 2024-07-22 |    30.00 |     1.50 |  28.50 |      20.00 |
+-----------+------------+------------+----------+----------+--------+------------+


**Items Table:**  ItemID (Primary Key), ItemName, Unit, Rate

+--------+-----------+------+-------+
| ItemID | ItemName  | Unit | Rate  |
+--------+-----------+------+-------+
|      1 | Pencil    | pcs  |  5.00 |
|      2 | Scale     | pcs  | 10.00 |
|      3 | DiaryMilk | pcs  | 10.00 |
|      4 | KitKat    | pcs  | 15.00 |
|      5 | Munch     | pcs  | 20.00 |
|      6 | Notebook  | -    | 45.00 |
|      7 | Rice      | kg   | 40.00 |
|      8 | InkBottle | -    | 30.00 |
|      9 | PenStand  | pcs  | 30.00 |
|     10 | Eraser    | pcs  | 10.00 |
|     11 | Mirror    | pcs  | 60.00 |
|     12 | Pen       | pcs  | 20.00 |
+--------+-----------+------+-------+


**InvoiceDetails Table:**  DetailID (Primary Key), InvoiceID (Foreign Key), ItemID (Foreign Key), Quantity, Price

+----------+-----------+--------+----------+--------+
| DetailID | InvoiceID | ItemID | Quantity | Price  |
+----------+-----------+--------+----------+--------+
|        1 |         1 |     12 |        5 | 100.00 |
|        2 |         1 |      1 |        2 |  10.00 |
|        3 |         1 |      4 |        1 |  15.00 |
|        4 |         1 |     10 |        1 |  10.00 |
|        5 |         1 |      2 |        1 |  10.00 |
|        6 |         1 |      9 |        1 |  30.00 |
|        7 |         2 |      3 |        2 |  20.00 |
|        8 |         3 |      1 |       10 |  50.00 |
|        9 |         4 |      5 |        2 |  40.00 |
|       10 |         4 |      6 |        2 |  90.00 |
|       11 |         5 |      7 |        2 |  80.00 |
|       12 |         6 |      8 |        1 |  30.00 |
|       13 |         7 |     12 |       15 | 300.00 |
|       14 |         8 |      7 |        5 | 200.00 |
|       15 |         9 |      3 |        2 |  20.00 |
|       16 |        10 |      1 |        2 |  10.00 |
|       17 |        10 |     12 |        1 |  20.00 |
|       18 |        10 |      6 |       10 | 450.00 |
|       19 |        11 |      4 |        2 |  30.00 |
+----------+-----------+--------+----------+--------+


**CLASSES AND METHODS USED:**

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

This project implements an invoice billing system in Java, utilizing MySQL for database management. It provides functionalities to create invoices, manage customers and items, and view sales data. Key features include:

**Customer Management:** Adding new customers, retrieving customer details by mobile number, and updating customer balances.

**Item Management:** Adding new items, updating item rates, and retrieving item details.

**Invoice Management:** Creating invoices, adding, updating, and deleting invoice details (items), calculating subtotal, applying discounts, and updating invoice totals.

**Sales Reporting:** Viewing all invoices, viewing invoices by ID, checking customer balances, and viewing item sales statistics.

The project is structured using object-oriented principles with classes for customers, items, invoices, and database connections. It includes error handling for database operations and input validations for customer names and mobile numbers.
			
