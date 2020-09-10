package generator;


import util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.Randomizer.random;

/**
 * AttrGenerator 姓名生成器
 *
 * @author zhangwenzhi
 * @description
 * @date 2020/8/13 9:16
 */
public class AttrGenerator {

    /**
     * 方法描述:姓名生成器 入口1
     * num-生成个数
     * surname-姓氏
     *
     * @author zhangwenzhi
     * @time 2020/4/15 9:06
     */
    public static List<Map> newName(String gender, String surname, int num) {
        List<Map> result = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Map<String, String> map = new HashMap<>();
            String name = "";
            //判断是否传入姓氏
            if (StringUtils.isBlank(surname)) {
                List<Map> list = ReadExcel.readExcelToListMap(FilesUtils.getExcelLocation("姓氏1074.xls"), "name", "rate");
                surname = Randomizer.generateResult(list, "name", "rate");
            }
            name += surname;
            map.put("surname", surname);
            //判断性别
            List<Object> list2;
            if (gender.equals(Constant.NAN)) {
                list2 = ReadExcel.randomFromExcel(FilesUtils.getExcelLocation("名-男.xls"), 1, "name");
            } else {
                list2 = ReadExcel.randomFromExcel(FilesUtils.getExcelLocation("名-女.xls"), 1, "name");
            }
            name += list2.get(0).toString();
            map.put("surname", surname);
            map.put("name", name);
            result.add(map);
        }
        return result;
    }

    //姓名生成器 入口2
    public static List<Map> newName(String gender, int num) {
        return newName(gender, "", num);
    }

    //姓名生成器 入口3
    public static List<Map> newName(String gender) {
        return newName(gender, "", 1);
    }

    //生成年龄
    public static double newAge() {
        double age = Randomizer.NormalDistributionWithBoundary(40, 1600, 0, 140);
        //0岁月份判断
        /*if(age == 0){
            age = (double)random(1,11) /100;
        }*/
        return age;
    }

    //生成生日
    public static LxDate newBirthday(int year) {
        int month = random(1, 12);
        int day = random(1, PubUtil.MonthContainDays(year, month));
        return new LxDate(year, month, day);
    }

    public static String randomType() {
        String[] type = {"D", "P", "T", "H"};
        return Randomizer.selectFromArray(type);
    }

    public static String randomGrade() {
        String[] grade = {"A", "B", "C", "D", "E", "F"};
        return Randomizer.selectFromArray(grade);
    }

    public static String randomGradeWeight(int weight) {
        List list = PubUtil.newList(
                PubUtil.newMap("name,rate", Constant.GradeArray[5]+"," + (3 * weight)),
                PubUtil.newMap("name,rate", Constant.GradeArray[4]+"," + (5 + 2 * weight)),
                PubUtil.newMap("name,rate", Constant.GradeArray[3]+"," + (10 + 15 * weight / 10)),
                PubUtil.newMap("name,rate", Constant.GradeArray[2]+"," + (20 + weight)),
                PubUtil.newMap("name,rate", Constant.GradeArray[1]+"," + (40 + 6 * weight / 10)),
                PubUtil.newMap("name,rate", Constant.GradeArray[0]+"," + (80))
        );
        return Randomizer.generateResult(list, "name", "rate");
    }

    public static Map<String, Integer> createGrowth(String type, String grade, int lv) {
        /*min growth rate*/
        final float MIN_GROWTH_RATE = 0.6f;

        int baseGrowth = 30, minGrowth, maxGrowth;

        for(int i=0;i<Constant.GradeArray.length;i++){
            if(Constant.GradeArray[i].equals(grade)){
                baseGrowth = 30 + 20*i;
                break;
            }
        }

        minGrowth = Math.round((float) baseGrowth / 5 * MIN_GROWTH_RATE);
        maxGrowth = 2 * baseGrowth / 5 - minGrowth;

        int sum = 0;
        int[] growthArr = new int[5];
        for (int i = 0; i < growthArr.length; i++) {
            growthArr[i] = random(minGrowth, maxGrowth);
            sum += growthArr[i];
        }
        int difference = baseGrowth - sum;
        int randomIndex;
        while (Math.abs(difference) > 0) {
            if (difference > 0) {
                //baseGrowth > sum
                randomIndex = PubUtil.getIndexFromArray(growthArr, maxGrowth, false);
                growthArr[randomIndex]++;
                difference--;
            } else {
                //baseGrowth < sum
                randomIndex = PubUtil.getIndexFromArray(growthArr, minGrowth, true);
                growthArr[randomIndex]--;
                difference++;
            }
        }

        int standJobAttr = baseGrowth / 10;

        switch (type) {
            case "D":
                growthArr[2] += 2 * standJobAttr;
                growthArr[4] += standJobAttr;
                break;
            case "P":
                growthArr[3] += 2 * standJobAttr;
                growthArr[1] += standJobAttr;
                break;
            case "T":
                growthArr[0] += 2 * standJobAttr;
                growthArr[2] += standJobAttr;
                break;
            case "H":
                growthArr[1] += 2 * standJobAttr;
                growthArr[3] += standJobAttr;
                break;
            default:
                break;
        }


        Map<String, Integer> growthMap = new HashMap<>();
        growthMap.put("hpg", growthArr[0]);
        growthMap.put("mpg", growthArr[1]);
        growthMap.put("adg", growthArr[2]);
        growthMap.put("apg", growthArr[3]);
        growthMap.put("spg", growthArr[4]);

        growthMap.putAll(getAttrFromGrowth(growthMap, lv));
        return growthMap;
    }

    public static Map<String, Integer> getAttrFromGrowth(Map<String, Integer> growth, int lv) {
        Map<String, Integer> attrMap = new HashMap<>();
        int hp = 5 * growth.get("hpg") * lv;
        int mp = 5 * growth.get("mpg") * lv;
        int ad = growth.get("adg") * lv;
        int ap = growth.get("apg") * lv;
        int speed = growth.get("spg") * lv;
        attrMap.put("hp", hp);
        attrMap.put("mp", mp);
        attrMap.put("ad", ad);
        attrMap.put("ap", ap);
        attrMap.put("speed", speed);
        return attrMap;
    }

    /**
     * get attribute from
     * type  grade  lv
     *
     * @author zhangwenzhi
     * @date 2020/8/27 9:58
     */
    public static Map<String, Integer> getAttr(String type, String grade, int lv) {
        Map<String, Integer> map = createGrowth(type, grade, lv);
        return getAttrFromGrowth(map, lv);
    }

    public static void main(String[] args) {
        /*D P T H  --  A B C D*/
        /*Map<String,Integer> map = createGrowth("T","A");
        System.out.println(map.toString());
        System.out.println(getAttrFromGrowth(map,1).toString());
        System.out.println(getAttrFromGrowth(map,2).toString());
        System.out.println(getAttrFromGrowth(map,3).toString());
        System.out.println(getAttrFromGrowth(map,4).toString());
        System.out.println(getAttrFromGrowth(map,5).toString());*/

        /*for(int i=0;i<10;i++){
            System.out.println(randomGradeWeight());
        }*/

        //System.out.println(newName("2", 20).toString());
        int weight = 100;

        Map map = PubUtil.newMap("A,B,C,D,E,F","0,0,0,0,0,0");
        String grade;
        for(int i=0;i<100;i++){
            grade = randomGradeWeight(weight);
            map.put(grade,Integer.valueOf(map.get(grade).toString())+1);
        }
        System.out.println(map.toString());
    }
}
