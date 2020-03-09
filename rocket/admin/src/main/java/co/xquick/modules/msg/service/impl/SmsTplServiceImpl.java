package co.xquick.modules.msg.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.ParamUtils;
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
        return new QueryWrapper<SmsTplEntity>()
                .like(ParamUtils.isNotEmpty(params.get("name")), "name", params.get("name"))
                .like(ParamUtils.isNotEmpty(params.get("content")), "content", params.get("content"))
                .like(ParamUtils.isNotEmpty(params.get("params")), "params", params.get("params"))
                .like(ParamUtils.isNotEmpty(params.get("config")), "config", params.get("config"));
    }

    @Override
    public SmsTplDTO getByCode(String code) {
        return ConvertUtils.sourceToTarget(baseMapper.selectOne(new QueryWrapper<SmsTplEntity>()
                .eq("code", code)
                .last("limit 1")), SmsTplDTO.class);
    }

    @Override
    public boolean saveOrUpdateDto(SmsTplDTO dto) {
        // 检查code是否存在
        boolean hasDuplicatedRecord = hasDuplicated(dto.getId(), "code", dto.getCode());
        if (hasDuplicatedRecord) {
            throw new XquickException(ErrorCode.HAS_DUPLICATED_RECORD, "编码");
        }
        return super.saveOrUpdateDto(dto);
    }

}
