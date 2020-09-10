function Util(){
}

Util.constant = {
    /*"甲乙丙丁戊己庚辛壬癸"*/
    "GradeArray":["己","戊","丁","丙","乙","甲"]
};

/**
 * 随机返回 -1,1
 * @param a
 * @param b
 * @return {number}
 */
Util.randomSort = function (a, b) {
    return Math.random()>.5 ? -1 : 1;
};

/**
 * 打乱数组顺序
 * @param arr
 */
Util.noOrderArray = function (arr) {
    arr.sort(Util.randomSort);
};


function sleep1(time){
    return new Promise(function(resolve){
        setTimeout(resolve, time);
    });
}

Util.sleep = function (time){
    var timeStamp = new Date().getTime();
    var endTime = timeStamp + time;
    while(true){
        if (new Date().getTime() > endTime){
            return;
        }
    }
};


/*(async function() {
    console.log('Do some thing, ' + new Date());
    await sleep(3000);
    console.log('Do other things, ' + new Date());
})();*/

function callService(serviceName,methodName,para) {
    let obj = {
        "serviceName":serviceName,
        "methodName":methodName,
        "para":para,
    };

    let result = null;

    $.ajax({
        url : "/main/service",
        data : obj,
        async : false,
        dataType : "json",
        type : "post",
        success : function(data) {
            result = data;
        },
        error : function() {
            //layer.msg('请求出错',{icon: 2});
        }
    });

    return result;
}

function callServiceWithoutResult(serviceName,methodName,para) {
    let obj = {
        "type":"service",
        "serviceName":serviceName,
        "methodName":methodName,
        "para":para,
    };
    send(JSON.stringify(obj));
}
