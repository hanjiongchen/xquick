package co.xquick.modules.uc.controller;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.HttpContextUtils;
import co.xquick.booster.util.bcrypt.BCryptPasswordEncoder;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.booster.validator.group.UpdateGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.modules.log.LogConst.LoginOperationEnum;
import co.xquick.modules.log.LogConst.LoginStatusEnum;
import co.xquick.modules.log.entity.LoginEntity;
import co.xquick.modules.log.service.LoginService;
import co.xquick.modules.uc.dto.PasswordDTO;
import co.xquick.modules.uc.dto.UserDTO;
import co.xquick.modules.uc.service.DeptService;
import co.xquick.modules.uc.service.RoleUserService;
import co.xquick.modules.uc.service.TokenService;
import co.xquick.modules.uc.service.UserService;
import co.xquick.modules.uc.user.SecurityUser;
import co.xquick.modules.uc.user.UserDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author Charles
 */
@RestController
@RequestMapping("uc/user")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleUserService roleUserService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private LoginService logLoginService;

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("uc:user:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<UserDTO> page = userService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("uc:user:info")
    public Result<?> info(@RequestParam Long id) {
        UserDTO data = userService.getDtoById(id);
        AssertUtils.isNull(data, ErrorCode.DB_RECORD_NOT_EXISTED);
        // 用户角色列表
        data.setRoleIdList(roleUserService.getRoleIdList(id));
        // 部门树
        data.setDeptChain(deptService.getParentChain(data.getDeptId()));
        return new Result<UserDTO>().ok(data);
    }

    @GetMapping("userInfo")
    @ApiOperation("登录用户信息")
    public Result<?> userInfo() {
        UserDTO data = ConvertUtils.sourceToTarget(SecurityUser.getUser(), UserDTO.class);
        return new Result<UserDTO>().ok(data);
    }

    @PutMapping("password")
    @ApiOperation("修改密码")
    @LogOperation("修改密码")
    public Result<?> password(@RequestBody PasswordDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto);

        UserDetail user = SecurityUser.getUser();
        UserDTO userDTO = userService.getDtoById(user.getId());
        AssertUtils.isNull(userDTO, ErrorCode.DB_RECORD_NOT_EXISTED);

        // 原密码不正确
        if (!new BCryptPasswordEncoder().matches(dto.getPassword(), user.getPassword())) {
            return new Result<>().error(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        userService.updatePassword(user.getId(), dto.getNewPassword());

        return new Result<>();
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("uc:user:save")
    public Result<?> save(@RequestBody UserDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        userService.saveOrUpdateDto(dto);

        return new Result<>();
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("uc:user:update")
    public Result<?> update(@RequestBody UserDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        userService.saveOrUpdateDto(dto);

        return new Result<>();
    }

    @PutMapping("changeStatus")
    @ApiOperation("更新状态")
    @LogOperation("更新状态")
    @RequiresPermissions("uc:user:update")
    public Result<?> changeStatus(@RequestBody UserDTO dto) {
        userService.changeStatus(dto);

        return new Result<>();
    }

    @DeleteMapping("delete")
    @LogOperation("删除")
    @RequiresPermissions("uc:user:delete")
    public Result<?> delete(@RequestBody List<Long> ids) {
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        userService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @PostMapping("logout")
    @ApiOperation(value = "退出")
    public Result<?> logout(HttpServletRequest request, @RequestParam int type) {
        UserDetail user = SecurityUser.getUser();

        // 退出
        tokenService.deleteToken(user.getToken());

        // 用户信息
        LoginEntity loginLog = new LoginEntity();
        loginLog.setType(type);
        loginLog.setOperation(LoginOperationEnum.LOGOUT.value());
        loginLog.setIp(HttpContextUtils.getIpAddr(request));
        loginLog.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        loginLog.setStatus(LoginStatusEnum.SUCCESS.value());
        loginLog.setCreateId(user.getId());
        loginLog.setCreateName(user.getUsername());
        loginLog.setCreateTime(new Date());
        logLoginService.save(loginLog);

        return new Result<>();
    }

}
