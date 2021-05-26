$(function (){
    let data = {
        "keyword": "",
        "pageRequest": {
            "current": 1,
            "size": 100
        }
    }
    getPostData('/blog/message/getCommentMessageList',data,'post',function (data) {
        if(data.code === 0) {
            let like = data.data.records;
            let str = "";
            for (let i = 0; i < like.length; i++) {
                let head = like[i].value;
                let messageId = like[i].messageId;
                let postId = like[i].postId;
                let name = like[i].name;
                let postTheme = like[i].postTheme;
                let messageContent = like[i].messageContent;
                let isRead = like[i].isRead;
                let createTime = like[i].createTime;
                let url ;

                str = "<li class=\"msg\">\n" +
                    "                <img src=\""+ head +"\" class=\"head\">\n" +
                    "                <div class=\"content\">\n" +
                    "                    <a class=\"content\" href=\"#\">"+ name +"</a>\n" +
                    "                    <span>"+ messageContent +"</span>\n" +
                    "                    <a class=\"text\" href=\"#\"> 《"+postTheme+"》 </a>\n"+
                    "                    <div class=\"time\">"+ createTime +"</div>\n" +
                    "                </div>\n" +
                    "            </li>"
                $(".like_list").append(str);
            }
        }else {
            alert(data.msg);
        }
    })
})