layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //信息列表
    var tableIns = table.render({
        elem: '#freeTimeVo',
        url : freeTimeVoUrl,
        cellMinWidth : 95,
        page : false,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "freeTimeVoTable",
        initSort: {
            field: 'id' //排序字段，对应 cols 设定的各字段名
            ,type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
        },
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: '时间表编号', minWidth:180, align:"center", sort:true},
            {field: 'staffId', title: '员工编号', minWidth:180, align:"center", sort:true},
            {field: 'name', title: '员工姓名', align:'center'},
            {field: 'sex', title: '性别', align:'center'},
            {field: 'identity', title: '身份', minWidth:100, align:'center'},
            {field: 'type', title: '员工类别', align:'center'},
            {field: 'freeTimeBegin', title: '空闲时间起始点', minWidth:180, align:'center', sort:true},
            {field: 'freeTimeEnd', title: '空闲时间结束点', minWidth:180, align:'center', sort:true},
            {title: '操作', minWidth:175, templet:'#freeTimeVoBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索功能
    $(".search_btn").on("click",function(){
        table.reload("freeTimeVoTable",{
            where: {
                name : $(".searchVal").val()  //搜索的关键字
            }
        })
    });

    //添加信息
    function addFreeTimeVo(edit, type){
        var index = layui.layer.open({
            title : "增加空闲时间",
            type : 2,
            content : "addFreeTimeVo.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                body.find(".staffId").val(edit.staffId);
                body.find(".name").val(edit.name);
                body.find(".sex").val(edit.sex);
                body.find(".identity").val(edit.identity);
                body.find(".type").val(edit.type);
                if(type === "edit"){
                    body.find(".id").val(edit.id);
                    body.find(".freeTimeBegin").val(edit.freeTimeBegin);
                    body.find(".freeTimeEnd").val(edit.freeTimeEnd);
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


    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('freeTimeVoTable'),
            data = checkStatus.data;
        if(data.length > 0) {

            layer.confirm('确定删除选中的信息？', {icon: 3, title: '提示信息'}, function (index, layero) {
                for (var i in data) {
                    $.post(address + "LaborDispatching/administrator/deleteFreeTimeVo",{
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
    table.on('tool(freeTimeVo)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'add') {  //增加
            addFreeTimeVo(data, "add");
        } else if(layEvent === 'edit'){ //编辑
            addFreeTimeVo(data, "edit");
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
                $.post(address + "LaborDispatching/administrator/deleteFreeTimeVo",{
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