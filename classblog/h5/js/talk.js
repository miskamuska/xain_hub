$(function () {
    let url = window.location.href.toString();
    let id =url.split("=")[1];
    let data = {
        friendUserId: id,
    }
    getPostData('/blog/letter/showLetterRecord',data,'post',function (data) {
        if(data.code === 0) {
            // console.log(data);
            $("#friend-name").html(data.data.friendName);
            let headPicUrl = data.data.headPicUrl;
            let myHeadPicUrl = data.data.myHeadPicUrl;
            let friendUserId = data.data.friendUserId;
            let url = "../html/his_page.html?id="+friendUserId;
            for(let i=0; i < data.data.record.length ;i++) {
                let str = "";
                let content = data.data.record[i].content;
                let time = data.data.record[i].time;
                if(data.data.record[i].isMe === true) {
                    str = "<li class='message-r'><a href='../html/mypage.html' class='ava'>" +
                        "<img src=\""+ myHeadPicUrl +"\" class='img-circle pic' id='avatar'></a>" +
                        "<div><span class='talk-content'>" + content + "</span></div>" +
                        "<span class='time'>" + time + "</span></li>";
                } else {
                    str = "<li class='message-l'><a href=\"" +url + "\" class='ava'>" +
                        "<img src=\""+ headPicUrl +"\" ></a>" +
                        "<div><span class='talk-content'>" + content + "</span></div>" +
                        "<span class='time'>" + time + "</span></li>";
                }
                $("#msg-list").append(str);
            }
        }
        else {
            alert(data.msg);
        }
    })
})
