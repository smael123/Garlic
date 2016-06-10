package Client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import Client.FinancialStaffMenu;
import Client.FinancialStudentMenu;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sharedClasses.AccountStaff;
import sharedClasses.FinancialAidStaff;
import sharedClasses.Student;
import sharedClasses.User;
import java.io.EOFException;

/**
 *
 * @author izzy
 */
public class Garlic {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) 
    {
        FinancialStaffMenu fstaffMenu;
        FinancialStudentMenu fstudentmenu;
        AccountStaffMenu astaffMenu;
        AccountStudentMenu astudentMenu;
        // TODO code application logic here
        
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Welcome to Garlic");
        //login
        while (true) //while true before
        {
            User markedUser = login();
            if (markedUser != null)
            {
                int choice = -1;

                if (markedUser.getClass() == Student.class)
                {
                    System.out.println("Welcome student.");
                     
                    choice = -1;

                    while (choice != 0)
                    {
                        displayDepartmentChoices();
                        choice = scan.nextInt();
                        switch(choice)
                        {
                            case 1:
                                astudentMenu = new AccountStudentMenu((Student)markedUser);
                                astudentMenu.displayMenu();
                                break;
                            case 2:
                                fstudentmenu = new FinancialStudentMenu((Student)markedUser);
                                fstudentmenu.displayMenu();
                                break;
                            default:
                                System.out.println("The program will now be terminating.");
                                break;
                        }
                    }
                }
                else if (markedUser.getClass() == FinancialAidStaff.class)
                {
                    System.out.println("Welcome financial staff.");
                   
                    fstaffMenu = new FinancialStaffMenu();
                    fstaffMenu.displayMenu();
                    System.out.println("Goodbye Financial Staff");     
                }		
                else if (markedUser.getClass() == AccountStaff.class)
                {
                    System.out.println("Welcome accounting staff.");
                    
                    astaffMenu = new AccountStaffMenu();
                    astaffMenu.displayMenu();
                    System.out.println("Goodbye Account Staff");     
                }
                else
                {
                    System.out.println("Goodbye!");
                    break;
                }
            }
            else
            {
                System.out.println("Wrong username/password");
                continue;
            }
        }
    }
    
    public static User login() //returns a single user
    {
        String userName; 
        String password; 
        Scanner keyboard = new Scanner(System.in);
        String serverName = "localhost";
        int choice = -1;
        

        // Display the menu. 
        System.out.println(
        "\n\t Welcome to Garlic 1.0" + "\n\nPlease Sign-in"); 
        System.out.println("Would You Like to Login?"); 
        System.out.println("\nEnter [1] or Quit [0]:");
        try
        {
            choice = keyboard.nextInt();

            switch(choice)
            {
                case 1:
                    // Check username and password. 
                    System.out.println("\nUsername: "); 
                    userName = keyboard.next();  
                    System.out.println("\nPassword: "); 
                    password = keyboard.next();

                    String[] strArr = new String[2];
                    strArr[0] = userName;
                    strArr[1] = password;

                    //BELOW IS NOT WORKING

                    final Socket sock = new Socket(serverName, 8765);
                    final ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
                    final ObjectInputStream input = new ObjectInputStream(sock.getInputStream());
                    //Object[] objArr = {5, strArr};
                    output.writeObject(strArr);
                    //output.reset();
                    User usr = (sharedClasses.User)input.readObject();
                    output.close();
                    input.close();
                    //return new Student("Yugi", "Moto", "kingofgames", "atem", "Art", "Theatre", 2.4f);
                    return usr;

                default:
                    System.out.println("Goodbye");
                    return null;
            }
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Invalid Input!");
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Garlic.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(Garlic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static void displayDepartmentChoices()
    {  
        System.out.println("What department would you like to access?\n");
        System.out.printf("%d: Accounting\n", 1);
        System.out.printf("%d: Financial Aid\n", 2);
        System.out.printf("%d: Quit\n", 0);
    }
    
    public static String inputServerName()
    {
        /*Scanner scan = new Scanner(System.in);
        System.out.println("Type the IP address of the server: ");
        String ipAddress = scan.next();*/
        return "localhost";        
    }

}