package co.xquick.modules.ba.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import co.xquick.booster.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 秉奥-用户检测
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("ba_exam_user")
public class ExamUserEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
	private Long userId;
    /**
     * 检测类型 1 成人检测 2 孩子检测
     */
	private Integer subjectType;
    /**
     * 检测结果
     */
	private String result;
    /**
     * 家长名字
     */
	private String parentName;
    /**
     * 小孩名字
     */
	private String childName;
    /**
     * 小孩年级
     */
	private String childClass;
}