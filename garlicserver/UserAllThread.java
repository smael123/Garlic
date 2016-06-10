/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garlicserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sharedClasses.Student;
import sharedClasses.User;

/**
 *
 * @author lucy
 */
public class UserAllThread extends UserThread
{
    UserAllThread(Socket sock) //serial dierctory will be the eligable schoalrships
    {
        super(sock);
        
    }
    
    @Override
    public void run()
    {	    
        try 
        {
            deserializeList();
            
	    final ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
            final ObjectInputStream input = new ObjectInputStream(sock.getInputStream());
            
            StringBuffer str = new StringBuffer();
            
            int i = 0;
            for (User user: userList)
            {
                if (user.getClass() == Student.class)
                {
                    str.append(i++);
                    str.append(" ");
                    str.append(user.toString());
                    str.append("\n");
                }
                
            }
            output.writeObject(str);
            System.out.println("** Closing connection with " + sock.getInetAddress() + ":" + sock.getPort() + " **");
            sock.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ScholarshipAllThread.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
