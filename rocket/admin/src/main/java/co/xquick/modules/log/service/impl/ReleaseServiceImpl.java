package co.xquick.modules.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.log.dao.ReleaseDao;
import co.xquick.modules.log.dto.ReleaseDTO;
import co.xquick.modules.log.entity.ReleaseEntity;
import co.xquick.modules.log.service.ReleaseService;
import org.apache.commons.lang3.StringUtils;
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
    public QueryWrapper<ReleaseEntity> getWrapper(String method, Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<ReleaseEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
