layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //信息列表
    var tableIns = table.render({
        elem: '#clientVo',
        url : clientUrl,
        cellMinWidth : 95,
        page : false,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "clientVoTable",
        initSort: {
            field: 'clientId' //排序字段，对应 cols 设定的各字段名
            ,type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
        },
        cols : [[
            {field: 'clientId', title: '客户编号', align:"center", sort:true},
            {field: 'name', title: '客户姓名', align:'center'},
            {field: 'telephone', title: '联系电话', align:'center'},
            {field: 'address', title: '联系地址', align:'center'},
            {field: 'type', title: '客户类别', minWidth:200, align:'center'},
            {field: 'userName', title: '用户名', align:"center"},
            {field: 'password', title: '密码', align:"center"},
            {title: '操作', minWidth:175, templet:'#clientVoBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索功能
    $(".search_btn").on("click",function(){
        table.reload("clientVoTable",{
            where: {
                type : $(".searchVal").val()  //搜索的关键字
            }
        })
    });

    //添加信息
    function addClientVo(){
        var index = layui.layer.open({
            title : "添加客户信息",
            type : 2,
            content : "clientRegister.html",
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            },
            cancel : function (index) {
                tableIns.reload();
                layer.close(index);
                return false;
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
        addClientVo();
    });

    //修改信息
    function editClientVo(edit){
        var index = layui.layer.open({
            title : "修改客户信息",
            type : 2,
            content : "clientPersonal.html",
            success : function(){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".clientId").val(edit.clientId);
                    body.find(".name").val(edit.name);
                    body.find(".telephone").val(edit.telephone);
                    body.find(".address").val(edit.address);
                    body.find(".type").val(edit.type);
                    body.find(".userName").val(edit.userName);
                    body.find(".password").val(edit.password);
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

    //列表操作
    table.on('tool(clientVo)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            editClientVo(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
                $.post(address + "LaborDispatching/clientVo/deleteClientVo",{
                    clientId : data.clientId
                },function(json){
                    var jsonObj = JSON.stringify(json);    //将json对象转换为字符串
                    jsonObj = eval('(' + jsonObj + ')');  // 把JSON字符串解析为javascript对象
                    layer.alert(jsonObj.msg);
                    tableIns.reload();
                    layer.close(index);
                });
            });
        }
    });

});