package co.xquick.modules.ba.dto;

import co.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 秉奥-教师
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "秉奥-教师")
public class TeacherDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "头像")
	private String imgs;

	@ApiModelProperty(value = "内容")
	private String content;

	@ApiModelProperty(value = "状态0 未激活 1 激活")
	private Integer status;

}
