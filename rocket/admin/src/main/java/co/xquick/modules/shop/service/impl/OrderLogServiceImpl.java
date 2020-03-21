package co.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.shop.dao.OrderLogDao;
import co.xquick.modules.shop.dto.OrderLogDTO;
import co.xquick.modules.shop.entity.OrderLogEntity;
import co.xquick.modules.shop.service.OrderLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 订单记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class OrderLogServiceImpl extends CrudServiceImpl<OrderLogDao, OrderLogEntity, OrderLogDTO> implements OrderLogService {

    @Override
    public QueryWrapper<OrderLogEntity> getWrapper(String method, Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<OrderLogEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
