package com.nb6868.xquick.modules.cms.controller;

import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.modules.cms.dto.ArticleDTO;
import com.nb6868.xquick.modules.cms.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 文章管理
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("cms/article")
@Api(tags = "文章")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("cms:article:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<ArticleDTO> list = articleService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("cms:article:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<ArticleDTO> page = articleService.pageDto(params);

        return new Result<PageData<ArticleDTO>>().ok(page);
    }


    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("cms:article:info")
    public Result<?> info(@RequestParam Long id) {
        ArticleDTO data = articleService.getDtoById(id);

        return new Result<ArticleDTO>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("cms:article:save")
    public Result<?> save(@RequestBody ArticleDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        articleService.saveDto(dto);

        return new Result<>();
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("cms:article:update")
    public Result<?> update(@RequestBody ArticleDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        articleService.updateDto(dto);

        return new Result<>();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("cms:article:delete")
    public Result<?> delete(@RequestParam Long id) {
        // 效验数据
        AssertUtils.isEmpty(id, "id");

        articleService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("cms:article:deleteBatch")
    public Result<?> delete(@RequestBody List<Long> ids) {
        // 效验数据
        AssertUtils.isListEmpty(ids, "id");

        articleService.logicDeleteByIds(ids);

        return new Result<>();
    }

}
