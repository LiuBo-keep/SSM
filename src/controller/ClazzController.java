package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/clazz")
@Controller
public class ClazzController {

    @RequestMapping(value = "/list")
    public String clazzList(){
        return "clazzList";
    }
}
