package co.xquick.modules.shop.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.TreeUtils;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.shop.dao.CategoryDao;
import co.xquick.modules.shop.dto.SpuCategoryDTO;
import co.xquick.modules.shop.dto.SpuCategoryTreeDTO;
import co.xquick.modules.shop.entity.SpuCategoryEntity;
import co.xquick.modules.shop.service.SpuCategoryService;
import co.xquick.modules.shop.service.SpuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 商品类别
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class SpuCategoryServiceImpl extends CrudServiceImpl<CategoryDao, SpuCategoryEntity, SpuCategoryDTO> implements SpuCategoryService {

    @Autowired
    private SpuService spuService;

    @Override
    public QueryWrapper<SpuCategoryEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<SpuCategoryEntity>(new QueryWrapper<>(), params)
                .like("name", "name")
                .eq("pid", "pid")
                .getQueryWrapper()
                .orderByAsc("sort");
    }

    @Override
    public List<SpuCategoryTreeDTO> tree(Map<String, Object> params) {
        List<SpuCategoryEntity> entityList = getBaseMapper().selectList(getWrapper("tree", params));

        List<SpuCategoryTreeDTO> dtoList = ConvertUtils.sourceToTarget(entityList, SpuCategoryTreeDTO.class);

        return TreeUtils.build(dtoList);
    }

    @Override
    protected void beforeSaveOrUpdateDto(SpuCategoryDTO dto, int type) {
        // 检查一下上级是否存在
        if (0L != dto.getPid()) {
            SpuCategoryEntity pEntity = getById(dto.getPid());
            AssertUtils.isEmpty(pEntity, ErrorCode.PARENT_NOT_EXISTED);
            dto.setParentName(pEntity.getName());
        }

        if (1 == type) {
            // 上级不能为自己
            AssertUtils.isTrue(dto.getId().equals(dto.getPid()), ErrorCode.PARENT_EQ_SELF);
        }
    }

    @Override
    protected void inSaveOrUpdateDto(SpuCategoryDTO dto, SpuCategoryEntity existedEntity, int type) {
        // 一级变x级,已存在下级,不允许修改级别
        if (1 == type && 0L != dto.getPid() && existedEntity.getPid() == 0) {
            boolean hasSub = hasSub("pid", dto.getId());
            AssertUtils.isTrue(hasSub, ErrorCode.COMMON_ERROR,"存在小类,不允许变更级别");
        }
    }

    @Override
    public boolean logicDeleteById(Serializable id) {
        if (query().eq("pid", id).count() > 0) {
            throw new XquickException("存在子类别,不允许删除");
        }
        if (spuService.query().eq("category_id", id).count() > 0) {
            throw new XquickException("该类别下存在商品,不允许删除");
        }
        return super.logicDeleteById(id);
    }

    @Override
    public List<SpuCategoryDTO> getParentMenuList(Long id) {
        List<SpuCategoryDTO> menus = new ArrayList<>();
        while (id != 0) {
            SpuCategoryDTO dto = getDtoById(id);
            if (dto != null) {
                menus.add(dto);
                id = dto.getPid();
            } else {
                id = 0L;
            }
        }
        Collections.reverse(menus);
        return menus;
    }
}
