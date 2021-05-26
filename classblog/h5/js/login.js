function login() {//登录
    let loginId = $("#login-id").val();
    let password = $("#password").val();
    let data = {
        password: password,
        loginId: loginId
    }
    getPostData('/blog/login',data,'post',function (data) {
        if(data.msg === 'success') {
            localStorage.setItem('token',data.data.token);
            window.location.href = '../html/homepage.html?sortTheme='+''+'&key='+'';
        } else {
            layer.msg(data.msg);
        }
    },1)
}
//登录和注册窗口切换
$(function(){
    $("#switch_register").click(function(){
        $('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
        $('#switch_register').removeClass("switch_btn").addClass('switch_btn_focus');
        $('#switch_bottom').animate({left:'0',width:'40px'});
        $('#register').css('display','none');
        $('#web_qr_login').css('display','block');
    });
    $("#switch_login").click(function(){
        $('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
        $('#switch_register').removeClass("switch_btn_focus").addClass('switch_btn');
        $('#switch_bottom').animate({left:'120px',width:'40px'});
        $('#register').css('display','block');
        $('#web_qr_login').css('display','none');
    });
});

//发送验证码
let timer_control;//时间控制变量
let count = 60;//验证码有效时间
let cur_count;//记录当前秒数
function register() {
    let studentName = $("#name").val();
    let email = $("#email").val();
    let password = $("#pass-word").val();
    let password1 = $("#pass-word1").val();
    let studentNumber = $("#studentNumber").val();
    cur_count = count;
    $('#receive-code2').attr('disabled',true);
    $('#receive-code2').text(cur_count + "秒后重获");
    timer_control = window.setInterval(SetRemainTime,1000);
    let data = {
        email: email,
        password: password,
        password1: password1,
        name: studentName,
        studentNumber: studentNumber,
    }
    getPostData('/blog/register',data,'post',function (data) {
        if(data.msg === 'success') {
            layer.msg("验证码发送成功")
        } else {
            window.clearInterval(timer_control);// 停止计时器
            document.getElementById("receive-code2").removeAttribute("disabled");//移除禁用状态改为可用
            $('#receive-code2').text("重获验证码");
            layer.msg(data.msg);
        }
    })
}
function checkCode(){//检查验证码
    let studentName = $("#name").val();
    let email = $("#email").val();
    let password = $("#pass-word").val();
    let password1 = $("#pass-word1").val();
    let studentNumber = $("#studentNumber").val();
    let code = $("#code").val();
    let data = {
        code: code,
        registerRequest: {
            email: email,
            password: password,
            password1: password1,
            name: studentName,
            studentNumber: studentNumber,
        }
    }
    getPostData('/blog/register/code',data,'post',function (data) {
        if(data.msg === 'success') {
            layer.msg("恭喜您，注册成功！");
            setTimeout(function(){ location.reload(); }, 1000);
        }else {
            window.clearInterval(timer_control);// 停止计时器
            document.getElementById("receive-code2").removeAttribute("disabled");//移除禁用状态改为可用
            $('#receive-code2').text("重获验证码");
            layer.msg(data.msg);
        }
    })
}
//timer处理函数
function SetRemainTime() {
    if (cur_count === 0) {//超时重新获取验证码
        window.clearInterval(timer_control);// 停止计时器
        document.getElementById("receive-code2").removeAttribute("disabled");//移除禁用状态改为可用
        $('#receive-code2').text("重获验证码")
    } else {
        cur_count--;
        $('#receive-code2').text(cur_count + "秒后重获")
    }
}
function keyLogin(){//回车键设置
    if (event.keyCode==13){  //回车键的键值为13
        document.getElementById("login_btn").click(); //调用登录按钮的登录事件
    }
}
