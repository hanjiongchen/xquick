package co.xquick.modules.shop.controller;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.booster.validator.group.UpdateGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.modules.shop.dto.CategoryDTO;
import co.xquick.modules.shop.dto.CategoryTreeDTO;
import co.xquick.modules.shop.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 商品类别
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("shop/category")
@Api(tags="商品类别")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("shop:category:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<CategoryDTO> list = categoryService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("tree")
    @ApiOperation("树表")
    @RequiresPermissions("shop:category:list")
    public Result<?> tree(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<CategoryTreeDTO> tree = categoryService.tree(params);

        return new Result<>().ok(tree);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("shop:category:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<CategoryDTO> page = categoryService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("shop:category:info")
    public Result<?> info(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        CategoryDTO data = categoryService.getDtoById(id);

        data.setParentMenuList(categoryService.getParentMenuList(data.getPid()));

        return new Result<CategoryDTO>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("shop:category:save")
    public Result<?> save(@RequestBody CategoryDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        categoryService.saveDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("shop:category:update")
    public Result<?> update(@RequestBody CategoryDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        categoryService.updateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("shop:category:delete")
    public Result<?> delete(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        categoryService.logicDeleteById(id);

        return new Result<>();
    }

}
