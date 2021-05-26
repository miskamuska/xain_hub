function user() {//个人信息显示
    showPic();
    getPostData('/blog/student/showInformation','','post',function (data) {
        if(data.msg === 'success') {
            $('#user_name').html('你好！ ' + data.data.name);
        } else {
            alert('对不起，请重新登陆');
            window.location.href = '../html/login.html';
        }
    })
}
function user_info() {
    window.location.href = '../html/userinfo.html';
}
//显示头像
function showPic() {
    let url = '';
    getPostData('/blog/head-pic/showHeadPic','','post',function (data) {
        if(data.code === 0){
            $(".pic").attr('src',data.data.value);
            $(".my_head").attr('src',data.data.value);
        }else {
            alert(data.msg)
        }
    })
    return url;
}

function logout() {//退出登录
    getPostData('/blog/logout','','post',function (data) {
        if(data.code === 0) {
            if (confirm("你确定要退出吗")){
                window.location.href = '../html/login.html';
            }
        }else {
            alert('退出失败');
        }
    })
}

function get_counts(id) {
    $.ajax({
        url: "http://localhost:8081/blog/post/getAuthorInformation",
        headers: {
            token: localStorage.getItem('token'),
        },
        type: "post",
        dataType: "json",
        contentType: 'application/json',
        async : false,
        data: JSON.stringify({
            "userId": id
        }),
        success: function (data) {
            if(data.msg === 'success') {
                //console.log(data.data.postCount);
                document.getElementById('counts1').innerHTML = data.data.postCount
                document.getElementById('counts2').innerHTML = data.data.gainUpvote
                document.getElementById('counts3').innerHTML = data.data.commentCount
                document.getElementById('name').innerHTML = data.data.name;
                document.getElementById('head').src = data.data.value;
            } else {
                alert(data.msg);
            }
        },
        error: function (msg) {
            alert("ajax连接异常：" + msg);
        }
    })
}



