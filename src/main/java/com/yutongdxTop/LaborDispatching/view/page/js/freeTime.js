layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
    var staffId = decodeURI(window.top.location.href.split('&')[1]);

    //信息列表
    var tableIns = table.render({
        elem: '#freeTime',
        url : freeTimeUrl,
        where: {id :staffId}, //如果无需传递额外参数，可不加该参数
        method: 'post', //如果无需自定义HTTP类型，可不加该参数
        cellMinWidth : 95,
        page : false,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "freeTimeTable",
        initSort: {
            field: 'id' //排序字段，对应 cols 设定的各字段名
            ,type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
        },
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: '时间表编号', align:"center", sort:true},
            {field: 'timeBegin', title: '空闲时间起始点', minWidth:150, align:'center', sort:true},
            {field: 'timeEnd', title: '空闲时间结束点', minWidth:150, align:'center', sort:true},
            {title: '操作', minWidth:175, templet:'#freeTimeBar',fixed:"right",align:"center"}
        ]]
    });

    //添加信息
    function addFreeTime(edit){
        var index = layui.layer.open({
            title : "增加空闲时间",
            type : 2,
            content : "addFreeTime.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".id").val(edit.id).attr("disabled","disabled").addClass("layui-disabled");
                    body.find(".timeBegin").val(edit.timeBegin);
                    body.find(".timeEnd").val(edit.timeEnd);
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
        addFreeTime();
    });

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('freeTimeTable'),
            data = checkStatus.data;
        if(data.length > 0) {

            layer.confirm('确定删除选中的信息？', {icon: 3, title: '提示信息'}, function (index, layero) {
                for (var i in data) {
                    $.post(address + "LaborDispatching/freelancer/deleteFreeTime",{
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
    table.on('tool(freeTime)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addFreeTime(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
                $.post(address + "LaborDispatching/freelancer/deleteFreeTime",{
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