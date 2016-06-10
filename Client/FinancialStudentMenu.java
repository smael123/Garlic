package Client;


import sharedClasses.Student;
import sharedClasses.Scholarship;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinancialStudentMenu extends FinancialMenu
{ 

    FinancialStudentMenu(Student student)
    {
        this.student = student;
        initializeEligableScholarships();
    }
    public void displayMenu()
    {
        Scanner keyboard = new Scanner(System.in); 
        
        int choice = -1;
        while (choice != 0)
        { 
            System.out.println("\nFinancial Aid"); 
           
            System.out.println("[1] View Your Scholarships");
            System.out.println("[2] Apply for a Scholarship");
            System.out.println("[3] View applied scholarships");
            //System.out.println("[3] View FAFSA/TASFA");
            System.out.println("[0] Exit Program");
            try
            {
                choice = keyboard.nextInt();
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Bad Input");
                continue; //get to the end of the while loop
            }

            switch(choice)
            {
                case 1: 
                   viewEligableScholarships();
                   break;
                case 2:
                    applyForScholarship();
                    break;
                case 3:
                    viewAppliedScholarships();
                    break;
                case 0: 
                    System.out.println("Program now terminating.");
                    break;
            }
        }
    }
    
    //outsource to server app
    public void initializeEligableScholarships()
    {
        try {
            final Socket sock = new Socket(inputServerName(), 8767);
            final ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
            final ObjectInputStream input = new ObjectInputStream(sock.getInputStream());
            //Object[] arr = {3, student};
            output.writeObject(student);
            student.eligibleScholarships = (ArrayList<Scholarship>)input.readObject();
            output.close();
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(FinancialStudentMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FinancialStudentMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void viewEligableScholarships()
    {
        if (student.eligibleScholarships.isEmpty()) //if there is nothing in eligable scholarships
        {
            System.out.println("We are sorry but you do not qualify for any scholarships.");
            return;
        }
        
        int i = 1;
        
        for (Scholarship schol: student.eligibleScholarships)
        {
            System.out.printf("[%d] %s\n\n", i++, schol.toString());
        }
    }
    
    
    public void applyForScholarship()
    {
        if (student.eligibleScholarships.isEmpty()) //if there is nothing in eligable scholarships
        {
            System.out.println("We are sorry but you do not qualify for any scholarships.");
        }
        else
        {
            for (Scholarship schol: student.eligibleScholarships)
            {
                Scanner scan = new Scanner(System.in);
                int choice = -1;
                int i = 1;
                
                while (choice != 0)
                {
                    viewEligableScholarships();
                    System.out.printf("[0] Quit");
                    System.out.println("\nWhich scholarship do you want to apply to?");
                    choice = scan.nextInt();
                    if (choice == 0)
                    {
                        break;
                    }
                    else
                    {
                        student.appliedScholarships.add(student.eligibleScholarships.get(choice - 1));
                        System.out.println("Congratulations! You applied to:");
                        System.out.println(student.eligibleScholarships.get(choice - 1).toString());

                        System.out.println(student.eligibleScholarships.get(choice - 1).reward + "has been added to your balance.");
                        student.balance += student.eligibleScholarships.get(choice - 1).reward;
                        updateServerBalance();
                    } 
                }
            }
        }
        
    }
    public void viewAppliedScholarships()
    {
        if (student.appliedScholarships.isEmpty()) //if there is nothing in eligable scholarships
        {
            System.out.println("You have not applied for any scholarships.");
            return;
        }
        
        int i = 1;
        
        for (Scholarship schol: student.appliedScholarships)
        {
            System.out.printf("[%d] %s\n", i++, schol.toString());
        }
    }
}