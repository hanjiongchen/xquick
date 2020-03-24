package co.xquick.modules.msg.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.msg.dao.MailTplDao;
import co.xquick.modules.msg.dto.MailTplDTO;
import co.xquick.modules.msg.entity.MailTplEntity;
import co.xquick.modules.msg.service.MailTplService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 邮件模板
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class MailTplServiceImpl extends CrudServiceImpl<MailTplDao, MailTplEntity, MailTplDTO> implements MailTplService {

    @Override
    public QueryWrapper<MailTplEntity> getWrapper(String method, Map<String, Object> params) {
        String name = (String) params.get("name");

        QueryWrapper<MailTplEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), "name", name);

        return wrapper;
    }

    @Override
    public MailTplEntity getByCode(String code) {
        return query().eq("code", code).last("limit 1").one();
    }

    @Override
    protected void beforeSaveOrUpdateDto(MailTplDTO dto, int type) {
        AssertUtils.isTrue(hasDuplicated(dto.getId(), "code", dto.getCode()), ErrorCode.HAS_DUPLICATED_RECORD, "编码");
    }

}
