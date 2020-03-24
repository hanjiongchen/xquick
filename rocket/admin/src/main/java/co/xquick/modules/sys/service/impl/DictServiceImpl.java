package co.xquick.modules.sys.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ParamUtils;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.sys.dao.DictDao;
import co.xquick.modules.sys.dto.DictDTO;
import co.xquick.modules.sys.entity.DictEntity;
import co.xquick.modules.sys.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

import static co.xquick.booster.constant.Constant.DICT_ROOT;

/**
 * 数据字典
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class DictServiceImpl extends CrudServiceImpl<DictDao, DictEntity, DictDTO> implements DictService {

    @Override
    public QueryWrapper<DictEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<DictEntity>(new QueryWrapper<>(), params)
                .eq("pid", "pid")
                .eq("type", "type")
                .like("name", "name")
                .eq("value", "value")
                .getQueryWrapper()
                .ne(ParamUtils.isEmpty(params.get("pid")), "pid", 0)
                .orderByAsc("sort", "value");
    }

    @Override
    protected void beforeSaveOrUpdateDto(DictDTO dto, int type) {
        if (type == 1) {
            // 更新下面所有的父类
            if (dto.getPid() == DICT_ROOT.longValue()){
                baseMapper.update(new DictEntity(), new UpdateWrapper<DictEntity>().eq("pid", dto.getId()).set("type", dto.getType()));
            }
        }
    }

}
