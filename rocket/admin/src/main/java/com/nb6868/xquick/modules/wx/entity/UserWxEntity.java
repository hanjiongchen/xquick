package com.nb6868.xquick.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nb6868.xquick.booster.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信用户
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("uc_user_wx")
public class UserWxEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 微信unionid
     */
	private String unionId;
    /**
     * 微信openid
     */
	private String openId;
    /**
     * 类型 1 移动应用 2 网站应用 3 公众帐号 4 小程序 5 第三方应用
     */
	private Integer appType;
    /**
     * 微信appid
     */
	private String appId;
    /**
     * 性别 0未知 1男性 2女性
     */
	private Integer gender;
    /**
     * 昵称
     */
	private String nickName;
    /**
     * 头像
     */
	private String avatarUrl;
    /**
     * 城市
     */
	private String city;
    /**
     * 省份
     */
	private String province;
    /**
     * 国家
     */
	private String country;
    /**
     * 对应的用户id
     */
	private Long userId;
}
