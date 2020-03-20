package co.xquick.modules.cms.controller;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.booster.validator.group.UpdateGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.modules.cms.dto.SiteDTO;
import co.xquick.modules.cms.service.SiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 站点
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("cms/site")
@Api(tags="站点")
public class SiteController {
    @Autowired
    private SiteService siteService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("cms:site:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<SiteDTO> list = siteService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("cms:site:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<SiteDTO> page = siteService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @LogOperation("信息")
    @RequiresPermissions("cms:site:info")
    public Result<?> info(HttpServletRequest request, @RequestParam Long id, @RequestParam(required = false) Long idid2) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        SiteDTO data = siteService.getDtoById(id);

        return new Result<>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("cms:site:save")
    public Result<?> save(@RequestBody SiteDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        siteService.saveOrUpdateDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("cms:site:update")
    public Result<?> update(@RequestBody SiteDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        siteService.saveOrUpdateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("cms:site:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        // 效验参数
        AssertUtils.isListEmpty(ids, "id");

        siteService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("cms:site:delete")
    public Result<?> delete(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        siteService.logicDeleteById(id);

        return new Result<>();
    }

}
