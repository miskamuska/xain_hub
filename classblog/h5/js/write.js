function submit() {//提交按钮
    layedit.sync(index)
    let sortId = $('#sort option:selected').val();//文章类别
    let postTheme = $('#title').val()//文章标题
    let postContent = $('#text').val()
    let data = {
        "postContent": postContent,
        "postTheme": postTheme,
        "sortTheme": sortId,
    }
    getPostData('/blog/post/writePost',data,'post',function (data) {
        if(data.code === 0) {
            layer.msg('发布成功');
            setTimeout(function(){ window.location.href = 'homepage.html?sortTheme=&key='; }, 1000);
        }else {
            layer.msg(data.msg);
        }
    })
}
function get_sort_option() {//获取分类选择
    getPostData('/blog/sort/getAllSort','','post',function (data) {
        if(data.code === 0) {
            for (let i = 0; i < data.data.length; i++) {
                $('#sort').append(new Option (data.data[i]));
                $('#select_sort').append(new Option (data.data[i]));
            }
        }else {
            layer.msg(data.msg);
        }
    })
}
