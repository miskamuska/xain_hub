/**
 * ajax请求数据
 * apiUrl:为接口
 * data:请求参数
 * type:请求类型
 * callback:回调函数
 * */
function getPostData(apiUrl,data,type,callback) {
    if (arguments.length === 4) {
        $.ajax({
            type: type,
            url: 'http://localhost:8081'+apiUrl,
            headers: {
                token: localStorage.getItem('token'),
            },
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: 'json',
            success: callback,
            error: function (e) {
                layer.msg("ajax连接异常：" + e);
            }
        });
    } else if (arguments.length === 5) {
        $.ajax({
            type: type,
            url: 'http://localhost:8081'+apiUrl,
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: 'json',
            success: callback,
            error: function (e) {
                layer.msg("ajax连接异常：" + e);
            }
        });
    }

}
function getPostData_pic(apiUrl,data,type,callback) {
    $.ajax({
        type: type,
        url: 'http://localhost:8081'+apiUrl,
        headers: {
            token: localStorage.getItem('token'),
        },
        data: data,
        contentType: false,
        processData: false,
        success: callback,
        error: function (e) {
            layer.msg("ajax连接异常：" + e);
        }
    });
}

