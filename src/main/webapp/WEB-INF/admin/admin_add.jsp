<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"/>
<body>
<div class="layui-fluid">
    <div class="layui-row">
        <form action="" method="post" class="layui-form layui-form-pane">
            <div class="layui-form-item">
                <label for="username" class="layui-form-label">
                    <span class="x-red">*</span>用户名
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="username" name="username" required="" lay-verify="username"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="password" class="layui-form-label">
                    <span class="x-red">*</span>密码
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="password" name="password" required="" lay-verify="pass"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="email" class="layui-form-label">
                    <span class="x-red">*</span>邮箱
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="email" name="email" required="" lay-verify="email"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="phone" class="layui-form-label">
                    <span class="x-red">*</span>手机
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="phone" name="phone" required="" lay-verify="required|phone"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">
                    角色
                </label>
                <table  class="layui-table layui-input-block">
                    <tbody>
                    <tr>
                        <td>
                            <div class="layui-input-block" id="roleCheckBox">

                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="layui-form-item">
                <button class="layui-btn" lay-submit="" lay-filter="add">增加</button>
            </div>
        </form>
    </div>
</div>
<script>
    /**
     * 获取角色信息
     */
    $.ajax({
        type:"get",
        url:"/admin/role/getRoleList",
        success:function (data){
            if (data.type="success") {
                for (var i = 0; i < data.data.length; i++) {
                    //console.log(data.data[i]);
                    $("#roleCheckBox").append('<input name="ids[]" lay-skin="primary" type="checkbox" value="' + data.data[i].id + '" title="' + data.data[i].name + '">');
                }
            }else {
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.msg(data.msg);
                });
            }
        }
    })
</script>
<script>
    layui.use(['form','layer'], function(){
        $ = layui.jquery;
        var form = layui.form
            ,layer = layui.layer;

        //自定义验证规则
        form.verify({
            username: function(value){
                if(value.length < 5){
                    return '昵称至少得5个字符';
                }
            }
            ,pass: [/(.+){6,12}$/, '密码必须6到12位']
            ,repass: function(value){
                if($('#L_pass').val()!=$('#L_repass').val()){
                    return '两次密码不一致';
                }
            }
        });

        //监听提交
        form.on('submit(add)', function(data){
            //发异步，把数据提交
            var info = data.field;
            console.log(data.field);
            $.ajax({
                type:"post",
                url:"/admin/user/addUser",
                data:{"userInfo":JSON.stringify(info)},
                success:function (data){
                    if (data.type == "success"){
                        setTimeout(function(){
                            window.parent.location.reload();//成功后刷新父界面
                        }, 1000);
                        layer.alert("增加成功！", {icon: 6},function () {
                            // 获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                        });
                    } else {
                        layer.alert(data.msg, {icon: 5},function () {
                            // 获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                        });
                    }
                }
            })
            return false;
        });


        form.on('checkbox(father)', function(data){

            if(data.elem.checked){
                $(data.elem).parent().siblings('td').find('input').prop("checked", true);
                form.render();
            }else{
                $(data.elem).parent().siblings('td').find('input').prop("checked", false);
                form.render();
            }
        });


    });
</script>
</body>

</html>
