package co.xquick.modules.msg.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.msg.dao.SmsTplDao;
import co.xquick.modules.msg.dto.SmsTplDTO;
import co.xquick.modules.msg.entity.SmsTplEntity;
import co.xquick.modules.msg.service.SmsTplService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 短信模板
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class SmsTplServiceImpl extends CrudServiceImpl<SmsTplDao, SmsTplEntity, SmsTplDTO> implements SmsTplService {

    @Override
    public QueryWrapper<SmsTplEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<SmsTplEntity>(new QueryWrapper<>(), params)
                .like("code", "code")
                .like("name", "name")
                .like("content", "content")
                .like("config", "config")
                .getQueryWrapper();
    }

    @Override
    public SmsTplEntity getByCode(String code) {
        return query().eq("code", code).last("limit 1").one();
    }

    @Override
    public boolean saveOrUpdateDto(SmsTplDTO dto) {
        // 检查code是否存在
        AssertUtils.isTrue(hasDuplicated(dto.getId(), "code", dto.getCode()), ErrorCode.HAS_DUPLICATED_RECORD, "编码");
        return super.saveOrUpdateDto(dto);
    }

}
