package co.xquick.modules.log.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.log.dao.ErrorDao;
import co.xquick.modules.log.dto.ErrorDTO;
import co.xquick.modules.log.entity.ErrorEntity;
import co.xquick.modules.log.service.ErrorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 异常日志
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Service
public class ErrorServiceImpl extends CrudServiceImpl<ErrorDao, ErrorEntity, ErrorDTO> implements ErrorService {

    @Override
    public QueryWrapper<ErrorEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<ErrorEntity>(new QueryWrapper<>(), params)
                // 请求uri
                .like("uri", "uri")
                // 创建时间区间
                .ge("startCreateTime", "create_time")
                .le("endCreateTime", "create_time")
                .getQueryWrapper();
    }

}
