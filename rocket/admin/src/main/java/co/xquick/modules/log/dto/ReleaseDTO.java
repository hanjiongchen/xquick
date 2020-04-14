package co.xquick.modules.log.dto;

import co.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 更新日志
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "更新日志")
public class ReleaseDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "android/ios/api/vue")
	private String type;

	@ApiModelProperty(value = "编码")
	private String code;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "版本号")
	private Integer versionNo;

	@ApiModelProperty(value = "版本名称")
	private String versionName;

	@ApiModelProperty(value = "更新记录")
	private String content;

	@ApiModelProperty(value = "下载链接")
	private String downloadLink;

	@ApiModelProperty(value = "强制更新")
	private Integer forceUpdate;

	@ApiModelProperty(value = "显示在下载页面")
	private Integer show;

}
