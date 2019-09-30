package controller;

import bean.Clazz;
import bean.Grade;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
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
    public ModelAndView clazzList(ModelAndView mav){
        List<Grade> gradeList=gradeService.findAll();

        mav.setViewName("clazzList");
        mav.addObject("gradeList",gradeList);
        mav.addObject("gradeListJson", JSONArray.fromObject(gradeList));
        return mav;
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
            @RequestParam(value = "gradeId",required = false,defaultValue = "") Long gradeId,
            Page page
    ){
        HashMap<String,Object> map=new HashMap<String,Object>();
        HashMap<String,Object> message=new HashMap<String,Object>();
        message.put("name","%"+name+"%");
        message.put("gradeId",gradeId);
        message.put("offset",page.getOffset());
        message.put("pageSize",page.getRows());


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

    /**
     *
     * 修改
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> editClazz(Clazz clazz){
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
            map.put("msg","所属年级不能为空！");
            return map;
        }

        if (clazzService.editUser(clazz)<=0){
            map.put("type","error");
            map.put("msg","修改失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","修改成功");
        return map;
    }

    /**
     *
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> deleteClazz(
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
            if (clazzService.delete(idsString)<=0){
                map.put("type","error");
                map.put("msg","删除失败");
                return map;
            }
        }catch (Exception e){
            map.put("type","error");
            map.put("msg","该年级下存在学生信息，请勿冲动！");
            return map;
        }

        map.put("type","success");
        map.put("msg","删除成功！");
        return map;
    }

}
