package co.xquick.modules.sys.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.JacksonUtils;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.sys.dao.ParamDao;
import co.xquick.modules.sys.dto.ParamDTO;
import co.xquick.modules.sys.entity.ParamEntity;
import co.xquick.modules.sys.service.ParamService;
import co.xquick.modules.uc.UcConst.UserTypeEnum;
import co.xquick.modules.uc.user.SecurityUser;
import co.xquick.modules.uc.user.UserDetail;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 参数管理
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Service
public class ParamServiceImpl extends CrudServiceImpl<ParamDao, ParamEntity, ParamDTO> implements ParamService {

    @Override
    public QueryWrapper<ParamEntity> getWrapper(String method, Map<String, Object> params) {
        UserDetail user = SecurityUser.getUser();
        return new WrapperUtils<ParamEntity>(new QueryWrapper<>(), params)
                .like("code", "code")
                .getQueryWrapper()
                .eq(user.getType() != UserTypeEnum.ADMIN.value(), "type", 1);
    }

    @Override
    public ParamDTO getByCode(String code) {
        UserDetail user = SecurityUser.getUser();
        ParamEntity entity = query()
                .eq(user.getType() != UserTypeEnum.ADMIN.value(), "type", 1)
                .eq("code", code)
                .last("limit 1")
                .one();
        return ConvertUtils.sourceToTarget(entity, ParamDTO.class);
    }


    @Override
    public String getContent(String code) {
        // 先从缓存中读取
        ParamEntity param = query().select("content").eq("code", code).last("limit 1").one();
        return ObjectUtils.isEmpty(param) ? null : param.getContent();
    }

    @Override
    public <T> T getContentObject(String code, Class<T> clazz) {
        String value = getContent(code);
        if (StringUtils.isNotBlank(value)) {
            return JacksonUtils.jsonToPojo(value, clazz);
        }

        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new XquickException(ErrorCode.PARAMS_GET_ERROR);
        }
    }

    @Override
    public <T> T getContentObject(String code, Class<T> clazz, T defaultObject) {
        String value = getContent(code);
        if (StringUtils.isNotBlank(value)) {
            return JacksonUtils.jsonToPojo(value, clazz);
        } else {
            return defaultObject;
        }
    }

    @Override
    public void updateContentByCode(String code, String content) {
        update().set("content", content).eq("code", code);
    }

}
