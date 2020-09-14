function GameUI() {}

GameUI.number = {
    team_a: 0,
    team_b: 0
};

GameUI.addTeam = function (teamName, teamMember) {
    for (let npc of teamMember) {
        GameUI.addTeamMember(teamName, npc);
    }
};

GameUI.addTeamMember = function (teamName, npc) {
    if (GameUI.number[teamName] >= 4) {
        return;
    }

    let html = '<div class="npc" id="npc_' + npc.getId() + '">\n' +
        '            <div class="npc-body">\n' +
        npc.getName() +
        '            </div>\n' +
        '            <div class="buff-line">\n' +
        '                <!--<div class="buff">\n' +
        '                    <span class="buff-name">甲</span>\n' +
        '                    <span class="buff-value">10</span>\n' +
        '                </div>\n' +
        '                <div class="buff">\n' +
        '                    <span class="buff-name">毒</span>\n' +
        '                    <span class="buff-value">133</span>\n' +
        '                </div>-->\n' +
        '            </div>\n' +
        '            <div class="progress hp">\n' +
        '                <div class="progress_bar red"></div>\n' +
        '                <div class="progress_value">' + npc.getCurrent_hp() + '/' + npc.getHp() + '</div>\n' +
        '            </div>\n' +
        '            <div class="progress mp">\n' +
        '                <div class="progress_bar aqua"></div>\n' +
        '                <div class="progress_value">' + npc.getCurrent_mp() + '/' + npc.getMp() + '</div>\n' +
        '            </div>\n' +
        '        </div>';
    $("#" + teamName).append(html);
    GameUI.number[teamName]++;

    GameUI.setProgress(npc);
};

GameUI.removeTeamMember = function (npc) {
    $("#npc_" + npc.getId()).remove();
    //console.log($("#npc_" + npc.getId()).parentElement)
};

GameUI.setProgress = function (npc) {
    $("#npc_" + npc.getId() + " > .hp > .progress_bar").css("width", 100 * npc.getCurrent_hp() / npc.getHp() + "%");
    $("#npc_" + npc.getId() + " > .hp > .progress_value").html(npc.getCurrent_hp() +"/"+ npc.getHp());

    $("#npc_" + npc.getId() + " > .mp > .progress_bar").css("width", 100 * npc.getCurrent_mp() / npc.getMp() + "%");
    $("#npc_" + npc.getId() + " > .mp > .progress_value").html(npc.getCurrent_mp() +"/"+ npc.getMp());
};

GameUI.log = function (msg) {
    $("#battle_msg").append("<div>"+msg+"</div>");
};

function MainUI() {

}



