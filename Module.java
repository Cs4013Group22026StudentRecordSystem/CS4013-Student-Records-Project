public class Module {
    private Grade grade;
    private String name;
    private String code;
    private int creditValue;

    public Module(String code, String name) {
        this(code, name, 5);
    }

    public Module(String code, String name, int creditValue) {
        this.code = code;
        this.name = name;
        this.creditValue = creditValue;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getCreditValue() {
        return creditValue;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Grade cannot be null.");
        }
        this.grade = grade;
    }

    public Module copy() {
        return new Module(code, name, creditValue);
    }
}
