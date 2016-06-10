package sharedClasses;

import java.io.Serializable;
import java.util.ArrayList;

/*Offset this to the server*/
public class Student extends User implements Serializable {
	
    //info that is useful to accounting
    public double balance; //how much they owe cannot be negative
    boolean monthlyPayment;
	
    //info for financial aid
    private double income;
    private String major;
    private String minor;
    private float GPA;
    public ArrayList<Scholarship> appliedScholarships;
    public ArrayList<Scholarship> eligibleScholarships;

    public Student(String firstName, String lastName, String userName, String password)
    {
            super(firstName, lastName, userName, password);
            this.appliedScholarships = new ArrayList<>();
            this.eligibleScholarships = new ArrayList<>();

            this.balance = 300.0;
    }
    public Student(String firstName, String lastName, String userName, String password, String major, String minor, float GPA)
    {
        super(firstName, lastName, userName, password);
        this.GPA = GPA;
        this.major = major;
        this.minor = minor;
        this.appliedScholarships = new ArrayList<>();
        this.eligibleScholarships = new ArrayList<>();

        this.balance = 300.0;
    }

    public void setGPA(float GPA)
    {
        this.GPA = GPA;
    }
    public void setMajor(String major)
    {
       this.major = major;
    }
    public void setMinor(String minor)
    {
        this.minor = minor;
    }

    public String getMajor() 
    {
        return major;
    }

    public String getMinor() 
	{
        return minor;
    }

    public float getGPA()
    {
        return GPA;
    }
    @Override
    public void overwriteBalance(User usr)
    {
        Student ash = (Student)usr;
        
        this.balance = ash.balance;
    }
}
