package service.Impl;

import Mapper.StudentMapper;
import bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.StudentService;

import java.util.List;
import java.util.Map;


@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public int addStudent(Student student) {
        return studentMapper.addStudent(student);
    }

    @Override
    public List<Student> findList(Map<String, Object> mansge) {
        return studentMapper.findList(mansge);
    }

    @Override
    public int getCount(Map<String, Object> mansge) {
        return studentMapper.getCount(mansge);
    }

    @Override
    public int deleteStudent(Long[] ids) {
        return studentMapper.deleteStudent(ids);
    }

    @Override
    public int updateStudent(Student student) {
        return studentMapper.updateStudent(student);
    }

    @Override
    public Student findStrudent(String username) {
        return studentMapper.findStrudent(username);
    }
}
