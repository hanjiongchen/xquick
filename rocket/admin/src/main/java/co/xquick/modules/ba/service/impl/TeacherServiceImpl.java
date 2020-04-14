package co.xquick.modules.ba.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.ba.dao.TeacherDao;
import co.xquick.modules.ba.dto.TeacherDTO;
import co.xquick.modules.ba.entity.TeacherEntity;
import co.xquick.modules.ba.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 秉奥-教师
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class TeacherServiceImpl extends CrudServiceImpl<TeacherDao, TeacherEntity, TeacherDTO> implements TeacherService {

    @Override
    public QueryWrapper<TeacherEntity> getWrapper(String method, Map<String, Object> params){
        return new WrapperUtils<TeacherEntity>(new QueryWrapper<>(), params)
                .eq("type", "type")
                .like("name", "name")
                .getQueryWrapper();
    }

}
