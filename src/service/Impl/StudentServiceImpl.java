package service.Impl;

import Mapper.StudentMapper;
import bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.StudentService;


@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public int addStudent(Student student) {
        return studentMapper.addStudent(student);
    }
}
