package Client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner; 
import java.util.logging.Level;
import java.util.logging.Logger;
import sharedClasses.Student;
/**
 *
 * @author Fernando R. 
 */
public abstract class AccountMenu {
    public Student student; 
    
    public String inputServerName()
    {
        /*Scanner scan = new Scanner(System.in);
        System.out.println("Type the IP address of the server: ");
        String ipAddress = scan.nextLine();*/
        return "localhost";        
    }
    
    public abstract void displayMenu();
    
    public void updateServerBalance()
    {
        ObjectOutputStream output = null;
        try {
            final Socket sock = new Socket(inputServerName(), 8771);
            output = new ObjectOutputStream(sock.getOutputStream());
            final ObjectInputStream input = new ObjectInputStream(sock.getInputStream());
            output.writeObject(student);
        } catch (IOException ex) {
            Logger.getLogger(AccountMenu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(AccountMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
}
