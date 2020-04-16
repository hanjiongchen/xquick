package com.nb6868.xquick.modules.msg.service.impl;

import com.nb6868.xquick.booster.pojo.Const;
import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.booster.util.WrapperUtils;
import com.nb6868.xquick.modules.msg.dao.NoticeLogDao;
import com.nb6868.xquick.modules.msg.dto.NoticeLogDTO;
import com.nb6868.xquick.modules.msg.entity.NoticeLogEntity;
import com.nb6868.xquick.modules.msg.service.NoticeLogService;
import com.nb6868.xquick.modules.uc.user.SecurityUser;
import com.nb6868.xquick.modules.uc.user.UserDetail;
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
