package controller;

import bean.Clazz;
import bean.Student;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.ClazzService;
import service.StudentService;
import util.SnUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 学生信息管理
 */

@RequestMapping(value = "/student")
@Controller
public class StudentCtroller {

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String studentList(Model model){
        List<Clazz> clazzList=clazzService.findAll();
        model.addAttribute("clazzList",clazzList);
        model.addAttribute("clazzListJson",JSONArray.fromObject(clazzList));
        return "Student";
    }

    /**
     * 上传头像
     */
    @RequestMapping(value = "/upload_photo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> uploadPhoto(MultipartFile photo,
                                          HttpServletRequest request,
                                          HttpServletResponse response
                                          ) throws IOException {
        Map<String,String> map=new HashMap<String,String>();

        String name=photo.getOriginalFilename();
        if (photo==null){
            //文件没有选择
            map.put("type","error");
            map.put("msg","请选择文件");
            return map;
        }
        if (photo.getSize()>10485760){
            //判断文件大小
            map.put("type","error");
            map.put("msg","文件大小超过10M，请上传小于10M的图片");
            return map;
        }
         String suffix=photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")+1,photo.getOriginalFilename().length());
        if (!"jpg,png,gif,jpeg".contains(suffix.toLowerCase())){
            //判断文件是否是图片
            map.put("type","error");
            map.put("msg","您上传的文件格式不对，请上传jpg,png,gif,jpeg格式的图片");
            return map;
        }

        //用uuid给图片重新命名
        String uuid=UUID.randomUUID().toString().toLowerCase().replace("-","");
        String newName=uuid.concat("."+suffix);

        //将文件保存在本地磁盘
        File file=new File("M:\\photo\\"+newName);
        if (!file.exists()){
            file.mkdirs();//判断文件加是否存，不存在就创建
        }

        photo.transferTo(file);

        map.put("type","success");
        map.put("msg","上传成功");
        map.put("src","/photo/"+newName);
        return map;
    }

    /**
     * 添加学生信息
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> addStudent(Student student){
        Map<String,String> map=new HashMap<String,String>();
        if (StringUtils.isEmpty(student.getUsername())){
            map.put("type","error");
            map.put("msg","学生姓名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(student.getPassword())){
            map.put("type","error");
            map.put("msg","学生密码不能为空");
            return map;
        }
        if (student.getClazzId()==null){
            map.put("type","error");
            map.put("msg","请选择所属班级");
            return map;
        }
        //设置学号
        student.setSn(SnUtil.getStudentSn("S",""));

        if (studentService.addStudent(student)<=0) {
            map.put("type","error");
            map.put("msg","添加失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }
}
