package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TeamController
 *
 * @author zhangwenzhi
 * @description
 * @date 2020/9/3 15:56
 */
@Controller
@RequestMapping(path = "/team")
public class TeamController {

    @PostMapping("/demo11")
    @ResponseBody
    public void demo11() {

    }
}
