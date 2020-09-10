function Timer() {}

Timer.lastTime = new Date().getTime();
Timer.runList = [{
    "runable":null,
    "time":0
}];

setInterval(function () {
    Timer.execute();
},100);

Timer.enqueue = function (runable,time) {
    Timer.runList.push({
        "runable":runable,
        "time":time
    });
};

Timer.execute = function () {
    if(Timer.runList.length <= 1){
        return;
    }
    if(new Date().getTime() - Timer.lastTime >= Timer.runList[0].time){
        Timer.runList[1].runable();
        Timer.lastTime = new Date().getTime();
        Timer.runList.splice(0, 1);
    }
};