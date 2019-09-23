package service.Impl;

import Mapper.UserMapper;
import bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserService;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public int insertUser(User user) {
         return userMapper.insertUser(user);
    }

    @Override
    public int editUser(User user) {
        return userMapper.editUser(user);
    }

    @Override
    public List<User> findList(Map<String,Object> queryMap) {
        return userMapper.findList(queryMap);
    }

    @Override
    public int getCount(Map<String, Object> queryMap) {
        return userMapper.getCount(queryMap);
    }
}
