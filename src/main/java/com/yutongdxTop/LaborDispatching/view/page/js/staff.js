layui.use(['form','layer','table'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    //信息列表
    var tableIns = table.render({
        elem: '#staff',
        url : staffUrl,
        cellMinWidth : 95,
        page : false,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "staffTable",
        initSort: {
            field: 'id' //排序字段，对应 cols 设定的各字段名
            ,type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
        },
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: '员工编号', align:"center"},
            {field: 'name', title: '员工姓名', align:'center'},
            {field: 'idNumber', title: '身份证号码', align:'center'},
            {field: 'sex', title: '性别', align:'center'},
            {field: 'identity', title: '身份', align:'center'},
            {field: 'type', title: '员工类别', minWidth:200, align:'center'},
            {title: '操作', minWidth:175, templet:'#staffBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索功能
    $(".search_btn").on("click",function(){
        table.reload("staffTable",{
            where: {
                type : $(".searchVal").val()  //搜索的关键字
            }
        })
    });

    //添加信息
    function addStaff(edit){
        var index = layui.layer.open({
            title : "增加全职员工信息",
            type : 2,
            content : "addStaff.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".id").val(edit.id).attr("disabled","disabled").addClass("layui-disabled");
                    body.find(".name").val(edit.name);
                    body.find(".idNumber").val(edit.idNumber);
                    body.find(".sex").val(edit.sex);
                    body.find(".identity").val(edit.identity).attr("disabled","disabled").addClass("layui-disabled");
                    body.find(".type").val(edit.type);
                    form.render();
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
        addStaff();
    });

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('staffTable'),
            data = checkStatus.data;
        if(data.length > 0) {

            layer.confirm('确定删除选中的信息？', {icon: 3, title: '提示信息'}, function (index, layero) {
                for (var i in data) {
                    $.post(address + "LaborDispatching/staff/deleteStaff",{
                        id : data[i].id
                    });
                }
                setTimeout(function(){
                    layer.msg("删除成功！");
                    tableIns.reload();
                    layer.close(index);
                },1000)

            });
        }else{
            layer.msg("请选择需要删除的信息");
        }
    });

    //列表操作
    table.on('tool(staff)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addStaff(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
                $.post(address + "LaborDispatching/staff/deleteStaff",{
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