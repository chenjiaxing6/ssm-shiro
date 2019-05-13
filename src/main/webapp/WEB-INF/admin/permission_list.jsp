<%--
  Created by IntelliJ IDEA.
  User: chenjiaxing
  Date: 2019/5/1
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"/>
<body>
<div class="x-nav">
          <span class="layui-breadcrumb">
            <a href="">首页</a>
            <a>
              <cite>权限列表</cite>
            </a>
          </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" onclick="location.reload()" title="刷新">
        <i class="layui-icon layui-icon-refresh" style="line-height:30px"></i></a>
</div>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body ">
                    <table class="layui-table" lay-data="{url:'/admin/permission/getPermissionList',page:true,id:'test',toolbar: '#toolbarDemo'}" lay-filter="test">
                        <thead>
                        <tr>
                            <th lay-data="{field:'id', sort: true}">ID</th>
                            <th lay-data="{field:'name', sort: true}">权限名称</th>
                            <th lay-data="{field:'resource', sort: true}">权限表达式</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/html" id="toolbarDemo">
    <div class = "layui-btn-container" >
        <button class = "layui-btn layui-btn-sm layui-btn-normal" lay-event = "reloadData" > 重新加载权限信息 </button>
    </div >
</script>
<script>

    layui.use('table',
    function() {
        var table = layui.table;

        //头工具栏事件
        table.on('toolbar(test)',
            function(obj) {
                var checkStatus = table.checkStatus(obj.config.id);
                switch (obj.event) {
                    case 'reloadData':
                        $.ajax({
                            type:"post",
                            url:"/admin/permission/reload",
                            success:function (data) {
                                if (data.type == 'success'){
                                    //执行重载
                                    table.reload('test', {
                                        page: {
                                            curr: 1 //重新从第 1 页开始
                                        }
                                    });
                                    layui.use('layer', function(){
                                        var layer = layui.layer;
                                        layer.msg(data.msg);
                                    });
                                    return false;
                                }else {
                                    layer.close(index);
                                    layui.use('layer', function(){
                                        var layer = layui.layer;
                                        layer.msg(data.msg);
                                    });
                                }
                            }
                        });
                        break;
                };
            });
    });


</script>
</html>