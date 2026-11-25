public class Module {
    private Grade grade;
    private String name;
    private String code;

    public Module(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
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
        return new Module(code, name);
    }
}
