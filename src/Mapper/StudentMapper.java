package Mapper;

import bean.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface StudentMapper {
    //添加
    public int addStudent(Student student);

    public List<Student> findList(Map<String,Object> queryMap);

    public int getCount(Map<String,Object> queryMap);
}
