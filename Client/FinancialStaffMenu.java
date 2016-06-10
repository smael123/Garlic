package Client;


import sharedClasses.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sharedClasses.Scholarship;
import sharedClasses.Student;

class FinancialStaffMenu extends FinancialMenu
{
    public void displayMenu()
    {
        //student = chooseStudent();
        Scanner keyboard = new Scanner(System.in);

        int choice = -1;
        while (choice != 0)
        {
            System.out.println("\n\t Financial Aid"); 
            System.out.println("\n[1] View Scholarships\n[2] Create a new Scholarship\n[0] Exit Program");
            choice = keyboard.nextInt();
            switch(choice)
            {
                case 1: 
                    viewAllScholarships(); 
                    break; 
                case 2: 
                    createNewScholarship();
                    break;
                default: 
                    System.out.println("Program now terminating."); 
                    break;
            }
        }
    }	
    public void createNewScholarship()
    {
        try {
            Scanner scan = new Scanner(System.in);
            
            String name; //name of scholarship
            String requiredMajor;
            String requiredMinor;
            float requiredGPA;
            double reward;
            
            System.out.println("Put nothing if no requirement. BUT for the GPA type 0\n");
            System.out.println("Name: ");
            name = scan.nextLine();
            System.out.println("Reward: ");
            reward = scan.nextDouble();
            scan.nextLine();
            System.out.println("Required Major: ");
            requiredMajor = scan.nextLine();
            System.out.println("Required Minor: ");
            requiredMinor = scan.nextLine();
            System.out.println("Required GPA: ");
            requiredGPA = scan.nextFloat();
            
            //create a scholarship and send it to the server so it can be serialized
            //(String name, float requiredGPA, String requiredMajor, String requiredMinor, double reward)
            Scholarship pack = new Scholarship(name, requiredGPA, requiredMajor, requiredMinor, reward);
            final Socket sock = new Socket(inputServerName(), 8770);
            final ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
            final ObjectInputStream input = new ObjectInputStream(sock.getInputStream());
            output.writeObject(pack); //move deserialization out of the constructor
            
        } catch (IOException ex) {
            Logger.getLogger(FinancialStaffMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void viewAllScholarships()
    {
        try 
        {
            StringBuffer clientStr;
            
            final Socket sock = new Socket(inputServerName(), 8766);
            final ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
            final ObjectInputStream input = new ObjectInputStream(sock.getInputStream()); //lets recieve the string of all the scholarships
            
            //output.writeObject(null); //maybe i do need to write something at least
            clientStr = (StringBuffer)input.readObject();
            System.out.println(clientStr);
            output.close();
            input.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FinancialStudentMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FinancialStaffMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //request ScholarshipAllThread
    }
    public Student chooseStudent()
    {
        try {
            viewAllStudents();
            int choice;
            Scanner scan = new Scanner(System.in);
            System.out.println("Pick a student by number: ");
            choice = scan.nextInt();
            final Socket sock = new Socket(inputServerName(), 8769);
            final ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
            final ObjectInputStream input = new ObjectInputStream(sock.getInputStream());
            output.writeObject(choice);
            Student stu = (Student)input.readObject();
            output.close();
            input.close();
            return stu;
            
           
        } catch (IOException ex) {
            Logger.getLogger(FinancialStaffMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FinancialStaffMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void viewAllStudents()
    {
        try 
        {
            StringBuffer clientStr;
            
            final Socket sock = new Socket(inputServerName(), 8768);
            final ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
            final ObjectInputStream input = new ObjectInputStream(sock.getInputStream()); //lets recieve the string of all the scholarships
            

            /*Object[] arr = {4, null};
            output.writeObject(arr);*/
            clientStr = (StringBuffer)input.readObject();
            System.out.println(clientStr);
            output.close();
            input.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FinancialStudentMenu.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(FinancialStaffMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}