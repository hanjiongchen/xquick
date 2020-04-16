package com.nb6868.xquick.modules.msg.service.impl;

import com.nb6868.xquick.booster.exception.ErrorCode;
import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.booster.util.WrapperUtils;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.modules.msg.dao.SmsTplDao;
import com.nb6868.xquick.modules.msg.dto.SmsTplDTO;
import com.nb6868.xquick.modules.msg.entity.SmsTplEntity;
import com.nb6868.xquick.modules.msg.service.SmsTplService;
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
    protected void beforeSaveOrUpdateDto(SmsTplDTO dto, int type) {
        AssertUtils.isTrue(hasDuplicated(dto.getId(), "code", dto.getCode()), ErrorCode.HAS_DUPLICATED_RECORD, "编码");
    }

}
