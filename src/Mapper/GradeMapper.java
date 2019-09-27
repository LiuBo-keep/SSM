package Mapper;

import bean.Grade;

import java.util.List;
import java.util.Map;

public interface GradeMapper {
    public int insertUser(Grade grade);

    public int editUser(Grade grade);

    public List<Grade> findList(Map<String,Object> queryMap);

    public List<Grade> findAll();


    public int getCount(Map<String,Object> queryMap);

    public int delete(String id);
}
