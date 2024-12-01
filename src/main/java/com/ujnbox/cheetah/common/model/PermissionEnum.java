package com.ujnbox.cheetah.common.model;

/**
 * 权限枚举类
 */
public enum PermissionEnum {
    NOT_MAINTAINER(0, "往期维护员"),
    MAINTAINER(1, "维护员"),
    MAINTAIN_ADMIN(2, "维护管理员"),
    SUPER_ADMIN(3, "超级管理员"),
    QUERY_MAINTENANCE(20, "查询维护"),
    FILL_MAINTENANCE(21, "填报维护"),
    ADD_MAINTENANCE(22, "添加维护"),
    EDIT_MAINTENANCE(23, "修改维护"),
    DELETE_MAINTENANCE(24, "删除维护"),
    QUERY_WORKLOAD(25, "查询工作量信息"),
    ADD_IP_INFO(26, "添加IP信息"),
    DELETE_IP_INFO(27, "删除IP信息"),
    EDIT_IP_INFO(28, "修改IP信息"),
    QUERY_IP_INFO(29, "查询IP信息"),
    QUERY_USER(30, "查询用户"),
    ADD_USER(31, "添加用户"),
    EDIT_USER(32, "修改用户"),
    DELETE_USER(33, "删除用户"),
    QUERY_DEPARTMENT(34, "查询部门"),
    ADD_DEPARTMENT(35, "添加部门"),
    EDIT_DEPARTMENT(36, "修改部门"),
    DELETE_DEPARTMENT(37, "删除部门"),
    QUERY_BUILDING(38, "查询建筑"),
    ADD_BUILDING(39, "添加建筑"),
    EDIT_BUILDING(40, "修改建筑"),
    DELETE_BUILDING(41, "删除建筑"),
    QUERY_MAINTENANCE_TYPE(42, "查询维护类型"),
    ADD_MAINTENANCE_TYPE(43, "添加维护类型"),
    EDIT_MAINTENANCE_TYPE(44, "修改维护类型"),
    DELETE_MAINTENANCE_TYPE(45, "删除维护类型");

    private final int code;
    private final String description;

    /**
     * 构造函数
     *
     * @param code 权限代码
     * @param description 权限描述
     */
    PermissionEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 获取权限代码
     *
     * @return 权限代码
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取权限描述
     *
     * @return 权限描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 根据代码获取枚举实例
     *
     * @param code 权限代码
     * @return 对应的枚举实例
     */
    public static PermissionEnum fromCode(int code) {
        for (PermissionEnum permission : PermissionEnum.values()) {
            if (permission.getCode() == code) {
                return permission;
            }
        }
        throw new IllegalArgumentException("未知权限代码: " + code);
    }
}