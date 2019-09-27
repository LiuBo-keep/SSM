package controller;

import bean.Clazz;
import bean.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ClazzService;
import service.GradeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/clazz")
@Controller
public class ClazzController {


    @Autowired
    private ClazzService clazzService;
    @Autowired
    private GradeService gradeService;
    /**
     *
     * 班级列表页
     *
     * @return
     */
    @RequestMapping(value = "/list")
    public String clazzList(Model model){
        List<Grade> gradeList=gradeService.findAll();
        model.addAttribute("gradeList",gradeList);
        return "clazzList";
    }


    /**
     * 添加
     *
     */

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> addClazz(Clazz clazz){
        HashMap<String,String> map=new HashMap<String,String>();
        if (clazz==null){
            map.put("type","error");
            map.put("msg","数据绑定出错，请联系开发者!");
            return map;
        }
        if (StringUtils.isEmpty(clazz.getName())){
            map.put("type","error");
            map.put("msg","年级名不能为空！");
            return map;
        }
        if (StringUtils.isEmpty(clazz.getGradeId())){
            map.put("type","error");
            map.put("msg","年级编号不能为空！");
            return map;
        }
        if (clazzService.insertUser(clazz)<=0){
            map.put("type","error");
            map.put("msg","添加失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }

}
