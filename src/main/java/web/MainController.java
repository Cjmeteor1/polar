package web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.Sorter;
import java.util.HashMap;

/**
 * MainController
 *
 * @author zhangwenzhi
 * @description
 * @date 2020/9/4 11:15
 */
@Controller
@RequestMapping("/main")
public class MainController {
    private Sorter sorter = new Sorter();

    @PostMapping("/service")
    @ResponseBody
    public JSONObject service(@RequestParam HashMap<String,String> msg){
        String serviceName = msg.get("serviceName");
        String methodName = msg.get("methodName");
        String para = msg.get("para");
        return sorter.sort(serviceName,methodName,para);
    }

}
