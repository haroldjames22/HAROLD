package villenabookselling;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Order {
    
    public void orderTransaction(){
        
        Scanner sc = new Scanner (System.in);
        String response;
        do{
            
       
        System.out.println("---ORDERS---");    
        System.out.println("1. Add order");
        System.out.println("2. View order");
        System.out.println("3. Update order");
        System.out.println("4. Delete order");
        System.out.println("5. Exit. ");
        
        System.out.println("Enter Action: ");
        int action = sc.nextInt();
        Order or = new Order ();
        

        switch(action){
            case 1:
                or.addOrders(); 
                viewOrders();
                break;
            case 2:       
                viewOrders();
                break;
            case 3:
                
                break;
            case 4:
                  
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
        while(conf.getSingleValue(csql, bid) == 0){
            System.out.print("Customer does not exist, Select Again: ");
            bid = sc.nextInt();
            
        }
        System.out.println("Enter Quantity: ");
        double quantity = sc.nextDouble();
        
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
        LocalDate currdate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = currdate. format(format);

        String status = "Pending";
        
        String qry = "INSERT INTO tbl_orders (c_id, b_id, o_quantity, o_due, o_rcash, o_date, o_status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        conf.addRecord(qry, cid, bid, quantity, due, rcash, date, status);
        
    }
    
    public void viewOrders() {
    String qry = "SELECT o_id, c_fname, b_title, b_author, b_genre, b_stockquantity, b_price, o_quantity, o_due, o_date, o_status FROM tbl_orders "
            + "LEFT JOIN tbl_customer ON tbl_customer.c_id = tbl_orders.c_id "
            + "LEFT JOIN tbl_books ON tbl_books.b_id = tbl_orders.b_id";

    String[] hrds = {"O_ID", "Customer", "Book Title", "Author", "Genre", "Stock" , "Price" , "Order Quantity", "Due", "Date" , "Order Status"};
    String[] clms = {"o_id", "c_fname", "b_title", "b_author", "b_genre", "b_stockquantity" , "b_price" , "o_quantity", "o_due", "o_date" ,"o_status"};
    config conf = new config();
    conf.viewRecords(qry, hrds, clms);
   }

}