import java.util.ArrayList;

public class Student{
    private int ID;
    private String StudentName;
    private ArrayList<Module> module;
    private ArrayList<QCA> result;
    
    public Student(int ID, String StudentName){
        this.ID = ID;
        this.StudentName = StudentName;
        module = new ArrayList<>();
    }
    
    public void addModule(Module a){
        module.add(a);
    }
    
    public Module getModule(int i){
        if (i >= 0 && i < module.size()){
            return module.get(i);
        }
        return null;
    }

    /**
     * Gives an overall QCA value taking the average of all QVP scores from modules
     * @return total: The QCA result
     */
    public double getQCAResult(){
        double total = 0;
        for(QCA results: result){
            total += results.getQCA();
        }
        return total/result.size();
    }
    
    public ArrayList<Module> getModule(){
        return module;
    }
    
    public int getNumberOfModules(){
        return module.size();
    }
    
    
}
