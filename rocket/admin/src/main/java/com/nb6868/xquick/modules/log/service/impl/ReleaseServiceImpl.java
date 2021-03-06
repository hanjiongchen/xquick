package com.nb6868.xquick.modules.log.service.impl;

import com.nb6868.xquick.booster.util.ConvertUtils;
import com.nb6868.xquick.booster.util.WrapperUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.modules.log.dao.ReleaseDao;
import com.nb6868.xquick.modules.log.dto.ReleaseDTO;
import com.nb6868.xquick.modules.log.entity.ReleaseEntity;
import com.nb6868.xquick.modules.log.service.ReleaseService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 更新日志
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class ReleaseServiceImpl extends CrudServiceImpl<ReleaseDao, ReleaseEntity, ReleaseDTO> implements ReleaseService {

    @Override
    public QueryWrapper<ReleaseEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<ReleaseEntity>(new QueryWrapper<>(), params)
                .like("code", "code")
                .like("name", "name")
                .eq("versionNo", "version_no")
                .eq("versionName", "version_name")
                .like("content", "content")
                .getQueryWrapper();
    }

    @Override
    public ReleaseDTO getLatestByCode(String code) {
        ReleaseEntity entity = query().eq("code", code).orderByDesc("version_no", "create_time").last("LIMIT 1").one();
        return ConvertUtils.sourceToTarget(entity, ReleaseDTO.class);
    }
}
