package Client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner; 
import java.util.logging.Level;
import java.util.logging.Logger;
import sharedClasses.Student;
/**
 *
 * @author Fernando Rodarte.  
 */
public class AccountStudentMenu extends AccountMenu{ 
    public AccountStudentMenu(Student student)
    {
        this.student = student; 
    }
    
    @Override
    public void displayMenu()
    {
        Scanner keyboard = new Scanner(System.in); 
        
        int choice = -1; 
            while(choice != 0)
            {
               System.out.println("\n\t Accounting Menu");   
               System.out.println("\n[1] Pay Tuition"+ "\n[2] View Future Tuition"
                                  + "\n[3] Pay Holds" + "\n[4] Pay for Testing"
                                  + "\n[5] Request Parking" + "\n[6] View Balance" + "\n[0] Exit Program"); 
               
               System.out.println("Please enter your option: "); 
                
               
               // Prevent Invalid Inputs. 
               try
               {
                   choice = keyboard.nextInt(); 
               }
               
               catch(IllegalArgumentException e)
               {
                   System.out.println("Invalid Input."); 
                   continue; 
               }
               
               // Go through features. 
               switch(choice)
               {
                   case 1:
                       //System.out.println("payTution");
                       payTuition(); 
                       break; 
                       
                   case 2: 
                       viewFutureTuition(); 
                       break; 
                       
                   case 3: 
                       payHold(); 
                       break; 
                   
                   case 4:
                       payForTesting(); 
                       break; 
                       
                   case 5:
                       payForParking(); 
                       break;
                   case 6:
                       viewBalance();
                       break;
                       
                   default: 
                       System.out.println("Program now terminating.");
               }
            }
        }
    
    public void payTuition()
    { 
        Scanner keyboard = new Scanner(System.in); 
       
        // Prompt user how many hours they are taking this semester. 
        System.out.println("How many credit hours are you taking this semester: "); 
        int hours = keyboard.nextInt(); 
        
        // Calculate the total tuition fees. 
        double tuitionTotal = 193*hours+574; // This was taken from UHD tuition info. 
        
        // Set possible situations. 
        if(student.balance >= tuitionTotal)
        {
            
                student.balance = student.balance - tuitionTotal;
                System.out.println("\n You have paid your tuition fees.");
                System.out.println("\nThis is the remaining balance: " + student.balance);
                updateServerBalance();
        }
        
        else
        {
            System.out.println("You don't have the funds to pay the tuition.");
            System.out.println("Would you like to make an installment payment plan?"
                               + " [Y] or [N]"); 
            String choice = " "; 
            
            while(!"N".equals(choice))
            {
               //choice = keyboard.nextLine(); 
               
               // Prevent Invalid Inputs. 
               try
               {
                   choice = keyboard.next(); //changed from nextLine to next (nextLine skips stuff)
               }
               
               catch(IllegalArgumentException e)
               {
                   System.out.println("Invalid Input."); 
               }
               
               switch(choice)
               {
                   case "Y": 
                       // Prompt user in how many months. 
                       System.out.println("In how many months would you like to pay: ");
                       int numberOfMonths = keyboard.nextInt(); 
                       
                       // Calculate the tuition + cashier's fee for installment plan. 
                       double paymentPlan = tuitionTotal/ numberOfMonths; 
                       
                       System.out.println("Your first payment will be $" + (paymentPlan + 25)); 
                       System.out.println("\n$25 was charged from the Cashier's Office as a fee for the plan."); 
                       System.out.println("\nThe next payments will be of $" + paymentPlan); 
                       System.out.println("\nPlease stop by the Cashier's Office to sign the contract."); 
                       break; 
                       
                   case "N": 
                       System.out.println("Please make proper arragements to pay your tuition "
                                          + "before the semester begins."); 
               }
               break; //Added this because you should only ask this question once
            }
        }
    }
    
    public void viewFutureTuition()
    {
        Scanner keyboard = new Scanner(System.in); 
        final double RATE = 1.05; 
        System.out.println("How many hours are going to take per semester? "); 
        int hours = keyboard.nextInt(); 
        double tuitionTotal = 0.0; 
        
        // Calculate current total for 2 semesters. 
        if(hours >= 12)
        {
            tuitionTotal = 2*(183*hours+562); 
        }
        else if(hours < 12 && hours  != 1)
        {
            tuitionTotal = 2*((hours-1)*249+299); 
        }
        else if(hours == 1)
        {
            tuitionTotal = 299; 
        }
        
        // This is only for a 5% in tuition fees over the years. You can modify this.  
        double geometric_seq = (1-Math.pow(RATE, 4)) / (1 - RATE);
        System.out.println("Total Cost of 4 Year Tution");

        for (int i = 0; i < 10; i++)
        {
            double futureTuition = tuitionTotal*geometric_seq; 

            System.out.println((i+1)+ " "+ "year from now: "+ futureTuition);
            
            // Increase tuition rate by 5%. 
            tuitionTotal = tuitionTotal * 1.05;
        }
        
    }
    
