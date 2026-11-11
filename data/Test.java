public class Test{
    public static void main(String[] args){
        Student student = new Student(1001, "john");
        
        Module maths = new Module("Maths" ,3);
        student.addModule(maths);
        System.out.println(" " + student.getModule(0));
    }
}