package co.xquick.modules.log.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.log.dao.LoginDao;
import co.xquick.modules.log.dto.LoginDTO;
import co.xquick.modules.log.entity.LoginEntity;
import co.xquick.modules.log.service.LoginService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 登录日志
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Service
public class LoginServiceImpl extends CrudServiceImpl<LoginDao, LoginEntity, LoginDTO> implements LoginService {

    @Override
    public QueryWrapper<LoginEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<LoginEntity>(new QueryWrapper<>(), params)
                // 状态
                .eq("status", "status")
                // 用户
                .like("createName", "create_name")
                // 创建时间区间
                .ge("startCreateTime", "create_time")
                .le("endCreateTime", "create_time")
                .getQueryWrapper();
    }

}
