package generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import service.NpcService;
import util.*;

import java.util.*;

import static util.Randomizer.random;


/**
 * @author zhangwenzhi
 * @description
 * @time 2020/4/13 14:03
 */
public class NPCFactory {
    private static NpcService npcService = new NpcService();

    /**
     * 方法描述: 随机人物生成器
     *
     * @author zhangwenzhi
     * @time 2020/4/15 16:23
     */
    public static JSONObject createCharacter(JSONObject para) {
        String surname = para.containsKey("surname") ? para.getString("surname") : "";
        String name = para.containsKey("name") ? para.getString("name") : "";
        String gender = para.containsKey("gender") ? para.getString("gender") : "";
        String birthday = para.containsKey("birthday") ? para.getString("birthday") : "";
        String type = para.containsKey("type") ? para.getString("type") : "";
        String grade = para.containsKey("grade") ? para.getString("grade") : "";
        int lv = para.containsKey("lv") ? para.getInteger("lv") : 1;
        int weight = para.containsKey("weight") ? para.getInteger("weight") : 1;

        //生成性别
        if (StringUtils.isBlank(gender)) {
            gender = String.valueOf(random(1, 2));
        }

        //判断姓名是否需要生成
        if (StringUtils.isBlank(surname) || StringUtils.isBlank(name)) {
            //生成姓名
            Map nameMap = AttrGenerator.newName(String.valueOf(gender), surname, 1).get(0);
            //生成姓氏
            if (StringUtils.isBlank(surname)) {
                surname = nameMap.get("surname").toString();
            }
            //生成名字
            if (StringUtils.isBlank(name)) {
                name = nameMap.get("name").toString();
            }
        }

        int age = 0;
        if (StringUtils.isBlank(birthday)) {
            //生成年龄
            age = (int) AttrGenerator.newAge();
            //生成生日
            birthday = AttrGenerator.newBirthday(new LxDate(Constant.START_DATE).getYear() - age).getDate();
        }

        //基本信息
        JSONObject character = new JSONObject();
        character.put("surname", surname);
        character.put("name", name);
        character.put("gender", gender);
        character.put("age", age);
        character.put("birthday", birthday);

        if (StringUtils.isBlank(type)) {
            type = AttrGenerator.randomType();
        }

        if (StringUtils.isBlank(grade)) {
            grade = AttrGenerator.randomGradeWeight(weight);
        }

        character.put("type", type);
        character.put("grade", grade);
        character.put("lv", lv);

        //生成成长值-属性值
        Map<String, Integer> growthMap = AttrGenerator.createGrowth(type, grade, lv);
        //成长值
        character.putAll(growthMap);

        //create skill  create team
        double dp = (double)character.getInteger("ad")/character.getInteger("ap")-1;
        String skills = createSkills(type,dp,0,9);

        character.put("skills", skills);
        character.put("team", "");
        character.put("npc_id", PubUtil.nextId());
        character.put("exp", 0);
        return character;
    }

    public static JSONObject createCharacter() {
        return createCharacter(new JSONObject());
    }

    /*
     * type 相性
     * grade:A   skill 5-9   Growth9   AD(s+1.5 ad+1.5) AP(mp+1.5 ap+1.5)  T(hp+3)  H(hp+0.7 ap+1.5 mp+0.8)
     * grade:B   skill 4-7   Growth7   AD(s+1 ad+1)     AP(mp+1 ap+1)      T(hp+2.0)  H(hp+0.5 ap+1 mp+0.5)
     * grade:C   skill 2-5   Growth5   AD(s+0.7 ad+0.7) AP(mp+0.7 ap+0.7)  T(hp+1.4)  H(hp+0.3 ap+0.7 mp+0.4)
     * grade:D   skill 1-3   Growth3   AD(s+0.5 ad+0.5) AP(mp+0.5 ap+0.5)  T(hp+1.0)  H(hp+0.2 ap+0.5 mp+0.3)
     */
    public static void batchNpc() {


    }

    public static void main(String[] args) {
        JSONObject jo = createCharacter();
        System.out.println("end");
    }


    /**
     * @param dp d/p-1 < 0.2
     * @author zhangwenzhi
     * @date 2020/9/8 15:05
     */
    private static String createSkills(String type, double dp, int min, int max) {
        int count = Randomizer.random(1, 2);
        List<JSONObject> list = npcService.queryAllSkillList();
        //洗牌
        Collections.shuffle(list);

        List<JSONObject> result = new LinkedList<>();
        String xx;
        for (JSONObject map : list) {
            xx = map.getString("xx");
            if (type.equals(xx) || ("D".equals(xx) && dp >= 0.2) || ("P".equals(xx) && dp <= -0.2)
                    || ("DP".equals(xx) && Math.abs(dp) < 0.2)) {
                result.add(map);
                if (count <= result.size()) {
                    return JSON.toJSONString(result);
                }
            }
        }

        return JSON.toJSONString(result);
    }


}
