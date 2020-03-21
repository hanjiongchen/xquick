package co.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.shop.dao.StockLogDao;
import co.xquick.modules.shop.dto.StockLogDTO;
import co.xquick.modules.shop.entity.StockLogEntity;
import co.xquick.modules.shop.service.StockLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 出入库记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class StockLogServiceImpl extends CrudServiceImpl<StockLogDao, StockLogEntity, StockLogDTO> implements StockLogService {

    @Override
    public QueryWrapper<StockLogEntity> getWrapper(String method, Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<StockLogEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
