function get_post(current,size) {
    // console.log(1)
    $.ajax({
        url: "http://localhost:8081/blog/notice/showNotice",
        type: "post",
        dataType: "json",
        contentType: 'application/json',
        async:false,
        headers: {
            token: localStorage.getItem('token'),
        },
        data: JSON.stringify({
            "keyWord": "",
            "postListRequest": {
                "current": current,
                "size": size
            },
        }),
        success: function (data) {
            if(data.code === 0) {
                let posts  = data.data.records;
                let post_length = posts.length;//帖子总数
                console.log(posts);
                let is_full = false;
                if(post_length < size) {
                    is_full = true
                }
                if(post_length > 0) {
                    //有帖子数据
                    create_posts(posts,is_full)
                }
            }else {
                alert(data.msg);
            }
        },
        error: function (msg) {
            alert("ajax连接异常：" + msg);
        }
    });
}
function create_posts(posts,is_full) {//展示函数
    //console.log(posts.length);
    let i;
    let load_more_div = document.createElement('div');//加载更多
    load_more_div.className = 'load_more';
    let load_more_a = document.createElement('a');
    load_more_a.className = 'load_more_a';
    load_more_a.innerText = '加载更多'
    load_more_a.href = 'javascript:load_more()'
    load_more_div.appendChild(load_more_a);

    let str = ''
    if(posts.length > 0) {
        for(i = 0; i < posts.length; i++) {
            str += "<div class=\"row_container clearfix\">\n" +
                "  <div class=\"information_container \">\n" +
                "    <div class=\"post_theme\">\n" +
                "      <a class=\"post_title\" href=\"../html/noticeDetail.html?id="+posts[i].noticeId+"\">"+posts[i].noticeTheme+"</a>\n" +
                "    </div>\n" +
                "    <div class=\"post_content\">"+posts[i].noticeContent+"</div>\n" +
                "  </div>\n" +
                "  <div class=\"time \">"+posts[i].createTime+"</div>\n" +
                "</div>"
        }
        $('div.post_list').append(str);//展示其
        if(is_full === false) {
            $('div.post_list').append(load_more_div);
        }
    }
}
