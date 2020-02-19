package co.xquick.modules.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.cms.dao.SiteDao;
import co.xquick.modules.cms.dto.SiteDTO;
import co.xquick.modules.cms.entity.SiteEntity;
import co.xquick.modules.cms.service.SiteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 站点
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class SiteServiceImpl extends CrudServiceImpl<SiteDao, SiteEntity, SiteDTO> implements SiteService {

    @Override
    public QueryWrapper<SiteEntity> getWrapper(String method, Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SiteEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
