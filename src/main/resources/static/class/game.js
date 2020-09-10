let currentGame;

/*对局*/
function Game() {
    /*team,npc,round*/
    this.teamA = [];
    this.teamB = [];
    this.aliveMember = 0;
    this.round = -1;
    this.actionList = [];
    this.winner = "";

    /**
     * 技能方法
     */
    this.skillFun = {
        "setDamage": function (from, skill, target, damage) {
            $.each(target, function (i, val) {
                let real = val.setDamage(damage);
                GameUI.log(from.getName() + "使用" + skill.getName() + "对" + val.getName() + "造成" + real + "点伤害");

                //is d
                if (!val.isAlive()) {
                    GameUI.removeTeamMember(val);
                }
            })
        },
        "setTreat": function (from, skill, target, damage) {
            $.each(target, function (i, val) {
                let real = val.setTreat(damage);
                GameUI.log(from.getName() + "使用" + skill.getName() + "对" + val.getName() + "造成" + real + "点治疗");
            })
        },
    };

    currentGame = this;
}

/**
 * time
 * game begin
 * round begin
 * action begin
 * use skill
 * set damage/treat
 * get damage/treat
 * action end
 * round end
 * game end
 */

/**
 * 选择器
 */
Game.prototype.selector = {
    /**
     * return one of skills
     * @param npc
     */
    "skillSelector": function (npc) {
        let skills = npc.getSkills();

        if (skills === null || skills === undefined) {
            console.warn("skillSelector 技能为空");
            skills = [];
        }

        Util.noOrderArray(skills);

        for (let i = 0; i < skills.length; i++) {
            if (skills[i].getSkillMp() <= npc.getCurrent_mp()) {
                return skills[i];
            }
        }

        return new Skill(null);
    },

    /**
     *
     * @param team
     * @param num  -1:all
     * @param onlyLive 0-无限制 1-仅存活 2-仅死亡
     */
    "teamMemberSelector": function (team, num, onlyLive) {
        if (num === 0) {
            return [];
        }
        if (onlyLive == null) {
            onlyLive = 1;
        }
        Util.noOrderArray(team);
        let res = [];

        for (let i = 0; i < team.length; i++) {
            if ((onlyLive === "1" && team[i].isAlive()) ||
                (onlyLive === "2" && !team[i].isAlive()) ||
                onlyLive === "0") {
                res.push(team[i]);
            }
            if (res.length >= num && num !== -1) {
                return res;
            }
        }
        return res;
    },

    "replaceSelector": function (game, npcList) {
        let num = npcList.length;
        if (num < 1) {
            return npcList;
        }
        return this.teamMemberSelector(game.getTeam(npcList[0].getTeam()), num, "1");
    },
};

Game.prototype.nextRound = function () {
    this.round++;
    let member = this.teamA.concat(this.teamB);
    let skill, target_a, target_b;

    //add actionList
    for (let i = 0; i < member.length; i++) {
        if (!member[i].isAlive()) {
            continue;
        }
        if (member[i].getAction() === "manual") {
            continue;
        }
        skill = this.selector.skillSelector(member[i]);

        target_a = this.selector.teamMemberSelector(this.getTeam(member[i].getTeam()), skill.getTarget_a(), "1");
        target_b = this.selector.teamMemberSelector(this.getTargetTeam(member[i].getTeam()), skill.getTarget_b(), "1");
        this.enqueue(member[i], skill, target_a, target_b);
    }
};

Game.prototype.start = function () {
    this.nextRound();
};

