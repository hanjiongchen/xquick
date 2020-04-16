package com.nb6868.xquick.modules.ba.dto;

import com.nb6868.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 秉奥-用户检测
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "秉奥-用户检测")
public class ExamUserDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "检测类型 1 成人检测 2 孩子检测")
	private Integer subjectType;

	@ApiModelProperty(value = "检测结果")
	private String result;

	@ApiModelProperty(value = "家长名字")
	private String parentName;

	@ApiModelProperty(value = "小孩名字")
	private String childName;

	@ApiModelProperty(value = "小孩年级")
	private String childClass;

}
