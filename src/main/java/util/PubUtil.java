package util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * PubUtil
 *
 * @author zhangwenzhi
 * @date 2020/6/1 9:08
 */
public class PubUtil {

    private final static Logger logger = LoggerFactory.getLogger(PubUtil.class);

    /**
     * 获取唯一id
     * @author zhangwenzhi
     * @date 2020/7/23 13:29
     */
    public static String nextId() {
        return SnowFlakeUtil.nextId(1);
    }

    /**
     * 将new Date转为字符串
     * @author zhangwenzhi
     * @date 2020/7/17 13:56
     */
    public static String formatDate(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * 日期格式化
     * @author zhangwenzhi
     * @date 2020/7/17 14:25
     */
    public static String formatDate(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 成功格式
     * @author zhangwenzhi
     * @date 2020/7/28 13:43
     */
    public static JSONObject success(JSONObject para, String msg){
        if(para == null){
            para = new JSONObject();
        }
        if(!para.containsKey("success")){
            para.put("success",true);
        }
        if(!para.containsKey("errorcode")){
            para.put("errorcode","0");
        }
        if(!para.containsKey("errortext")){
            para.put("errortext","");
        }
        if(!para.containsKey("errorMsg")){
            para.put("errorMsg","");
        }
        para.put("data",msg);
        return para;
    }

    //Overload
    public static JSONObject success(JSONObject para){
        return success(para,"");
    }

    //Overload
    public static JSONObject success(String msg){
        return success(null,msg);
    }

    //Overload
    public static JSONObject success(){
        return success(null,"");
    }

    /**
     * 返回错误信息
     * @author zhangwenzhi
     * @date 2020/7/28 13:42
     */
    public static JSONObject fail(String errortext) {
        JSONObject result = new JSONObject();
        result.put("success",false);
        result.put("errorcode","1");
        result.put("errortext",errortext);
        result.put("errorMsg",errortext);
        result.put("msg",errortext);
        return result;
    }

    /**
     * 生成以，分割的字符串
     * @author zhangwenzhi
     * @date 2020/7/29 9:226
     */
    public static String newExp(Object... objects){
        StringBuilder sb = new StringBuilder();
        for (Object object : objects) {
            if(object != null){
                sb.append(object);
            }
            sb.append(",");
        }
      return sb.toString();
    }

    /**
     * 获取当天起始时间
     * @author zhangwenzhi
     * @date 2020/8/13 15:26
     */
    public static Date getTodayStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取当天结束时间
     * @author zhangwenzhi
     * @date 2020/8/13 15:27
     */
    public static Date getTodayEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    private static boolean inList(String key, List<String> list) {
        for (String listStr : list) {
            if (key.equals(listStr)) return true;
        }
        return false;
    }

    public static String  getUrlDomain(String destination){

            if(destination==null||destination.trim().equals("")){

                return null;

            }

            String domain = null;

            URL url =null;

            try {
                url= new URL(destination);
                domain =url.getProtocol()+"://"+url.getHost();
                return domain;
            } catch (MalformedURLException e) {
                logger.info("【解析url失败】"+e.toString() +"【url】"+url);
                return domain;
            }

    }

    //每月多少天
    public static int MonthContainDays(int year,int month){
        if(isLeapYear(year)&&month==2) return 29;
        if(!isLeapYear(year)&&month==2) return 28;
        if(month==4||month==6||month==7||month==11) {
            return 30;
        }else{
            return 31;
        }
    }

    //判断闰年
    public static boolean isLeapYear(int year){
        return year / 4 == 0 && year / 100 != 0;
    }


    public static Map newMap(String key, String value){
        Map map = new HashMap();
        String[] keyList = key.split(",");
        String[] valueList = value.split(",");

        for(int i=0;i<keyList.length;i++){
            if(valueList.length<(i+1)){
                map.put(keyList[i],"");
            }else{
                map.put(keyList[i],valueList[i]);
            }
        }
        return map;
    }

    public static List newList(Object... objects){
        List list = new LinkedList();
        for(Object o:objects){
            list.add(o);
        }
        return list;
    }

    /**
     * greater true:大于等于边界值
     * @author zhangwenzhi
     * @date 2020/8/27 9:09
     */
    public static int getIndexFromArray(int[] array,int range,boolean greater ){
        List<Integer> list = new LinkedList<>();
        for(int i=0;i<array.length;i++){
            if(greater){
                if (array[i]>range){
                    list.add(i);
                }
            }else {
                if (array[i]<range){
                    list.add(i);
                }
            }
        }
        int r = Randomizer.random(0,list.size()-1);
        return list.get(r);
    }
}

