package util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: zhangwenzhi
 * @time: 2020/4/14 15:16
 */
public class Randomizer {

    /**
     * 方法描述: 随机结果生成器
     * 从list中随机选取结果
     * @param list 名称-概率分布表
     * @param name list中map的key
     * @param rate list中map的value
     * @author zhangwenzhi
     * @time 2020/4/14 15:52
     */
    public static String generateResult(List<Map> list,String name,String rate){
        //随机区域大小
        Double area = 0.0;
        for(Map map:list){
            area = area + Double.parseDouble(map.get(rate).toString());
        }
        //保留两位小数(雾)
        BigDecimal bigDecimal = new BigDecimal(area);
        area = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        Double math_random = Math.random()*area;
        Double sum =0.0;
        for (int i=0;i<list.size();i++){
            sum = sum + Double.parseDouble(list.get(i).get(rate).toString());
            if(math_random<=sum){
                return list.get(i).get(name).toString();
            }
        }
        return "";
    }

    /**
     * 方法描述: 随机整数生成器
     * 包含上下界
     * @author zhangwenzhi
     * @time 2020/4/14 16:38
     */
    public static int random(int min,int max){
        java.util.Random r = new java.util.Random();
        int result = r.nextInt(max-min+1);
        return result+min;
    }

    public static String selectFromArray(String[] array){
        if(array.length<1){
            System.out.println("array is empty");
            return "";
        }
        int r = random(0,array.length-1);
        return array[r];
    }

    //带边界的正态分布 多值 boundary
    public static double NormalDistributionWithBoundary(double u,double v,double min,double max){
        return NormalDistribution(u,v,min,max);
    }
    //带边界的正态分布 多值 boundary
    public static List<Double> NormalDistributionWithBoundary(double u,double v,int num,double min,double max){
        List<Double> result = new ArrayList<>();
        for(int i=0;i<num;i++){
            result.add(NormalDistribution(u,v,min,max));
        }
        return result;
    }

    /**
     * 方法描述:可变权重随机器(幸运值分配)
     * @param expect 期待值（最优值）
     * @param lucky 幸运值（-100至100）
     * @author zhangwenzhi
     * @time 2020/4/16 9:59
     */
    public static double LuckyValueNormalDistribution(double u,double v,double min,double max,double expect,double lucky){
        //幸运差：描述了最优值与均值之间的差值
        double difference = Math.abs(expect-u);
        //修正值：修改后的幸运差
        double correction = difference * (100-lucky)/100;
        if(expect >= u){
            u = expect - correction;
        }else{
            u = expect + correction;
        }
        //方差缩小，提高聚集效果
        if(lucky>=60||lucky<=-60){
            v = v/3;
        }
        return NormalDistributionWithBoundary(u,v,min,max);
    }

    //普通正态随机分布
    //参数 u 均值
    //参数 v 方差
    public static double NormalDistribution(float u,float v){
        java.util.Random random = new java.util.Random();
        return Math.floor(Math.sqrt(v)*random.nextGaussian()+u);
    }

    //普通正态随机分布(带边界)
    //参数 u 均值
    //参数 v 方差
    private static double NormalDistribution(double u,double v,double min,double max){
        java.util.Random random = new java.util.Random();
        Double result;
        while (true){
            result = Math.sqrt(v)*random.nextGaussian()+u;
            if(min<result && result<max) return Math.floor(result);
        }
    }

    /**
     * 方法描述: 正态分布函数
     * 有最大值
     * @author zhangwenzhi
     * @time 2020/4/17 9:30
     */
    public static double getGaussian(double u,double v,double x,double max){
        return max*Math.exp(-Math.pow(x-u,2)/(2*v));
    }
    //无最大值
    public static double getGaussian(double u,double v,double x){
        return 1/Math.sqrt(2*Math.PI*v) * Math.exp(-Math.pow(x-u,2)/(2*v));
    }

    //标准正态随机分布
    private static double StandardNormalDistribution(){
        java.util.Random random = new java.util.Random();
        return random.nextGaussian();
    }

    /**
     * 方法描述: 概率判断器
     * @param rate 概率（0-100）
     * @author: zhangwenzhi
     * @time: 2020/4/17 10:33
     */
    public static boolean toBoolean(double rate){
        return rate > random(0,100);
    }

    /**
     * 生成不重复的一组数字
     * @author zhangwenzhi
     * @date 2020/8/17 11:09
     */
/*    public static int[] getIntArrayNoRepeat(int start,int end,int num){
        if(end-start+1<num){
            num = end-start+1;
        }
        int[] result = new int[num];
        for(int c=0;c<result.length;c++){
            result[c] = -999;
        }
        int r;
        int current = 0;

        while (current < num){
            r = random(start,end);
            //不重复
            if(!PubUtil.intInArray(r,result)){
                result[current] = r;
                current++;
            }
        }
        return result;
    }*/

    /**
     * 取set中的随机值
     * @author zhangwenzhi
     * @date 2020/8/27 10:10
     */
    public static String randomFromSet(Set<String> para){
        if(para.size()<1){
            return "";
        }
        int r = random(0,para.size()-1);
        int current = 0;
        for(String key:para){
            if (current == r){
                return key;
            }
            current++;
        }
        return "";
    }



}
