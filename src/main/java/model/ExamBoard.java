package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**ExamBoard class simulates a real exam boad:
 * a meeting where the decisions about student progresion are made
 * 
*/
public class ExamBoard {
    
    private Programme programme; 
    private AcademicPeriod period;
    private ArrayList<ProgressionDecision> decisions;
    
    /**Default Constructor*/
    public ExamBoard(){
        decisions = new ArrayList<ProgressionDecision>();
    }
    
    
    public void addDecision(ProgressionDecision decision){
        decisions.add(decision);
    }

    public ArrayList<ProgressionDecision> getDecisions() {
        return decisions;
    }
}
