package com.nb6868.xquick.modules.sys.controller;

import com.nb6868.xquick.booster.pojo.Kv;
import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.util.JacksonUtils;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.annotation.AnonAccess;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.common.util.ExcelUtils;
import com.nb6868.xquick.modules.sys.dto.ParamDTO;
import com.nb6868.xquick.modules.sys.excel.ParamExcel;
import com.nb6868.xquick.modules.sys.service.ParamService;
import com.nb6868.xquick.modules.uc.UcConst;
import com.nb6868.xquick.modules.uc.dto.LoginCfg;
import com.nb6868.xquick.modules.uc.dto.LoginChannel;
import com.nb6868.xquick.modules.uc.dto.LoginChannelCfg;
import com.google.common.base.Splitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 参数管理
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("sys/param")
@Api(tags = "参数管理")
public class ParamController {

    @Autowired
    private ParamService paramService;

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("sys:param:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<ParamDTO> page = paramService.pageDto(params);

        return new Result<>().success(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("sys:param:info")
    public Result<?> info(@RequestParam Long id) {
        ParamDTO data = paramService.getDtoById(id);

        return new Result<>().success(data);
    }

    @GetMapping("getContentByCodes")
    @ApiOperation("通过code获取对应参数的content")
    @AnonAccess
    public Result<?> getContentByCodes(@Param("codes") String codes) {
        // 效验数据
        AssertUtils.isEmpty(codes, "codes");
        Iterable<String> codeList = Splitter.on(',').trimResults().omitEmptyStrings().split(codes.trim());
        Kv kv = Kv.init();
        for (String code : codeList) {
            String content = paramService.getContent(code);
            if (UcConst.LOGIN_CFG_ADMIN.equalsIgnoreCase(code)) {
                // 用户登录信息
                LoginCfg loginCfg = JacksonUtils.jsonToPojo(content, LoginCfg.class);
                for (LoginChannel channel : loginCfg.getChannels()) {
                    if (channel.getEnable()) {
                        channel.setCfg(paramService.getContentObject(UcConst.LOGIN_CHANNEL_CFG_PREFIX + channel.getType(), LoginChannelCfg.class));
                    }
                }
                kv.set(code, loginCfg);
            } else {
                kv.set(code, JacksonUtils.jsonToMap(content));
            }

        }
        return new Result<>().success(kv);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:param:save")
    public Result<?> save(@RequestBody ParamDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        paramService.saveDto(dto);

        return new Result<>().success(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:param:update")
    public Result<?> update(@RequestBody ParamDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        paramService.updateDto(dto);

        return new Result<>().success(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:param:delete")
    public Result<?> delete(@RequestParam Long id) {
        //效验数据
        AssertUtils.isEmpty(id, "id");

        paramService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("sys:param:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        paramService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("sys:param:export")
    @ApiImplicitParam(name = "code", value = "参数编码", paramType = "query", dataType = "String")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<ParamDTO> list = paramService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "参数", list, ParamExcel.class);
    }

}
