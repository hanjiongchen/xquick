package co.xquick.modules.uc.service.impl;

import co.xquick.booster.constant.Constant;
import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.*;
import co.xquick.booster.util.bcrypt.BCryptPasswordEncoder;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.log.LogConst;
import co.xquick.modules.log.LogConst.LoginStatusEnum;
import co.xquick.modules.log.entity.LoginEntity;
import co.xquick.modules.log.service.LoginService;
import co.xquick.modules.msg.dto.SmsLogDTO;
import co.xquick.modules.msg.service.SmsLogService;
import co.xquick.modules.sys.service.ParamService;
import co.xquick.modules.uc.UcConst.LoginTypeEnum;
import co.xquick.modules.uc.UcConst.UserStatusEnum;
import co.xquick.modules.uc.UcConst.UserTypeEnum;
import co.xquick.modules.uc.dao.UserDao;
import co.xquick.modules.uc.dto.LoginConfigDTO;
import co.xquick.modules.uc.dto.UserDTO;
import co.xquick.modules.uc.entity.UserEntity;
import co.xquick.modules.uc.enums.GenderEnum;
import co.xquick.modules.uc.service.*;
import co.xquick.modules.uc.user.SecurityUser;
import co.xquick.modules.uc.user.UserDetail;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<UserDao, UserEntity, UserDTO> implements UserService {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private LoginService logLoginService;
    @Autowired
    private ParamService paramService;
    @Autowired
    private RoleUserService roleUserService;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private SmsLogService smsLogService;

    @Override
    public QueryWrapper<UserEntity> getWrapper(String method, Map<String, Object> params) {
        QueryWrapper<UserEntity> qw = new WrapperUtils<UserEntity>(new QueryWrapper<>(), params)
                .eq("status", "uc_user.status")
                .eq("type", "uc_user.type")
                .like("username", "uc_user.username")
                .like("deptId", "uc_user.deptId")
                .like("mobile", "mobile")
                .like("realName", "real_name")
                // 数据过滤
                .apply(Constant.SQL_FILTER)
                .getQueryWrapper()
                .eq("uc_user.deleted", 0);

        // 普通管理员，只能查询所属部门及子部门的数据
        UserDetail user = SecurityUser.getUser();
        if (user.getType() > UserTypeEnum.SYSADMIN.value()) {
            qw.in(user.getType() > UserTypeEnum.SYSADMIN.value(), "uc_user.dept_id", deptService.getSubDeptIdList(user.getDeptId()));
        }
        // 角色
        String[] roleIds = ParamUtils.toArray(params, "roleIds");
        qw.and(roleIds.length > 0, queryWrapper -> {
            for (int i = 0; i < roleIds.length; i++) {
                queryWrapper.or(i != 0).apply("find_in_set({0}, role.roleIds)", roleIds[i]);
            }
        });
        return qw;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> login(HttpServletRequest request, co.xquick.modules.uc.dto.LoginDTO login) {
        // 获得登录配置
        LoginConfigDTO loginConfig = paramService.getContentObject(Constant.LOGIN_CONFIG_KEY + "_" + login.getType(), LoginConfigDTO.class, null);
        AssertUtils.isNull(loginConfig, ErrorCode.UNKNOWN_LOGIN_TYPE);

        // 验证码是否正确
        if (loginConfig.getCaptcha()) {
            // 启用验证码
            if (StringUtils.isEmpty(login.getCaptcha()) || StringUtils.isEmpty(login.getUuid())) {
                throw new XquickException(ErrorCode.CAPTCHA_ERROR);
            } else if (!login.getCaptcha().equalsIgnoreCase(loginConfig.getMagicCaptcha()) && !captchaService.validate(login.getUuid(), login.getCaptcha())) {
                // 不等于魔法验证码
                throw new XquickException(ErrorCode.CAPTCHA_ERROR);
            }
        }

        // 登录日志
        LoginEntity loginLog = new LoginEntity();
        loginLog.setType(login.getType());
        loginLog.setOperation(LogConst.LoginOperationEnum.LOGIN.value());
        loginLog.setCreateTime(new Date());
        loginLog.setIp(HttpContextUtils.getIpAddr(request));
        loginLog.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));

        // 获取帐号
        UserDTO user;
        if (LoginTypeEnum.ADMIN_USER_PWD.value() == login.getType() || LoginTypeEnum.APP_USER_PWD.value() == login.getType()) {
            // 帐号密码登录
            if (StringUtils.isEmpty(login.getUsername()) || StringUtils.isEmpty(login.getPassword())) {
                throw new XquickException(ErrorCode.ERROR_REQUEST);
            }
            user = getByUsername(login.getUsername());
            if (user == null) {
                throw new XquickException(ErrorCode.ACCOUNT_NOT_EXIST);
            }
            if (!PasswordUtils.matches(login.getPassword(), user.getPassword())) {
                throw new XquickException(ErrorCode.PASSWORD_ERROR);
            }
        } else if (LoginTypeEnum.ADMIN_MOBILE_PWD.value() == login.getType() || LoginTypeEnum.APP_MOBILE_PWD.value() == login.getType()) {
            // 手机号密码登录
            if (StringUtils.isEmpty(login.getMobile()) || StringUtils.isEmpty(login.getPassword())) {
                throw new XquickException(ErrorCode.ERROR_REQUEST);
            }
            user = getByMobile(login.getMobile());
            if (user == null) {
                throw new XquickException(ErrorCode.ACCOUNT_NOT_EXIST);
            }
            if (!PasswordUtils.matches(login.getPassword(), user.getPassword())) {
                throw new XquickException(ErrorCode.PASSWORD_ERROR);
            }
        } else if (LoginTypeEnum.ADMIN_MOBILE_SMS.value() == login.getType() || LoginTypeEnum.APP_MOBILE_SMS.value() == login.getType()) {
            // 手机号验证码登录
            if (StringUtils.isEmpty(login.getMobile()) || StringUtils.isEmpty(login.getCode())) {
                throw new XquickException(ErrorCode.ERROR_REQUEST);
            }
            user = getByMobile(login.getMobile());
            //  校验验证码
            SmsLogDTO lastSmsLog = smsLogService.findLastLogByTplCode("LOGIN", login.getMobile());
            if (null == lastSmsLog) {
                loginLog.setStatus(LoginStatusEnum.FAIL.value());
                logLoginService.save(loginLog);
                throw new XquickException("验证码无效");
            }
            Map<String, Object> smsParams = JacksonUtils.jsonToMap(lastSmsLog.getParams());
            if (!login.getCode().equalsIgnoreCase(smsParams.get("code").toString())) {
                loginLog.setStatus(LoginStatusEnum.FAIL.value());
                logLoginService.save(loginLog);
                throw new XquickException("验证码错误");
            }
            if (DateUtils.timeDiff(lastSmsLog.getCreateTime()) > 10 * 60 * 1000) {
                loginLog.setStatus(LoginStatusEnum.FAIL.value());
                logLoginService.save(loginLog);
                throw new XquickException("验证码已过期");
            }
            // 将短信消费掉
            lastSmsLog.setConsumed(1);
            smsLogService.updateDto(lastSmsLog);
        } else {
            throw new XquickException(ErrorCode.UNKNOWN_LOGIN_TYPE);
        }

        if (user == null) {
            if (!loginConfig.getAutoCreate()) {
                throw new XquickException(ErrorCode.ACCOUNT_NOT_EXIST);
            }
            // 用户不存在,创建一个
            user = new UserDTO();
            user.setStatus(UserStatusEnum.ENABLED.value());
            user.setMobile(login.getMobile());
            user.setUsername(login.getMobile());
            user.setType(UserTypeEnum.USER.value());
            user.setGender(GenderEnum.UNKNOWN);
            // 生成随机密码
            String password = RandomStringUtils.randomAlphanumeric(8);
            // 密码加密
            user.setPassword(PasswordUtils.encode(password));
            // user.setRoleIdList(loginConfig.getAutoCreateUserRoleIds().split(","));

            saveDto(user);
            //保存角色用户关系
            roleUserService.saveOrUpdate(user.getId(), user.getRoleIdList());
        }

        // 账号停用
        if (user.getStatus() == UserStatusEnum.DISABLE.value()) {
            loginLog.setStatus(LoginStatusEnum.LOCK.value());
            loginLog.setCreateId(user.getId());
            loginLog.setCreateName(user.getUsername());
            logLoginService.save(loginLog);

            throw new XquickException(ErrorCode.ACCOUNT_DISABLE);
        }

        // 登录成功
        loginLog.setStatus(LoginStatusEnum.SUCCESS.value());
        loginLog.setCreateId(user.getId());
        loginLog.setCreateName(user.getUsername());
        logLoginService.save(loginLog);

        Map<String, Object> map = new HashMap<>(2);
        map.put(Constant.TOKEN_HEADER, tokenService.createToken(user.getId(), loginConfig));
        map.put("expire", loginConfig.getExpire());
        map.put("user", user);
        return map;
    }

    @Override
    public UserDTO getByUsername(String username) {
        UserEntity entity = baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", username).last("limit 1"));
        return ConvertUtils.sourceToTarget(entity, currentDtoClass());
    }

    @Override
    public UserDTO getByMobile(String mobile) {
        UserEntity entity = baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("mobile", mobile).last("limit 1"));
        return ConvertUtils.sourceToTarget(entity, currentDtoClass());
    }

    @Override
    public boolean changeStatus(UserDTO dto) {
        return update(new UserEntity(), new UpdateWrapper<UserEntity>().set("status", dto.getStatus()).eq("id", dto.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(Long id, String newPassword) {
        return update(new UserEntity(), new UpdateWrapper<UserEntity>().eq("id", id).set("password", new BCryptPasswordEncoder().encode(newPassword)));
    }

    @Override
    public int getCountByDeptId(Long deptId) {
        return count(new QueryWrapper<UserEntity>().eq("dept_id", deptId));
    }

    @Override
    public boolean isUsernameExisted(String code, Long id) {
        return SqlHelper.retBool(count(new QueryWrapper<UserEntity>()
                .eq("username", code)
                .ne(id != null && id != 0, "id", id)));
    }

    @Override
    public boolean isMobileExisted(String mobile, Long id) {
        return SqlHelper.retBool(count(new QueryWrapper<UserEntity>()
                .eq("mobile", mobile)
                .ne(id != null && id != 0, "id", id)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateDto(UserDTO dto) {
        // 检查用户权限
        UserDetail user = SecurityUser.getUser();
        if (user.getType() > dto.getType()) {
            throw new XquickException("无权创建高等级用户");
        }
        // 系统管理员必须指定dept
        if (dto.getType() == UserTypeEnum.DEPTADMIN.value() && dto.getDeptId() == null) {
            throw new XquickException("单位管理员需指定所在单位");
        }
        // 只能创建子部门
        if (user.getDeptId() != null) {
            if (dto.getDeptId() == null) {
                throw new XquickException("需指定所在单位");
            } else {
                // todo 检查是否子部门
            }
        }
        // 检查用户名和手机号是否已存在
        boolean hasDuplicatedUsername = hasDuplicated(dto.getId(), "username", dto.getUsername());
        if (hasDuplicatedUsername) {
            throw new XquickException(ErrorCode.HAS_DUPLICATED_RECORD, "用户名");
        }
        boolean hasDuplicatedMobile = hasDuplicated(dto.getId(), "mobile", dto.getMobile());
        if (hasDuplicatedMobile) {
            throw new XquickException(ErrorCode.HAS_DUPLICATED_RECORD, "手机号");
        }

        if (dto.getId() == null) {
            // 新增
            dto.setPassword(PasswordUtils.encode(dto.getPassword()));
        } else {
            // 更新
            UserEntity existIdUser = getById(dto.getId());
            if (null == existIdUser) {
                throw new XquickException(ErrorCode.ACCOUNT_NOT_EXIST);
            }
            // 密码加密
            if (StringUtils.isEmpty(dto.getPassword())) {
                // 对于null的不会更新字段
                dto.setPassword(null);
            } else {
                dto.setPassword(PasswordUtils.encode(dto.getPassword()));
            }
        }
        boolean ret = super.saveOrUpdateDto(dto);
        // 保存角色用户关系
        roleUserService.saveOrUpdate(dto.getId(), dto.getRoleIdList());
        return ret;
    }

}
