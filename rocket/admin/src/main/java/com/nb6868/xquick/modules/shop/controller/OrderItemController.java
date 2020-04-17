package com.nb6868.xquick.modules.shop.controller;

import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.common.util.ExcelUtils;
import com.nb6868.xquick.modules.shop.dto.OrderItemDTO;
import com.nb6868.xquick.modules.shop.excel.OrderItemExcel;
import com.nb6868.xquick.modules.shop.service.OrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 订单明细
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("shop/orderItem")
@Api(tags="订单明细")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("shop:orderItem:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<OrderItemDTO> list = orderItemService.listDto(params);

        return new Result<>().success(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("shop:orderItem:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<OrderItemDTO> page = orderItemService.pageDto(params);

        return new Result<>().success(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("shop:orderItem:info")
    public Result<?> info(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        OrderItemDTO data = orderItemService.getDtoById(id);

        return new Result<OrderItemDTO>().success(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("shop:orderItem:save")
    public Result<?> save(@RequestBody OrderItemDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        orderItemService.saveDto(dto);

        return new Result<>().success(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("shop:orderItem:update")
    public Result<?> update(@RequestBody OrderItemDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        orderItemService.updateDto(dto);

        return new Result<>().success(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("shop:orderItem:delete")
    public Result<?> delete(@RequestBody Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        orderItemService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("shop:orderItem:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        // 效验参数
        AssertUtils.isListEmpty(ids, "id");

        orderItemService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("shop:orderItem:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<OrderItemDTO> list = orderItemService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "订单明细", list, OrderItemExcel.class);
    }

}
