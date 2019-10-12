package service;

import bean.Student;

import java.util.List;
import java.util.Map;


public interface StudentService {
    //添加
    public int addStudent(Student student);
    //结果集
    public List<Student> findList(Map<String,Object> mansge);
    //总行数
    public int getCount(Map<String,Object> mansge);
    //删除
    public int deleteStudent(Long [] ids);
    //修改
    public int updateStudent(Student student);
    //查询单个
    public Student findStrudent(String username);

}
