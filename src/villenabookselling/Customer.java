package villenabookselling;

import java.util.Scanner;


public class Customer {
    
    public void cTransaction(){
        
        Scanner sc = new Scanner (System.in);
        String response;
        do{
            
       
        System.out.println("Welcome Customer!");    
        System.out.println("1. Add customer");
        System.out.println("2. View customer");
        System.out.println("3. Update customer");
        System.out.println("4. Delete customer");
        System.out.println("5. Exit. ");
        
        System.out.println("Enter Action: ");
        int action = sc.nextInt();
        Customer ct = new Customer ();
        

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
        System.out.println("Enter the ID to update: ");
        int id = sc.nextInt();
  
        while(conf.getSingleValue("SELECT c_id FROM tbl_customer WHERE c_id = ?", id) == 0){
        System.out.println("Selected ID doesn't exist!");
        System.out.print("Select Customer ID Again: ");
        id = sc.nextInt();
        }
        
        System.out.println("New First Name: ");
        String nfname = sc.next();
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
        System.out.println("Enter the ID  to delete: ");
        int id = sc.nextInt();
        
        while(conf.getSingleValue("SELECT c_id FROM tbl_customers WHERE c_id = ?", id) == 0){
        System.out.println("Selected ID doesn't exist!");
        System.out.print("Select Customer ID Again: ");
        id = sc.nextInt();
        }
        
        String qry = "DELETE FROM tbl_customer WHERE c_id = ?";
        
       
        conf.deleteRecord(qry, id);
    }
}