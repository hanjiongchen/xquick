package co.xquick.modules.shop.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.shop.dao.ReceiverDao;
import co.xquick.modules.shop.dto.ReceiverDTO;
import co.xquick.modules.shop.entity.ReceiverEntity;
import co.xquick.modules.shop.service.ReceiverService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 收件地址
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class ReceiverServiceImpl extends CrudServiceImpl<ReceiverDao, ReceiverEntity, ReceiverDTO> implements ReceiverService {

    @Override
    public QueryWrapper<ReceiverEntity> getWrapper(String method, Map<String, Object> params){
       return new WrapperUtils<ReceiverEntity>(new QueryWrapper<>(), params)
                .eq("defaultItem", "shop_receiver.status")
                .eq("userId", "shop_receiver.user_id")
                .eq("mobile", "shop_receiver.mobile")
                .like("address", "shop_receiver.address")
                .like("consignee", "shop_receiver.consignee")
                .getQueryWrapper()
                .eq("shop_receiver.deleted", 0);
    }

    @Override
    public boolean setDefaultItem(Long id) {
        return false;
    }

}
