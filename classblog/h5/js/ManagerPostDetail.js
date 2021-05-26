let url = window.location.href.toString();
console.log(url);
let id =url.split("=")[1];//文章id
function get_post() {
    let data = {
        "postId": id
    }
    getPostData('/blog/post/getPostDetail',data,'post',function (data) {
        if(data.msg === 'success') {
            let posts  = data.data;
            create_posts(posts)
        }else {
            alert(data.msg);
        }
    })
}
let like_click ;//判断是否点赞参数
let collect_click;//判断是否收藏参数
let str = ''
function create_posts(posts) {//展示函数
    document.getElementById('like_counts').innerHTML = posts.showLikeResponse.likeCount + '赞';//显示点赞数
    document.getElementById('collect_counts').innerHTML = posts.collectCount + '收藏';//显示收藏数

    if(posts.isUpvote === true) {//判断是否点赞
        like_click = 1;
        document.getElementById('like').style.background = '#EC7259'
        document.getElementById('like').style.color = 'white';
    }else {
        like_click = 0
    }
    if(posts.isCollect === true) {//判断是否收藏
        collect_click = 1
        document.getElementById('collect').style.background = '#EC7259'
        document.getElementById('collect').style.color = 'white';
    }else {
        collect_click = 0;
    }
    str += "<div class=\"post_container clearfix\" id=\""+ posts.postId+ "\">\n" +
        "  <div class=\"row_container clearfix\">\n" +
        "    <h1 class=\"post_title\">"+ posts.postTheme+ "</h1>\n" +
        "    <div class=\"writer_container \">\n" +
        "      <a href=\"../html/his_page.html?userId ="+ posts.userId+ "\">\n" +
        "      <img class=\"head_pic\" src=\""+ posts.headPicUrl+ "\" alt=\"头像\"></a>\n" +
        "      <div class=\"info\">\n" +
        "        <a class=\"writer_name\" href=\"../html/his_page.html?userId ="+ posts.userId+ "\">梁恪瑜</a>\n" +
        "        <div class=\"time\">"+ posts.updateTime+ "</div>\n" +
        "      </div>\n" +
        "    </div>\n" +
        "    <div class=\"post_content\">"+ posts.postContent+ "</div>\n" +
        "  </div>\n" +
        "</div>\n" +
        "<div class=\"comment\">\n" +
        "  <div class=\"my_comment\">\n" +
        "    <img class=\"my_head\" src=\""+ showPic() +"\" alt=\"头像\">\n" +
        "    <textarea class=\"comment_text\" id=\"comment_"+ posts.postId+ "\" placeholder=\"写下你的评论...\"></textarea>\n" +
        "    <div class=\"btn_div\">\n" +
        "    <button class=\"comment_btn\" value=\""+ posts.postId+ "\" onclick=\"comment(this.value)\">发表</button>\n" +
        "    </div>\n" +
        "  </div>\n" +
        "  <span class=\"comment_h\">全部评论:</span>\n" +
        "</div>"
    for(let i=0; i < posts.commentList.length; i++) {
        str += "\n" +
            "<div class=\"comment_div\">\n" +
            "  <div class=\"del_mod_div dropdown\">\n" +
            "    <button data-toggle=\"dropdown\" class=\"del_mod glyphicon glyphicon-chevron-down dropdown-toggle\"></button>\n" +
            "    <ul class=\"dropdown-menu pull-right menu\" style=\"min-width: 100px;\">\n" +
            "      <li><button class=\"del_a\" value=\""+posts.commentList[i].commentId+"\" onclick=\"delete_comment(this.value)\">删除该评论</button></li>\n" +
            "    </ul>\n" +
            "  </div>\n" +
            "  <div class=\"comment_info\">\n" +
            "    <a class=\"comment_heada\" href=\"../html/his_page.html?userId ="+posts.commentList[i].userId+"\"><img class=\"comment_head\" src=\""+posts.commentList[i].headPicUrl+"\"></a>\n" +
            "    <div class=\"comment_inf\">\n" +
            "      <a class=\"comment_name\" href=\"../html/his_page.html?userId ="+posts.commentList[i].userId+"\">"+posts.commentList[i].name+"</a>\n" +
            "      <div class=\"comment_time\">"+posts.commentList[i].createTime+"</div>\n" +
            "    </div>\n" +
            "  </div>\n" +
            "  <div class=\"comment_t\">\n" +
            "    "+posts.commentList[i].comment+"\n" +
            "    <div class=\"reply\">\n" +
            "      <i class=\"reply_i glyphicon glyphicon-comment\"></i>\n" +
            "      <button class=\"reply_btn\" data-toggle=\"collapse\" data-target=\"#demo"+posts.commentList[i].commentId+"\">回复</button>\n" +
            "      <div id=\"demo"+posts.commentList[i].commentId+"\" class=\"collapse\">\n" +
            "        <textarea class=\"comment_text\" id=\"comment_text1_"+posts.commentList[i].commentId+"\" placeholder=\"写下你的评论...\"></textarea>\n" +
            "        <div class=\"btn_div\">\n" +
            "          <button class=\"comment_btn\" value=\""+posts.commentList[i].commentId+"\" name=\""+posts.commentList[i].userId+"\" onclick=\"reply(this.value,this.name)\">发表</button>\n" +
            "        </div>\n" +
            "      </div>\n" +
            "    </div>\n" +
            "  </div>\n" +
            "</div>"

        if(posts.commentList[i].replyList.length > 0) {
            for(let j = 0;j < posts.commentList[i].replyList.length; j++) {
                str += "<div class=\"comment_div1\">\n" +
                    "  <div class=\"del_mod_div dropdown\">\n" +
                    "    <button data-toggle=\"dropdown\" class=\"del_mod glyphicon glyphicon-chevron-down dropdown-toggle\"></button>\n" +
                    "      <ul class=\"dropdown-menu pull-right menu\" style=\"min-width: 100px;\">\n" +
                    "        <li><button class=\"del_a\" value=\""+posts.commentList[i].replyList[j].replyId+"\" onclick=\"delete_reply(this.value)\">删除该回复</button></li>\n" +
                    "      </ul>\n" +
                    "  </div>\n" +
                    "  <div class=\"comment_info1\">\n" +
                    "    <img class=\"comment_head1\" src=\""+posts.commentList[i].replyList[j].fromHeadPicUrl+"\">\n" +
                    "      <div class=\"comment_inf1\">\n" +
                    "        <div class=\"comment_name1\">"+posts.commentList[i].replyList[j].fromName+"</div>\n" +
                    "        <div class=\"comment_time1\">"+posts.commentList[i].replyList[j].createTime+"</div>\n" +
                    "      </div>\n" +
                    "  </div>\n" +
                    "  <div class=\"comment_t1\">"+posts.commentList[i].replyList[j].revert+"</div>\n" +
                    "</div>"
            }
        }
    }
    $('div.post_list').append(str)
}

