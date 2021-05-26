function user() {//个人信息显示
    showPic();
    getPostData('/blog/student/showInformation','','post',function (data) {
        if(data.msg === 'success') {
            $('#user_name').html('你好！ ' + data.data.name);
            $('.my_name').html(data.data.name);
        } else {
            alert('对不起，请重新登陆');
            window.location.href = '../html/login.html';
        }
    })
}
//显示头像
function showPic() {
    let url = '';
    getPostData('/blog/head-pic/showHeadPic','','post',function (data) {
        if(data.code === 0){
            $(".pic").attr('src',data.data.value);
            $(".my_head").attr('src',data.data.value);
        }else {
            console(data.msg)
        }
    })
    return url;
}
function logout() {//退出登录
    layer.confirm('您确定要退出吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
        getPostData('/blog/logout','','post',function (data) {
            if(data.code === 0) {
                window.location.href = '../html/login.html';
            }else {
                alert(data.msg);
            }
        })
    });
}

function get_counts() {//获取文章的点赞数和评论数
    getPostData('/blog/post/getCount','','post',function (data) {
        if(data.msg === 'success') {
            document.getElementById('counts1').innerHTML = data.data.postCount
            document.getElementById('counts2').innerHTML = data.data.gainUpvote
            document.getElementById('counts3').innerHTML = data.data.commentCount
        } else {
            console(data.msg);
        }
    })
}
function user_info() {//进入个人信息页面
    window.location.href = '../html/userinfo.html';
}

function get_sort_btn() {//主界面动态添加分类改变颜色
    getPostData('/blog/sort/writeGetSort','','post',function (data) {
        if(data.msg === 'success') {
           let sort = data.data;
           let str = '';
           let cur_sort = change_sort();
           let flag = 0;
           for (let i = 0; i<sort.length; i++) {
               let j = i+1;
               if (sort[i].sortId === cur_sort) {
                   str += ' <a href="javascript:go_sort(\''+sort[i].sortId+'\');" class="w3-bar-item w3-button sort_item active1" id="a'+j+'">'+sort[i].sortTheme+'</a>'
                   flag = 1;
               }else {
                   str += ' <a href="javascript:go_sort(\''+sort[i].sortId+'\');" class="w3-bar-item w3-button sort_item" id="a'+j+'">'+sort[i].sortTheme+'</a>'
               }
           }
           $('#sort').append(str);
           if (flag === 0 ) {
               document.getElementById('a0').classList.add('active1');
           }

        } else {
            alert(data.msg);
        }
    })
}
function change_sort() {//获取当前分类sortid
    let url = window.location.href.toString();
    let url1 = decodeURI(url);
    let s = url1.split("=")[1]
    let sort = s.split("&")[0]
    return sort;
}

function get_his_counts(id) {
    let data = {
        "userId": id
    }
    getPostData('/blog/post/getAuthorInformation',data,'post',function (data) {
        if(data.msg === 'success') {
                //console.log(data.data.postCount);
                document.getElementById('counts1').innerHTML = data.data.postCount
                document.getElementById('counts2').innerHTML = data.data.gainUpvote
                document.getElementById(
                    'counts3').innerHTML = data.data.commentCount
                document.getElementById('name').innerHTML = data.data.name;
                document.getElementById('head').src = data.data.value;
            } else {
                alert(data.msg);
            }
    })
}
$(function (){
        getPostData('/blog/message/messageCount','','post',function (data) {
            if(data.msg === 'success') {
                if(data.data.messageCount > 0) {
                    $('.badge1').html(data.data.messageCount) ;
                }else {
                    $('.badge1').html();
                }

            } else {
                alert(data.msg);
            }
        })
})
function get_message_counts() {
    getPostData('/blog/message/upvoteCount','','post',function (data) {
        if(data.msg === 'success') {
            if(data.data.messageCount > 0) {
                $('#like_count').html(data.data.messageCount) ;
            }else {
                $('#like_count').html();
            }

        } else {
            console.log('点赞消息显示有误')
        }
    })
    getPostData('/blog/message/collectCount','','post',function (data) {
        if(data.msg === 'success') {
            if(data.data.messageCount > 0) {
                $('#collect_count').html(data.data.messageCount) ;
            }else {
                $('#collect_count').html();
            }

        } else {
            console.log('收藏消息显示有误')
        }
    })
    getPostData('/blog/message/commentAndReplyCount','','post',function (data) {
        if(data.msg === 'success') {
            if(data.data.messageCount > 0) {
                $('#com_count').html(data.data.messageCount) ;
            }else {
                $('#com_count').html();
            }

        } else {
            console.log('评论回复消息显示有误')
        }
    })
    getPostData('/blog/letter/showAllLetterCount','','post',function (data) {
        if(data.msg === 'success') {
            if(data.data.messageCount > 0) {
                $('#msg_count').html(data.data.messageCount) ;
            }else {
                $('#msg_count').html();
            }

        } else {
            console.log('私信消息显示有误')
        }
    })
}








