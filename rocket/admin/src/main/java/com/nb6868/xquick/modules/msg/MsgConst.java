package com.nb6868.xquick.modules.msg;

/**
 * 消息模块常量
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface MsgConst {

    /**
     * 验证码短信模板前缀
     */
    String SMS_CODE_TPL_PREFIX = "CODE_";

    /**
     * 短信验证码模板-通用
     */
    String SMS_TPL_CODE_COMMON = SMS_CODE_TPL_PREFIX + "COMMON";
    /**
     * 短信验证码模板-登录
     */
    String SMS_TPL_LOGIN = SMS_CODE_TPL_PREFIX + "LOGIN";
    /**
     * 短信验证码模板-修改密码
     */
    String SMS_TPL_CHANGE_PASSWORD = SMS_CODE_TPL_PREFIX + "CHANGE_PASSWORD";
    /**
     * 短信验证码模板-注册
     */
    String SMS_TPL_REGISTER = SMS_CODE_TPL_PREFIX+  "REGISTER";

    /**
     * 消息推送服务商
     */
     enum PushServiceEnum {

        /**
         * 极光推送
         */
        JPUSH(1);

        private int value;

        PushServiceEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    /**
     * 推送类型
     */
    enum PushTypeEnum {
        /**
         * 全部推送
         */
        ALL(10),
        /**
         * 按alias推送
         */
        ALIAS(20),
        /**
         * 按tags推送
         */
        TAGS(30),
        /**
         * 按alias和alias的交集推送
         */
        ALIAS_AND_TAGS(40);

        private int value;

        PushTypeEnum(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    /**
     * 短信提供商
     */
    enum SmsServiceEnum {

        /**
         * 阿里云
         */
        ALIYUN(1),
        /**
         * 聚合
         */
        JUHE(2);

        private int value;

        SmsServiceEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

}