function like() {//点击点赞
    if(like_click === 0) {
        let data = {
            "postId": id
        }
        getPostData('/blog/upvote/upvote',data,'post',function (data) {
            if(data.code === 0) {
                layer.msg('点赞成功');
                setTimeout(function(){ location.reload(); }, 500);
            }else {
                alert(data.msg);
            }
        })
    }else {
        can_like();
    }
}
function collect() {//点击收藏
    if(collect_click === 0) {
        let data = {
            "postId": id
        }
        getPostData('/blog/collect/addCollect',data,'post',function (data) {
            if(data.code === 0) {
                layer.msg('收藏成功');
                setTimeout(function(){ location.reload(); }, 500);
            }else {
                alert(data.msg);
            }
        })
    }else {
        can_collect();
    }
}
function comment(id) {//评论
    let comment = $("#comment_"+id).val();
    if(comment === '') {
        layer.msg('评论不能为空')
    }else {
        let data = {
            postId: id,
            comment: comment
        }
        getPostData('/blog/comment/addComment',data,'post',function (data) {
            if(data.code === 0) {
                layer.msg('发表成功')
                $("#comment_"+id).val('');
            }else {
                alert(data.msg);
            }
        })
        location.reload();
    }
}
function can_like() {//取消点赞
    let data = {
        "postId": id
    }
    getPostData('/blog/upvote/cancelTheUpvote',data,'post',function (data) {
        if(data.code === 0) {
            layer.msg('取消成功');
            setTimeout(function(){ location.reload(); }, 500);
        }else {
            alert(data.msg);
        }
    })
}
function can_collect() {//取消收藏
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
function reply(commentid,userid) {//回复
    let reply = $("#comment_text1_"+commentid).val();
    if(reply === '') {
        layer.msg('评论不能为空')
    }else {
        let data = {
            "commentId": commentid,
            "reply": reply,
            "toUserId": userid
        }
        getPostData('/blog/reply/addReply',data,'post',function (data) {
            if(data.code === 0) {
                layer.msg('发表成功')
                $("#comment_text1_"+commentid).val('');
            }else {
                alert(data.msg);
            }
        })
        location.reload();
    }
}
function delete_comment(commentid) {//删除评论
    if (confirm('您是否要删除这条评论？')) {
        let data = {
            "commentId": commentid,
        }
        getPostData('/blog/comment/deleteComment',data,'post',function (data) {
            if(data.code === 0) {
                layer.msg('删除成功')
                setTimeout(function(){ location.reload(); }, 500);
            }else {
                alert(data.msg);
            }
        })
    }
}
function delete_reply(replyId) {//删除回复
    if (confirm('您是否要删除这条回复？')) {
        let data = {
            "replyId": replyId,
        }
        getPostData('/blog/reply/deleteReply',data,'post',function (data) {
            if(data.code === 0) {
                layer.msg('删除成功')
                setTimeout(function(){ location.reload(); }, 500);
            }else {
                alert(data.msg);
            }
        })
    }
}

