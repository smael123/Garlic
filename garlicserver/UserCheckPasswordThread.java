/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garlicserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sharedClasses.FinancialAidStaff;
import sharedClasses.Student;
import sharedClasses.User;

/**
 *
 * @author lucy
 */
public class UserCheckPasswordThread extends UserThread
{    
    UserCheckPasswordThread(Socket sock)
    {
        super(sock);
    }
    @Override
    public void run()
    {
        try {
           deserializeList();
           final ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
           final ObjectInputStream input = new ObjectInputStream(sock.getInputStream());
           
            String[] userPass = (String[])input.readObject();
            System.out.printf("%s %s", userPass[0], userPass[1]);
            for (User user: userList)
            {
                if (user.checkPassword(userPass[0], userPass[1]))
                {
                    output.writeObject((User)user);
                    break;
                }    
            }
            System.out.println("** Closing connection with " + sock.getInetAddress() + ":" + sock.getPort() + " **");
            sock.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(UserCheckPasswordThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserCheckPasswordThread.class.getName()).log(Level.SEVERE, null, ex);
        } /*catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(UserCheckPasswordThread.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    
    
}
