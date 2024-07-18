# InvoiceBillingSystem

**TABLE DESIGN:**


**Customers Table:**

CustomerID (Primary Key): Auto-incremented unique identifier for each customer.

Name: Name of the customer.

MobileNumber: Unique mobile number of the customer.

Balance: Current balance due for the customer.


**Invoices Table:**

InvoiceID (Primary Key): Auto-incremented unique identifier for each invoice.

CustomerID (Foreign Key): Links to the CustomerID in the Customers table.

Date: Date when the invoice was created.

SubTotal: Total amount before applying any discounts.

Discount: Discount applied to the invoice.

Total: Total amount to be paid after discount.


**Items Table:**

ItemID (Primary Key): Auto-incremented unique identifier for each item.

ItemName: Name of the item.

Unit: Unit of measurement for the item.

Rate: Rate of the item per unit.


**InvoiceDetails Table:**

DetailID (Primary Key): Auto-incremented unique identifier for each invoice detail.

InvoiceID (Foreign Key): Links to the InvoiceID in the Invoices table.

ItemID (Foreign Key): Links to the ItemID in the Items table.

Quantity: Quantity of the item purchased in the invoice.

Price: Total price for the quantity of the item in that invoice.



**Module Description :**

This project implements an invoice billing system in Java, utilizing MySQL for database management. It provides functionalities to create invoices, manage customers and items, and view sales data. Key features include:

**Customer Management:** Adding new customers, retrieving customer details by mobile number, and updating customer balances.

**Item Management:** Adding new items, updating item rates, and retrieving item details.

**Invoice Management:** Creating invoices, adding, updating, and deleting invoice details (items), calculating subtotal, applying discounts, and updating invoice totals.

**Sales Reporting:** Viewing all invoices, viewing invoices by ID, checking customer balances, and viewing item sales statistics.

The project is structured using object-oriented principles with classes for customers, items, invoices, and database connections. It includes error handling for database operations and input validations for customer names and mobile numbers.
			
