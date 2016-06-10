package garlicserver;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.net.ServerSocket;  // The server uses this to bind to a port
import java.net.Socket;        // Incoming connections are represented as sockets
import java.util.ArrayList;
import sharedClasses.Scholarship;

public abstract class ScholarshipThread extends Thread
{
    public ArrayList<Scholarship> scholarshipList;
    Socket sock;
    String serialDirectory;
    
    ScholarshipThread(Socket sock)
    {
        this.sock = sock;
        this.serialDirectory = "scholarships.es";
        
        //deserializeList();
    }
 
    public abstract void run();
    
    public void serializeList()
    {
        //serialize userList
        try
        {
            String serialFileName = "scholarships.es";
            FileOutputStream fileOut = new FileOutputStream(serialFileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(scholarshipList);
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
        try
        {
            //deserialize
            FileInputStream fileIn = new FileInputStream("scholarships.es");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            scholarshipList = (ArrayList<Scholarship>)in.readObject();
            in.close();
            fileIn.close();
        }
        catch(IOException i)
        {
            i.printStackTrace();
            return;
        }
        catch(ClassNotFoundException c)
        {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
    }
}