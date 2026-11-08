package repository;
import java.util.List;
import model.Student;

public interface IStudentRepository {
    public Student findById(String id);
    public void save(Student student);
    public List<Student> findAll();
}