//入队
Game.prototype.enqueue = async function (npc, skill, target_a, target_b) {
    if (this.actionList.length <= this.round) {
        this.actionList.push([])
    }
    this.actionList[this.round].push({
        "npc": npc,
        "skill": skill,
        "target_a": target_a,
        "target_b": target_b
    });

    let arr = this.actionList[this.round];

    //execute actionList  =>  arr
    if (arr.length < this.aliveMember) {
        return;
    }
    //sort
    for (let i = arr.length - 1; i > 0; i--) {
        for (let j = 0; j < i; j++) {
            if (arr[j].npc.getCurrent_speed() < arr[j + 1].npc.getCurrent_speed()) {
                [arr[j], arr[j + 1]] = [arr[j + 1], arr[j]];
            }
        }
    }

    //执行
    await this.executeAction(arr);
    /*for (let m = 0; m < arr.length; m++) {
        if (!arr[m].npc.isAlive()) {
            continue;
        }
        //Util.sleep(1000);
        this.useSkill(arr[m].npc, arr[m].skill, this.selector.replaceSelector(this, arr[m].target_a),
            this.selector.replaceSelector(this, arr[m].target_b))
    }*/


    //判断
    if (this.getAliveNumber("teamA") === 0) {
        console.log("teamB");
        this.winner = "teamB";
    } else if (this.getAliveNumber("teamB") === 0) {
        console.log("teamA");
        this.winner = "teamA";
    } else {
        this.aliveMember = this.getAliveNumber("all");

        //this.nextRound();
        setTimeout(function () {currentGame.nextRound();},1000);
    }
};


Game.prototype.executeActionOne = function (arr,m) {
    if(arr.length < m+1){
        return;
    }

    this.useSkill(arr[m].npc, arr[m].skill, this.selector.replaceSelector(this, arr[m].target_a),
        this.selector.replaceSelector(this, arr[m].target_b));

    if(arr.length > m+1){
        setTimeout(function () {
            currentGame.executeActionOne(arr,m+1);
        },1000);
    }
};

Game.prototype.executeAction = function (arr) {
    let i = 0;
    let t;
    t = setInterval(executeActionOne, 1000);

    function executeActionOne() {
        i++;
        if(i >= arr.length) {
            clearInterval(t);
            return;
        }

        if (!arr[i].npc.isAlive()) {
            console.log(1243);
            return;
        }
        //Util.sleep(1000);
        currentGame.useSkill(arr[i-1].npc, arr[i-1].skill, currentGame.selector.replaceSelector(currentGame, arr[i-1].target_a),
            currentGame.selector.replaceSelector(currentGame, arr[i-1].target_b));

    }
};

Game.prototype.getAliveNumber = function (name) {
    let team;
    if (name === "all") {
        team = this.teamA.concat(this.teamB);
    } else if (name === "teamA") {
        team = this.teamA;
    } else if (name === "teamB") {
        team = this.teamB;
    }
    let num = 0;
    for (let i = 0; i < team.length; i++) {
        if (team[i].isAlive()) {
            num++;
        }
    }
    return num;
};

Game.prototype.useSkill = function (npc, skill, target_a, target_b) {
    if (target_a.length < 1 && target_b.length < 1) {
        return;
    }
    let runable = skill.getRunable();
    let mp = skill.getSkillMp();
    eval(runable);
    npc.setCurrent_mp(npc.getCurrent_mp() - mp);
    GameUI.setProgress(npc);
};

Game.prototype.setTeam = function (name, para) {
    if (name === "teamA") {
        this.teamA = para;
        $.each(this.teamA, function (i, val) {
            /*手动*/
            //val.setAction("manual");
            val.setAction("auto");
            val.setTeam("teamA");
        });
        GameUI.addTeam("team_a", para);
    } else if (name === "teamB") {
        this.teamB = para;
        $.each(this.teamB, function (i, val) {
            /*自动*/
            val.setAction("auto");
            val.setTeam("teamB");
        });
        GameUI.addTeam("team_b", para);
    }
    this.aliveMember += para.length;
};

/**
 * 获取对立队伍(敌人)
 * @param name
 * @return {*}
 */
Game.prototype.getTargetTeam = function (name) {
    if (name === "teamB") {
        return this.teamA;
    } else if (name === "teamA") {
        return this.teamB;
    }
    return null;
};

Game.prototype.getTeam = function (name) {
    if (name === "teamA") {
        return this.teamA;
    } else if (name === "teamB") {
        return this.teamB;
    }
    return null;
};

Game.prototype.setNpc = function (para) {
    this.npc = para;
};
Game.prototype.getNpc = function () {
    return this.npc;
};
Game.prototype.setRound = function (para) {
    this.round = para;
};
Game.prototype.getRound = function () {
    return this.round;
};