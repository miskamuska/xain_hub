let Max = 99999
let page = 1;//分页控制
$(function(){
    function addFunctionAlty(value, row, index) {
        return [
            '<button data-ripple id="delete" class="button ripple ripple-container" style="margin-left: 30px;background-color: red;">删除</button>',
        ].join('');
    }
    window.operateEvents = {
        'click #delete': function (e, value, row, index) {
            delete_post(row.noticeId);
        }
    };
    $('#table').bootstrapTable({//展示列表
        ajax: function (request) {
            let data = {
                "content": "",
                "postListRequest": {
                    "current": 1,
                    "size": Max
                },
                "theme": ""
            }
            getPostData('/blog/notice/showNotice',data,'post',function (data) {
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
            field: 'noticeTheme',
            title: '标题',
            switchable: true,
            formatter: function (value, row, index) {
                let url = '../html/ManagerNoticeDetail.html?id='+row.noticeId;
                let a = "<a target='_blank' href="+url+">"+row.noticeTheme+"</a>";
                return a
            }
        }, {
            field: 'name',
            title: '发布人',
            switchable: true
        }, {
            field: 'createTime',
            title: '修改日期',
            switchable: true
        }, {
            field: 'operate',
            title: '操作',
            events: operateEvents,//给按钮注册事件
            formatter: addFunctionAlty//表格中增加按钮
        }],
    })
})
function delete_post(id) {
    layer.confirm('您确定要删除此公告吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
        let data = {
            "noticeId": id
        }
        getPostData('/blog/notice/deleteNotice',data,'post',function (data) {
            if(data.code === 0) {
                layer.msg('删除成功');
                location.reload();
            }else {
                alert('删除失败');
            }
        })
    })
}
function show_sort(sort,id) {//修改分类模态框显示原分类
    document.getElementById('ori_sort').innerHTML = sort;
    sort_id = id;
}
function change_sort() {
    let sort = $('#new_sort').val();
    let data = {
        "id": sort_id,
        "sortTheme": sort
    }
    getPostData('/blog/post/cancelTop',data,'post',function (data) {
        if(data.code === 0) {
            layer.msg('修改成功');
            location.reload();
        }else {
            alert('修改失败');
        }
    })
}
function select() {
    let theme = $('#select_theme').val()
    let data = {
        "content": '',
        "postListRequest": {
            "current": 1,
            "size": Max
        },
        "theme": theme
    }
    getPostData('/blog/notice/showNotice',data,'post',function (data) {
        if (data.code === 0) {
            $('#table').bootstrapTable('load', data.data.records);
        } else {
            alert(data.msg);
        }
    })
}