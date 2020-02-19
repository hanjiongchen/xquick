package co.xquick.modules.uc;

/**
 * 用户相关常量
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface UcConst {

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
        DISABLE(0),
        ENABLED(1);

        private int value;

        UserStatusEnum(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
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
        BUTTON(1);

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
        ADMIN_USER_PWD(10, "后台-帐号密码登录"),
        ADMIN_MOBILE_PWD(20, "后台-手机密码登录"),
        ADMIN_MOBILE_SMS(30, "后台-手机短信登录"),
        ADMIN_WECHAT(40, "后台-微信登录"),
        APP_USER_PWD(50, "APP-帐号密码登录"),
        APP_MOBILE_PWD(60, "APP-手机密码登录"),
        APP_MOBILE_SMS(70, "APP-手机短信登录"),
        APP_WECHAT(80, "APP-微信登录");

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
