package sharedClasses;

import java.io.Serializable;

public class User implements Serializable
{
    public String firstName;
    public String lastName;
    String userName;
    String password;

    User(String firstName, String lastName, String userName, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }
    public boolean checkPassword (String attemptedUser, String attemptedPass) //checks username and password
    {
        if (this.userName.equals(attemptedUser) && this.password.equals(attemptedPass))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    @Override
    public String toString()
    {
        return (firstName + " " + lastName);
    }
    
    public void overwriteBalance(User usr)
    {
        System.out.println("A staff member does not have balance");
    }
            
}