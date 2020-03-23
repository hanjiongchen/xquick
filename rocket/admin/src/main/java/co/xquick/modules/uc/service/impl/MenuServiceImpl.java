package co.xquick.modules.uc.service.impl;

import co.xquick.booster.constant.Constant;
import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.TreeUtils;
import co.xquick.modules.uc.UcConst.UserTypeEnum;
import co.xquick.modules.uc.dao.MenuDao;
import co.xquick.modules.uc.dto.MenuTreeDTO;
import co.xquick.modules.uc.entity.MenuEntity;
import co.xquick.modules.uc.service.MenuService;
import co.xquick.modules.uc.service.RoleMenuService;
import co.xquick.modules.uc.user.UserDetail;
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
public class MenuServiceImpl extends CrudServiceImpl<MenuDao, MenuEntity, MenuTreeDTO> implements MenuService {

    @Autowired
    private RoleMenuService sysRoleMenuService;

    @Override
    public QueryWrapper<MenuEntity> getWrapper(String method, Map<String, Object> params) {
        return new QueryWrapper<>();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateDto(MenuTreeDTO dto) {
        if (dto.hasId()) {
            // 更新
            // 上级菜单不能为自身
            if (dto.getId().equals(dto.getPid())) {
                throw new XquickException(ErrorCode.SUPERIOR_MENU_ERROR);
            }
        }
        return super.saveOrUpdateDto(dto);
    }

    @Override
    public List<MenuTreeDTO> getAllMenuList(Integer type) {
        List<MenuEntity> menuList = baseMapper.getMenuList(type);

        List<MenuTreeDTO> dtoList = ConvertUtils.sourceToTarget(menuList, MenuTreeDTO.class);

        return TreeUtils.build(dtoList, Constant.MENU_ROOT);
    }

    @Override
    public List<MenuTreeDTO> getUserMenuList(UserDetail user, Integer type) {
        List<MenuEntity> menuList;

        // 系统管理员，拥有最高权限
        if (user.getType() == UserTypeEnum.ADMIN.value()) {
            menuList = baseMapper.getMenuList(type);
        } else {
            menuList = baseMapper.getUserMenuList(user.getId(), type);
        }

        List<MenuTreeDTO> dtoList = ConvertUtils.sourceToTarget(menuList, MenuTreeDTO.class);

        return TreeUtils.build(dtoList);
    }

    @Override
    public List<MenuEntity> getListByUser(UserDetail user) {
        // 系统管理员，拥有最高权限
        if (user.getType() == UserTypeEnum.ADMIN.value()) {
            return query().orderByAsc("sort").list();
        } else {
            return baseMapper.getListByUserId(user.getId());
        }
    }


    @Override
    public List<MenuTreeDTO> getListPid(Long pid) {
        List<MenuEntity> menuList = baseMapper.getListPid(pid);

        return ConvertUtils.sourceToTarget(menuList, MenuTreeDTO.class);
    }

    @Override
    public List<MenuTreeDTO> getParentMenuList(Long id) {
        List<MenuTreeDTO> menus = new ArrayList<>();
        while (id != 0) {
            MenuTreeDTO dto = getDtoById(id);
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
