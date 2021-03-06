package com.nb6868.xquick.modules.uc;

/**
 * 用户相关常量
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface UcConst {

    /**
     * 游客token
     */
    String TOKEN_GUEST = "00000000000000000000000000000001";
    /**
     * 游客角色编码
     */
    String ROLE_CODE_GUEST = "GUEST";

    /**
     * 匿名角色编码
     */
    String ROLE_CODE_ANON = "ANNO";

    /**
     * 匿名用户token
     */
    String TOKEN_ANON = "00000000000000000000000000000000";

    /**
     * 管理平台登录配置
     */
    String LOGIN_CFG_ADMIN = "LOGIN_CFG_ADMIN";

    /**
     * 登录配置前缀
     */
    String LOGIN_CHANNEL_CFG_PREFIX = "LOGIN_CHANNEL_CFG_";

    /**
     * token header
     */
    String TOKEN_HEADER = "token";

    /**
     * 部门最大等级限制
     */
    int DEPT_HIERARCHY_MAX = 10;

    /**
     * 用户状态
     */
    enum UserStatusEnum {

        /**
         * 详见name
         */
        PENDING(-1, "待审核"),
        DISABLE(0, "冻结"),
        ENABLED(1, "正常");

        private int value;
        private String name;

        UserStatusEnum(int value) {
            this.value = value;
        }

        UserStatusEnum(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int value() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }
    }

    /**
     * 用户类型
     */
    enum UserTypeEnum {

        /**
         * 详见name
         */
        ADMIN(0, "超级管理员"),
        SYSADMIN(10, "系统管理员"),
        DEPTADMIN(20, "单位管理员"),
        USER(100, "用户");

        private int value;
        private String name;

        UserTypeEnum(int value) {
            this.value = value;
        }

        UserTypeEnum(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int value() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

    }

    /**
     * 菜单类型枚举
     */
    enum MenuTypeEnum {
        /**
         * 菜单
         */
        MENU(0),
        /**
         * 按钮
         */
        BUTTON(1),
        /**
         * 页面
         */
        PAGE(1);

        private int value;

        MenuTypeEnum(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    /**
     * 登录操作类型
     */
    enum LoginOperationEnum {
        /**
         * 用户登录
         */
        LOGIN(0),
        /**
         * 用户退出
         */
        LOGOUT(1);

        private int value;

        LoginOperationEnum(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    /**
     * 登录类型
     */
    enum LoginTypeEnum {

        /**
         * 详见name
         */
        ADMIN_LOGOUT(-10, "后台退出"),
        APP_LOGOUT(-100, "APP退出"),
        ADMIN_USER_PWD(10, "后台帐号密码登录"),
        ADMIN_MOBILE_PWD(20, "后台手机密码登录"),
        ADMIN_MOBILE_SMS(30, "后台手机短信登录"),
        ADMIN_WECHAT(40, "后台微信登录"),
        APP_USER_PWD(100, "APP帐号密码登录"),
        APP_MOBILE_PWD(101, "APP手机密码登录"),
        APP_MOBILE_SMS(102, "APP手机短信登录"),
        APP_WECHAT(103, "APP微信登录"),
        APP_APPLE(104, "APP苹果登录"),
        APP_APPLE_MOBILE_SMS(105, "APP苹果登录验证手机号");

        private int value;
        private String name;

        LoginTypeEnum(int value) {
            this.value = value;
        }

        LoginTypeEnum(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int value() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

    }

}
