package controller;

import bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;
import util.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员
 *
 */

@RequestMapping(value = "/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *
     * 用户管理页面
     *
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "UserList";
    }

    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "username",required = false,defaultValue = "") String username,
            Page page
    ){
        Map<String,Object> hashMap=new HashMap<String,Object>();
        Map<String,Object> queryMap=new HashMap<String,Object>();
        queryMap.put("username","%"+username+"%");
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());

        hashMap.put("rows",userService.findList(queryMap));
        hashMap.put("total",userService.getCount(queryMap));

        return hashMap;
    }

    /**
     *
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(User user){

        HashMap<String,String> hashMap=new HashMap<String,String>();
        if (user==null){
            hashMap.put("type","error");
            hashMap.put("msg","数据绑定出错，请联系开发者!");
            return hashMap;
        }
        if (StringUtils.isEmpty(user.getUsername())){
            hashMap.put("type","error");
            hashMap.put("msg","用户名不能为空！");
            return hashMap;
        }
        if (StringUtils.isEmpty(user.getPassword())){
            hashMap.put("type","error");
            hashMap.put("msg","密码不能为空！");
            return hashMap;
        }

        User u=userService.findByUsername(user.getUsername());
        if (u!=null){
            hashMap.put("type","error");
            hashMap.put("msg","用户名以存在");
            return hashMap;
        }
        if (userService.insertUser(user)<=0){
            hashMap.put("type","error");
            hashMap.put("msg","添加失败");
            return hashMap;
        }
        hashMap.put("type","success");
        hashMap.put("msg","添加成功");
        return hashMap;
    }

    /**
     *
     * 编辑用户信息
     *
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> editUser(User user){
      HashMap<String,String> map=new HashMap<String,String>();
        if (user==null){
            map.put("type","error");
            map.put("msg","数据绑定出错，请联系开发者!");
            return map;
        }
        if (StringUtils.isEmpty(user.getUsername())){
            map.put("type","error");
            map.put("msg","用户名不能为空！");
            return map;
        }
        if (StringUtils.isEmpty(user.getPassword())){
            map.put("type","error");
            map.put("msg","密码不能为空！");
            return map;
        }
        User u=userService.findByUsername(user.getUsername());
        if (u!=null){
           if (user.getId()!=u.getId()){
               map.put("type","error");
               map.put("msg","用户名以存在");
               return map;
           }
        }

        if (userService.editUser(user)<=0){
            map.put("type","error");
            map.put("msg","修改失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","修改成功");
      return map;
    };

    /**
     * 删除用户
     *
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> deleteUser(
            @RequestParam(value = "ids[]",required = true) Long [] ids
    ){
        HashMap<String,String> map=new HashMap<String,String>();

        if (ids==null){
            map.put("type","error");
            map.put("msg","请选择您要删除的用户");
            return map;
        }

        String idsString="";
        for(Long id:ids){
            idsString+=id+",";
        }

        idsString=idsString.substring(0,idsString.length()-1);

            if (userService.delete(idsString)<=0){
                map.put("type","error");
                map.put("msg","删除失败");
                return map;
            }

        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }
}
