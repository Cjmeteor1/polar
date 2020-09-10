function Npc() {
    /*let npc_id, surname, name, gender, age, birthday, type, grade, lv,
        hp, mp, current_hp, current_mp, ad, ap, current_ad, current_ap, crits, speed, current_speed,
        hpg, mpg, adg, apg, spg, exp, team, action = "auto" manual;*/
    this.skills = [];
    //this.setAll(data);
}

Npc.create = function(map){
    /*num lv grade weight(1+)*/
    let res = callService("NpcService", "createNpc", JSON.stringify(map));
    if(!res.success){
        console.error("Npc.create出错");
    }
    let data = JSON.parse(res.data);
    let npcList = [],npc;
    for(let row of data){
        npc = new Npc();
        npc.setAll(row);
        npc.setSkills(JSON.parse(row.skills));
        npcList.push(npc);
    }
    return npcList;
};

Npc.prototype.isAlive = function(){
    return this.current_hp > 0;
};

Npc.prototype.setDamage = function (n) {
    let damage = n;
    this.current_hp -= n;
    if (this.current_hp < 0){
        damage = n+this.current_hp;
        this.current_hp =0;
        GameUI.log(this.name+"阵亡!")
    }
    GameUI.setProgress(this);
    return damage;
};

Npc.prototype.setTreat = function (n) {
    let treat = n;
    this.current_hp += n;
    if (this.current_hp > this.hp){
        treat = n+this.hp-this.current_hp;
        this.current_hp = this.hp;
    }
    return treat;
};

Npc.queryNpcById = function (id) {
    let npc = new Npc();
    let res = callService("NpcService", "selectNpcById", id);
    if(!res.success){return;}
    let data = JSON.parse(res.data);
    npc.setAll(data);

    //查询技能
    let sResult = callService("NpcService", "queryAllSkill", id);
    if(!sResult.success){return;}
    let skillArr = JSON.parse(sResult.data);
    npc.setSkills(skillArr);
    return npc;
};

Npc.prototype.save = function () {
    callService("NpcService", "updateOrInsertNpc", JSON.stringify(this));
};

/**
 * 获取奖励
 */
Npc.prototype.getReward = function (){
    let reward = this.lv;
    let arr = Util.constant.GradeArray;
    switch (this.grade) {
        case arr[0]:
            break;
        case arr[1]:
            reward = Math.round(1.5 * reward);
            break;
        case arr[2]:
            reward = Math.round(2 * reward);
            break;
        case arr[3]:
            reward = Math.round(3 * reward);
            break;
        case arr[4]:
            reward = Math.round(4 * reward);
            break;
        case arr[5]:
            reward = Math.round(5 * reward);
            break;
    }
    return reward;
};

Npc.prototype.getSkills = function () {
    return this.skills;
};

Npc.prototype.setSkills = function (skillArr) {
    for(let val of skillArr){
        this.skills.push(new Skill(val))
    }
};

Npc.prototype.setAll = function (data) {
    this.npc_id = data.npc_id;
    this.surname = data.surname;
    this.name = data.name;
    this.gender = data.gender;
    this.age = data.age;
    this.birthday = data.birthday;
    this.type = data.type;
    this.grade = data.grade;
    this.lv = data.lv;
    this.hp = data.hp;
    this.mp = data.mp;
    this.current_hp = data.current_hp;
    this.current_mp = data.current_mp;
    this.ad = data.ad;
    this.ap = data.ap;
    this.crits = data.crits;
    this.speed = data.speed;
    this.hpg = data.hpg;
    this.mpg = data.mpg;
    this.adg = data.adg;
    this.apg = data.apg;
    this.spg = data.spg;
    this.exp = data.exp;

    this.current_hp = data.current_hp === undefined?data.hp:data.current_hp;
    this.current_mp = data.current_mp=== undefined?data.mp:data.current_mp;
    this.current_ad = data.ad;
    this.current_ap = data.ap;
    this.current_speed = data.speed;
};

