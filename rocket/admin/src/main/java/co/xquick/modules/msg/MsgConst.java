package co.xquick.modules.msg;

/**
 * 消息模块常量
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface MsgConst {

    // 短信模板-验证
    String SMS_TPL_VERIFY = "VERIFY";
    // 短信模板-登录
    String SMS_TPL_LOGIN = "LOGIN";
    // 短信模板-修改密码
    String SMS_TPL_CHANGE_PASSWORD = "CHANGE_PASSWORD";
    // 短信模板-注册
    String SMS_TPL_REGISTER = "REGISTER";

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
