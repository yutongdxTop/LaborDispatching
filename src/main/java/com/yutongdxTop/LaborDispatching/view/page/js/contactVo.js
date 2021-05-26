layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //信息列表
    var tableIns = table.render({
        elem: '#contactVo',
        url : contactVoUrl,
        cellMinWidth : 95,
        page : false,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "contactVoTable",
        initSort: {
            field: 'id' //排序字段，对应 cols 设定的各字段名
            ,type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
        },
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: '联系方式编号', minWidth:180, align:"center", sort:true},
            {field: 'staffId', title: '员工编号', minWidth:180, align:"center", sort:true},
            {field: 'name', title: '员工姓名', align:'center'},
            {field: 'sex', title: '性别', align:'center'},
            {field: 'identity', title: '身份', align:'center'},
            {field: 'contactDetails', title: '联系方式', align:'center'},
            {field: 'contactValue', title: '联系方式的值', minWidth:180, align:'center'},
            {title: '操作', minWidth:175, templet:'#contactVoBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索功能
    $(".search_btn").on("click",function(){
        table.reload("contactVoTable",{
            where: {
                name : $(".searchVal").val()  //搜索的关键字
            }
        })
    });

    //添加信息
    function addContactVo(edit, type){
        var index = layui.layer.open({
            title : "增加联系方式",
            type : 2,
            content : "addContactVo.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                body.find(".staffId").val(edit.staffId);
                body.find(".name").val(edit.name);
                body.find(".sex").val(edit.sex);
                body.find(".identity").val(edit.identity);
                if(type === "edit"){
                    body.find(".id").val(edit.id);
                    body.find(".contactDetails").val(edit.contactDetails);
                    body.find(".contactValue").val(edit.contactValue);
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
        var checkStatus = table.checkStatus('contactVoTable'),
            data = checkStatus.data;
        if(data.length > 0) {

            layer.confirm('确定删除选中的信息？', {icon: 3, title: '提示信息'}, function (index, layero) {
                for (var i in data) {
                    $.post(address + "LaborDispatching/administrator/deleteContactVo",{
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
    table.on('tool(contactVo)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'add') {  //增加
            addContactVo(data, "add");
        } else if(layEvent === 'edit'){ //编辑
            addContactVo(data, "edit");
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
                $.post(address + "LaborDispatching/administrator/deleteContactVo",{
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