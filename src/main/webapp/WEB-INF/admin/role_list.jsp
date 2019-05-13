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
              <cite>角色列表</cite>
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
                    <table class="layui-table" lay-data="{url:'/admin/role/getRoleListByPage',page:true,toolbar: '#toolbarDemo',id:'test'}" lay-filter="test">
                        <thead>
                        <tr>
                            <th lay-data="{type:'checkbox'}">ID</th>
                            <th lay-data="{field:'id', sort: true}">ID</th>
                            <th lay-data="{field:'name', sort: true}">角色名称</th>
                            <th lay-data="{field:'sn', sort: true}">角色标志</th>
                            <th lay-data="{field:'desc', sort: true}">角色描述</th>
                            <th lay-data="{toolbar: '#rightConlumn'}"></th>
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
        <button class="layui-btn" onclick="xadmin.open('添加角色','/admin/system/role_add',600,400)"><i class="layui-icon"></i>添加</button>
        <button class = "layui-btn layui-btn-sm layui-btn-danger" lay-event = "getCheckData" > <i class="layui-icon layui-icon-delete"></i>  批量删除 </button>
        <%--<button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button >--%>
        <%--<button class = "layui-btn layui-btn-sm" lay-event = "isAll" > 验证是否全选</button>--%>
    </div >
</script>
<script type="text/html" id="rightConlumn">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script>
    layui.use('table',
    function() {
        var table = layui.table;

        //监听单元格编辑
        table.on('edit(test)',
            function(obj) {
                var value = obj.value //得到修改后的值
                    ,
                    data = obj.data //得到所在行所有键值
                    ,
                    field = obj.field; //得到字段
                layer.msg('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value);
            });

        //头工具栏事件
        table.on('toolbar(test)',
            function(obj) {
                var checkStatus = table.checkStatus(obj.config.id);
                switch (obj.event) {
                    case 'getCheckData':
                        var data = checkStatus.data;
                        var info = "";
                        for (var i = 0; i <data.length ; i++) {
                            info += data[i].id+',';
                        }
                        layer.confirm('真的全部删除么', function(index){
                            // obj.del(); //删除对应行（tr）的DOM结构
                            //向服务端发送删除指令
                            $.ajax({
                                type:"post",
                                url:"/admin/role/deleteAllRole",
                                data:{"info":info},
                                success:function (data) {
                                    if (data.type == 'success'){
                                        layer.msg("删除成功！");
                                        //执行重载
                                        table.reload('test', {
                                            page: {
                                                curr: 1 //重新从第 1 页开始
                                            }
                                        });
                                        layer.close(index);
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
                        });
                        break;
                    case 'getCheckLength':
                        var data = checkStatus.data;
                        layer.msg('选中了：' + data.length + ' 个');
                        break;
                    case 'isAll':
                        layer.msg(checkStatus.isAll ? '全选': '未全选');
                        break;
                };
            });
        //监听行工具事件
        table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                ,layEvent = obj.event; //获得 lay-event 对应的值
            if(layEvent === 'detail'){
                layer.msg('查看操作');
            } else if(layEvent === 'del'){
                layer.confirm('真的删除么', function(index){
                    // obj.del(); //删除对应行（tr）的DOM结构
                    //向服务端发送删除指令
                    $.ajax({
                        type:"post",
                        url:"/admin/role/deleteRole",
                        data:{"id":data.id},
                        success:function (data) {
                            if (data.type == 'success'){
                                layer.msg("删除成功！");
                                //执行重载
                                table.reload('test', {
                                    page: {
                                        curr: 1 //重新从第 1 页开始
                                    }
                                });
                                layer.close(index);
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
                });
            } else if(layEvent === 'edit'){
                var data = obj.data //获得当前行数据
                //layer.msg(data.id);
                xadmin.open('编辑角色','/admin/system/role_update?id='+data.id,600,400);
            }
        });
    });
</script>
</html>