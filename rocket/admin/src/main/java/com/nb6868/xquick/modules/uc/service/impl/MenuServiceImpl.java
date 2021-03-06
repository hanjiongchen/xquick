package com.nb6868.xquick.modules.uc.service.impl;

import com.nb6868.xquick.booster.exception.ErrorCode;
import com.nb6868.xquick.booster.exception.XquickException;
import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.booster.util.ConvertUtils;
import com.nb6868.xquick.booster.util.TreeUtils;
import com.nb6868.xquick.modules.uc.UcConst.UserTypeEnum;
import com.nb6868.xquick.modules.uc.dao.MenuDao;
import com.nb6868.xquick.modules.uc.dto.MenuDTO;
import com.nb6868.xquick.modules.uc.dto.MenuTreeDTO;
import com.nb6868.xquick.modules.uc.entity.MenuEntity;
import com.nb6868.xquick.modules.uc.service.MenuService;
import com.nb6868.xquick.modules.uc.service.RoleMenuService;
import com.nb6868.xquick.modules.uc.user.UserDetail;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * 菜单管理
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Service
public class MenuServiceImpl extends CrudServiceImpl<MenuDao, MenuEntity, MenuDTO> implements MenuService {

    @Autowired
    private RoleMenuService sysRoleMenuService;

    @Override
    public QueryWrapper<MenuEntity> getWrapper(String method, Map<String, Object> params) {
        return new QueryWrapper<>();
    }

    @Override
    protected void beforeSaveOrUpdateDto(MenuDTO dto, int type) {
        if (1 == type) {
            // 更新
            // 上级菜单不能为自身
            if (dto.getId().equals(dto.getPid())) {
                throw new XquickException(ErrorCode.SUPERIOR_MENU_ERROR);
            }
        }
    }

    @Override
    public List<MenuTreeDTO> getTreeByType(Integer type) {
        List<MenuEntity> menuList = getListByType(type);
        List<MenuTreeDTO> dtoList = ConvertUtils.sourceToTarget(menuList, MenuTreeDTO.class);
        return TreeUtils.build(dtoList);
    }

    @Override
    public List<MenuTreeDTO> getTreeByUser(UserDetail user, Integer type) {
        List<MenuEntity> entityList = getListByUser(user, type);
        List<MenuTreeDTO> dtoList = ConvertUtils.sourceToTarget(entityList, MenuTreeDTO.class);
        return TreeUtils.build(dtoList);
    }

    @Override
    public List<MenuEntity> getListByType(Integer type) {
        return query().eq(type != null, "type", type).orderByAsc("sort").list();
    }

    @Override
    public List<MenuEntity> getListByUser(UserDetail user, Integer type) {
        // 系统管理员，拥有最高权限
        if (user.getType() == UserTypeEnum.ADMIN.value()) {
            return getListByType(type);
        } else {
            return getBaseMapper().getListByUserId(user.getId(), type);
        }
    }

    @Override
    public List<String> getPermissionsListByRoles(List<String> roleCodes) {
        return getBaseMapper().getPermissionsListByRoles(roleCodes);
    }

    @Override
    public List<String> getPermissionsList() {
        // select permissions from uc_menu where permissions != '' and deleted = 0
        return listObjs(new QueryWrapper<MenuEntity>().select("permissions").ne("permissions", ""), Object::toString);
    }

    @Override
    public List<String> getPermissionsListByUserId(Long userId) {
        return getBaseMapper().getPermissionsListByUserId(userId);
    }

    @Override
    public List<MenuDTO> getParentList(Long id) {
        List<MenuDTO> menus = new ArrayList<>();
        while (id != 0) {
            MenuDTO dto = getDtoById(id);
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

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean logicDeleteByIds(Collection<? extends Serializable> idList) {
        // 判断是否有子菜单或按钮
        if (hasRecord(new QueryWrapper<MenuEntity>().in("pid", idList))) {
            throw new XquickException(ErrorCode.HAS_SUB_RECORD);
        }
        List<Long> ids = (List<Long>) idList;
        // 删除角色菜单关系
        sysRoleMenuService.deleteByMenuIds(ids);
        // 删除菜单
        return super.logicDeleteByIds(idList);
    }

}
