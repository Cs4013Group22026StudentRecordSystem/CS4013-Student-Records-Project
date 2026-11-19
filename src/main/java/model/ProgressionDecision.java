package model;

public class ProgressionDecision {
    
    private Student student;
    private double qca;
    private Decision decision;
    private String reason;
    
    /** Default constructor */
    public ProgressionDecision(){
        
    }
    
    public ProgressionDecision(Student student, double qca, Decision decision, String reason){
        this.student = student;
        this.qca = qca;
        this.decision = decision;
        this.reason = reason;
    }
    
}
