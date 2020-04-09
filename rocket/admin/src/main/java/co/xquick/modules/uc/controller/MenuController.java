package co.xquick.modules.uc.controller;

import co.xquick.booster.pojo.Kv;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.TreeUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.common.annotation.GuestAccess;
import co.xquick.common.annotation.LogOperation;
import co.xquick.modules.uc.UcConst;
import co.xquick.modules.uc.dto.MenuDTO;
import co.xquick.modules.uc.dto.MenuTreeDTO;
import co.xquick.modules.uc.entity.MenuEntity;
import co.xquick.modules.uc.service.MenuService;
import co.xquick.modules.uc.service.ShiroService;
import co.xquick.modules.uc.user.SecurityUser;
import co.xquick.modules.uc.user.UserDetail;
import com.google.common.base.Splitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单权限
 * 注意这不仅仅是menu，还是permissions
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("uc/menu")
@Api(tags = "菜单权限")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private ShiroService shiroService;

    @GetMapping("userMenu")
    @ApiOperation("用户菜单权限")
    public Result<?> userMenu() {
        UserDetail user = SecurityUser.getUser();
        // 获取该用户所有menu
        List<MenuEntity> allList = menuService.getListByUser(user, null);
        // 过滤出其中显示菜单
        List<MenuTreeDTO> menuList = new ArrayList<>();
        // 过滤出其中路由菜单
        List<MenuDTO> urlList = new ArrayList<>();
        // 过滤处出其权限
        Set<String> permissions = new HashSet<>();
        allList.forEach(menu -> {
            if (menu.getShowMenu() == 1 && menu.getType() == UcConst.MenuTypeEnum.MENU.value()) {
                menuList.add(ConvertUtils.sourceToTarget(menu, MenuTreeDTO.class));
            }
            if (StringUtils.isNotBlank(menu.getUrl())) {
                urlList.add(ConvertUtils.sourceToTarget(menu, MenuDTO.class));
            }
            if (StringUtils.isNotBlank(menu.getPermissions())) {
                // 去除中间的空内容
                permissions.addAll(Splitter.on(',').trimResults().omitEmptyStrings().splitToList(menu.getPermissions().trim()));
            }
        });
        // 将菜单列表转成菜单树
        List<MenuTreeDTO> menuTree = TreeUtils.build(menuList);
        return new Result<>().ok(Kv.init().set("menuTree", menuTree).set("urlList", urlList).set("permissions", permissions));
    }

    @GetMapping("userTree")
    @ApiOperation("当前用户菜单")
    @ApiImplicitParam(name = "type", value = "菜单类型 0：菜单 1：按钮  null：全部", paramType = "query", dataType = "int")
    public Result<?> userTree(Integer type) {
        UserDetail user = SecurityUser.getUser();
        List<MenuTreeDTO> list = menuService.getTreeByUser(user, type);

        return new Result<>().ok(list);
    }

    @GetMapping("permissions")
    @ApiOperation("登录用户权限范围")
    public Result<?> permissions() {
        UserDetail user = SecurityUser.getUser();
        Set<String> set = shiroService.getUserPermissions(user);

        return new Result<Set<String>>().ok(set);
    }

    @GetMapping("tree")
    @ApiOperation("列表")
    @ApiImplicitParam(name = "type", value = "菜单类型 0：菜单 1：按钮  null：全部", paramType = "query", dataType = "int")
    @RequiresPermissions("uc:menu:list")
    public Result<?> tree(Integer type) {
        List<MenuTreeDTO> list = menuService.getTreeByType(type);

        return new Result<>().ok(list);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("uc:menu:info")
    public Result<?> info(@RequestParam Long id) {
        MenuDTO data = menuService.getDtoById(id);

        data.setParentMenuList(menuService.getParentList(data.getPid()));

        return new Result<>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("uc:menu:save")
    public Result<?> save(@RequestBody MenuDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, DefaultGroup.class);

        menuService.saveDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("uc:menu:update")
    public Result<?> update(@RequestBody MenuDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, DefaultGroup.class);

        menuService.updateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("uc:menu:delete")
    public Result<?> delete(@RequestParam Long id) {
        // 效验数据
        AssertUtils.isEmpty(id, "id");

        menuService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("uc:menu:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        // 效验数据
        AssertUtils.isListEmpty(ids, "id");

        menuService.logicDeleteByIds(ids);

        return new Result<>();
    }

}
