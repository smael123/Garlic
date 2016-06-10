package sharedClasses;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author izzy
 */
public class Scholarship implements Serializable
{
    public String name; //name of scholarship
    public String requiredMajor;
    public String requiredMinor;
    public float requiredGPA;
    public double reward;
    
    public Scholarship(String name, float requiredGPA, String requiredMajor, String requiredMinor, double reward)
    {
        this.name = name; //name of scholarship
        this.requiredGPA = requiredGPA;  
        this.requiredMajor = requiredMajor;
        this.requiredMinor = requiredMinor;
        this.reward = reward;
    }
	
    @Override
    public String toString()
    {
        String s1 = ("Scholarship: " + this.name + "\n");
        String s2 = ("Required GPA: " + this.requiredGPA) + "\n";
        String s3 = ("Required Major: " + this.requiredMajor + "\n");
        String s4 = ("Required Minor: " + this.requiredMinor + "\n");
        String s5 = ("Reward: " + this.reward + "\n");

        return s1 + s2 + s3 + s4 + s5;
    }
        
    public boolean checkIfEligable(Student student)
    {
        if (requiredGPA > student.getGPA()) //assume every student will have a GPA
        {
            return false;
        }
        if (!requiredMajor.isEmpty())
        {
            if (!requiredMajor.equals(student.getMajor()))
            {
                return false;
            }
        }
        if (!requiredMinor.isEmpty())
        {
            if (!requiredMinor.equals(student.getMinor()))
            {
                return false;
            }
        }
        return true;
    }
   
}
