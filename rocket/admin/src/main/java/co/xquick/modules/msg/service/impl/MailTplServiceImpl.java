package co.xquick.modules.msg.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
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
    public MailTplDTO getByCode(String code) {
        return ConvertUtils.sourceToTarget(baseMapper.selectOne(new QueryWrapper<MailTplEntity>()
                .eq("code", code)
                .last("limit 1")), MailTplDTO.class);
    }

    @Override
    public boolean saveOrUpdateDto(MailTplDTO dto) {
        // 检查code是否存在
        if (hasDuplicated(dto.getId(), "code", dto.getCode())) {
            throw new XquickException(ErrorCode.HAS_DUPLICATED_RECORD, "编码");
        }
        return super.saveOrUpdateDto(dto);
    }

}