Npc.prototype.setId = function (para) {
    this.npc_id = para;
};
Npc.prototype.getId = function () {
    return this.npc_id;
};
Npc.prototype.setSurname = function (para) {
    this.surname = para;
};
Npc.prototype.getSurname = function () {
    return this.surname;
};
Npc.prototype.setName = function (para) {
    this.name = para;
};
Npc.prototype.getName = function () {
    return this.name;
};
Npc.prototype.setGender = function (para) {
    this.gender = para;
};
Npc.prototype.getGender = function () {
    return this.gender;
};
Npc.prototype.setAge = function (para) {
    this.age = para;
};
Npc.prototype.getAge = function () {
    return this.age;
};
Npc.prototype.setBirthday = function (para) {
    this.birthday = para;
};
Npc.prototype.getBirthday = function () {
    return this.birthday;
};
Npc.prototype.setType = function (para) {
    this.type = para;
};
Npc.prototype.getType = function () {
    return this.type;
};
Npc.prototype.setGrade = function (para) {
    this.grade = para;
};
Npc.prototype.getGrade = function () {
    return this.grade;
};
Npc.prototype.setLv = function (para) {
    this.lv = para;
};
Npc.prototype.getLv = function () {
    return this.lv;
};
Npc.prototype.setHp = function (para) {
    this.hp = para;
};
Npc.prototype.getHp = function () {
    return this.hp;
};
Npc.prototype.setMp = function (para) {
    this.mp = para;
};
Npc.prototype.getMp = function () {
    return this.mp;
};
Npc.prototype.setCurrent_hp = function (para) {
    this.current_hp = para;
};
Npc.prototype.getCurrent_hp = function () {
    return this.current_hp;
};
Npc.prototype.setCurrent_mp = function (para) {
    this.current_mp = para;
};
Npc.prototype.getCurrent_mp = function () {
    return this.current_mp;
};
Npc.prototype.setAd = function (para) {
    this.ad = para;
};
Npc.prototype.getAd = function () {
    return this.ad;
};
Npc.prototype.setAp = function (para) {
    this.ap = para;
};
Npc.prototype.getAp = function () {
    return this.ap;
};
Npc.prototype.setCurrent_ad = function (para) {
    this.current_ad = para;
};
Npc.prototype.getCurrent_ad = function () {
    return this.current_ad;
};
Npc.prototype.setCurrent_ap = function (para) {
    this.current_ap = para;
};
Npc.prototype.getCurrent_ap = function () {
    return this.current_ap;
};
Npc.prototype.setCrits = function (para) {
    this.crits = para;
};
Npc.prototype.getCrits = function () {
    return this.crits;
};
Npc.prototype.setSpeed = function (para) {
    this.speed = para;
};
Npc.prototype.getSpeed = function () {
    return this.speed;
};
Npc.prototype.setCurrent_speed = function (para) {
    this.current_speed = para;
};
Npc.prototype.getCurrent_speed = function () {
    return this.current_speed;
};
Npc.prototype.setHpg = function (para) {
    this.hpg = para;
};
Npc.prototype.getHpg = function () {
    return this.hpg;
};
Npc.prototype.setMpg = function (para) {
    this.mpg = para;
};
Npc.prototype.getMpg = function () {
    return this.mpg;
};
Npc.prototype.setAdg = function (para) {
    this.adg = para;
};
Npc.prototype.getAdg = function () {
    return this.adg;
};
Npc.prototype.setApg = function (para) {
    this.apg = para;
};
Npc.prototype.getApg = function () {
    return this.apg;
};
Npc.prototype.setSpg = function (para) {
    this.spg = para;
};
Npc.prototype.getSpg = function () {
    return this.spg;
};
Npc.prototype.setExp = function (para) {
    this.exp = para;
};
Npc.prototype.getExp = function () {
    return this.exp;
};
Npc.prototype.setTeam = function (para) {
    this.team = para;
};
Npc.prototype.getTeam = function () {
    return this.team;
};
Npc.prototype.setAction = function (para) {
    this.action = para;
};
Npc.prototype.getAction = function () {
    return this.action;
};