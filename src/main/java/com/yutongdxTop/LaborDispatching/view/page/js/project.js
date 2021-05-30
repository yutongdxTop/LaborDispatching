layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
    var clientId = -1;
    //按照服务器部署的网址划分网址
    var html = decodeURI(parent.location.href.split('/')[parent.location.href.split('/').length - 1].split('?')[0]);
    if (html === "clientIndex.html") {  //若上一级界面是客户主界面则只展示客户发布的项目信息
        clientId = decodeURI(window.top.location.href.split('?')[1].split('&')[1])
    }

    //信息列表
    var tableIns = table.render({
        elem: '#project',
        url : projectUrl,
        where: {id : clientId, type : "all"}, //如果无需传递额外参数，可不加该参数
        method: 'post', //如果无需自定义HTTP类型，可不加该参数
        cellMinWidth : 95,
        page : false,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "projectTable",
        initSort: {
            field: 'id' //排序字段，对应 cols 设定的各字段名
            ,type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
        },
        cols : [[
            {field: 'id', title: '项目编号', minWidth:170, align:"center", sort:true},
            {field: 'type', title: '项目类型', minWidth:100, align:'center'},
            {field: 'description', title: '项目描述', align:'center'},
            {field: 'clientId', title: '客户编号', minWidth:170, align:'center'},
            {field: 'beginTime', title: '项目开始时间', minWidth:170, align:'center'},
            {field: 'endTime', title: '项目结束时间', minWidth:170, align:"center"},
            {field: 'status', title: '项目接单员工编号', minWidth:170, align:"center", sort:true},
            {field: 'time', title: '项目发布时间', minWidth:170, align:"center"},
            {title: '操作', minWidth:175, templet:'#projectBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索功能
    $(".search_btn").on("click",function(){
        table.reload("projectTable",{
            where: {
                id : clientId,
                type : $(".searchVal").val()  //搜索的关键字
            }
        })
    });

    //添加信息
    function addProject(edit){
        var index = layui.layer.open({
            title : "添加项目信息",
            type : 2,
            content : "addProject.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (clientId !== -1) {  //客户无权利更改项目关联的客户编号和项目接单状态
                    body.find(".clientId").attr("disabled","disabled").addClass("layui-disabled");
                    body.find(".status").attr("disabled","disabled").addClass("layui-disabled");
                }
                if(edit){
                    body.find(".id").val(edit.id);
                    body.find(".type").val(edit.type);
                    body.find(".description").val(edit.description);
                    body.find(".clientId").append('<option value="' + edit.clientId + '" selected>' + "客户编号:" + edit.clientId + '</option>');
                    body.find(".beginTime").val(edit.beginTime);
                    body.find(".endTime").val(edit.endTime);
                    body.find(".status").val(edit.status);
                    body.find(".time").val(edit.time);
                    form.render();
                } else {
                    var id = decodeURI(window.top.location.href.split('&')[1]);
                    body.find(".clientId").append('<option value="' + id + '" selected>' + "客户编号:" + id + '</option>');
                    body.find(".status").val("未接单");
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        });
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    }

    $(".addNews_btn").click(function(){
        addProject();
    });

    //列表操作
    table.on('tool(project)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addProject(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
                $.post(address + "LaborDispatching/project/deleteProject",{
                    id : data.id
                },function(json){
                    var jsonObj = JSON.stringify(json);    //将json对象转换为字符串
                    jsonObj = eval('(' + jsonObj + ')');  // 把JSON字符串解析为javascript对象
                    layer.msg(jsonObj.msg);
                    tableIns.reload();
                    layer.close(index);
                });
            });
        }
    });

});