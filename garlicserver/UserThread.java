package garlicserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import sharedClasses.FinancialAidStaff;
import sharedClasses.Student;
import sharedClasses.User;

public abstract class UserThread extends Thread
{
    public ArrayList<User> userList; //dpnt deseialize it here send it
    public Socket sock;

    UserThread(Socket sock)
    {
        this.sock = sock;
        //this.userList = userList;
        //deserializeList();
    }
    
    public abstract void run();
    
    /*public void initializeUsers()
    {
        userList  = new ArrayList<>();
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
                else if (userInfo.equals("F"))
                {
                    userList.add(new FinancialAidStaff(userInfo[1], userInfo[2], userInfo[3], userInfo[4]));
                }
                else
                {
                    //add accounting staff object to Users
                    //userList.add(new AccountingStaff(userInfo[1], userInfo[2], userInfo[3], userInfo[4]));
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
        
    }*/
    public void serializeList()
    {
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
    public void deserializeList()
    {
        //deserialize
        try
        {
            FileInputStream fileIn = new FileInputStream("users.es");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            userList = (ArrayList<User>)in.readObject();
            in.close();
            fileIn.close();
            
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }
        catch(ClassNotFoundException c)
        {
            System.out.println("Employee class not found");
            c.printStackTrace();
        }
    }
}