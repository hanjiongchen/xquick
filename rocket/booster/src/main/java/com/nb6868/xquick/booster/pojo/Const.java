package com.nb6868.xquick.booster.pojo;

/**
 * 常量
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface Const {
    /**
     * OK
     */
    String OK = "OK";
    /**
     * 用户标识
     */
    String USER_KEY = "userId";
    /**
     * 菜单根节点标识
     */
    Long MENU_ROOT = 0L;
    /**
     * 部门根节点标识
     */
    Long DEPT_ROOT = 0L;
    /**
     * 数据字典根节点标识
     */
    Long DICT_ROOT = 0L;
    /**
     *  升序
     */
    String ASC = "asc";
    /**
     * 降序
     */
    String DESC = "desc";
    /**
     * 创建时间字段名
     */
    String CREATE_DATE = "create_time";

    /**
     * 创建时间字段名
     */
    String ID = "id";

    /**
     * 数据权限过滤
     */
    String SQL_FILTER = "sqlFilter";

    /**
     * 数据权限过滤,自己创建的
     */
    String SQL_FILTER_MY = "sqlFilterMy";

    /**
     * 当前页码
     */
    String PAGE = "page";
    /**
     * 每页显示记录数
     */
    String LIMIT = "limit";
    /**
     * 排序字段
     */
    String ORDER_FIELD = "orderField";
    /**
     * 排序方式
     */
    String ORDER = "order";

    /**
     * 短信配置KEY
     */
    String SMS_CONFIG_KEY = "SMS_CONFIG_KEY";
    /**
     * 邮件配置KEY
     */
    String MAIL_CONFIG_KEY = "MAIL_CONFIG_KEY";
    /**
     * 消息推送配置KEY
     */
    String PUSH_CONFIG_KEY = "PUSH_CONFIG_KEY";

    /**
     * 结果枚举
     */
    enum ResultEnum {

        /**
         * 详见name
         */
        SUCCESS(1, "成功"),
        FAIL(0, "失败");

        private int value;
        private String name;

        ResultEnum(int value) {
            this.value = value;
        }

        ResultEnum(int value, String name) {
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
     * 二元布尔枚举
     */
    enum BooleanEnum {

        /**
         * 详见name
         */
        TRUE(1, "是"),
        FALSE(0, "否");

        private int value;
        private String name;

        BooleanEnum(int value) {
            this.value = value;
        }

        BooleanEnum(int value, String name) {
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
