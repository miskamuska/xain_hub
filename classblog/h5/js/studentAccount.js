let Max = 99999;
$(function(){
    function addFunctionAlty(value, row, index) {
        return [
            '<button id="delete" data-ripple type="button" class="button ripple ripple-container" style="background-color: red">删除</button>',
            '<button id="reset" data-ripple type="button" class="button ripple ripple-container" style="margin-left: 50px;background-color: gray">重置</button>',
        ].join('');
    }
    window.operateEvents = {
        'click #delete': function (e, value, row, index) {
            del(row.studentNumber);
        }, 'click #reset': function (e, value, row, index) {
            reset(row.studentNumber);
        }
    };
$('#table').bootstrapTable({//展示列表
    ajax: function (request) {
        let data = {
            "name": "",
            "number": "",
            "pageRequest": {
                "current": 1,
                "size": Max
            }
        }
        getPostData('/blog/student/studentList',data,'post',function (data) {
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
        field: 'name',
        title: '姓名',
        switchable: true

    }, {
        field: 'studentNumber',
        title: '学号',
        switchable: true
    }, {
        field: 'operate',
        title: '操作',
        events: operateEvents,//给按钮注册事件
        formatter: addFunctionAlty//表格中增加按钮
    }],
})
})

function del(userId) {//删除账号
    let data = {
        userId: userId
    }
    getPostData('/blog/student/deleteStudent',data,'post',function (data) {
        if(data.code === 0) {
            layer.confirm('您确定要删除该用户吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
                layer.msg('删除成功！', {icon: 1});
                setTimeout(function(){ location.reload(); }, 500);
            });
            // if (confirm("你确定要删除吗")){
            //     layer.msg('删除成功！', {icon: 1});
            //     setTimeout(function(){ location.reload(); }, 500);
            // }
        } else {
            alert(data.msg);
        }
    })
}
function reset (id) {
    let data = {
        "loginId": id
    }
    getPostData('/blog/resetPassword',data,'post',function (data) {
        if (data.code === 0) {
            layer.confirm('您确定要重置该学生的密码吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
                layer.msg('重置成功');
            });
        } else {
            alert(data.msg);
        }
    })
}
function select() {
    let id = $('#select_Id').val()
    let name = $('#select_Name').val();
    let data = {
        "name": name,
        "number": id,
        "pageRequest": {
            "current": 1,
            "size": Max
        }
    }
    getPostData('/blog/student/studentList',data,'post',function (data) {
        if (data.code === 0) {
            console.log(data);
            $('#table').bootstrapTable('load', data.data.records);
        } else {
            alert(data.msg);
        }
    })
}

