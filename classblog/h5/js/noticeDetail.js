let url = window.location.href.toString();
let id =url.split("=")[1];
function get_notice() {//获取公告详细内容
    let data = {
        "noticeId": id
    }
    getPostData('/blog/notice/showNoticeDeatil',data,'post',function (data) {
        if(data.code === 0) {
            $('#time2').html(data.data.createTime)
            $('#author').html(data.data.name)
            $('#title_content').html (data.data.noticeTheme);
            $('#notice_content').html (data.data.noticeContent);
        }else {
            alert(data.msg);
        }
    })
}
