package invoiceBilling;

import java.util.Scanner;

public class MainConsole {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Invoice Billing System");
            System.out.println("1. Create Invoice");
            System.out.println("2. View All Invoices");
            System.out.println("3. View Invoice by ID");
            System.out.println("4. View Customer Balance");
            System.out.println("5. View Item Sales");
            System.out.println("6. Create Item");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    InvoiceBilling.createInvoice();
                    break;
                case 2:
                    InvoiceBilling.viewAllInvoices();
                    break;
                case 3:
                    System.out.print("Enter Invoice ID: ");
                    int invoiceId = scanner.nextInt();
                    InvoiceBilling.viewInvoiceByID(invoiceId);
                    break;
                case 4:
                    InvoiceBilling.viewCustomerBalance();
                    break;
                case 5:
                    InvoiceBilling.viewItemSales();
                    break;
                case 6:
                    InvoiceBilling.createItem();
                    break;
                case 7:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
