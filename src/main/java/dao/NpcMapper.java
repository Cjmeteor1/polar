package dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * NpcMapper
 *
 * @author zhangwenzhi
 * @description
 * @date 2020/8/17 14:28
 */
public class NpcMapper {
    private SqlSession sqlSession;

    public NpcMapper(){
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try{
            inputStream = Resources.getResourceAsStream(resource);
        }catch (IOException e){
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    public JSONObject selectOne(String id){
        return sqlSession.selectOne("NpcMapper.selectByID", id);
    }

    public void insert(JSONObject para){
        sqlSession.insert("NpcMapper.insert", para);
        sqlSession.commit();
    }

    public void update(JSONObject para){
        sqlSession.insert("NpcMapper.update", para);
        sqlSession.commit();
    }

    public List queryAllSkill(String npc_id){
        return sqlSession.selectList("NpcMapper.queryAllSkill", npc_id);
    }

    public List<JSONObject> queryAllSkillList(){
        return sqlSession.selectList("NpcMapper.queryAllSkillList");
    }

    public void deleteAllSkill(String id){
        sqlSession.update("NpcMapper.deleteAllSkill", id);
        sqlSession.commit();
    }

    public void saveAllSkill(List list){
        sqlSession.insert("NpcMapper.saveAllSkill", list);
        sqlSession.commit();
    }

    public List<JSONObject> selectByTeamID(String id){
        return sqlSession.selectList("NpcMapper.selectByTeamID",id);
    }


    /*public NPC selectOne(String id){
        return sqlSession.selectOne("NpcMapper.selectByID", id);
    }

    public List<NPC> selectByTeamID(String team){
        NpcMapper npcMapper = new NpcMapper();
        List<NPC> list = sqlSession.selectList("NpcMapper.selectByTeamID", team);

        for(NPC npc:list){
            npc.setCurrent();
            npc.setSkills(npcMapper.queryAllSkill(npc.getNpc_id()));
        }
        return list;
    }



    public void saveNpcSkill(Map para){
        sqlSession.insert("NpcMapper.saveAllSkill", para);
        sqlSession.commit();
    }

    public List<Skill> queryAllSkill(String npc_id){
        Map para = new HashMap();
        para.put("npc_id",npc_id);
        List<Skill> result = new LinkedList<>();
        List<Map> list = sqlSession.selectList("NpcMapper.queryAllSkill", para);

        for(Map map:list){
            result.add(new Skill(map.get("skill_id").toString()));
        }
        return result;
    }*/




}
