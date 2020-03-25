package co.xquick.modules.shop;

/**
 * 商城相关常量
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface ShopConst {

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
