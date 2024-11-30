
package villenabookselling;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Books {
    
    public void bTransaction(){
        
        Scanner sc = new Scanner (System.in);
        String response = "yes";
        int action = -1;    
        Books bk = new Books ();
        do{
            
       
        System.out.println("Welcome to Books!");    
        System.out.println("");
        System.out.println("1. Add Books");
        System.out.println("2. View Books");
        System.out.println("3. Update Books");
        System.out.println("4. Delete Books");
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
                bk.addBooks();           
                break;
            case 2:       
                bk.viewBooks();
                break;
            case 3:
                bk.viewBooks();
                bk.updateBooks();
                bk.viewBooks();
                break;
            case 4:
                bk.viewBooks();
                bk.deleteBooks();
                bk.viewBooks();    
                break;
        }
        System.out.println("Do you want to continue? (yes/no)");
        response = sc.next();
    }while(response.equalsIgnoreCase("yes"));
    System.out.println("Thank You, See you soonest!");
    
    }
    
    
    public void addBooks(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Title: ");
        String title = sc.next();
        System.out.print("Price: ");
        double pr = sc.nextDouble();
        System.out.print("Stock Quantity: ");
        double stck = sc.nextDouble();
        System.out.print("Author: ");
        String auth = sc.next();
        System.out.print("Genre: ");
        String genre = sc.next();
        String stat;
        if (stck > 0) {
            stat = "Available";
        } else {
            stat = "Not Available";
        }

        String sql = "INSERT INTO tbl_books (b_title, b_price, b_stockquantity, b_author, b_genre, b_status) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, title, pr, stck, auth, genre, stat);


    }

    public void viewBooks() {
        config conf = new config();
        String Query = "SELECT * FROM tbl_books";
        String[] Headers = {"Books_ID","Title", "Price", "StockQuantity", "Author", "Genre", "Book Status"};
        String[] Columns = {"b_id", "b_title", "b_price", "b_stockquantity", "b_author", "b_genre", "b_status"};
        
        
        conf.viewRecords(Query, Headers, Columns);
    }
    private void updateBooks() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;
        while (true) {
            System.out.print("Enter the Book ID to update: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Book ID: ");
            sc.next();
        }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT b_id FROM tbl_books WHERE b_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric ID.");
                sc.nextLine(); 
            }
        }
        
        System.out.println("New Book Title: ");
        String nbtitle = sc.next();
        System.out.println("New Book Price: ");
        String nbpr = sc.next();
        System.out.println("New Stock Quantity: ");
        String nsq = sc.next();
        System.out.println("New Author: ");
        String nauth = sc.next();
        System.out.println("New Genre: ");
        String genre = sc.next();
        String qry = "UPDATE tbl_books SET b_title = ?, b_price = ?, b_stockquantity = ?, b_author = ?, b_genre = ? WHERE b_id = ?";
        
        
        conf.updateRecord(qry, nbtitle, nbpr, nsq, nauth, genre );         
        
        
    }
    
    private void deleteBooks() {
        Scanner sc = new Scanner (System.in);
        config conf = new config();
        
        int id;
        while (true) {
            System.out.print("Enter the Book ID to update: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Book ID: ");
            sc.next();
        }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT b_id FROM tbl_books WHERE b_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric ID.");
                sc.nextLine(); 
            }
        }
        
        String qry = "DELETE FROM tbl_books WHERE b_id = ?";
        
        
        conf.deleteRecord(qry, id);
    }
}

