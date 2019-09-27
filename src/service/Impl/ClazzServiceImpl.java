package service.Impl;

import Mapper.ClazzMapper;
import bean.Clazz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ClazzService;

import java.util.List;
import java.util.Map;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public int insertUser(Clazz clazz) {
        return clazzMapper.insertUser(clazz);
    }

    @Override
    public int editUser(Clazz clazz) {
        return clazzMapper.editUser(clazz);
    }

    @Override
    public List<Clazz> findList(Map<String, Object> queryMap) {
        return clazzMapper.findList(queryMap);
    }

    @Override
    public List<Clazz> findAll() {
        return clazzMapper.findAll();
    }

    @Override
    public int getCount(Map<String, Object> queryMap) {
        return clazzMapper.getCount(queryMap);
    }

    @Override
    public int delete(String id) {
        return clazzMapper.delete(id);
    }
}
