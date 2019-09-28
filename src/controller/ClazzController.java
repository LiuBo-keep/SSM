package controller;

import bean.Clazz;
import bean.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ClazzService;
import service.GradeService;
import util.Page;

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
     *
     * 年级页面
     *
     */
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name",required = false,defaultValue = "") String name,
            @RequestParam(value = "gradeId",required = false,defaultValue = "") String gradeId,
            Page page
    ){
        HashMap<String,Object> map=new HashMap<String,Object>();
        HashMap<String,Object> message=new HashMap<String,Object>();
        message.put("name","%"+name+"%");
        message.put("gradeId",gradeId);
        message.put("offset",page.getOffset());
        message.put("pageSize",page.getRows());

        List<Clazz> list=clazzService.findList(message);
        for (Clazz clazz:list){
            System.out.println(clazz);
        }

        map.put("rows",clazzService.findList(message));
        map.put("total",clazzService.getCount(message));

        return map;
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
            map.put("msg","请选择所属年级！");
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
