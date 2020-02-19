package co.xquick.booster.service.impl;

import co.xquick.booster.dao.BaseDao;
import co.xquick.booster.pojo.PageData;
import co.xquick.booster.service.CrudService;
import co.xquick.booster.util.ConvertUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * DTO CRUD基础服务类
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public class CrudServiceImpl<M extends BaseDao<T>, T, D> extends BaseServiceImpl<M, T> implements CrudService<T, D> {

    /**
     * 构建条件构造器
     */
    public QueryWrapper<T> getWrapper(String method, Map<String, Object> params) {
        return new QueryWrapper<>();
    }

    @SuppressWarnings("unchecked")
    protected Class<D> currentDtoClass() {
        return (Class<D>) ReflectionKit.getSuperClassGenericType(getClass(), 2);
    }

    @Override
    public PageData<D> pageDto(Map<String, Object> params) {
        return pageDto(getPage(params, null, false), getWrapper("page", params));
    }

    @Override
    public PageData<D> pageDto(IPage<T> page, Wrapper<T> queryWrapper) {
        IPage<T> iPage = page(page, queryWrapper);

        PageData<D> pageData = getPageData(iPage, currentDtoClass());
        pageData.setLimit((int) page.getSize());
        pageData.setPage((int) page.getCurrent());
        pageData.setLastPage(page.getSize() * page.getCurrent() >= page.getTotal());
        return pageData;
    }

    @Override
    public List<D> listDto(Map<String, Object> params) {
        List<T> entityList = list(getWrapper("list", params));

        return ConvertUtils.sourceToTarget(entityList, currentDtoClass());
    }

    @Override
    public List<D> listDto(Wrapper<T> queryWrapper) {
        List<T> entityList = list(queryWrapper);

        return ConvertUtils.sourceToTarget(entityList, currentDtoClass());
    }

    @Override
    public int count(Map<String, Object> params) {
        return count(getWrapper("count", params));
    }

    @Override
    public D getDtoById(Serializable id) {
        T entity = getById(id);
        return ConvertUtils.sourceToTarget(entity, currentDtoClass());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDto(D dto) {
        T entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        boolean ret = save(entity);
        // copy主键值到dto
        BeanUtils.copyProperties(entity, dto);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDtos(List<D> dtos) {
        List<T> entityList = ConvertUtils.sourceToTarget(dtos, currentModelClass());
        // copy主键值到dto
        BeanUtils.copyProperties(entityList, dtos);
        return saveBatch(entityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDto(D dto) {
        T entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDtos(List<D> dtos) {
        List<T> entityList = ConvertUtils.sourceToTarget(dtos, currentModelClass());
        return updateBatchById(entityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateDto(D dto) {
        T entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        if (hasIdVal(entity)) {
            return updateById(entity);
        } else {
            boolean ret = save(entity);
            // copy主键值到dto
            BeanUtils.copyProperties(entity, dto);
            return ret;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateDtos(List<D> dtos) {
        List<T> entityList = ConvertUtils.sourceToTarget(dtos, currentModelClass());
        boolean ret = saveOrUpdateBatch(entityList);
        // copy主键值到dto
        BeanUtils.copyProperties(entityList, dtos);
        return ret;
    }

}
