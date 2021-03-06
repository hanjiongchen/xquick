package com.nb6868.xquick.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nb6868.xquick.booster.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 广告位
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_ad")
public class AdEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
	private String name;
    /**
     * 位置
     */
	private String position;
    /**
     * 链接
     */
	private String link;
    /**
     * 备注
     */
	private String remark;
    /**
     * 图片
     */
	private String imgs;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 是否需要登录 0 不需要 1需要
     */
	private Integer needLogin;
}