    public void payHold()
    {
        Scanner keyboard = new Scanner(System.in); 
        System.out.println("What type of hold do you have?"); 
        System.out.println("\n[L] Library, [T] Tuition, [P] Parking"); 
        String choice = keyboard.next(); //changed from nextLine to next (nextLine skips stuff)
        
        if(null != choice)
        switch (choice) {
            case "L":{
                System.out.println("You did not pay for a lost book.");
                System.out.println("The fee is $25");
                System.out.println("Would you like to pay it? [Y] or [N]");
                String choice2 = keyboard.next(); //changed from nextLine to next (nextLine skips stuff)
                if("Y".equals(choice2))
                {
                    if(student.balance >= 25)
                    {
                        student.balance = student.balance - 25;
                        System.out.println("Thank you for paying your hold.");
                        System.out.println("Your new balance is " + student.balance);
                        updateServerBalance();
                    }
                    else
                        System.out.println("You don't have enough money.");
                }
                else
                {
                    System.out.println("Please make sure to pay this fee before the semester begins.");
                }       break; 
                }
            case "T":{
                System.out.println("You did not pay your tuition.");
                System.out.println("The fee is $2000");
                System.out.println("Would you like to pay it? [Y] or [N]");
                String choice2 = keyboard.next(); //changed from nextLine to next (nextLine skips stuff)
                if("Y".equals(choice2))
                {
                    if(student.balance >= 2000)
                    {
                        student.balance = student.balance - 2000;
                        System.out.println("Thank you for paying your hold.");
                        System.out.println("Your new balance is " + student.balance);
                        updateServerBalance();
                    }
                    else
                        System.out.println("You don't have enough money.");
                }
                else
                {
                    System.out.println("Please make sure to pay this fee before the semester begins.");
                }       break; 
                }
            case "P":{
                System.out.println("You did not pay your parking for this semester.");
                System.out.println("The fee is $400");
                System.out.println("Would you like to pay it? [Y] or [N]");
                String choice2 = keyboard.next(); //changed from nextLine to next (nextLine skips stuff)
                if("Y".equals(choice2))
                {
                    if(student.balance >= 400)
                    {
                        student.balance = student.balance - 400;
                        System.out.println("Thank you for paying your hold.");
                        System.out.println("Your new balance is " + student.balance);
                        updateServerBalance();
                    }
                    else
                        System.out.println("You don't have enough money.");
                }
                else
                {
                    System.out.println("Please make sure to pay this fee before the semester begins.");
                }       break;
                }
            default:
                break;
        }
    }
    
    public void payForTesting()
    {
        // Display the current date. 
        int day, month, year; 
        GregorianCalendar currentDate = new GregorianCalendar();
        day = currentDate.get(Calendar.DAY_OF_MONTH);
        month = currentDate.get(Calendar.MONTH)+1;   // Gregorian months 0-11. 
        year = currentDate.get(Calendar.YEAR); 
        
        Scanner keyboard = new Scanner(System.in); 
        System.out.println("\n\tTesting"); 
        System.out.println("\nThe testing center offers math, reading and writing exams "
                + "for incoming students");
        System.out.println("Would you like to take the exams at UHD?");
        System.out.println("The fee is $40");
        System.out.println("Would you like to pay it? [Y] or [N]");
        String choice2 = keyboard.next(); //changed from nextLine to next (nextLine skips stuff)
        
        if(null != choice2)
            switch (choice2) {
            case "Y":
                if(student.balance >= 40)
                {
                    student.balance = student.balance - 40;
                    System.out.println("Thank you for paying.");
                    System.out.println("Your new balance is " + student.balance);
                    updateServerBalance();
                    System.out.println("Today is: " + currentDate.getTime()); 
                    System.out.println("When would you like to take the exam?");
                    System.out.println("Month: ");
                    int month_2 = keyboard.nextInt();
                    System.out.println("Day: ");
                    int day_2 = keyboard.nextInt();
                    System.out.println("Year: ");
                    int year_2 = keyboard.nextInt();
                    
                    // Pass args through Constructor. 
                    GregorianCalendar newDate = new GregorianCalendar(year_2, (month_2-1), day_2); 
                    System.out.println("Your test is on " + newDate.getTime()); 
                }
                else
                    System.out.println("You don't have enough money.");
                break;
            case "N":
                System.out.println("Thank you.");
                break;
            default: 
                System.out.println("Invalid Input.");
                break;
        } 
    }
    
    public void payForParking()
    {
        Scanner keyboard = new Scanner(System.in); 
        System.out.println("\n\tParking"); 
        System.out.println("\nStudents must obtain a parking permit to park at UHD parking areas."); 
        System.out.println("Would you like to purchase the parking permit?");
        System.out.println("The fee is $400");
        System.out.println("Would you like to pay it? [Y] or [N]");
        String choice2 = keyboard.next();//changed from nextLine to next (nextLine skips stuff)
        
        if(null != choice2)
            switch (choice2) {
            case "Y":
                if(student.balance >= 400)
                {
                    student.balance = student.balance - 400;
                    System.out.println("Thank you for paying.");
                    System.out.println("Your new balance is " + student.balance);
                    updateServerBalance();
                }
                else
                    System.out.println("You don't have enough money.");
                break;
            case "N":
                System.out.println("Thank you.");
                break;
            default: 
                System.out.println("Invalid Input.");
                break;
        } 
    }
    public void viewBalance()
    {
        System.out.printf("Your balance is: %f\n", student.balance);
    }
}