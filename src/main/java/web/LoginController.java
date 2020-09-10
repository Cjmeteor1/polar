package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * LoginController
 *
 * @author zhangwenzhi
 * @description
 * @date 2020/9/3 8:52
 */
@Controller
public class LoginController {
    @GetMapping({"","/"})
    public String index(){
        return "/index";
    }
}
