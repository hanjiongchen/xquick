package co.xquick.modules.sys.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.ParamUtils;
import co.xquick.booster.util.TreeUtils;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.sys.dao.RegionDao;
import co.xquick.modules.sys.dto.RegionDTO;
import co.xquick.modules.sys.dto.RegionTreeDTO;
import co.xquick.modules.sys.entity.RegionEntity;
import co.xquick.modules.sys.service.RegionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 行政区域
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Service
public class RegionServiceImpl extends CrudServiceImpl<RegionDao, RegionEntity, RegionDTO> implements RegionService {

    @Override
    public QueryWrapper<RegionEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<RegionEntity>(new QueryWrapper<>(), params)
                .eq("pid", "pid")
                .eq("level", "level")
                .like("name", "name")
                .getQueryWrapper()
                .eq("sys_region.deleted", 0);
    }

    @Override
    public List<RegionTreeDTO> treeList(Map<String, Object> params) {
        List<RegionEntity> entityList = baseMapper.selectList(getWrapper("treeList", params));

        List<RegionTreeDTO> dtoList = ConvertUtils.sourceToTarget(entityList, RegionTreeDTO.class);

        return TreeUtils.build(dtoList);
    }

    @Override
    public List<RegionDTO> listDto(Map<String, Object> params) {
        List<RegionEntity> entityList;
        if (ParamUtils.isNotEmpty(params.get("withChildNum")) && "true".equalsIgnoreCase(params.get("withChildNum").toString())) {
            entityList = baseMapper.selectListWithChildNum(getWrapper("list", params));
        } else {
            entityList = baseMapper.selectList(getWrapper("list", params));
        }

        return ConvertUtils.sourceToTarget(entityList, currentDtoClass());
    }

}
