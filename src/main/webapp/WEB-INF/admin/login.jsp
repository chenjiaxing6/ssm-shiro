<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chenjiaxing
  Date: 2019/4/30
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  isELIgnored="false" language="java"%>
<!doctype html>
<html  class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>后台登录</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="stylesheet" href="/assets/admin/css/font.css">
    <link rel="stylesheet" href="/assets/admin/css/login.css">
    <link rel="stylesheet" href="/assets/admin/css/xadmin.css">
    <script src="/assets/admin/js/jquery.min.js"/>
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="/assets/admin/lib/layui/layui.js" charset="utf-8"></script>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="login-bg">

<div class="login layui-anim layui-anim-up">
    <div class="message">后台登录</div>
    <div id="darkbannerwrap"></div>

    <form method="post" class="layui-form" >
        <input name="username" placeholder="用户名"  type="text" lay-verify="required" class="layui-input" >
        <hr class="hr15">
        <input name="password" lay-verify="required" placeholder="密码"  type="password" class="layui-input">
        <hr class="hr15">
        <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
        <hr class="hr20" >
    </form>
</div>

<script>
    layui.use('layer', function(){
        var layer = layui.layer;
        <c:if test="${errorMsg != null}">
            layer.msg('${errorMsg}');
        </c:if>
    });
    $(function  () {
        layui.use('form', function(){
            var form = layui.form;
            // layer.msg('玩命卖萌中', function(){
            //   //关闭后的操作
            //   });
            //监听提交
            form.on('submit(login)', function(data){
                // // alert(888)
                // layer.msg(JSON.stringify(data.field),function(){
                //     location.href='index.html'
                // });
                var info = data.field;
                $.ajax({
                    type:"post",
                    url:"/admin/user/login",
                    data:{"username":info.username,"password":info.password},
                    success:function () {
                    }
                })
            });
        });
    })
</script>
<!-- 底部结束 -->
</body>
</html>
