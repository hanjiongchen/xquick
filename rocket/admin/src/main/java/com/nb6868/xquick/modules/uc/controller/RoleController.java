package com.nb6868.xquick.modules.uc.controller;

import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.modules.uc.dto.RoleDTO;
import com.nb6868.xquick.modules.uc.service.RoleDataScopeService;
import com.nb6868.xquick.modules.uc.service.RoleMenuService;
import com.nb6868.xquick.modules.uc.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("uc/role")
@Api(tags="角色管理")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleMenuService roleMenuService;
	@Autowired
	private RoleDataScopeService roleDataScopeService;

	@GetMapping("page")
	@ApiOperation("分页")
	@RequiresPermissions("uc:role:page")
	public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params){
		PageData<RoleDTO> page = roleService.pageDto(params);

		return new Result<>().success(page);
	}

	@GetMapping("list")
	@ApiOperation("列表")
	@RequiresPermissions("uc:role:list")
	public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params){
		List<RoleDTO> data = roleService.listDto(params);

		return new Result<>().success(data);
	}

	@GetMapping("info")
	@ApiOperation("信息")
	@RequiresPermissions("uc:role:info")
	public Result<?> info(@RequestParam Long id){
		RoleDTO data = roleService.getDtoById(id);

		// 查询角色对应的菜单
		List<Long> menuIdList = roleMenuService.getMenuIdListByRoleId(id);
		data.setMenuIdList(menuIdList);

		// 查询角色对应的数据权限
		List<Long> deptIdList = roleDataScopeService.getDeptIdListByUserId(id);
		data.setDeptIdList(deptIdList);

		return new Result<>().success(data);
	}

	@PostMapping("save")
	@ApiOperation("保存")
	@LogOperation("保存")
	@RequiresPermissions("uc:role:save")
	public Result<?> save(@RequestBody RoleDTO dto){
		//效验数据
		ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

		roleService.saveDto(dto);

		return new Result<>().success(dto);
	}

	@PutMapping("update")
	@ApiOperation("修改")
	@LogOperation("修改")
	@RequiresPermissions("uc:role:update")
	public Result<?> update(@RequestBody RoleDTO dto){
		// 效验数据
		ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

		roleService.updateDto(dto);

		return new Result<>().success(dto);
	}

	@DeleteMapping("delete")
	@ApiOperation("删除")
	@LogOperation("删除")
	@RequiresPermissions("uc:role:delete")
	public Result<?> delete(@RequestParam Long id) {
		// 效验数据
		AssertUtils.isEmpty(id, "id");

		roleService.logicDeleteById(id);

		return new Result<>();
	}

	@DeleteMapping("deleteBatch")
	@ApiOperation("批量删除")
	@LogOperation("批量删除")
	@RequiresPermissions("uc:role:deleteBatch")
	public Result<?> deleteBatch(@RequestBody List<Long> ids) {
		// 效验数据
		AssertUtils.isListEmpty(ids, "id");

		roleService.logicDeleteByIds(ids);

		return new Result<>();
	}
}
