package com.nb6868.xquick.modules.sys.service.impl;

import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.booster.util.WrapperUtils;
import com.nb6868.xquick.modules.sys.dao.CalendarDao;
import com.nb6868.xquick.modules.sys.dto.CalendarDTO;
import com.nb6868.xquick.modules.sys.entity.CalendarEntity;
import com.nb6868.xquick.modules.sys.service.CalendarService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 万年历
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class CalendarServiceImpl extends CrudServiceImpl<CalendarDao, CalendarEntity, CalendarDTO> implements CalendarService {

    @Override
    public QueryWrapper<CalendarEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<CalendarEntity>(new QueryWrapper<>(), params)
                .eq("year", "year")
                .eq("month", "month")
                .eq("day", "day")
                .eq("type", "type")
                .ge("day_date", "startDayDate")
                .le("day_date", "endDayDate")
                .getQueryWrapper();
    }

}
