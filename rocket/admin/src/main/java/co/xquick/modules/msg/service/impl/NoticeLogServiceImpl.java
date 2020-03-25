package co.xquick.modules.msg.service.impl;

import co.xquick.booster.pojo.Const;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.msg.dao.NoticeLogDao;
import co.xquick.modules.msg.dto.NoticeLogDTO;
import co.xquick.modules.msg.entity.NoticeLogEntity;
import co.xquick.modules.msg.service.NoticeLogService;
import co.xquick.modules.uc.user.SecurityUser;
import co.xquick.modules.uc.user.UserDetail;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * 通知发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class NoticeLogServiceImpl extends CrudServiceImpl<NoticeLogDao, NoticeLogEntity, NoticeLogDTO> implements NoticeLogService {

    @Override
    public QueryWrapper<NoticeLogEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<NoticeLogEntity>(new QueryWrapper<>(), params)
                .eq("status", "status")
                .eq("read", "read")
                .like("content", "content")
                .apply(Const.SQL_FILTER)
                .apply(Const.SQL_FILTER_MY)
                .getQueryWrapper();
    }

    @Override
    public boolean read(Long[] ids) {
        UserDetail user = SecurityUser.getUser();
        update(new NoticeLogEntity(), new UpdateWrapper<NoticeLogEntity>()
                .set("readed", 1)
                .in("id", Arrays.asList(ids))
                .eq("user_id", user.getId()));
        return true;
    }

}
