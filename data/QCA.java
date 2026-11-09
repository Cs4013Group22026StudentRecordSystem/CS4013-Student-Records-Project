public class QCA{
    private double[] results;
    private double QCA;
    private Module tests;
    
    /**
     * Constructer for QCA. size depends on amount of tests in a moduel
     * @param moduel tests, number of tests in a module
     */
    public QCA(Module tests){
        this.tests = tests;
        results = new double[tests.getNumberofTests()];
    }
    
    /**
     * Setter that totals the cumulative score
     * @param i, index
     * @param value, total value of tests
     */
    void setResults(int i, double value){
        if(i >= 0 && i < results.length) {
            results[i] = value;
        }
    }
    
    /**
     * Getter that return the score from a certain test in a module
     * @param i, index
     * return results, returns the score
     * return -1, if a failure occures returns -1
     */
    double getScore(int i){
        if(i >= 0 && i < results.length){
            return results[i];
        }
        return -1;
    }
    
    /**
     * Gathers the total score from a module
     * 
     * return total. the total grade of a module
     */
    double getTotal(){
        double total = 0;
        //get all values is scores sotre in score and add to total
        for(double result : results){
            total += result;
        }
        return total;
    }
    
    /**
     * Assignes a grade depending on the getTotal() outcome.
     * return grade. The grade
     */
    String getGrade(){
        String grade = "";
        double total = getTotal();
        if (total <= 1){
            return "F";
        } else if (total <= 30){
            return "D2";
        } else if (total <= 35){
            return "D1";
        } else if (total <= 40 ){
            return "C3";
        } else if (total <= 45 ){
            return "C2";
        } else if (total <= 52 ){
            return "C1";
        } else if (total <= 56 ){
            return "B3";
        } else if (total <= 60 ){
            return "B2";
        } else if (total <= 64 ){
            return "B1";
        } else if (total <= 72 ){
            return "A2";
        } else
        return "A1";
    }
}