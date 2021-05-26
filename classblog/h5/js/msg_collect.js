$(function (){
    let data = {
        "keyword": "",
        "pageRequest": {
            "current": 1,
            "size": 100
        }
    }
    getPostData('/blog/message/getCollectMessageList',data,'post',function (data) {
        if(data.code === 0) {
            let like = data.data.records;
            if (like.length === 0) {
                let str = '';
                str ="<img src='../img/space4.png' id='space'>"
                $('.like_list').append(str);//无帖子
            }else {
                let str = "";
                for (let i = 0; i < like.length; i++) {
                    let head = like[i].value;
                    let messageId = like[i].messageId;
                    let postId = like[i].postId;
                    let fromUserId = like[i].fromUserId;
                    let name = like[i].name;
                    let postTheme = like[i].postTheme;
                    let messageContent = like[i].messageContent;
                    let isRead = like[i].isRead;
                    let createTime = like[i].createTime;
                    let url ;
                    let bac_color = "";
                    if (isRead === false) {
                        bac_color = "style='background-color: #F6FBFF'";
                    }

                    str = "<li class=\"msg\""+ bac_color+">\n" +
                        "                <a href='"+"../html/his_page.html?userId ="+fromUserId+"'><img src=\""+ head +"\" class=\"head\"></a>\n" +
                        "                <div class=\"content\">\n" +
                        "                    <a class=\"content\" href=\""+"../html/his_page.html?userId ="+fromUserId+"\">"+ name +"</a>\n" +
                        "                    <span>"+ messageContent +"</span>\n" +
                        "                    <a class=\"text\" href=\""+"../html/post.html?id ="+postId+"\"> 《"+postTheme+"》 </a>\n"+
                        "                    <div class=\"time\">"+ createTime +"</div>\n" +
                        "                </div>\n" +
                        "            </li>"
                    $(".like_list").append(str);
            }

                getPostData('/blog/message/clearCollectCount','','post',function (data) {
                    if(data.code === 0) {

                    }else {
                        console.log('清空失败')
                    }
                })
            }
        }else {
            alert(data.msg);
        }
    })
})