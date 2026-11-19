package model;

import java.util.ArrayList;

public class Transcript {
    private Student student;
    private ArrayList<Result> results;
    private double semesterQCA;
    private double overallQCA;
    
    /**Default constructor */
    public Transcript(){
        results = new ArrayList<Result>();
    }
    
    public double getSemesterQCA(){
        return semesterQCA;
    }
}
