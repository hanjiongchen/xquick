package co.xquick.modules.uc.service.impl;

import co.xquick.booster.service.impl.BaseServiceImpl;
import co.xquick.modules.uc.dao.RoleMenuDao;
import co.xquick.modules.uc.entity.RoleMenuEntity;
import co.xquick.modules.uc.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenuDao, RoleMenuEntity> implements RoleMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIds) {
        // 先删除角色菜单关系
        deleteByRoleIds(Collections.singletonList(roleId));

        if (ObjectUtils.isNotEmpty(menuIds)) {
            //保存角色菜单关系
            for (Long menuId : menuIds) {
                RoleMenuEntity sysRoleMenuEntity = new RoleMenuEntity();
                sysRoleMenuEntity.setMenuId(menuId);
                sysRoleMenuEntity.setRoleId(roleId);
                //保存
                save(sysRoleMenuEntity);
            }
        }
    }

    @Override
    public List<Long> getMenuIdList(Long roleId) {
        return baseMapper.getMenuIdList(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByRoleIds(List<Long> roleIds) {
        return logicDeleteByWrapper(new QueryWrapper<RoleMenuEntity>().in("role_id", roleIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByMenuIds(List<Long> menuIds) {
        return logicDeleteByWrapper(new QueryWrapper<RoleMenuEntity>().in("menu_id", menuIds));
    }

}
