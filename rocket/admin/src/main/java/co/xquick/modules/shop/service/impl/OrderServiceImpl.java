package co.xquick.modules.shop.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.shop.dao.OrderDao;
import co.xquick.modules.shop.dto.OrderDTO;
import co.xquick.modules.shop.entity.OrderEntity;
import co.xquick.modules.shop.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 订单
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class OrderServiceImpl extends CrudServiceImpl<OrderDao, OrderEntity, OrderDTO> implements OrderService {

    @Override
    public QueryWrapper<OrderEntity> getWrapper(String method, Map<String, Object> params){

        return new WrapperUtils<OrderEntity>(new QueryWrapper<>(), params)
                .like("no", "no")
                .eq("userId", "user_id")
                .eq("status", "status")
                // 创建时间区间
                .ge("startCreateTime", "create_time")
                .le("endCreateTime", "create_time")
                .and("search", queryWrapper -> {
                    String search = (String) params.get("search");
                    queryWrapper.like("no", search);
                })
                .getQueryWrapper();
    }

}
