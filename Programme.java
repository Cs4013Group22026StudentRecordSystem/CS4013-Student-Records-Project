import java.util.ArrayList;

public class Programme{
    private String name;
    private ArrayList<Module> modules;
    //change to current 5 modules
    
    public Programme(String name){
        this.name =name;
        modules = new ArrayList<>(5);
    }
    
    public void addModule(Module a){
        if(modules.size() >= 5){
            throw new IllegalArgumentException("max module limit reached(5)");
        }
        modules.add(a);
    }
    
    //
}
