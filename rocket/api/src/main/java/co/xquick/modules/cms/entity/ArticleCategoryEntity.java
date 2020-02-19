package co.xquick.modules.cms.entity;

import co.xquick.booster.pojo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章分类
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cms_article_category")
public class ArticleCategoryEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 备注
     */
    private String remark;
    /**
     * 图片
     */
    private String imgs;
}
