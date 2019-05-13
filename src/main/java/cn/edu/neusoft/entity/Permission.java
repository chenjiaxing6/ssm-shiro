package cn.edu.neusoft.entity;

import lombok.Data;

/**
 * @author Chen
 * @create 2019-04-30 17:49
 */
@Data
public class Permission {
    private Long id;
    private String name;
    private String resource;
}
