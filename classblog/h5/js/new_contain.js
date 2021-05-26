function get_sort() {//获取分类
    let url = window.location.href.toString();
    let url1 = decodeURI(url);
    let s = url1.split("=")[1]
    let sort = s.split("&")[0]
    let key =url1.split("=")[2];
    return sort
}
function search() {//搜索函数
    let key = $('#nav_input').val();
    window.open('../html/homepage.html?sortTheme='+get_sort()+'&key='+key);
}
function go_sort(sort) {//分类函数
    let url = window.location.href.toString();
    let url1 = decodeURI(url);
    let key =url1.split("=")[2];
    window.location.href = '../html/homepage.html?sortTheme='+sort+'&key='+key;
}
let page = 1;//分页控制
function load_more() {
    $('.load_more').hide();
    get_post(page,10);
}
function get_post(current,size) {//获取文章
    let url = window.location.href.toString();
    let url1 = decodeURI(url);
    let s = url1.split("=")[1]
    let sort = s.split("&")[0]
    let key =url1.split("=")[2];
    $('#nav_input').val(key);
    let data = {
        "keyword": key,
        "postListRequest": {
            "current": current,
            "size": size,
        },
        "sortId": sort,
    }
    getPostData('/blog/post/searchPost',data,'post',function (data) {
        if(data.code === 0) {
            page++;
            let posts  = data.data.records;
            let post_length = posts.length;//帖子总数
            let is_full = false;//判断当前帖子是否为最大数
            if(post_length < size) {
                is_full = true
            }
            if(post_length > 0) {//有帖子数据
                create_posts(posts,is_full)
            }else {
                let str = '';
                str ="<img src='../img/space3.png' id='space'>"
                $('div.post_list').append(str);//无帖子
            }
        }else {
            console(data.msg);
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
    let str = '';
    if(posts.length > 0) {
        for(i = 0; i < posts.length; i++) {
            let post_id = posts[i].postId;
            if (posts[i].isTop === true) {
                str += "<div class=\"post_container clearfix\" id=\""+ post_id +"\">\n" +
                    "  <div class=\"row_container clearfix\">\n" +
                    "    <div class=\"information_container \">\n" +
                    "      <div class=\"post_theme\">\n" +
                    "        <a class=\"post_title\" href=\"../html/post.html?id ="+ post_id +"\" target=\"_blank\">\n" +
                    "        "+ posts[i].postTheme +"<i class=\"glyphicon glyphicon-pushpin top_i\"></i>\n" +
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
            }else {
                str += "<div class=\"post_container clearfix\" id=\""+ post_id +"\">\n" +
                    "  <div class=\"row_container clearfix\">\n" +
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
        }
        $('div.post_list').append(str);//展示其
        if(is_full === false) {
            $('div.post_list').append(load_more_div);
        }

    }
}
function keyLogin(){//设置enter功能
    if (event.keyCode==13){  //回车键的键值为13
        document.getElementById("search_btn").click(); //调用登录按钮的登录事件
    }
}