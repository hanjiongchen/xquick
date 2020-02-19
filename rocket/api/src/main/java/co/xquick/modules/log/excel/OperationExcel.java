package co.xquick.modules.log.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 操作日志
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
public class OperationExcel {
    @Excel(name = "用户操作")
    private String operation;
    @Excel(name = "请求URI")
    private String uri;
    @Excel(name = "请求方式")
    private String method;
    @Excel(name = "请求参数")
    private String params;
    @Excel(name = "请求时长(毫秒)")
    private Integer requestTime;
    @Excel(name = "User-Agent")
    private String userAgent;
    @Excel(name = "操作IP")
    private String ip;
    @Excel(name = "状态", replace = {"失败_0", "成功_1"})
    private Integer status;
    @Excel(name = "用户名")
    private String createName;
    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
