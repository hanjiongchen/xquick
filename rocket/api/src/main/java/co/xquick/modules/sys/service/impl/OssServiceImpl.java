package co.xquick.modules.sys.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.sys.dao.OssDao;
import co.xquick.modules.sys.dto.OssDTO;
import co.xquick.modules.sys.entity.OssEntity;
import co.xquick.modules.sys.service.OssService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 素材库
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class OssServiceImpl extends CrudServiceImpl<OssDao, OssEntity, OssDTO> implements OssService {

    @Override
    public QueryWrapper<OssEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<OssEntity>(new QueryWrapper<>(), params)
                .eq("url", "url")
                .getQueryWrapper();
    }

}
