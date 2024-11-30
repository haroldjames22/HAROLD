package villenabookselling;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Order {
    
    public void orderTransaction(){
        
        Scanner sc = new Scanner (System.in);
        String response = "yes";
        int action = -1;
        Order or = new Order ();
        
        do{
        System.out.println("Welcome to orders!"); 
        System.out.println("");
        System.out.println("1. Add order");
        System.out.println("2. View order");
        System.out.println("3. Update order");
        System.out.println("4. Delete order");
        System.out.println("5. Exit. ");
        
        System.out.println("Enter Action: ");
        try {
                action = sc.nextInt(); 
            } catch (InputMismatchException e) {
                System.out.println("Invalid action, Please enter a numeric action.");
                sc.nextLine();
                continue; 
            }

            if (action < 1 || action > 5) {
                System.out.println("Invalid action, Please enter a number between 1 to 5.");
                continue; 
            }
        

        switch(action){
            case 1:
                or.addOrders(); 
                or.viewOrders();
                break;
            case 2:       
                or.viewOrders();
                break;
            case 3:
                or.viewOrders();
                or.updateOrders();
                or.viewOrders();
                break;
            case 4:
                or.viewOrders();
                or.deleteOrders();
                or.viewOrders();
                break;
        }
        System.out.println("Do you want to continue? (yes/no)");
        response = sc.next();
    }while(response.equalsIgnoreCase("yes"));
    System.out.println("Thank You, See you soonest!");
    
    }
    
    
    public void addOrders(){
        Scanner sc = new Scanner (System.in);   
        config conf = new config(); 
        Customer ct = new Customer ();
        ct.viewCustomers();
        
        System.out.print("Enter the ID of the Customer: ");
        int cid = sc.nextInt();
        
        String csql = "SELECT c_id FROM tbl_customer WHERE c_id = ?";
        while(conf.getSingleValue(csql, cid) == 0){
            System.out.print("Customer does not exist, Select Again: ");
            cid = sc.nextInt();
            
        }
        Books bk = new Books ();
        bk.viewBooks();
        
        System.out.print("Enter the ID of the Book: ");
        int bid = sc.nextInt();
        
        String bsql = "SELECT b_id FROM tbl_books WHERE b_id = ?";
        while(conf.getSingleValue(bsql, bid) == 0){
            System.out.print("Customer does not exist, Select Again: ");
            bid = sc.nextInt();
            
        }
       String stockQuery = "SELECT b_stockquantity FROM tbl_books WHERE b_id = ?";
        double stock = conf.getSingleValue(stockQuery, bid);
        
        double quantity;
        if (stock == 0) {
            System.out.println("Book is out of stock and not available for ordering.");
            quantity = 0;

            
            String updateStatusQuery = "UPDATE tbl_books SET b_status = 'Not Available' WHERE b_id = ?";
            conf.updateRecord(updateStatusQuery, bid);
        } else {
            System.out.print("Enter Quantity: ");
            quantity = sc.nextDouble();
            
            while (quantity > stock) {
                System.out.println("Requested quantity exceeds available stock. Available stock: " + stock);
                System.out.print("Enter Quantity: ");
                quantity = sc.nextDouble();
            }
        }
        
        String priceqry = "SELECT b_price FROM tbl_books WHERE b_id = ?";
        double price = conf.getSingleValue(priceqry, bid);
        double due = price * quantity;
        
        System.out.println("---------------------------");
        System.out.println("Total Due: "+due);
        System.out.println("---------------------------");

        System.out.println("");
        
        System.out.println("Enter the received cash: ");
        double rcash = sc.nextDouble();
        
        while(rcash < due){
            System.out.println("Invalid Amount, Try Again: ");
            rcash = sc.nextDouble();
        }
        sc.nextLine();
        System.out.print("Order Date: ");
        String date = sc.nextLine();
        
        String status = (quantity == 0) ? "Not Available" : "Pending";

        
        String qry = "INSERT INTO tbl_orders (c_id, b_id, o_quantity, o_due, o_rcash, o_date, o_status)"
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        conf.addRecord(qry, cid, bid, quantity, due, rcash, date, status);
        
    }
    private void updateOrders() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        while (true) {
            System.out.print("Enter the ID to delete: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Order ID: ");
            sc.next();
        }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT o_id FROM tbl_orders WHERE o_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid ID.");
                sc.nextLine(); 
            }
        }
        
        sc.nextLine();
        System.out.println("Enter New Order Date: ");
        String date = sc.nextLine();

        String qry = "UPDATE tbl_orders SET o_date = ? WHERE o_id = ?";
        conf.updateRecord(qry, date, id);
    }
    public void viewOrders() {
    String qry = "SELECT o_id, c_fname, b_title, b_author, b_genre, b_stockquantity, b_price, o_quantity, o_due, o_rcash, o_date, o_status FROM tbl_orders "
            + "LEFT JOIN tbl_customer ON tbl_customer.c_id = tbl_orders.c_id "
            + "LEFT JOIN tbl_books ON tbl_books.b_id = tbl_orders.b_id";

    String[] hrds = {"O_ID", "Customer", "Book Title", "Author", "Genre", "Stock" , "Price" , "Order Quantity", "Due", "Rcash", "Date", "Order Status"};
    String[] clms = {"o_id", "c_fname", "b_title", "b_author", "b_genre", "b_stockquantity" , "b_price" , "o_quantity", "o_due", "o_rcash", "o_date" ,"o_status"};
    config conf = new config();
    conf.viewRecords(qry, hrds, clms);
   }
    
  
    
    private void deleteOrders() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        while (true) {
            System.out.print("Enter the ID to delete: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Order ID: ");
            sc.next();
        }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT o_id FROM tbl_orders WHERE o_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid ID.");
                sc.nextLine(); 
            }
        }

        String qry = "DELETE FROM tbl_orders WHERE o_id = ?";
        conf.deleteRecord(qry, id);
    }
}

