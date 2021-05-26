let Max = 99999;
$(function(){//删除和重置密码函数
    function addFunctionAlty(value, row, index) {
        return [
            '<button data-ripple id="pass" type="button" class="button ripple-container ripple">通过</button>',
            '<button data-ripple id="fail" type="button" class="button ripple-container ripple" style="margin-left: 50px;background-color: red">拒绝</button>',
        ].join('');
    }
    window.operateEvents = {
        'click #pass': function (e, value, row, index) {
            pass(row.studentNumber);
        }, 'click #fail': function (e, value, row, index) {
            fail(row.studentNumber);
        }
    };
    $('#table').bootstrapTable({
        ajax: function (request) {
            let data = {
                "postListRequest": {
                    "current": 1,
                    "size": Max
                },
                "studentName": "",
                "studentNumber": ""
            }
            getPostData('/blog/student/showReviewStudent',data,'post',function (data) {
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


function pass(studentId) {
    console.log(studentId)
    let isPass = "是";
    let data = {
        "isPass": isPass,
        "studentId": studentId
    }
    getPostData('/blog/student/reviewStudentAccounts',data,'post',function (data) {
        if(data.code === 0) {
            layer.confirm('您要通过此账号的申请吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
                layer.msg("已通过");
                setTimeout(function(){ location.reload(); }, 1000);
            })
        } else {
            alert(data.msg);
        }
    })
}

function fail(studentId) {
    let isPass = "否";
    let data = {
        isPass: isPass,
        studentId: studentId
    }
    getPostData('/blog/student/reviewStudentAccounts',data,'post',function (data) {
        if (data.code === 0) {
            layer.confirm('您要拒绝此账号的申请吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
                setTimeout(function(){ location.reload(); }, 1000);
            })
        }
        else {
            alert(data.msg);
        }
    })
}
function select() {
    let id = $('#select_Id').val()
    let name = $('#select_Name').val();
    console.log(id)
    let data = {
        "postListRequest": {
            "current": 1,
            "size": Max
        },
        "studentName": name,
        "studentNumber": id
    }
    getPostData('/blog/student/showReviewStudent',data,'post',function (data) {
        if (data.code === 0) {
            console.log(data);
            $('#table').bootstrapTable('load', data.data.records);
        } else {
            alert(data.msg);
        }
    })
}