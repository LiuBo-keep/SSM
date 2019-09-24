package service.Impl;

import Mapper.GradeMapper;
import bean.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.GradeService;

import java.util.List;
import java.util.Map;

@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public int insertUser(Grade grade) {
        return gradeMapper.insertUser(grade);
    }

    @Override
    public int editUser(Grade grade) {
        return gradeMapper.editUser(grade);
    }

    @Override
    public List<Grade> findList(Map<String, Object> queryMap) {
        return gradeMapper.findList(queryMap);
    }

    @Override
    public int getCount(Map<String, Object> queryMap) {
        return gradeMapper.getCount(queryMap);
    }

    @Override
    public int delete(String id) {
        return gradeMapper.delete(id);
    }
}
