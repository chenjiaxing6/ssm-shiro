##ssm-shiro
使用Spring、SpringMVC、Mytatis、Shiro开发的RBAC角色权限管理系统
##用到的技术
Spring+SpringMVC+Mybatis++Druid+Shiro+Layui+全部使用ajax异步加载
##实现功能
暂时实现的功能为管理员的列表的查看，管理员删除，管理员增加以及选择角色，角色列表的查看，角色的删除以及批量删除，角色的添加以及对应角色权限的添加，每个功能模块都有对应的权限，超级管理员具有所有权限，并可以根据具体的场景分配不同的权限，如果用户没有权限页面将会提示无权限访问，并且不会显示相应数据。
## 功能截图
登录界面
![](http://ishangit.cn/zb_users/upload/2019/05/20190509093358155736563886083.png)
管理员管理：
![](http://ishangit.cn/zb_users/upload/2019/05/20190509093443155736568385960.png)
管理员更新:
![](http://ishangit.cn/zb_users/upload/2019/05/20190509093519155736571956678.png)
无权限提示：
![](http://ishangit.cn/zb_users/upload/2019/05/20190509094005155736600558928.png)