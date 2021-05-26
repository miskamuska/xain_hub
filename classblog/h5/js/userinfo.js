$(function () {//显示个人信息
    showPic();
    getPostData('/blog/student/showInformation', '', 'post', function (data) {
        if (data.code === 0) {
            $("#studentName").val(data.data.name);
            $("#studentNumber").val(data.data.studentNumber);
            $("#email").val(data.data.email);
            $("#signature").val(data.data.personalSignature);
            if (data.data.gender === '男') {
                $("#gender1").attr('checked', 'true');
            }
            if (data.data.gender === '女') {
                $("#gender2").attr('checked', 'true');
            }
            $("#constellation").val(data.data.constellation);
            $("#day").val(data.data.birthdayDay);
            $("#year").val(data.data.birthdayYear);
            $("#month").val(data.data.birthdayMonth);
            $("#location").val(data.data.location);
            $('#user_name').html('你好！ ' + data.data.name);
            $('.my_name').html(data.data.name);
        } else {
            alert(data.msg);
        }
    })
})

//修改基本信息
function changInfo() {
    let data = {
        email: $("#email").val(),
        gender: $("input[type='radio']:checked").val(),
        personalSignature: $("#signature").val(),
        birthdayDay: $('#day option:selected').val(),
        birthdayMonth: $('#month option:selected').val(),
        birthdayYear: $('#year option:selected').val(),
        constellation: $('#constellation option:selected').val(),
        location: $("#location").val(),
    }
    getPostData('/blog/student/changeStudentInformation',data,'post',function (data) {
        if(data.code === 0) {
            layer.msg("修改成功！");
            setTimeout(function(){ location.reload(); }, 3000);
        } else {
            layer.msg(data.msg);
        }
    })
}

//修改密码
function changPassword() {
    $("#changeSubmit").click(function () {
        let data = {
            password: $("#password").val(),
            newPassword: $("#newPassword").val(),
            newPasswordAgain: $("#twicePassword").val(),
        }
        getPostData('/blog/student/changePassword', data, 'post', function (data) {
            if (data.code === 0) {
                layer.msg(" 密码修改成功");
                setTimeout(function () {
                    location.reload();
                }, 3000);
            } else {
                layer.msg(data.msg);
            }
        })
    })
}

//修改邮箱
//发送验证码
let timer_control;
let count = 60;
let cur_count;//记录当前秒数
function changeEmail() {
    let newEmail = $("#email").val();
    cur_count = count;
    $('#receive-code').attr('disabled',true);
    $('#receive-code').text(cur_count + "秒后重获");
    timer_control = window.setInterval(SetRemainTime,1000);
    let data = {
        newEmail: newEmail,
    }
    getPostData('/blog/student/updateEmail',data,'post',function (data) {
        if(data.msg === 'success') {
            layer.msg("验证码发送成功")
        }
        else {
            window.clearInterval(timer_control);// 停止计时器
            document.getElementById("receive-code").removeAttribute("disabled");//移除禁用状态改为可用
            $('#receive-code').text("重获验证码");
            layer.msg(data.msg);
        }
    })
}
function receive(){//检查验证码
    let newEmail = $("#email").val();
    let code = $("#code").val();
    let data = {
        code: code,
        newEmail: newEmail,
    }
    getPostData('/blog/student/updateEmail/verCode',data,'post',function (data) {
        if(data.msg === 'success') {
            layer.msg('修改成功');
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

//显示邮箱
$(function () {
    getPostData('/blog/student/showEmail','','post',function (data) {
        if(data.code === 0) {
            $("#newEmail").val(data.data);
        }else {
            alert(data.msg);
        }
    })
})
//显示密码
$(function () {
    getPostData('/blog/student/showPwd','','post',function (data) {
        if(data.code === 0) {
            $("#newPw").val(data.data);
        }else {
            alert(data.msg);
        }
    })
})
/**
 * 上传图片
 */
$("#changPic").change(function(){
    let formData = new FormData();
    formData.append('file',$("#changPic")[0].files[0]);
    console.log(formData)
    getPostData_pic('/blog/head-pic/uploadHeadPic',formData,'post',function (data) {
        if(data.code === 0){
            showPic();
            layer.msg("上传成功！");
        }else {
            layer.msg(data.msg);
        }
    })
})
//显示头像
function showPic() {
    getPostData('/blog/head-pic/showHeadPic','','post',function (data) {
        if(data.code === 0){
            $(".pic").attr('src',data.data.value);
        }else {
            alert(data.msg)
        }
    })
}
