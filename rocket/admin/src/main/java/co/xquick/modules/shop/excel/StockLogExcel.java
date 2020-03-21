package co.xquick.modules.shop.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 出入库记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StockLogExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "spu id")
    private Long spuId;
    @Excel(name = "sku id")
    private Long skuId;
    @Excel(name = "类型 0 入库 1 出库")
    private Integer type;
    @Excel(name = "入库数量")
    private Integer inQty;
    @Excel(name = "出库数量")
    private Integer outQty;
    @Excel(name = "出入库后库存")
    private Integer stock;
    @Excel(name = "创建者")
    private Long createId;
    @Excel(name = "创建时间")
    private Date createTime;
    @Excel(name = "更新者")
    private Long updateId;
    @Excel(name = "更新时间")
    private Date updateTime;
    @Excel(name = "删除标记")
    private Integer deleted;

}