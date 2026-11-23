package model;
/**
 * 
 * ProgressionDecision 
 * stores the full prograssion descision information about one student.
 * Has a {@link Student} about whom the decision is made
 * Has Student's QCA
 * Has a decision from enum {@link Decision}
 * Has a string reason: for 'REPEAT MODULE' desicion reason is module, this field can bu null
 * It it a atomic building block for the {@link ExamBoard} class
 */
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
    
    @Override
    public String toString(){
        return "Progression decision for "+ student.toString() + "; QCA is" + qca + " " + decision.toString() + reason;
    }
}
