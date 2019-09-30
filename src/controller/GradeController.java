package controller;

import bean.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.GradeService;
import util.Page;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/grade")
@Controller
public class GradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * 年级表单
     *
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String gradeList(){
        return "gradeList";
    }

    /**
     *
     * 年级页面
     * @param name
     * @param page
     * @return
     */
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name",required = false,defaultValue = "") String name,
            Page page
    ){
        Map<String,Object> Map=new HashMap<String,Object>();
        Map<String,Object> queryMap=new HashMap<String,Object>();
        queryMap.put("name","%"+name+"%");
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());

        Map.put("rows",gradeService.findList(queryMap));
        Map.put("total",gradeService.getCount(queryMap));

        return Map;
    }


    /**
     *
     * 添加年级
     * @param grade
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Grade grade){

        HashMap<String,String> Map=new HashMap<String,String>();
        if (grade==null){
            Map.put("type","error");
            Map.put("msg","数据绑定出错，请联系开发者!");
            return Map;
        }
        if (StringUtils.isEmpty(grade.getName())){
            Map.put("type","error");
            Map.put("msg","年级名不能为空！");
            return Map;
        }

        if (gradeService.insertUser(grade)<=0){
            Map.put("type","error");
            Map.put("msg","添加失败");
            return Map;
        }
        Map.put("type","success");
        Map.put("msg","添加成功");
        return Map;
    }

    /**
     *
     * 编辑年级信息
     *
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> editUser(Grade grade){
        HashMap<String,String> map=new HashMap<String,String>();
        if (grade==null){
            map.put("type","error");
            map.put("msg","数据绑定出错，请联系开发者!");
            return map;
        }
        if (StringUtils.isEmpty(grade.getName())){
            map.put("type","error");
            map.put("msg","年级不能为空！");
            return map;
        }

        if (gradeService.editUser(grade)<=0){
            map.put("type","error");
            map.put("msg","修改失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","修改成功");
        return map;
    };

    /**
     * 删除年级列表
     *
     */

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> delete(
            @RequestParam(value = "ids[]",required = true) Long [] ids
    ){
        HashMap<String,String> map=new HashMap<String,String>();

        if (ids==null || ids.length==0){
            map.put("type","error");
            map.put("msg","请选择你要删除的数据");
            return map;
        }

        String idsString="";
        for(Long id:ids){
            idsString+=id+",";
        }

        idsString=idsString.substring(0,idsString.length()-1);

        try{
            if (gradeService.delete(idsString)<=0){
                map.put("type","error");
                map.put("msg","删除失败");
                return map;
            }
        }catch (Exception e){
            map.put("type","error");
            map.put("msg","该年级下存在班级信息，请勿冲动！");
            return map;
        }

        map.put("type","success ");
        map.put("msg","删除成功！");
        return map;
    }

}
