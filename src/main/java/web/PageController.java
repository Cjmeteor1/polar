package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * PageController
 *
 * @author zhangwenzhi
 * @description
 * @date 2020/9/8 18:27
 */
@Controller
@RequestMapping(path = "/page")
public class PageController {
    @GetMapping("/websocket")
    public String websocket(){
        return "/websocket";
    }

    @GetMapping(value = "game")
    public Object game(){
        return "/polar/game";
    }
}
