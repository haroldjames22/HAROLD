
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
        System.out.println("4. EXIT");
        
        System.out.print("Enter Action: ");
        int act = sc.nextInt();
        
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
