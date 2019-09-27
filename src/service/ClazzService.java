package service;

import bean.Clazz;

import java.util.List;
import java.util.Map;

public interface ClazzService {

    public int insertUser(Clazz clazz);

    public int editUser(Clazz clazz);

    public List<Clazz> findList(Map<String,Object> queryMap);

    public List<Clazz> findAll();

    public int getCount(Map<String,Object> queryMap);

    public int delete(String id);
}
