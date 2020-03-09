package co.xquick.modules.uc.controller;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.booster.validator.group.UpdateGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.modules.uc.dto.RoleDTO;
import co.xquick.modules.uc.service.RoleDataScopeService;
import co.xquick.modules.uc.service.RoleMenuService;
import co.xquick.modules.uc.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "角色名", paramType = "query", dataType="String")
	})
	@RequiresPermissions("uc:role:page")
	public Result page(@ApiIgnore @RequestParam Map<String, Object> params){
		PageData<RoleDTO> page = roleService.pageDto(params);

		return new Result<>().ok(page);
	}

	@GetMapping("list")
	@ApiOperation("列表")
	@RequiresPermissions("uc:role:list")
	public Result<List<RoleDTO>> list(@ApiIgnore @RequestParam Map<String, Object> params){
		List<RoleDTO> data = roleService.listDto(params);

		return new Result<List<RoleDTO>>().ok(data);
	}

	@GetMapping("info")
	@ApiOperation("信息")
	@RequiresPermissions("uc:role:info")
	public Result info(@RequestParam Long id){
		RoleDTO data = roleService.getDtoById(id);

		// 查询角色对应的菜单
		List<Long> menuIdList = roleMenuService.getMenuIdList(id);
		data.setMenuIdList(menuIdList);

		// 查询角色对应的数据权限
		List<Long> deptIdList = roleDataScopeService.getDeptIdList(id);
		data.setDeptIdList(deptIdList);
		return new Result<>().ok(data);
	}

	@PostMapping("save")
	@ApiOperation("保存")
	@LogOperation("保存")
	@RequiresPermissions("uc:role:save")
	public Result save(@RequestBody RoleDTO dto){
		//效验数据
		ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

		roleService.saveOrUpdateDto(dto);

		return new Result();
	}

	@PutMapping("update")
	@ApiOperation("修改")
	@LogOperation("修改")
	@RequiresPermissions("uc:role:update")
	public Result update(@RequestBody RoleDTO dto){
		// 效验数据
		ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

		roleService.saveOrUpdateDto(dto);

		return new Result();
	}

	@DeleteMapping("delete")
	@ApiOperation("删除")
	@LogOperation("删除")
	@RequiresPermissions("uc:role:delete")
	public Result delete(@RequestBody List<Long> ids) {
		// 效验数据
		AssertUtils.isListEmpty(ids, "id");

		roleService.logicDeleteByIds(ids);

		return new Result();
	}
}
