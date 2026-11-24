import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Programme {
    private String code;
    private String name;
    private ArrayList<Module> modules;
    private Map<String, Integer> moduleSemesters;

    public Programme(String name) {
        this(name, name);
    }

    public Programme(String code, String name) {
        this.code = code;
        this.name = name;
        this.modules = new ArrayList<>(5);
        this.moduleSemesters = new LinkedHashMap<>();
    }

    public void addModule(Module module) {
        addModule(module, 1);
    }

    public void addModule(Module module, int semester) {
        if (module == null || module.getCode() == null) {
            throw new IllegalArgumentException("Module and module code are required.");
        }
        if (semester < 1) {
            throw new IllegalArgumentException("Semester must be positive.");
        }
        if (modules.size() >= 5) {
            throw new IllegalArgumentException("max module limit reached(5)");
        }
        if (moduleSemesters.containsKey(module.getCode())) {
            throw new IllegalArgumentException("Module is already in this programme.");
        }
        modules.add(module);
        moduleSemesters.put(module.getCode(), semester);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Module> getModules() {
        return new ArrayList<>(modules);
    }

    public int getModuleSemester(String moduleCode) {
        Integer semester = moduleSemesters.get(moduleCode);
        if (semester == null) {
            throw new IllegalArgumentException(
                    "Module is not in this programme: " + moduleCode);
        }
        return semester;
    }

    public Programme copy() {
        Programme copy = new Programme(code, name);
        for (Module module : modules) {
            copy.addModule(module.copy(), getModuleSemester(module.getCode()));
        }
        return copy;
    }
}
