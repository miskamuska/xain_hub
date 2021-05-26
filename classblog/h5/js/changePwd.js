let loginId
function get_id() {
    let url = window.location.href.toString();
    loginId =url.split("=")[1];
    $('#login-id').val(loginId);
}
function changePwd() {
    let pwd1 = $("#pw1").val();
    let pwd2 = $("#pw2").val();
    let data = {
        loginId: loginId,
        pwd1: pwd1,
        pwd2: pwd2
    }
    getPostData('/blog/login/forget-pwd/update',data,'post',function (data) {
        if(data.msg === 'success') {
            layer.msg("密码修改成功");
            window.location.href = '../html/login.html';
        }else if(pwd1 !== pwd2) {
            layer.msg(data.msg);
        }else if(pwd1.length < 6) {
            layer.msg(data.msg);
        }
        else {
            layer.msg(data.msg);
        }
    })
}