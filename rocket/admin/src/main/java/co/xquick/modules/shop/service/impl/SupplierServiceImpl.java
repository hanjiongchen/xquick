package co.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.shop.dao.SupplierDao;
import co.xquick.modules.shop.dto.SupplierDTO;
import co.xquick.modules.shop.entity.SupplierEntity;
import co.xquick.modules.shop.service.SupplierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 供应商
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class SupplierServiceImpl extends CrudServiceImpl<SupplierDao, SupplierEntity, SupplierDTO> implements SupplierService {

    @Override
    public QueryWrapper<SupplierEntity> getWrapper(String method, Map<String, Object> params){
        String name = (String)params.get("name");

        QueryWrapper<SupplierEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), "name", name);

        return wrapper;
    }

}
