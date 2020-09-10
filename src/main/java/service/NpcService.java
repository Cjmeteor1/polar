package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.NpcMapper;
import generator.NPCFactory;

import java.util.List;

/**
 * NpcService
 *
 * @author zhangwenzhi
 * @description
 * @date 2020/9/4 9:26
 */
public class NpcService {
    private NpcMapper npcMapper = new NpcMapper();

    public String selectNpcById(String id){
        return npcMapper.selectOne(id).toJSONString();
    }

    public void updateOrInsertNpc(String str){
        JSONObject npc = JSONObject.parseObject(str);
        JSONObject dbNPC = npcMapper.selectOne(npc.getString("npc_id"));
        if(dbNPC == null){
            npcMapper.insert(npc);
        }else{
            npcMapper.update(npc);
        }
        //TODO delete and insert skills
        npcMapper.deleteAllSkill(npc.getString("npc_id"));

        JSONArray skills = npc.getJSONArray("skills");
        JSONArray skillSavePara = new JSONArray();

        for(int i=0;i<skills.size();i++){
            JSONObject current = new JSONObject();
            current.put("npc_id",npc.getString("npc_id"));
            current.put("skill_id",skills.getJSONObject(i).getString("id"));
            skillSavePara.add(current);
        }
        npcMapper.saveAllSkill(skillSavePara);
    }

    public String queryAllSkill(String id){
        return JSON.toJSONString(npcMapper.queryAllSkill(id));
    }

    public List<JSONObject> queryAllSkillList(){
        return npcMapper.queryAllSkillList();
    }

    public String createNpc(String str){
        JSONObject map = JSONObject.parseObject(str);
        int num = map.containsKey("num")?map.getInteger("num"):1;
        JSONArray data = new JSONArray();
        JSONObject npc;
        for(int i=0;i<num;i++){
            npc = NPCFactory.createCharacter(map);
            data.add(npc);
        }
        return data.toJSONString();
    }
}
