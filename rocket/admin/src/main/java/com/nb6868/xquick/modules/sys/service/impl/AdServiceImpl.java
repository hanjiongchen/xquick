package com.nb6868.xquick.modules.sys.service.impl;

import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.booster.util.WrapperUtils;
import com.nb6868.xquick.modules.sys.dao.AdDao;
import com.nb6868.xquick.modules.sys.dto.AdDTO;
import com.nb6868.xquick.modules.sys.entity.AdEntity;
import com.nb6868.xquick.modules.sys.service.AdService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 广告位
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class AdServiceImpl extends CrudServiceImpl<AdDao, AdEntity, AdDTO> implements AdService {

    @Override
    public QueryWrapper<AdEntity> getWrapper(String method, Map<String, Object> params){
        return new WrapperUtils<AdEntity>(new QueryWrapper<>(), params)
                .eq("position", "position")
                .like("name", "name")
                .getQueryWrapper();
    }

}
