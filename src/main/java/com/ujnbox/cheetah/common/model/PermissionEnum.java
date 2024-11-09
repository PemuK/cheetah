package com.ujnbox.cheetah.common.model;

/**
 * 权限枚举类
 */
public enum PermissionEnum {
    NOT_MAINTAINER(0,"往期维护员"),
    MAINTAINER(1, "维护员"),
    MAINTAIN_ADMIN(2, "维护管理员"),
    SUPER_ADMIN(3, "超级管理员");

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
