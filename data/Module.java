public class Module{
    private double[] weight;
    private String ModuleName;
    private int numberOfTests;
     private QCA result;
    
    public Module(String name, int numberOfTests){
      this.ModuleName = name;
      this.numberOfTests = numberOfTests;
      weight = new double[numberOfTests];  
    }
    
    public String getModuleName(){
        return ModuleName;
    }
    
    
    public void setWeight(int i, double value){
        if(i >= 0 && i < weight.length){
            weight[i] = value;
        }
    }
    
    public double getWeight(int i, double value){
        if(i >= 0 && i < weight.length){
            return weight[i];
        }
        return -1;
    }
    
    public int getNumberofTests() {
        return weight.length;
    }
    
}
