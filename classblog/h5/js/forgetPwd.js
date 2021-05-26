let timer_control;
let count = 60;
let cur_count;//记录当前秒数
function receiveCode() {
    let loginId = $("#login-id").val();
    let email = $("#email").val();
    cur_count = count;
    $('#receive-code').attr('disabled',true);
    $('#receive-code').text(cur_count + "秒后重获");
    timer_control = window.setInterval(SetRemainTime,1000);
    let data = {
        "loginId": loginId,
        "email": email,
    }
    getPostData('/blog/login/forget-pwd',data,'post',function (data) {
        if(data.msg === 'success') {
            layer.msg("验证码发送成功");
        }else {
            window.clearInterval(timer_control);// 停止计时器
            document.getElementById("receive-code").removeAttribute("disabled");//移除禁用状态改为可用
            $('#receive-code').text("重获验证码");
            layer.msg(data.msg);
        }
    })
}

function checkCode(){
    let loginId = $("#login-id").val();
    let code = $("#code").val();
    let data = {
        "code": code
    }
    getPostData('/blog/login/forget-pwd/code',data,'post',function (data) {
        if(data.msg === 'success') {
            window.location.href = '../html/changePwd.html?loginId ='+ loginId;
        }else {
            window.clearInterval(timer_control);// 停止计时器
            document.getElementById("receive-code").removeAttribute("disabled");//移除禁用状态改为可用
            $('#receive-code').text("重获验证码");
            layer.msg(data.msg);
        }
    })
}
//timer处理函数
function SetRemainTime() {
    if (cur_count === 0) {//超时重新获取验证码
        window.clearInterval(timer_control);// 停止计时器
        document.getElementById("receive-code").removeAttribute("disabled");//移除禁用状态改为可用
        $('#receive-code').text("重获验证码")
    } else {
        cur_count--;
        $('#receive-code').text(cur_count + "秒后重获")
    }
}
