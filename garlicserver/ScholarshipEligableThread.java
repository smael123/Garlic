/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garlicserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class ScholarshipEligableThread extends ScholarshipThread {
    
    Student student;
    
    
    ScholarshipEligableThread(Socket sock) //serial dierctory will be the eligable schoalrships
    {
        super(sock);
    }
    
    @Override
    public void run()
    {	
        try {
            deserializeList();
            
            ArrayList<Scholarship> sentBackScholarshipList;
            ObjectInputStream input = null;
            input = new ObjectInputStream(sock.getInputStream());
            final ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
            //read student object
           
            student = (Student)input.readObject();
            sentBackScholarshipList = new ArrayList<>();
            
            for (Scholarship schol : scholarshipList)
            {
                if (schol.checkIfEligable(student))
                {
                   sentBackScholarshipList.add(schol);
                }
            }
            output.writeObject(sentBackScholarshipList);
            System.out.println("** Closing connection with " + sock.getInetAddress() + ":" + sock.getPort() + " **");
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(ScholarshipEligableThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ScholarshipEligableThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
