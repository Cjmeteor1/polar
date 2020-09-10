package service;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PubUtil;

/**
 * Sorter
 *
 * @author zhangwenzhi
 * @description
 * @date 2020/9/2 14:22
 */
public class Sorter {
    private final static Logger logger = LoggerFactory.getLogger(Sorter.class);
    private NpcService npcService = new NpcService();

    /*public static void main(String[] args){
        Sorter sorter = new Sorter();
        sorter.sort("","selectNpcById","");
    }*/

    public JSONObject sort(String serviceName,String methodName,String para){
        Object currentService ;
        String result = "";

        if("NpcService".equals(serviceName)){
            currentService = npcService;
        }else{
            return PubUtil.fail("传入服务名不存在");
        }

        try {
            //java反射机制调用方法
            result = (String) currentService.getClass().getMethod(methodName, new Class[]{String.class})
                    .invoke(currentService,new Object[]{para});
        } catch (Exception e) {
            e.printStackTrace();
            // cause 反射失败原因
            Throwable cause = e.getCause();
            logger.info(cause.getMessage());
            return PubUtil.fail(cause.getMessage());
        }
        return PubUtil.success(result);
    }


}
