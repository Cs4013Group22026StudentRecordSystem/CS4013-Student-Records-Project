public class Module{
    private String result;
    private String name;
    private String code;
    //PRIVATE DOUBL3 QVP
    public Module(String code, String name, String result){
        this.code = code;
        this.name = name;
    }
    
    public void setResult(Grade grade){
        
        boolean valid = false;
 
        while(!valid){
            try{
                grade = Grade.valueOf(result.toUpperCase());
                this.result = result;
                valid = true;
            }
            catch(IllegalArgumentException e){
                System.out.println("Invalid input for result.");
            }
        }
        //grade.getQCA()
    }   
    
    //calculate QVP/ assign
    
    //public DOUBE getgrADE()
    //CASE 
    //DOUBLE PVQ;
    
}