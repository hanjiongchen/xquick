package co.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.shop.dao.ReceiverDao;
import co.xquick.modules.shop.dto.ReceiverDTO;
import co.xquick.modules.shop.entity.ReceiverEntity;
import co.xquick.modules.shop.service.ReceiverService;
import org.apache.commons.lang3.StringUtils;
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
        String id = (String)params.get("id");

        QueryWrapper<ReceiverEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
