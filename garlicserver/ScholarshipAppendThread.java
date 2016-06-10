/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garlicserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sharedClasses.Scholarship;
import sharedClasses.Student;

/**
 *
 * @author izzy
 */
public class ScholarshipAppendThread extends ScholarshipThread{
    Scholarship scholarship;
    
    
    ScholarshipAppendThread(Socket sock) //serial dierctory will be the eligable schoalrships
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
            
            scholarship = (Scholarship)input.readObject();
            scholarshipList.add(scholarship);
            serializeList(); //we might not need the socket open for this

            System.out.println("** Closing connection with " + sock.getInetAddress() + ":" + sock.getPort() + " **");
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(ScholarshipAppendThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ScholarshipAppendThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
