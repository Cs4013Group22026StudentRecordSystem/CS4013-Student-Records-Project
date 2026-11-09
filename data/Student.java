import java.util.ArrayList;

public class Student{
    private int ID;
    private String StudentName;
    private ArrayList<Module> module;
    
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
    
    public ArrayList<Module> getModule(){
        return module;
    }
    
    public int getNumberOfModules(){
        return module.size();
    }
    
    
}