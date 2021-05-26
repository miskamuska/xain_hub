function submit() {//发布公告
    layedit.sync(index);
    let Theme = $('#title').val()//文章标题
    let Content = $('#text').val();//文章内容
    let data = {
        "noticeContent": Content,
        "noticeTheme": Theme
    }
    getPostData('/blog/notice/addNotice',data,'post',function (data) {
        if(data.code === 0) {
            layer.msg('发布成功')
            window.location.href = '../html/notice_manager.html';
        }else {
            layer.msg(data.msg);
        }
    })
}


