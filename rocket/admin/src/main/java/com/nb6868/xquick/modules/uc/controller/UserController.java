package com.nb6868.xquick.modules.uc.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.nb6868.xquick.booster.exception.ErrorCode;
import com.nb6868.xquick.booster.exception.XquickException;
import com.nb6868.xquick.booster.pojo.MsgResult;
import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.util.ConvertUtils;
import com.nb6868.xquick.booster.util.HttpContextUtils;
import com.nb6868.xquick.booster.util.JacksonUtils;
import com.nb6868.xquick.booster.util.bcrypt.BCryptPasswordEncoder;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.annotation.AnonAccess;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.common.util.AESUtils;
import com.nb6868.xquick.common.util.ExcelUtils;
import com.nb6868.xquick.modules.log.entity.LoginEntity;
import com.nb6868.xquick.modules.log.service.LoginService;
import com.nb6868.xquick.modules.uc.dto.*;
import com.nb6868.xquick.modules.uc.entity.RoleUserEntity;
import com.nb6868.xquick.modules.uc.excel.UserExcel;
import com.nb6868.xquick.modules.uc.service.DeptService;
import com.nb6868.xquick.modules.uc.service.RoleUserService;
import com.nb6868.xquick.modules.uc.service.TokenService;
import com.nb6868.xquick.modules.uc.service.UserService;
import com.nb6868.xquick.modules.uc.user.SecurityUser;
import com.nb6868.xquick.modules.uc.user.UserDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("uc:user:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<UserDTO> list = userService.listDto(params);

        return new Result<>().success(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("uc:user:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<UserDTO> page = userService.pageDto(params);

        return new Result<>().success(page);
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
        return new Result<UserDTO>().success(data);
    }

    @GetMapping("userInfo")
    @ApiOperation("登录用户信息")
    public Result<?> userInfo() {
        UserDTO data = ConvertUtils.sourceToTarget(SecurityUser.getUser(), UserDTO.class);
        return new Result<UserDTO>().success(data);
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

        userService.saveDto(dto);

        return new Result<>();
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("uc:user:update")
    public Result<?> update(@RequestBody UserDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        userService.updateDto(dto);

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
    public Result<?> delete(@RequestParam Long id) {
        //效验数据
        AssertUtils.isEmpty(id, "id");

        userService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @LogOperation("批量删除")
    @RequiresPermissions("uc:user:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        userService.logicDeleteByIds(ids);

        return new Result<>();
    }

    /**
     * 加密登录
     * 逻辑同login接口
     */
    @PostMapping("loginEncrypt")
    @ApiOperation(value = "加密登录")
    @AnonAccess
    public Result<?> loginEncrypt(HttpServletRequest request, @RequestBody String loginEncrypted) throws UnsupportedEncodingException {
        // 密文转json明文
        String loginRaw = AESUtils.decrypt(URLDecoder.decode(loginEncrypted, "utf-8"));
        // json明文转实体
        LoginRequest login = JacksonUtils.jsonToPojo(loginRaw, LoginRequest.class);

        // 效验数据
        ValidatorUtils.validateEntity(login, DefaultGroup.class);

        return userService.login(request, login);
    }

    /**
     * 登录
     * 支持帐号登录、短信登录
     */
    @PostMapping("login")
    @ApiOperation(value = "登录")
    @AnonAccess
    public Result<?> login(HttpServletRequest httpServletRequest, @RequestBody LoginRequest request) {
        // 效验数据
        ValidatorUtils.validateEntity(request, DefaultGroup.class);

        return userService.login(httpServletRequest, request);
    }

    /**
     * 注册
     */
    @PostMapping("register")
    @ApiOperation(value = "注册")
    @AnonAccess
    public Result<?> register(@RequestBody RegisterRequest request) {
        // 效验数据
        ValidatorUtils.validateEntity(request, DefaultGroup.class);

        return userService.register(request);
    }

    /**
     * 通过短信验证码修改密码
     * 忘记密码功能,通过短信验证码找回
     */
    @PostMapping("changePasswordBySmsCode")
    @ApiOperation(value = "通过短信验证码修改密码")
    @AnonAccess
    public Result<?> changePasswordBySmsCode(@RequestBody ChangePasswordBySmsCodeRequest request) {
        // 效验数据
        ValidatorUtils.validateEntity(request, DefaultGroup.class);

        return userService.changePasswordBySmsCode(request);
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
        loginLog.setIp(HttpContextUtils.getIpAddr(request));
        loginLog.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        loginLog.setResult(0);
        loginLog.setCreateId(user.getId());
        loginLog.setCreateName(user.getUsername());
        loginLog.setCreateTime(new Date());
        logLoginService.save(loginLog);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("uc:user:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<UserDTO> list = userService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "用户", list, UserExcel.class);
    }

    @PostMapping("import")
    @ApiOperation("导入")
    @LogOperation("导入")
    @RequiresPermissions("uc:user:import")
    public Result<?> importExcel(@RequestParam("file") MultipartFile file, @RequestParam(value = "deptId") Long deptId) throws Exception {
        if (file.isEmpty()) {
            return new Result<Map<String, Object>>().error(ErrorCode.UPLOAD_FILE_EMPTY);
        }
        ImportParams params = new ImportParams();
        params.setStartSheetIndex(0);
        List<UserExcel> list = ExcelImportUtil.importExcel(file.getInputStream(), UserExcel.class, params);
        if (list.size() > 1000) {
            throw new XquickException("单次导入不要超过1000条");
        }
        List<MsgResult> result = new ArrayList<>();
        for (UserExcel item : list) {
            String userName = StringUtils.deleteWhitespace(item.getUsername());
            String realName = StringUtils.deleteWhitespace(item.getRealName());
            String mobile = StringUtils.deleteWhitespace(item.getMobile());
            String remark = StringUtils.deleteWhitespace(item.getRemark());
            UserDTO user = new UserDTO();
            user.setDeptId(deptId);

            if (StringUtils.isBlank(userName)) {
                result.add(new MsgResult().error("用户名不能为空"));
            } else if (StringUtils.isBlank(mobile)) {
                result.add(new MsgResult().error("手机号不能为空"));
            } else if (userService.isMobileExisted(mobile, null)) {
                result.add(new MsgResult().error("手机号已存在"));
            } else if (userService.isUsernameExisted(userName, null)) {
                result.add(new MsgResult().error(userName + "用户名已存在"));
            } else {
                user.setRemark(remark);
                user.setRealName(realName);
                user.setUsername(userName);
                user.setMobile(mobile);
                user.setStatus(1);
                // 生成随机密码
                //String pwd = StringUtils.isEmpty(password) ? RandomStringUtils.randomAlphanumeric(8) : password;
                // 生成随机salt
                //user.setSalt(RandomStringUtils.randomAlphanumeric(6));
                // 密码加密
                //user.setPassword(PasswordUtils.encode(password + user.getSalt()));
                //user.setType(UserTypeEnum.USER.value());
                userService.saveDto(user);
                // 插入用户与角色关系表
                RoleUserEntity roleUser = new RoleUserEntity();
                roleUser.setUserId(user.getId());
                roleUserService.save(roleUser);
                result.add(new MsgResult().success("导入成功"));
                new Result<String>().success("sss");
            }
        }
        return new Result<>().success(result);
    }

}
