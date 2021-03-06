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
import java.util.logging.Level;
import java.util.logging.Logger;
import sharedClasses.Student;
import sharedClasses.User;

/**
 *
 * @author lucy
 */
public class StudentUpdateThread extends UserThread{
    StudentUpdateThread(Socket sock)
    {
        super(sock);
    }
    public void run()
    {
        
        try 
        {
            deserializeList();
            
        
        final ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
        final ObjectInputStream input = new ObjectInputStream(sock.getInputStream());
            
        User updatedusr = (User)input.readObject();
        for (User usr: userList)
        {

                if (updatedusr.firstName.equals(usr.firstName))
                {
                    usr.overwriteBalance(updatedusr);
                    break;
                }
            
        }
        serializeList();
            
        System.out.println("** Closing connection with " + sock.getInetAddress() + ":" + sock.getPort() + " **");
        sock.close();
        } catch (IOException ex) {
            Logger.getLogger(UserCheckPasswordThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserCheckPasswordThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
