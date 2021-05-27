layui.use(['form','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer;
    var $ = layui.jquery;

    //登录按钮
    form.on("submit(login)",function(data){
        $(this).text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
        $.post(address + "LaborDispatching/user/login",{
            name : data.field.userName
        }, function(json){
            var jsonObj = JSON.stringify(json);    //将json对象转换为字符串
            jsonObj= eval('(' + jsonObj + ')');  // 把JSON字符串解析为javascript对象
            if(jsonObj.data == null) {
                layer.msg('账号不存在');
                setTimeout(function(){
                    parent.location.reload();
                },1500)
            } else if(data.field.password === jsonObj.data.password && data.field.code.toUpperCase() === CodeVal.toUpperCase()) {
                layer.msg('登录成功');
                setTimeout(function(){
                    if (jsonObj.data.identity === "管理员") {
                        window.location.href = "../html/administratorIndex.html?" + data.field.userName + "&"+ jsonObj.data.staffId +"&";
                    } else if (jsonObj.data.identity === "自由职业者"){
                        window.location.href = "../html/freelancerIndex.html?" + data.field.userName + "&"+ jsonObj.data.staffId +"&";
                    } else {
                        window.location.href = "../html/clientIndex.html?" + data.field.userName + "&"+ jsonObj.data.staffId +"&";
                    }
                },1500)
            } else {
                layer.msg('用户密码或验证码不正确');
                setTimeout(function(){
                    parent.location.reload();
                },1500)
            }

        });

        return false;
    });

    //注册按钮
    $(".staffSignupBtn").click(function () {
        window.location.href = "../html/staffRegister.html";
        return false;
    });

    $(".clientSignupBtn").click(function () {
        window.location.href = "../html/clientRegister.html";
        return false;
    });

    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    });
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    });
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() !== ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    })
});