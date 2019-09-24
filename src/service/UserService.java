package service;

import bean.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    public User findByUsername(String username);

    public int insertUser(User user);

    public int editUser(User user);

    public List<User> findList(Map<String,Object> queryMap);

    public int getCount(Map<String,Object> queryMap);

    public int delete(String id);
}
