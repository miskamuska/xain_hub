let page = 1;//分页控制
function load_more() {
    $('.load_more').hide();
    get_post(page,10);
}
function get_post(current,size) {
    let data = {
        "keyWord": "",
        "pageRequest": {
            "current": current,
            "size": size
        },
    }
    getPostData('/blog/post/getMyPostList',data,'post',function (data) {
        if(data.code === 0) {
            page++;
            let posts  = data.data.records;
            let post_length = posts.length;//帖子总数
            // console.log(posts[0].id);
            let is_full = false;
            if(post_length < size) {
                is_full = true
            }
            if(post_length > 0) {//有帖子数据
                create_posts(posts,is_full)
            }else {
                let str = '';
                str ="<img src='../img/space1.png' id='space'>"
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
    let str = '';
    if(posts.length > 0) {
        for(i = 0; i < posts.length; i++) {
            let post_id = posts[i].postId;
            str += "<div class=\"post_container clearfix\" id=\""+ post_id +"\">\n" +
                "  <div class=\"row_container clearfix\">\n" +
                "<div class=\"del_mod_div dropdown\">\n" +
                "    <button data-toggle=\"dropdown\" class=\"del_mod glyphicon glyphicon-chevron-down dropdown-toggle\"></button>\n" +
                "        <ul class=\"dropdown-menu pull-right menu\" style=\"min-width: 100px;\">\n" +
                "            <li><button data-toggle=\"modal\" data-target=\"#change_sort\" class=\"mod_a\" value=\""+posts[i].sortTheme+"\" name=\""+ post_id +"\" onclick=\"show_sort(this.value,this.name)\">修改分类</button></li>\n" +
                "            <li><button class=\"del_a\" value=\""+ post_id +"\" onclick=\"delete_post(this.value)\">删除帖子</button></li>\n" +
                "        </ul>\n" +
                "</div>" +
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
                "<div class=\"sort\">"+posts[i].sortTheme+"</div>" +
                "  </div>\n" +
                "</div>"

        }
        $('div.post_list').append(str);//展示其
        if(is_full === false) {
            $('div.post_list').append(load_more_div);
        }
    }
}
function delete_post(id) {//删除帖子
    let data = {
        "postId": id
    }
    layer.confirm('您确定要删除这篇帖子吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
        getPostData('/blog/post/deleteMyPost',data,'post',function (data) {
            if(data.code === 0) {
                layer.msg('删除成功');
                location.reload();
            }else {
                alert('删除失败');
            }
        })
    })
}
function show_sort(sort,id) {//修改框原分类显示
    document.getElementById('ori_sort').innerHTML = sort;
    sort_id = id;
    console.log(sort_id)
}
function change_sort() {//修改分类
    let sort = $('#sort option:selected').val();
    let data = {
        "postId": sort_id,
        "sortTheme": sort
    }
    layer.confirm('您确定更改该帖子的分类吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
        getPostData('/blog/post/modifyPost',data,'post',function (data) {
            if(data.code === 0) {
                layer.msg('修改成功！');
                setTimeout(function(){ location.reload(); }, 500);
            }else {
                layer.msg(data.msg);
            }
        })
    });
    // if(confirm('您确定更改该帖子的分类吗？')) {
    //     getPostData('/blog/post/modifyPost',data,'post',function (data) {
    //         if(data.code === 0) {
    //             layer.msg(data.msg);
    //             setTimeout(function(){ location.reload(); }, 500);
    //         }else {
    //             alert('修改失败');
    //         }
    //     })
    // }
}