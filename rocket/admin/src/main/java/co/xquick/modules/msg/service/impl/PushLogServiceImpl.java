package co.xquick.modules.msg.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ParamUtils;
import co.xquick.modules.msg.dao.PushLogDao;
import co.xquick.modules.msg.dto.PushLogDTO;
import co.xquick.modules.msg.entity.PushLogEntity;
import co.xquick.modules.msg.service.PushLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 消息推送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class PushLogServiceImpl extends CrudServiceImpl<PushLogDao, PushLogEntity, PushLogDTO> implements PushLogService {

    @Override
    public QueryWrapper<PushLogEntity> getWrapper(String method, Map<String, Object> params){
        return new QueryWrapper<PushLogEntity>()
                .like(ParamUtils.isNotEmpty(params.get("content")), "content", params.get("content"))
                .like(ParamUtils.isNotEmpty(params.get("params")), "params", params.get("params"))
                .like(ParamUtils.isNotEmpty(params.get("result")), "result", params.get("result"))
                .eq(ParamUtils.isNotEmpty(params.get("status")), "status", params.get("status"))
                .eq(ParamUtils.isNotEmpty(params.get("alias")), "alias", params.get("alias"))
                .eq(ParamUtils.isNotEmpty(params.get("tags")), "tags", params.get("tags"));
    }

}
