package com.nb6868.xquick.modules.shop.controller;

import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.util.ParamUtils;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.modules.shop.dto.SpuCategoryDTO;
import com.nb6868.xquick.modules.shop.dto.SpuCategoryTreeDTO;
import com.nb6868.xquick.modules.shop.service.SpuCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
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
@RequestMapping("shop/spuCategory")
@Api(tags="商品类别")
public class SpuCategoryController {

    @Autowired
    private SpuCategoryService categoryService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("shop:spuCategory:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<SpuCategoryDTO> list = categoryService.listDto(params);

        return new Result<>().success(list);
    }

    @GetMapping("tree")
    @ApiOperation("树表")
    @RequiresPermissions("shop:spuCategory:list")
    public Result<?> tree(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<SpuCategoryTreeDTO> tree = categoryService.tree(params);

        // 使用迭代器的删除方法删除
        if (ParamUtils.toBoolean(params.get("filterEmptyChild"), false)) {
            tree.removeIf(spuCategoryTree -> ObjectUtils.isEmpty(spuCategoryTree.getChildren()));
        }

        return new Result<>().success(tree);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("shop:category:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<SpuCategoryDTO> page = categoryService.pageDto(params);

        return new Result<>().success(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("shop:spuCategory:info")
    public Result<?> info(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        SpuCategoryDTO data = categoryService.getDtoById(id);

        data.setParentMenuList(categoryService.getParentMenuList(data.getPid()));

        return new Result<SpuCategoryDTO>().success(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("shop:spuCategory:save")
    public Result<?> save(@RequestBody SpuCategoryDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        categoryService.saveDto(dto);

        return new Result<>().success(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("shop:spuCategory:update")
    public Result<?> update(@RequestBody SpuCategoryDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        categoryService.updateDto(dto);

        return new Result<>().success(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("shop:spuCategory:delete")
    public Result<?> delete(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        categoryService.logicDeleteById(id);

        return new Result<>();
    }

}
