layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    var status = decodeURI(location.href.split('?')[1]);

    //信息列表
    var tableIns = table.render({
        elem: '#project',
        url : auditUrl,
        where: {status : status, type : "all"}, //如果无需传递额外参数，可不加该参数
        method: 'post', //如果无需自定义HTTP类型，可不加该参数
        cellMinWidth : 95,
        page : false,
        toolbar:true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "projectTable",
        initSort: {
            field: 'id' //排序字段，对应 cols 设定的各字段名
            ,type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
        },
        cols : [[
            {field: 'id', title: '项目编号',minWidth:170, align:"center", sort:true},
            {field: 'type', title: '项目类型', align:'center'},
            {field: 'description', title: '项目描述', minWidth:170, align:'center'},
            {field: 'clientId', title: '客户编号', minWidth:170, align:'center'},
            {field: 'beginTime', title: '项目开始时间', minWidth:170, align:'center'},
            {field: 'endTime', title: '项目结束时间', minWidth:170, align:"center"},
            {field: 'status', title: '项目接单员工编号', minWidth:170, align:"center", sort:true},
            {field: 'time', title: '项目发布时间', minWidth:170, align:"center"}
        ]]
    });

    //搜索功能
    $(".search_btn").on("click",function(){
        table.reload("projectTable",{
            where: {
                status : status,
                type : $(".searchVal").val()  //搜索的关键字
            }
        })
    });

});