package interceptor;
/**
 *
 * 登录过滤拦截器
 *
 */

import bean.Student;
import bean.User;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
         String url=request.getRequestURI();
         System.out.println("进入拦截器,url="+url);
         Object  user=request.getSession().getAttribute("user");
         Student student= (Student) request.getSession().getAttribute("student");
         if (user==null){
             System.out.println("未登录或登录以时效，url="+url);

             if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
                 //ajax请求
                 HashMap<String,String> hashMap=new HashMap<String,String>();
                 hashMap.put("type","error");
                 hashMap.put("msg","登录状态已失效，请重新去登录");
                 response.getWriter().write(JSONObject.fromObject(hashMap).toString());
                 return false;
             }
             response.sendRedirect("/system/login");
             return false;
         }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
