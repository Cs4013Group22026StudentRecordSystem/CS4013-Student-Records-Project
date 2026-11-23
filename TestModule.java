import java.util.ArrayList;

public class TestModule{
    public static void main(String[] args){
        Programme pro = new Programme("Computers");
        ArrayList<Programme> mods = new ArrayList<>();
        
        Module mod = new Module("CS1101", "Games", "NA");
        
        mod.setResult(Grade.A1);
        
        
    }
}