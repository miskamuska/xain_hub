let Max = 99999
let sort_id = -1;
let page = 1;//分页控制
function load_more() {
    $('.load_more').hide();
    get_post(page,10);
}
$(function(){
    function addFunctionAlty(value, row, index) {
        let str = ''
        if(row.isTop === true){
            str = '<button data-ripple id="can_top" class="button ripple-container ripple" style="background-color: dodgerblue;width: 100px">取消置顶</button>'
        }else {
            str = '<button data-ripple id="top" class="button ripple ripple-container" style="width: 100px">置顶文章</button>'
        }
        return [
            str,
            '<button data-ripple id="mod" data-target="#change_sort" data-toggle="modal" class="button ripple ripple-container" style="margin-left: 20px;background-color: gray;">修改</button>',
            '<button id="delete"  class="button ripple-container ripple" style="margin-left: 20px;background-color: red;">删除</button>',
        ].join('');
    }
    window.operateEvents = {
        'click #top': function (e, value, row, index) {
            top_post(row.postId)
        },'click #can_top': function (e, value, row, index) {
            cancelTop(row.postId)
        }, 'click #mod': function (e, value, row, index) {
            get_sort(row.sortTheme,row.postId)
        },'click #delete': function (e, value, row, index) {
            delete_post(row.postId);
        }
    };
    $('#table').bootstrapTable({//展示列表
        ajax: function (request) {
            let data = {
                "postListRequest": {
                    "current": 1,
                    "size": Max,
                },
                "postTheme": ""
            }
            getPostData('/blog/post/search',data,'post',function (data) {
                if (data.code === 0 ) {
                    request.success({
                        row: data.data.records,
                    });
                    $('#table').bootstrapTable('load', data.data.records);
                } else {
                    alert("系统错误");
                }
            })
        },
        formatLoadingMessage:function()
        {
            return "数据加载中...";
        },
//每页显示
        formatRecordsPerPage:function(pageNumber)
        {
            return pageNumber+'行每页';
        },
//页码
        formatShowingRows:function(pageFrom, pageTo, totalRows)
        {
            return "第"+pageFrom+"-"+pageTo+"行，总共"+totalRows+ "行";
        },

        formatSRPaginationPreText :function(){
            return '上一页';
        },

        formatSRPaginationPageText:function(page){
            return '跳转至'+page;
        },
        formatSRPaginationNextText:function(){
            return '下一页';
        },

        formatDetailPagination:function(totalRows)
        {
            return "总计："+totalRows + "行";
        }
        ,
        formatSearch:function()
        {
            return  "搜索";
        }
        ,
        formatClearSearch:function()
        {
            return  "清除搜索";
        }
        ,
        formatNoMatches:function()
        {
            return  "未查询到相关记录";
        } ,
        formatPaginationSwitch:function()
        {
            return "隐藏显示分页";
        }
        ,
        formatPaginationSwitchDown:function()
        {
            return "显示分页";
        },
        formatPaginationSwitchUp:function()
        {
            return "隐藏分页";
        } ,
        formatRefresh:function()
        {
            return "刷新";
        }  ,
        formatToggle:function()
        {
            return "展开";
        }  ,
        formatToggleOn:function()
        {
            return "显示卡片视图";
        } ,
        formatToggleOff :function()
        {
            return "隐藏卡片视图";
        },
        formatColumns:function(){
            return "列视图";
        }
        ,
        formatColumnsToggleAll:function()
        {
            return 'Toggle all';
        }
        ,
        formatFullscreen:function()
        {
            return '全屏';
        } ,
        formatAllRows: function()
        {
            return '全部';
        },
        toolbar: '#toolbar',
        //striped: true,
        singleSelect: true,
        clickToSelect: true,
        sortName: "create_time",
        sortOrder: "desc",
        pageSize: 10,
        pageNumber: 1,
        pageList: "[10, 25, 50, 100, All]",
        showToggle: true,
        showRefresh: true,
        showColumns: true,
        showLoading: false,
        search: false,
        pagination: true,

        columns: [{
            field: 'postTheme',
            title: '标题',
            switchable: true,
            formatter: function (value, row, index) {
                let url = '../html/ManagerPostDetail.html?id='+row.postId;
                let a = "<a target='_blank' href="+url+">"+row.postTheme+"</a>";
                return a
            }
        }, {
            field: 'sortTheme',
            title: '类别',
            switchable: true
        }, {
            field: 'name',
            title: '作者',
            switchable: true
        }, {
            field: 'createTime',
            title: '发表时间',
            switchable: true
        },{
            field: 'status',
            title: '状态',
            switchable: true,
            formatter: function (value, row, index) {
                let a = '';
                if (row.isTop === true) {
                    a = '<p style="color: #31aa25">置顶</p>'
                }
                return a;
            }
        }, {
            field: 'operate',
            title: '操作',
            events: operateEvents,//给按钮注册事件
            formatter: addFunctionAlty//表格中增加按钮
        }],
    })
})


