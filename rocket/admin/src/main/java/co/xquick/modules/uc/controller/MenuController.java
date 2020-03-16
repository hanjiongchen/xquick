package co.xquick.modules.uc.controller;

import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.modules.uc.dto.MenuTreeDTO;
import co.xquick.modules.uc.service.MenuService;
import co.xquick.modules.uc.service.ShiroService;
import co.xquick.modules.uc.user.SecurityUser;
import co.xquick.modules.uc.user.UserDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("userTree")
    @ApiOperation("当前用户菜单")
    @ApiImplicitParam(name = "type", value = "菜单类型 0：菜单 1：按钮  null：全部", paramType = "query", dataType = "int")
    public Result userTree(Integer type) {
        UserDetail user = SecurityUser.getUser();
        List<MenuTreeDTO> list = menuService.getUserMenuList(user, type);

        return new Result<>().ok(list);
    }

    @GetMapping("permissions")
    @ApiOperation("登录用户权限范围")
    public Result<Set<String>> permissions() {
        UserDetail user = SecurityUser.getUser();
        Set<String> set = shiroService.getUserPermissions(user);

        return new Result<Set<String>>().ok(set);
    }

    @GetMapping("tree")
    @ApiOperation("列表")
    @ApiImplicitParam(name = "type", value = "菜单类型 0：菜单 1：按钮  null：全部", paramType = "query", dataType = "int")
    @RequiresPermissions("uc:menu:list")
    public Result tree(Integer type) {
        List<MenuTreeDTO> list = menuService.getAllMenuList(type);

        return new Result<>().ok(list);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("uc:menu:info")
    public Result info(@RequestParam Long id) {
        MenuTreeDTO data = menuService.getDtoById(id);
        data.setParentMenuList(menuService.getParentMenuList(data.getPid()));

        return new Result<>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("uc:menu:save")
    public Result save(@RequestBody MenuTreeDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, DefaultGroup.class);

        menuService.saveOrUpdateDto(dto);

        return new Result();
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("uc:menu:update")
    public Result update(@RequestBody MenuTreeDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, DefaultGroup.class);

        menuService.saveOrUpdateDto(dto);

        return new Result();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("uc:menu:delete")
    public Result delete(@RequestBody List<Long> ids) {
        // 效验数据
        AssertUtils.isListEmpty(ids, "id");

        menuService.logicDeleteByIds(ids);

        return new Result();
    }

}