package co.xquick.modules.ba.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.ba.dao.SubjectDao;
import co.xquick.modules.ba.dto.SubjectDTO;
import co.xquick.modules.ba.entity.SubjectEntity;
import co.xquick.modules.ba.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 秉奥-检测题
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class SubjectServiceImpl extends CrudServiceImpl<SubjectDao, SubjectEntity, SubjectDTO> implements SubjectService {

    @Override
    public QueryWrapper<SubjectEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<SubjectEntity>(new QueryWrapper<>(), params)
                .eq("type", "type")
                .like("question", "question")
                .getQueryWrapper();
    }

}
