package villenabookselling;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Reports {

    public void reportsTransaction() {
        Scanner sc = new Scanner(System.in);
        String response = "yes";
        int action = -1;
        Reports rp = new Reports();

        do {
            System.out.println("");
            System.out.println(" WELCOME TO VIEW REPORTS");
            System.out.println("");
            System.out.println("1. View All Reports");
            System.out.println("2. Receipt by Order ID");
            System.out.println("3. Exit");

            System.out.print("Enter Action: ");
            try {
                action = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid action, Please enter a numeric action.");
                sc.nextLine();
                continue;
            }

            if (action < 1 || action > 3) {
                System.out.println("Invalid action, Please enter a number between 1 to 3.");
                continue;
            }

            switch (action) {
                case 1:
                    rp.viewAllReports();
                    break;
                case 2:
                    rp.viewReceipt();
                    break;
            }
            if (action != 3) {
                System.out.print("Do you want to continue? (yes/no): ");
                response = sc.next();
            } else {
                response = "no";
            }
        } while (response.equalsIgnoreCase("yes"));
        System.out.println("Thank You, See you soonest!");
    }

    public void viewAllReports() {
        config conf = new config();
        String qry = "SELECT o_id, c_fname, b_title, b_author, b_genre, o_quantity, o_due, o_rcash, o_date, o_status FROM tbl_orders "
                + "LEFT JOIN tbl_customer ON tbl_customer.c_id = tbl_orders.c_id "
                + "LEFT JOIN tbl_books ON tbl_books.b_id = tbl_orders.b_id";
        String[] headers = {"Order ID", "Customer Name", "Book Title", "Author", "Genre", "Quantity", "Due", "Received Cash", "Date", "Status"};
        String[] columns = {"o_id", "c_fname", "b_title", "b_author", "b_genre", "o_quantity", "o_due", "o_rcash", "o_date", "o_status"};
        conf.viewRecords(qry, headers, columns);
    }

    public void viewReceipt() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int orderId;

        while (true) {
            System.out.print("Enter the Order ID to make receipt: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Order ID: ");
                sc.next();
            }
            try {
                orderId = sc.nextInt();
                if (conf.getSingleValue("SELECT o_id FROM tbl_orders WHERE o_id = ?", orderId) != 0) {
                    break;
                }
                System.out.println("Order ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric ID.");
                sc.nextLine();
            }
        }

        String receiptQuery = "SELECT o_id, c_fname, b_title, b_author, b_genre, o_quantity, b_price, o_due, o_rcash, o_date FROM tbl_orders "
                + "LEFT JOIN tbl_customer ON tbl_customer.c_id = tbl_orders.c_id "
                + "LEFT JOIN tbl_books ON tbl_books.b_id = tbl_orders.b_id WHERE o_id = ?";
        String[] receiptHeaders = {"Order ID", "Customer Name", "Book Title", "Author", "Genre", "Quantity", "Price", "Total Due", "Received Cash", "Order Date"};
        String[] receiptColumns = {"o_id", "c_fname", "b_title", "b_author", "b_genre", "o_quantity", "b_price", "o_due", "o_rcash", "o_date"};

        System.out.println("-------------------------------------- RECEIPT --------------------------------------");
        conf.viewSingleRecord(receiptQuery, receiptHeaders, receiptColumns, orderId);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("Thank you for your purchase!");
    }
}