function delete_post(id) {
    let data = {
        "postId": id
    }
    layer.confirm('您确定要删除这篇帖子吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
        getPostData('/blog/post/deletePost',data,'post',function (data) {
            if(data.code === 0) {
                layer.msg('删除成功！');
                location.reload();
            }else {
                layer.msg(data.msg);
            }
        })
    });
    // if(confirm('您确定要删除这篇帖子吗？')) {
    //     getPostData('/blog/post/deletePost',data,'post',function (data) {
    //         if(data.code === 0) {
    //             layer.msg('删除成功');
    //             location.reload();
    //         }else {
    //             alert('删除失败');
    //         }
    //     })
    // }
}
function top_post(id) {
    let data = {
        "postId": id
    }
    layer.confirm('您确定要置顶这篇帖子吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
        getPostData('/blog/post/top',data,'post',function (data) {
            if(data.code === 0) {
                layer.msg('置顶成功!');
                location.reload();
            }else {
                layer.msg(data.msg);
            }
        })
    });
    // if(confirm('您确定要置顶这篇帖子吗？')) {
    //     getPostData('/blog/post/top',data,'post',function (data) {
    //         if(data.code === 0) {
    //             layer.msg('置顶成功');
    //             location.reload();
    //         }else {
    //             alert('置顶失败');
    //         }
    //     })
    // }
}
function cancelTop(id) {
    let data = {
        "postId": id
    }
    layer.confirm('您确定要取消置顶吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
        getPostData('/blog/post/cancelTop',data,'post',function (data) {
            if(data.code === 0) {
                layer.msg('取消置顶成功!');
                location.reload();
            }else {
                layer.msg(data.msg);
            }
        })
    });
    // if(confirm('您确定要取消置顶吗？')) {
    //     getPostData('/blog/post/cancelTop',data,'post',function (data) {
    //         if(data.code === 0) {
    //             layer.msg('取消置顶成功');
    //             location.reload();
    //         }else {
    //             alert('取消置顶失败');
    //         }
    //     })
    // }
}
function get_sort(sort,id) {//修改分类时显示原分类
    document.getElementById('ori_sort').innerHTML = sort;
    sort_id = id;
}
function change_sort() {//修改分类
    let sort = $('#sort option:selected').val();
    let data = {
        "postId": sort_id,
        "sortTheme": sort
    }
        getPostData('/blog/post/modifyPost',data,'post',function (data) {
            if(data.code === 0) {
                layer.msg(data.msg);
                setTimeout(function(){ location.reload(); }, 500);
            }else {
                alert('修改失败');
            }
        })
}
function select() {
    let theme = $('#select_theme').val()
    let data = {
        "postListRequest": {
            "current": 1,
            "size": Max,
        },
        "postTheme": theme
    }
    getPostData('/blog/post/search',data,'post',function (data) {
        if (data.code === 0) {
            $('#table').bootstrapTable('load', data.data.records);
        } else {
            alert(data.msg);
        }
    })
}