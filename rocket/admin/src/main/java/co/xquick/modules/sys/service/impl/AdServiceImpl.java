package co.xquick.modules.sys.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.sys.dao.AdDao;
import co.xquick.modules.sys.dto.AdDTO;
import co.xquick.modules.sys.entity.AdEntity;
import co.xquick.modules.sys.service.AdService;
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
