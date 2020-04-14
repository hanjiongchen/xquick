package co.xquick.modules.ba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.ba.dao.ExamUserDao;
import co.xquick.modules.ba.dto.ExamUserDTO;
import co.xquick.modules.ba.entity.ExamUserEntity;
import co.xquick.modules.ba.service.ExamUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 秉奥-用户检测
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class ExamUserServiceImpl extends CrudServiceImpl<ExamUserDao, ExamUserEntity, ExamUserDTO> implements ExamUserService {

    @Override
    public QueryWrapper<ExamUserEntity> getWrapper(String method, Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<ExamUserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
