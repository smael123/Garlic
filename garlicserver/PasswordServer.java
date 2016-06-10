/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garlicserver;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sharedClasses.AccountStaff;
import sharedClasses.FinancialAidStaff;
import sharedClasses.Scholarship;
import sharedClasses.Student;
import sharedClasses.User;

/**
 *
 * @author izzy
 */
public class PasswordServer 
{
//THIS SHOULD RUN FIRST TO INITIALIZE THE DATA STORED IN THE TXT FILES
    /**
     * @param args the command line arguments
     */
    
    public static final int SERVER_PORT = 8765;
    /*public static ArrayList<User> userList;
    public static ArrayList<Scholarship> scholarshipList;*/
    
    public static void main(String[] args) 
    {
        /*initializeScholarships();
        initializeUsers();
        ClientRequest cReq = new ClientRequest();*/
        
        try 
        {  
            final ServerSocket serverSock = new ServerSocket(SERVER_PORT);
            Socket sock = null;
            UserCheckPasswordThread checkPasswordThread = null;
            initializeUsers();
            initializeScholarships();
   
            while (true)
            {
                sock = serverSock.accept();
                System.out.println("Accepted connection");
                checkPasswordThread = new UserCheckPasswordThread(sock);
                checkPasswordThread.start();
                //accept an array of 2 Objects, downcast the first as Integer and downcast the second as whatever you need
                /*final ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
                final ObjectInputStream input = new ObjectInputStream(sock.getInputStream());
                Object[] arr = (Object[])input.readObject();
                int request = (Integer)(arr[0]);
                Object obj = arr[1];
                System.out.println(request);
                //output.reset();*/

                /*switch (request)
                {
                    case 2:
                        System.out.println("all scholarships");
                        allScholarshipsThread = new ScholarshipAllThread(sock, "scholarships.es", scholarshipList);
                        allScholarshipsThread.start();
                        break;
                    case 3:
                        System.out.println("eligable scholarships");
                        eligableScholarshipsThread = new ScholarshipEligableThread(sock, "scholarships.es", scholarshipList);
                        eligableScholarshipsThread.start();
                        break;
                    case 5:
                        System.out.println("password");
                        checkPasswordThread = new UserCheckPasswordThread(sock, userList);
                        checkPasswordThread.start();
                        break;
                    default:
                        System.out.println("Nothing");
                        
                        
                        break; //do nothing NONE
                }
            }*/
        } 
        
    }   catch (IOException ex) {
            Logger.getLogger(PasswordServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    public static void initializeScholarships()
    {
        //initialize scholarshipList from scholarships.txt
        ArrayList<Scholarship> scholarshipList = new ArrayList<>();
        String fileName = "scholarships.txt";
        
        try 
        {
            //name, gpa, major, minor, reward
            BufferedReader inputStream = new BufferedReader(new FileReader(fileName));
            String line;
            String[] scholarshipInfo;
                      
            while ((line = inputStream.readLine()) != null) 
            {
                //name:gpa:major:minor:reward
                scholarshipInfo = line.split(":");
                scholarshipList.add(new Scholarship(scholarshipInfo[0], Float.valueOf(scholarshipInfo[1]), scholarshipInfo[2], scholarshipInfo[3], Double.valueOf(scholarshipInfo[4])));
            }
                    
            inputStream.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.out.printf("File %s was not found", fileName);
            System.out.println("or could not be opened.");
        } catch (IOException ex) { 
            Logger.getLogger(PasswordServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //serialize scholarshipList
        try
        {
            String serialFileName = "scholarships.es";
            FileOutputStream fileOut = new FileOutputStream(serialFileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            out.writeObject(scholarshipList);

            out.close();
            fileOut.close();
            System.out.printf("scholarhips were serialized in %s\n", serialFileName);
        } 
        catch(IOException i)
        {
            i.printStackTrace();
        }
    }
    public static void initializeUsers()
    {
        ArrayList<User> userList  = new ArrayList<>();
        String fileName  = "users.txt";
        
        try 
        {
            BufferedReader inputStream = new BufferedReader(new FileReader(fileName));
            String line;
            String[] userInfo;

            //2- Loop over the file using BufferedReader object and the readLine() method, which will read
            // a single line in the text file at a time and return it in a String object
            while ((line = inputStream.readLine()) != null) 
            {
                //3- Put your line processing code here - this code just prints out the line read from file
                //fname:lname:usrName:passWord:major:minor:GPA
                userInfo = line.split(":");

                if (userInfo[0].equals("S"))
                { 
                    userList.add(new Student(userInfo[1], userInfo[2], userInfo[3], userInfo[4], userInfo[5], userInfo[6], Float.valueOf(userInfo[7])));
                }
                else if (userInfo[0].equals("F"))
                {
                    userList.add(new FinancialAidStaff(userInfo[1], userInfo[2], userInfo[3], userInfo[4]));
                }
                else if (userInfo[0].equals("A"))
                {
                    //add accounting staff object to Users
                    userList.add(new AccountStaff(userInfo[1], userInfo[2], userInfo[3], userInfo[4]));
                }
                else
                {
                    System.out.println("Unrecognized line.");
                }
            }
            //4- Close your BufferedReader stream
            inputStream.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.out.printf("File %s was not found", fileName);
            System.out.println("or could not be opened.");
        } 
        catch (IOException e) 
        {
            System.out.println("Error reading from " + fileName);
        }
        //serialize userList
        try
        {
            String serialFileName = "users.es";
            FileOutputStream fileOut = new FileOutputStream(serialFileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(userList);
            out.close();
            fileOut.close();
            System.out.printf("users were serialized in %s\n", serialFileName);
        } 
        catch(IOException i)
        {
            i.printStackTrace();
        }
    }
    
}
