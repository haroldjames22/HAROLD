
package villenabookselling;

import java.util.InputMismatchException;
import java.util.Scanner;

import java.util.Scanner;


public class VILLENABOOKSELLING {

    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);    
        boolean exit = true;
        do{
        System.out.println("WELCOME TO BOOK SELLING!");
        System.out.println("");
        System.out.println("1. CUSTOMER");
        System.out.println("2. BOOKS");
        System.out.println("3. ORDERS");
        System.out.println("4. VIEW REPORTS");
        System.out.println("5. EXIT");
        System.out.println("");
        System.out.print("Enter Action: ");
        
        int act = -1; 

            try {
                act = sc.nextInt(); 
            } catch (InputMismatchException e) {
               
                System.out.println("Invalid action, Please enter a numeric action.");
                sc.nextLine(); 
                continue; 
            }

            if (act < 1 || act > 5) {
                System.out.println("Invalid action, Please enter a number between 1 to 5.");
                continue; 
            }

        
        switch(act){
            case 1:
                Customer ct = new Customer ();
                ct.cTransaction();
            break;
            
            case 2:
                Books bk = new Books ();
                bk.bTransaction();
            break;
            
            case 3:
                Order or = new Order ();
                or.orderTransaction();
            break;
            case 4:
                Reports rp = new Reports();
                rp.reportsTransaction();
                
            case 5:
                System.out.println("you sure??? (yes/no): ");
                String resp = sc.next();
                    if(resp.equalsIgnoreCase("yes")){
                           exit = false;
                }
            break;   
            
        }
        }while (exit);
        System.out.print("See youu!");               
    
    }} 
