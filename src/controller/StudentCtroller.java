package controller;

import bean.Clazz;
import bean.Grade;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.ClazzService;
import service.GradeService;

import java.util.List;

/**
 * 学生信息管理
 *
 */

@RequestMapping(value = "/student")
@Controller
public class StudentCtroller {

    @Autowired
    private GradeService gradeService;
    @Autowired
    private ClazzService clazzService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String studentList(Model model){
        List<Clazz> clazzList=clazzService.findAll();
        List<Grade> gradeList=gradeService.findAll();
        model.addAttribute("clazzList",clazzList);
        model.addAttribute("clazzListJson",JSONArray.fromObject(clazzList));
        model.addAttribute("gradeList",gradeList);
        model.addAttribute("gradeListJson",JSONArray.fromObject(gradeList));
        return "Student";
    }
}
