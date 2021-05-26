let page = 1;//分页控制
function load_more() {
    $('.load_more').hide();
    get_post(page,10);
}
function get_post(current,size) {
    let data = {
        "current": current,
        "size": size,
    }
    getPostData('/blog/collect/showMyCollect',data,'post',function (data) {
        if(data.msg === 'success') {
            page++;
            let posts  = data.data;
            let post_length = posts.length;//帖子总数
            let is_full = false;
            if(post_length < size) {
                is_full = true
            }
            if(post_length > 0) {//有帖子数据
                create_posts(posts,is_full)
            }else {
                let str = '';
                str ="<img src='../img/space2.png' id='space' alt='您还没有收藏哦'>"
                $('div.post_list').append(str);//无帖子
            }
        }else {
            alert(data.msg);
        }
    })
}
function create_posts(posts,is_full) {//展示函数
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
            let post_id = posts[i].postId;
            str += "<div class=\"post_container clearfix\" id=\""+ post_id +"\">\n" +
                "  <div class=\"row_container clearfix\">\n" +
                "<div class=\"cancel\">\n" +
                "  <button class=\"glyphicon glyphicon-remove cancel_a\" value=\""+ post_id +"\" onclick=\"can_collect(this.value)\"></button>\n" +
                "</div>"+
                "    <div class=\"information_container \">\n" +
                "      <div class=\"post_theme\">\n" +
                "        <a class=\"post_title\" href=\"../html/post.html?id ="+ post_id +"\" target=\"_blank\">\n" +
                "        "+ posts[i].postTheme +
                "        </a>\n" +
                "      </div>\n" +
                "      <div class=\"post_content\">\n" +
                "      "+ posts[i].postContent +"\n" +
                "      </div>\n" +
                "    </div>\n" +
                "    <div class=\"writer_container \">\n" +
                "      <a class=\"writer_name\">"+ posts[i].name +"</a>\n" +
                "      <a class=\"like_count\">\n" +
                "        <i class=\"glyphicon glyphicon-heart like_i\"></i>\n" +
                "        <a class=\"like_count_1\">"+ posts[i].upvoteCount +"</a>\n" +
                "      </a>\n" +
                "      <a class=\"comment_count\">\n" +
                "        <i class=\"glyphicon glyphicon-comment comment_i\"></i>\n" +
                "        <a class=\"comment_count_1\">"+ posts[i].commentCount +"</a>\n" +
                "      </a>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>"
        }
        $('div.post_list').append(str);//展示其
        if(is_full === false) {
            $('div.post_list').append(load_more_div);
        }
    }
}
function can_collect(id) {//取消收藏
    let data = {
        "postId": id
    }
    getPostData('/blog/collect/deleteCollect',data,'post',function (data) {
        if(data.code === 0) {
            layer.msg('取消成功');
            setTimeout(function(){ location.reload(); }, 500);
        }else {
            alert(data.msg);
        }
    })
}
