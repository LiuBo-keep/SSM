package controller;

import bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;
import util.CpachaUtil;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/system")
public class loginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    /**
     * 登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model){
        return "login";
    }

    /**
     * 登录表单提交
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> login(
            @RequestParam(value = "username",required = true) String username,
            @RequestParam(value = "password",required = true) String password,
            @RequestParam(value = "vcode",required = true) String vcode,
            @RequestParam(value = "type",required = true) int type,
            HttpServletRequest request
    ){
        Map<String,String> map=new HashMap<String,String>();
        if (StringUtils.isEmpty(username)){
            map.put("type","error");
            map.put("msg","用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)){
            map.put("type","error");
            map.put("msg","密码不能为空");
            return map;
        }
        if (StringUtils.isEmpty(vcode)){
            map.put("type","error");
            map.put("msg","验证码不能为空");
            return map;
        }
        String  loginCpach=(String) request.getSession().getAttribute("loginCpach");
        if (StringUtils.isEmpty(loginCpach)){
            map.put("type","error");
            map.put("msg","长时间未操作，验证码以失效，请刷新后重试");
            return map;
        }
        if (!vcode.toUpperCase().equals(loginCpach.toUpperCase())){
            map.put("type","error");
            map.put("msg","验证码错误");
            return map;
        }
        request.getSession().setAttribute("loginCpach",null);

        //从数据库中查找用户
        User user=userService.findByUsername(username);
        if (type==1){
            //管理员
            if (user==null){
                map.put("type","error");
                map.put("msg","该用户不存在");
                return map;
            }
            if (!password.equals(user.getPassword())){
                map.put("type","error");
                map.put("msg","密码错误");
                return map;
            }
            request.getSession().setAttribute("user",user);
        }
        if (type==2){
            //学生
        }

        map.put("type","success");
        map.put("msg","登录成功");
        return map;
    }

    /**
     * 显示验证码
     * @param request
     * @param vl
     * @param w
     * @param h
     * @param response
     */
    @RequestMapping(value = "/get_cpacha",method = RequestMethod.GET)
    public void getCpacha(HttpServletRequest request,
                          @RequestParam(value = "vl",defaultValue = "4",required = false) Integer vl,
                          @RequestParam(value = "w" ,defaultValue = "98",required = false) Integer w,
                          @RequestParam(value = "h",defaultValue = "33",required = false) Integer h,
                          HttpServletResponse response){
        CpachaUtil cpachaUtil=new CpachaUtil(vl,w,h);
       //获取验证码
        String generatorVCode=cpachaUtil.generatorVCode();
       //将验证码存入session
        request.getSession().setAttribute("loginCpach",generatorVCode);
        //给验证码加障碍
        BufferedImage generatorRotateVCodeImage=cpachaUtil.generatorRotateVCodeImage(generatorVCode,true);

        try {
            ImageIO.write(generatorRotateVCodeImage,"gif",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
