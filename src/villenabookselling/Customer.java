package villenabookselling;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Customer {
    
    public void cTransaction(){
        
        Scanner sc = new Scanner (System.in);
        String response = "yes";
        int action = -1;  
        Customer ct = new Customer ();
        do{
            
       
        System.out.println("Welcome Customer!");   
        System.out.println("");
        System.out.println("1. Add customer");
        System.out.println("2. View customer");
        System.out.println("3. Update customer");
        System.out.println("4. Delete customer");
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
                ct.addCustomers();        
                break;
            case 2:       
                ct.viewCustomers();
                break;
            case 3:
                ct.viewCustomers();
                ct.updateCustomers();
                ct.viewCustomers();
                break;
            case 4:
                ct.viewCustomers();
                ct.deleteCustomers();
                ct.viewCustomers();    
                break;
        }
        System.out.println("Do you want to continue? (yes/no)");
        response = sc.next();
    }while(response.equalsIgnoreCase("yes"));
    System.out.println("Thank You, See you soonest!");
    
    }
    
    
    public void addCustomers(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("First Name: ");
        String fname = sc.nextLine();
        System.out.print("Last Name: ");
        String lname = sc.next();
        System.out.print("Email: ");
        String email = sc.next();
        System.out.print("Contact: ");
        String cont = sc.next();

        String sql = "INSERT INTO tbl_customer (c_fname, c_lname, c_email, c_contact) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, email, cont);


    }

    public void viewCustomers() {
        config conf = new config();
        String Query = "SELECT * FROM tbl_customer";
        String[] Headers = {"Customers_ID","FirstName", "LastName", "Email", "Contact"};
        String[] Columns = {"c_id", "c_fname", "c_lname", "c_email", "c_contact"};
        
        
        conf.viewRecords(Query, Headers, Columns);
    }
    private void updateCustomers() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;
        while (true) {
            System.out.print("Enter the Customer ID to update: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Customer ID: ");
            sc.next();
        }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT c_id FROM tbl_customer WHERE c_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric ID.");
                sc.nextLine(); 
            }
        }
        
        System.out.println("New First Name: ");
        String nfname = sc.nextLine();
        sc.nextLine();
        System.out.println("New Last Name: ");
        String nlname = sc.next();
        System.out.println("New Email: ");
        String nemail = sc.next();
        System.out.println("New Contact: ");
        String ncont = sc.next();
        String qry = "UPDATE tbl_customer SET c_fname = ?, c_lname = ?, c_email = ?, c_contact = ? WHERE c_id = ?";
        
        
        conf.updateRecord(qry, nfname, nlname, nemail, ncont, id);         
        
        
    }
    
    private void deleteCustomers() {
        Scanner sc = new Scanner (System.in);
        config conf = new config();
        int id;
        while (true) {
            System.out.print("Enter the Customer ID to update: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Customer ID: ");
            sc.next();
        }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT c_id FROM tbl_customer WHERE c_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric ID.");
                sc.nextLine(); 
            }
        }
        
        String qry = "DELETE FROM tbl_customer WHERE c_id = ?";
        
       
        conf.deleteRecord(qry, id);
    }
}