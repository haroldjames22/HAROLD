
package villenabookselling;

import java.util.Scanner;


public class Books {
    
    public void bTransaction(){
        
        Scanner sc = new Scanner (System.in);
        String response;
        do{
            
       
        System.out.println("Welcome to Books!");    
        System.out.println("1. Add Books");
        System.out.println("2. View Books");
        System.out.println("3. Update Books");
        System.out.println("4. Delete Books");
        System.out.println("5. Exit. ");
        
        System.out.println("Enter Action: ");
        int action = sc.nextInt();
        Books bk = new Books ();
        

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
        double sq = sc.nextDouble();
        System.out.print("Author: ");
        String auth = sc.next();
        System.out.print("Genre: ");
        String genre = sc.next();

        String sql = "INSERT INTO tbl_books (b_title, b_price, b_stockquantity, b_author, b_genre) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, title, pr, sq, auth, genre);


    }

    public void viewBooks() {
        config conf = new config();
        String Query = "SELECT * FROM tbl_books";
        String[] Headers = {"Books_ID","Title", "Price", "StockQuantity", "Author", "Genre"};
        String[] Columns = {"b_id", "b_title", "b_price", "b_stockquantity", "b_author", "b_genre"};
        
        
        conf.viewRecords(Query, Headers, Columns);
    }
    private void updateBooks() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.println("Enter the ID to update: ");
        int id = sc.nextInt();
        
        while(conf.getSingleValue("SELECT b_id FROM tbl_books WHERE b_id = ?", id) == 0){
        System.out.println("Selected ID doesn't exist!");
        System.out.print("Select Book ID Again: ");
        id = sc.nextInt();
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
        
        System.out.println("Enter the ID  to delete: ");
        int id = sc.nextInt();
        
        while(conf.getSingleValue("SELECT b_id FROM tbl_books WHERE b_id = ?", id) == 0){
        System.out.println("Selected ID doesn't exist!");
        System.out.print("Select Book ID Again: ");
        id = sc.nextInt();
        }
        
        String qry = "DELETE FROM tbl_books WHERE b_id = ?";
        
        
        conf.deleteRecord(qry, id);
    }
}

