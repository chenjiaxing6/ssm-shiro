package cn.edu.neusoft.entity;

import lombok.Data;

/**
 * @author Chen
 * @create 2019-05-08 20:52
 */
@Data
public class RolePermission {
    private  Long id;
    private Long roleId;
    private  Long permissionId;
}
