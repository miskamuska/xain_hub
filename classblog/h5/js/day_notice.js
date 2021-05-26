


$(function (){
    getPostData('/blog/notice/showHomePageNotice','','post',function (data) {
        if(data.code === 0) {
            let str = "";
            for (let i = 0; i < 2; i++) {
                let title = data.data[i].noticeTheme;
                let day = data.data[i].day;
                let yearMonth = data.data[i].yearMonth;
                let noticeId = data.data[i].noticeId;
                let url = "../html/noticeDetail.html?id="+noticeId;

                str = "<li><a href=" +url + " class='gp-pr'>"
                    + "<div class='topDate04 gp-pa white gp-center'><span class='gp-f12' id='yearMonth'>"
                    + yearMonth
                    + "</span><i class='gp-center'>" + "</i><strong class='gp-f18 gp-fwn' id='day'>"
                    + day
                    + "</strong></div>"
                    + "<h3 class='gp-text04 gp-fwn gp-f16' id='title'>"
                    + title+"</h3>"
                    + "</a></li>";
                $("#notice-list").append(str);
            }
        }else {
            alert(data.msg);
        }
    })
})