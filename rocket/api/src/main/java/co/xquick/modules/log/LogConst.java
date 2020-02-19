package co.xquick.modules.log;

/**
 * 日志常量
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface LogConst {

    /**
     * 登录操作枚举
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
     * 登录状态枚举
     */
    enum LoginStatusEnum {
        /**
         * 失败
         */
        FAIL(0),
        /**
         * 成功
         */
        SUCCESS(1),
        /**
         * 账号已锁定
         */
        LOCK(2);

        private int value;

        LoginStatusEnum(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    /**
     * 操作状态枚举
     */
    enum OperationStatusEnum {
        /**
         * 失败
         */
        FAIL(0),
        /**
         * 成功
         */
        SUCCESS(1);

        private int value;

        OperationStatusEnum(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }


}
