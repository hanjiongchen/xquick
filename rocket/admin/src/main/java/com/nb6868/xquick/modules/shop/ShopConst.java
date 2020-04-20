package com.nb6868.xquick.modules.shop;

/**
 * 商城相关常量
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface ShopConst {

    /**
     * 订单状态类型
     */
    enum OrderStatusEnum {

        /**
         * 详见name
         */
        CANCELED(-10, "已取消"),
        PLACED(0, "已下单"),
        COMPLETED(100, "已完成");

        private int value;
        private String name;

        OrderStatusEnum(int value) {
            this.value = value;
        }

        OrderStatusEnum(int value, String name) {
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
     * 分销类型
     */
    enum DistTypeEnum {

        /**
         * 详见name
         */
        NONE(0, "不参与"),
        SCALE(1, "按比例"),
        FIX_VALUE(2, "固定值"),
        RANDOM(3, "随机");

        private int value;
        private String name;

        DistTypeEnum(int value) {
            this.value = value;
        }

        DistTypeEnum(int value, String name) {
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
     * 进出库记录类型
     */
    enum StockLogTypeEnum {

        /**
         * 详见name
         */
        IN(0, "入库"),
        OUT(1, "出库");

        private int value;
        private String name;

        StockLogTypeEnum(int value) {
            this.value = value;
        }

        StockLogTypeEnum(int value, String name) {
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
