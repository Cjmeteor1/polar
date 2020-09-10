function ajax() {
    $.ajax({
        url: 'login/goHome',
        type: 'post',
        data: {},
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        dataType: 'json',
        success: function (res) {

        },
        error: function () {
            layer.msg("系统错误,请联系管理员!");
            return;
        }
    });
}

function AjaxRequest(url, data) {
    this.url = url;
    this.data = data;
    this.contentType = 'application/x-www-form-urlencoded;charset=UTF-8';
}

AjaxRequest.prototype.execute = function () {
    var result = null;
    var callback = this.callback;
    var failedCallback = this.failedCallback;

    $.ajax({
        type: 'post',
        async: false,
        url: this.url,
        data: this.data,
        dataType: 'json',
        contentType: this.contentType,
        success: function (data) {
            result = data;
        },
        error: function (XMLHttpRequest) {
            layer.alert('请求失败,HTTP' + XMLHttpRequest.status);
        }
    });
    return result;
};

