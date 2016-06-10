package Client;
import java.util.Scanner; 
/**
 *
 * @author Fernando R. 
 */
public class AccountStaffMenu extends AccountMenu{  
    @Override
    public void displayMenu()
    {
        Scanner keyboard = new Scanner(System.in); 
        
        int choice = -1; 
        while(choice != 0)
        {
            System.out.println("\n\t Staff Accounting Menu"); 
            System.out.println("\n[1] Change Base Tuition Rate" + 
                                   "\n[0] Exit Program"); 
            System.out.println("Please enter your option: "); 
            //choice = keyboard.nextInt(); 
               
            // Prevent Invalid Inputs. 
            try
            {
                choice = keyboard.nextInt(); 
            }
               
            catch(IllegalArgumentException e)
            {
                System.out.println("Invalid Input.");  
            }
            
            // Go through features. 
            switch(choice)
            {
                case 1: 
                    changeTuitionRate();  
                    break; 
                default: 
                    System.out.println("Program now terminating."); 
            }
        }
    }
    
    public void changeTuitionRate() //changed from viewFutureTution to this
    {
        final double originalRate = 1.0; 
        String option; 
        do
        {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("At what % rate will you increase tuition: "); 
        double RATE = keyboard.nextInt(); 
        RATE = (RATE/100)+1.0; 
        
        System.out.println("$ per hour: "); 
        double perHour = keyboard.nextDouble(); 
        System.out.println("$ for base: ");
        double baseRate = keyboard.nextDouble(); 
        
        System.out.println("How many hours per semester do you want to measure? "); 
        double hours = keyboard.nextDouble(); 
        
        // Calculate current total per semester with changes. 
        double changedTuitionTotal = perHour*hours+baseRate; 
        double geometric_seq_2 = (1-Math.pow(RATE, 4))/ (1-RATE);
        System.out.println("NEW Total Cost of 4 Year Tuition"); 
        
        for(int i = 0; i < 10; i++)
        {
            double futureTuitionChange = changedTuitionTotal*geometric_seq_2; 
            System.out.println((i+1) + " " + "year from now: " + futureTuitionChange); 
            
            // Increase tuition rate by whatever the staff entered. 
            changedTuitionTotal = changedTuitionTotal * RATE; 
        }
        
        // Calculate current total per semester without any changes. 
        double tuitionTotal = 193*hours+574; 
        
        // This is only for a 5% in tuition fees over the years. You can modify this.  
        double geometric_seq = (1-Math.pow(originalRate, 4)) / (1 - originalRate);
        System.out.println("Total Cost of 4 Year Tution");

        for (int i = 0; i < 10; i++)
        {
            double futureTuition = tuitionTotal*geometric_seq; 

            System.out.println((i+1)+ " "+ "year from now: "+ futureTuition);
            
            // Increase tuition rate by 0%.  
            tuitionTotal = tuitionTotal * 1.00;
        }
       
        System.out.println("Would you to try again? [N] No and [Y] Yes"); 
        option = keyboard.next(); 
    }while(!"N".equals(option)); 
}
}