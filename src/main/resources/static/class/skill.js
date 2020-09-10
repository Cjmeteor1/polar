function Skill(data) {
    /*id, type, name, grade, xx, owner, target_a, target_b, mp, runable*/
    if(data === null){
        this.name = "基础攻击";
        this.runable = "this.skillFun.setDamage(npc,skill,target_b,Math.round(5*npc.lv))";
        this.mp = 0;
        this.target_a = 0;
        this.target_b = 1;

        this.id = "-1";
        this.type = "";
        this.grade = 0;
        this.xx ="";
        this.owner = "all";
    }else{
        this.id = data.id;
        this.type = data.type;
        this.name = data.name;
        this.grade = data.grade;
        this.xx = data.xx;
        this.owner = data.owner;
        this.target_a = data.target_a;
        this.target_b = data.target_b;
        this.mp = data.mp;
        this.runable = data.runable;
    }
}

Skill.prototype.setId = function (para) {
    this.id = para;
};
Skill.prototype.getId = function () {
    return this.id;
};
Skill.prototype.setType = function (para) {
    this.type = para;
};
Skill.prototype.getType = function () {
    return this.type;
};
Skill.prototype.setName = function (para) {
    this.name = para;
};
Skill.prototype.getName = function () {
    return this.name;
};
Skill.prototype.setGrade = function (para) {
    this.grade = para;
};
Skill.prototype.getGrade = function () {
    return this.grade;
};
Skill.prototype.setXx = function (para) {
    this.xx = para;
};
Skill.prototype.getXx = function () {
    return this.xx;
};
Skill.prototype.setOwner = function (para) {
    this.owner = para;
};
Skill.prototype.getOwner = function () {
    return this.owner;
};
Skill.prototype.setTarget_a = function (para) {
    this.target_a = para;
};
Skill.prototype.getTarget_a = function () {
    return this.target_a;
};
Skill.prototype.setTarget_b = function (para) {
    this.target_b = para;
};
Skill.prototype.getTarget_b = function () {
    return this.target_b;
};
Skill.prototype.setSkillMp = function (para) {
    this.mp = para;
};
Skill.prototype.getSkillMp = function () {
    return this.mp;
};
Skill.prototype.setRunable = function (para) {
    this.runable = para;
};
Skill.prototype.getRunable = function () {
    return this.runable;
};