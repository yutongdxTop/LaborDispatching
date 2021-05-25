layui.use(['form', 'layedit', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;
    var url = window.parent.document.URL;
    var userName, id;
    //按照服务器部署的网址划分网址
    var html = decodeURI(parent.location.href.split('/')[parent.location.href.split('/').length - 1]);

    if (html !== "freelancer.html") {  //若上一级页面不是自由职业者信息管理界面则是自由职业者更改个人信息
        userName = decodeURI(url.split('?')[1].split('&')[0]);
        id = decodeURI(url.split('&')[1]);

        //渲染信息
        $.post( address + "LaborDispatching/staffVo/getStaffVoByUserName",{
            userName : userName
        },function(json){
            var jsonObj = JSON.stringify(json);    //将json对象转换为字符串
            jsonObj = eval('(' + jsonObj + ')');  // 把JSON字符串解析为javascript对象
            $(".staffId").val(jsonObj.data.staffId);
            $(".name").val(jsonObj.data.name);
            $(".idNumber").val(jsonObj.data.idNumber);
            $(".sex").val(jsonObj.data.sex);
            $(".identity").val(jsonObj.data.identity).attr("disabled","disabled").addClass("disabled");;
            $(".type").val(jsonObj.data.type);
            $(".userName").val(jsonObj.data.userName);
            $(".password").val(jsonObj.data.password);
            form.render();
        });
    }

    //监听提交
    form.on('submit(set)', function (data) {
        layer.alert(JSON.stringify(data.field), {
            title: '最终的提交信息'
        }, function () {
            $.post(address + "LaborDispatching/staffVo/updateStaffVo", {
                staffId : data.field.staffId,
                name : data.field.name,
                idNumber : data.field.idNumber,
                sex : data.field.sex,
                identity : data.field.identity,
                type : data.field.type,
                userName : data.field.userName,
                password : data.field.password
            }, function(json){
                var jsonObj = JSON.stringify(json);    //将json对象转换为字符串
                jsonObj = eval('(' + jsonObj + ')');  // 把JSON字符串解析为javascript对象
                layer.msg(jsonObj.msg);
                if (jsonObj.msg === "修改成功!") {
                    setTimeout(function(){
                        parent.location.search = "?" + data.field.userName + "&" + id + "&";
                    },1000);
                }
            });
        });
        return false;
    });

});