package co.xquick.modules.uc.service.impl;

import cn.hutool.core.collection.CollUtil;
import co.xquick.booster.service.impl.BaseServiceImpl;
import co.xquick.modules.uc.dao.RoleDataScopeDao;
import co.xquick.modules.uc.entity.RoleDataScopeEntity;
import co.xquick.modules.uc.service.RoleDataScopeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 角色数据权限
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Service
public class RoleDataScopeServiceImpl extends BaseServiceImpl<RoleDataScopeDao, RoleDataScopeEntity> implements RoleDataScopeService {

    @Override
    public List<Long> getDeptIdList(Long roleId) {
        return baseMapper.getDeptIdList(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
        // 先删除角色数据权限关系
        deleteByRoleIds(Collections.singletonList(roleId));

        // 角色没有一个数据权限的情况
        if(CollUtil.isEmpty(deptIdList)){
            return ;
        }

        // 保存角色数据权限关系
        for(Long deptId : deptIdList){
            RoleDataScopeEntity sysRoleDataScopeEntity = new RoleDataScopeEntity();
            sysRoleDataScopeEntity.setDeptId(deptId);
            sysRoleDataScopeEntity.setRoleId(roleId);

            //保存
            save(sysRoleDataScopeEntity);
        }
    }

    @Override
    public boolean deleteByRoleIds(List<Long> roleIds) {
        return logicDeleteByWrapper(new QueryWrapper<RoleDataScopeEntity>().in("role_id", roleIds));
    }
}
