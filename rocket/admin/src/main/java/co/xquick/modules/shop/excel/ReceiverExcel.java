package co.xquick.modules.shop.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 收件地址
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ReceiverExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "用户id")
    private Long userId;
    @Excel(name = "区域名称,如浙江省,宁波市,鄞州区")
    private String regionName;
    @Excel(name = "区域编号,如33000,33010,33011")
    private String regionCode;
    @Excel(name = "详细门牌号")
    private String address;
    @Excel(name = "收件人")
    private String consignee;
    @Excel(name = "邮编")
    private String zipCode;
    @Excel(name = "收件人手机号")
    private String mobile;
    @Excel(name = "默认项")
    private Integer defaultItem;
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