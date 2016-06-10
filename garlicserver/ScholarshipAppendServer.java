/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garlicserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author izzy
 */
public class ScholarshipAppendServer {
    public static final int SERVER_PORT = 8770;
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
            ScholarshipAppendThread appendScholarThread = null;
   
            while (true)
            {
                sock = serverSock.accept();
                System.out.println("Accepted connection");
                appendScholarThread = new ScholarshipAppendThread(sock);
                appendScholarThread.start();
            }
        
    }   catch (IOException ex) {
            Logger.getLogger(PasswordServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
