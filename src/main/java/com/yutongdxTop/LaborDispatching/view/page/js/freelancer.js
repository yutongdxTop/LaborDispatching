layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //信息列表
    var tableIns = table.render({
        elem: '#freelancer',
        url : freelancerUrl,
        cellMinWidth : 95,
        page : false,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "freelancerTable",
        initSort: {
            field: 'staffId' //排序字段，对应 cols 设定的各字段名
            ,type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
        },
        cols : [[
            {field: 'staffId', title: '员工编号', minWidth:170, align:"center", sort:true},
            {field: 'name', title: '员工姓名', align:'center'},
            {field: 'idNumber', title: '身份证号码', align:'center'},
            {field: 'sex', title: '性别', align:'center'},
            {field: 'identity', title: '身份', align:'center'},
            {field: 'type', title: '员工类别', minWidth:200, align:'center'},
            {field: 'userName', title: '用户名', align:"center"},
            {field: 'password', title: '密码', align:"center"},
            {title: '操作', minWidth:175, templet:'#freelancerBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索功能
    $(".search_btn").on("click",function(){
        table.reload("freelancerTable",{
            where: {
                type : $(".searchVal").val()  //搜索的关键字
            }
        })
    });

    //添加信息
    function addFreelancer(){
        var index = layui.layer.open({
            title : "添加自由职业者信息",
            type : 2,
            content : "staffRegister.html",
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
        addFreelancer();
    });

    //修改信息
    function editFreelancer(edit){
        var index = layui.layer.open({
            title : "修改员工信息",
            type : 2,
            content : "staffPersonal.html",
            success : function(){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".staffId").val(edit.staffId);
                    body.find(".name").val(edit.name);
                    body.find(".idNumber").val(edit.idNumber);
                    body.find(".sex").val(edit.sex);
                    body.find(".identity").val(edit.identity);
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
    table.on('tool(freelancer)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            editFreelancer(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
                $.post(address + "LaborDispatching/staffVo/deleteFreelancer",{
                    staffId : data.staffId
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