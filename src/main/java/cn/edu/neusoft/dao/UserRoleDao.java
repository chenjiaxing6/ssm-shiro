package cn.edu.neusoft.dao;

import cn.edu.neusoft.entity.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Chen
 * @create 2019-05-06 19:04
 */
@Repository
public interface UserRoleDao {
    void insertUserRole(UserRole userRole);

    void deleteByUserId(Long id);
}
