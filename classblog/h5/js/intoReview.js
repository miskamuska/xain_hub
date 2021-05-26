function administratorLogin() {//管理员登录
    let loginId = $("#loginId").val();
    let password = $("#password").val();
    let data = {
        loginId: loginId,
        password: password,
    }
    getPostData('/blog/admin-login',data,'post',function (data) {
        if(data.msg === 'success') {
            localStorage.setItem('token',data.data.token);
            window.location.href = '../html/manager.html';
        } else {
            layer.msg(data.msg);
        }
    })
}
function keyLogin(){//回车键设置
    if (event.keyCode==13){  //回车键的键值为13
        document.getElementById("login_btn").click(); //调用登录按钮的登录事件
    }
}