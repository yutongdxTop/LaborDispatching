layui.use(['form', 'layedit', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;
    var url = window.top.location.href;
    var userName, id;
    var html = decodeURI(parent.location.href.split('/')[6]);

    if (html !== "client.html") {  //用户更改个人信息
        userName = decodeURI(url.split('?')[1].split('&')[0]);
        id = decodeURI(url.split('&')[1]);
        //渲染信息
        $.post( address + "LaborDispatching/clientVo/getClientVoByUserName",{
            userName : userName
        },function(json){
            var jsonObj = JSON.stringify(json);    //将json对象转换为字符串
            jsonObj = eval('(' + jsonObj + ')');  // 把JSON字符串解析为javascript对象
            $(".id").val(jsonObj.data.clientId);
            $(".name").val(jsonObj.data.name);
            $(".telephone").val(jsonObj.data.telephone);
            $(".address").val(jsonObj.data.address);
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
            $.post(address + "LaborDispatching/clientVo/updateClientVo", {
                clientId : id,
                name : data.field.name,
                telephone : data.field.telephone,
                address : data.field.address,
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