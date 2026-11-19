package model;

public class Result {
    private Student student;
    private Module module;
    private Grade grade;
    private AcademicPeriod period;
    private int attempt;
    
    /** Default constructor */
    public Result(){
        
    }
    
    public Result(Student student, Module module, Grade grade, AcademicPeriod period, int attempt){
        this.student = student;
        this.module = module;
        this.grade = grade;
        this.period = period;
        this.attempt = attempt;
    }
}
