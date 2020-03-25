package co.xquick.modules.uc.service.impl;

import co.xquick.booster.pojo.Const;
import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.*;
import co.xquick.booster.util.bcrypt.BCryptPasswordEncoder;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.log.entity.LoginEntity;
import co.xquick.modules.log.service.LoginService;
import co.xquick.modules.msg.MsgConst;
import co.xquick.modules.msg.entity.SmsLogEntity;
import co.xquick.modules.msg.service.SmsLogService;
import co.xquick.modules.sys.service.ParamService;
import co.xquick.modules.uc.UcConst;
import co.xquick.modules.uc.UcConst.LoginTypeEnum;
import co.xquick.modules.uc.UcConst.UserStatusEnum;
import co.xquick.modules.uc.UcConst.UserTypeEnum;
import co.xquick.modules.uc.dao.UserDao;
import co.xquick.modules.uc.dto.*;
import co.xquick.modules.uc.entity.UserAppleEntity;
import co.xquick.modules.uc.entity.UserEntity;
import co.xquick.modules.uc.service.*;
import co.xquick.modules.uc.user.SecurityUser;
import co.xquick.modules.uc.user.UserDetail;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private UserAppleService userAppleService;

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
                .apply(Const.SQL_FILTER)
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

    @Override
    public Result<?> register(RegisterRequest request) {
        // 操作结果
        int resultCode = 0;
        // 登录用户
        if (isMobileExisted(request.getMobile(), null)) {
            return new Result<>().error(ErrorCode.HAS_DUPLICATED_RECORD, "手机号已注册");
        } else if (isUsernameExisted(request.getUsername(), null)) {
            return new Result<>().error(ErrorCode.HAS_DUPLICATED_RECORD, "用户名已注册");
        } else {
            //  校验验证码
            SmsLogEntity lastSmsLog = smsLogService.findLastLogByTplCode(MsgConst.SMS_TPL_REGISTER, request.getMobile());
            if (null == lastSmsLog || !request.getSmsCode().equalsIgnoreCase(JacksonUtils.jsonToMap(lastSmsLog.getParams()).get("code").toString())) {
                // 验证码错误,找不到验证码
                resultCode = ErrorCode.SMS_CODE_ERROR;
            } else {
                // 验证码正确,校验有效时间
                if (DateUtils.timeDiff(lastSmsLog.getCreateTime()) > 15 * 60 * 1000) {
                    resultCode = ErrorCode.SMS_CODE_EXPIRED;
                } else {
                    // 验证成功,创建用户
                    UserEntity entity = new UserEntity();
                    entity.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
                    entity.setUsername(request.getUsername());
                    entity.setMobile(request.getMobile());
                    entity.setMobileArea(request.getMobileArea());
                    save(entity);
                }
                // 将短信消费掉
                smsLogService.consumeById(lastSmsLog.getId());
            }
            return new Result<>().setCode(resultCode);
        }
    }

    @Override
    public Result<?> changePasswordBySmsCode(ChangePasswordBySmsCodeRequest request) {
        // 操作结果
        int resultCode = 0;
        // 登录用户
        UserDTO user = getByMobile(request.getMobileArea(), request.getMobile());
        if (user == null) {
            // 帐号不存在
            resultCode = ErrorCode.ACCOUNT_NOT_EXIST;
        } else if (user.getStatus() != UserStatusEnum.ENABLED.value()) {
            // 帐号锁定
            resultCode = ErrorCode.ACCOUNT_DISABLE;
        } else {
            //  校验验证码
            SmsLogEntity lastSmsLog = smsLogService.findLastLogByTplCode(MsgConst.SMS_TPL_CHANGE_PASSWORD, request.getMobile());
            if (null == lastSmsLog || !request.getSmsCode().equalsIgnoreCase(JacksonUtils.jsonToMap(lastSmsLog.getParams()).get("code").toString())) {
                // 验证码错误,找不到验证码
                resultCode = ErrorCode.SMS_CODE_ERROR;
            } else {
                // 验证码正确,校验有效时间
                if (DateUtils.timeDiff(lastSmsLog.getCreateTime()) > 15 * 60 * 1000) {
                    resultCode = ErrorCode.SMS_CODE_EXPIRED;
                } else {
                    // 验证成功,修改密码
                    updatePassword(user.getId(), request.getPassword());
                }
                // 将短信消费掉
                smsLogService.consumeById(lastSmsLog.getId());
            }
        }
        return new Result<>().setCode(resultCode);
    }

    @Override
    public Result<?> login(HttpServletRequest request, LoginRequest login) {
        // 登录日志
        LoginEntity loginLog = new LoginEntity();
        loginLog.setType(login.getType());
        loginLog.setCreateTime(new Date());
        loginLog.setCreateName(login.getUsername());
        loginLog.setIp(HttpContextUtils.getIpAddr(request));
        loginLog.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        // 登录结果
        int loginResult = 0;
        // 登录用户
        UserDTO user = null;
        // 获得登录配置
        LoginCfg loginConfig = paramService.getContentObject(UcConst.LOGIN_CFG + "_" + login.getType(), LoginCfg.class, null);
        if (null == loginConfig) {
            // 未找到登录配置
            loginResult = ErrorCode.UNKNOWN_LOGIN_TYPE;
        } else if (loginConfig.isCaptcha() && (StringUtils.isEmpty(login.getCaptcha()) || StringUtils.isEmpty(login.getUuid()) || (!login.getCaptcha().equalsIgnoreCase(loginConfig.getMagicCaptcha()) && !captchaService.validate(login.getUuid(), login.getCaptcha())))) {
            // 验证码错误
            loginResult = ErrorCode.CAPTCHA_ERROR;
        } else {
            // 不需要验证码或验证码正确
            if (LoginTypeEnum.ADMIN_USER_PWD.value() == login.getType() || LoginTypeEnum.APP_USER_PWD.value() == login.getType()) {
                // 帐号密码登录
                if (StringUtils.isEmpty(login.getUsername()) || StringUtils.isEmpty(login.getPassword())) {
                    // 参数为空
                    loginResult = ErrorCode.ERROR_REQUEST;
                } else {
                    user = getByUsername(login.getUsername());
                    if (user == null) {
                        // 帐号不存在
                        loginResult = ErrorCode.ACCOUNT_NOT_EXIST;
                    } else if (user.getStatus() != UserStatusEnum.ENABLED.value()) {
                        // 帐号锁定
                        loginResult = ErrorCode.ACCOUNT_DISABLE;
                    } else if (!PasswordUtils.matches(login.getPassword(), user.getPassword())) {
                        // 密码不匹配
                        loginResult = ErrorCode.ACCOUNT_PASSWORD_ERROR;
                    }
                }
            } else if (LoginTypeEnum.ADMIN_MOBILE_PWD.value() == login.getType() || LoginTypeEnum.APP_MOBILE_PWD.value() == login.getType()) {
                // 手机号密码登录
                if (StringUtils.isEmpty(login.getMobile()) || StringUtils.isEmpty(login.getPassword())) {
                    // 参数为空
                    loginResult = ErrorCode.ERROR_REQUEST;
                } else {
                    user = getByMobile(login.getMobileArea(), login.getMobile());
                    if (user == null) {
                        // 帐号不存在
                        loginResult = ErrorCode.ACCOUNT_NOT_EXIST;
                    } else if (user.getStatus() != UserStatusEnum.ENABLED.value()) {
                        // 帐号锁定
                        loginResult = ErrorCode.ACCOUNT_DISABLE;
                    } else if (!PasswordUtils.matches(login.getPassword(), user.getPassword())) {
                        // 密码不匹配
                        loginResult = ErrorCode.ACCOUNT_PASSWORD_ERROR;
                    }
                }
            } else if (LoginTypeEnum.ADMIN_MOBILE_SMS.value() == login.getType() || LoginTypeEnum.APP_MOBILE_SMS.value() == login.getType()) {
                // 手机号验证码登录
                if (StringUtils.isEmpty(login.getMobile()) || StringUtils.isEmpty(login.getSmsCode())) {
                    // 参数为空
                    loginResult = ErrorCode.ERROR_REQUEST;
                } else {
                    user = getByMobile(login.getMobile());
                    if (user == null) {
                        // 帐号不存在
                        loginResult = ErrorCode.ACCOUNT_NOT_EXIST;
                    } else if (user.getStatus() != UserStatusEnum.ENABLED.value()) {
                        // 帐号锁定
                        loginResult = ErrorCode.ACCOUNT_DISABLE;
                    } else {
                        //  校验验证码
                        SmsLogEntity lastSmsLog = smsLogService.findLastLogByTplCode(MsgConst.SMS_TPL_LOGIN, login.getMobile());
                        if (null == lastSmsLog || !login.getSmsCode().equalsIgnoreCase(JacksonUtils.jsonToMap(lastSmsLog.getParams()).get("code").toString())) {
                            // 验证码错误,找不到验证码
                            loginResult = ErrorCode.SMS_CODE_ERROR;
                        } else {
                            // 验证码正确
                            if (DateUtils.timeDiff(lastSmsLog.getCreateTime()) > loginConfig.getSmsCodeValidTime()) {
                                loginResult = ErrorCode.SMS_CODE_EXPIRED;
                            }
                            // 将短信消费掉
                            smsLogService.consumeById(lastSmsLog.getId());
                        }
                    }
                }
            } else if (LoginTypeEnum.APP_APPLE.value() == login.getType()) {
                if (StringUtils.isEmpty(login.getAppleIdentityToken())) {
                    // 参数为空
                    loginResult = ErrorCode.ERROR_REQUEST;
                } else {
                    // jwt解析identityToken, 获取userIdentifier
                    DecodedJWT jwt = JWT.decode(login.getAppleIdentityToken());
                    // app包名
                    String packageName = jwt.getAudience().get(0);
                    // 用户id
                    String userIdentifier = jwt.getSubject();
                    // 有效期
                    Date expireDate = jwt.getExpiresAt();
                    if (expireDate.after(new Date())) {
                        loginResult = ErrorCode.APPLE_LOGIN_ERROR;
                    } else {
                        // todo 使用apple keys做验证
                        // 通过packageName和userIdentifier找对应的数据记录
                        UserAppleEntity userApple = userAppleService.getByUserIdentifier(packageName, userIdentifier);
                        if (userApple == null) {
                            // 不存在记录,则保存记录
                            userApple = new UserAppleEntity();
                            userApple.setPackageName(packageName);
                            userApple.setUserIdentifier(userIdentifier);
                            userAppleService.save(userApple);
                        }
                        if (userApple.getUserId() == null) {
                            // 未绑定用户
                            loginResult = ErrorCode.APPLE_NOT_BIND;
                        } else {
                            user = getDtoById(userApple.getUserId());
                            if (user == null) {
                                // 帐号不存在
                                loginResult = ErrorCode.ACCOUNT_NOT_EXIST;
                            } else if (user.getStatus() != UserStatusEnum.ENABLED.value()) {
                                // 帐号锁定
                                loginResult = ErrorCode.ACCOUNT_DISABLE;
                            }
                        }
                    }
                }
            } else {
                loginResult = ErrorCode.UNKNOWN_LOGIN_TYPE;
            }

            if (user == null && loginConfig.isAutoCreate()) {
                // 没有该用户，并且需要自动创建用户
                user = new UserDTO();
                user.setStatus(UserStatusEnum.ENABLED.value());
                user.setMobile(login.getMobile());
                user.setUsername(login.getMobile());
                user.setType(UserTypeEnum.USER.value());
                user.setGender(3);
                // 密码加密
                user.setPassword(PasswordUtils.encode(login.getMobile()));
                saveDto(user);
                //保存角色用户关系
                roleUserService.saveOrUpdate(user.getId(), user.getRoleIdList());
                // 保存成功
                loginResult = 0;
            }
        }
        if (null != user) {
            loginLog.setCreateName(user.getUsername());
            loginLog.setCreateId(user.getId());
        }
        loginLog.setResult(loginResult);
        logLoginService.save(loginLog);

        if (loginResult == 0) {
            // 登录成功
            Map<String, Object> map = new HashMap<>(3);
            map.put(UcConst.TOKEN_HEADER, tokenService.createToken(user.getId(), loginConfig));
            map.put("expire", loginConfig.getExpire());
            map.put("user", user);
            return new Result<>().ok(map);
        } else {
            // 登录失败
            return new Result<>().error(loginResult);
        }
    }

    @Override
    public UserDTO getByUsername(String username) {
        UserEntity entity = query().eq("username", username).last("limit 1").one();
        return ConvertUtils.sourceToTarget(entity, currentDtoClass());
    }

    @Override
    public UserDTO getByMobile(String mobileArea, String mobile) {
        UserEntity entity = query().eq("mobile_area", mobileArea).eq("mobile", mobile).last("limit 1").one();
        return ConvertUtils.sourceToTarget(entity, currentDtoClass());
    }

    @Override
    public UserDTO getByMobile(String mobile) {
        return getByMobile("86", mobile);
    }

    @Override
    public boolean changeStatus(UserDTO dto) {
        return update().set("status", dto.getStatus()).eq("id", dto.getId()).update(new UserEntity());
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
    public boolean isUsernameExisted(String username, Long id) {
        return hasDuplicated(id, "username", username);
    }

    @Override
    public boolean isMobileExisted(String mobile, Long id) {
        return hasDuplicated(id, "mobile", mobile);
    }

    @Override
    protected void beforeSaveOrUpdateDto(UserDTO dto, int type) {
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
            }
        }
        // 检查用户名和手机号是否已存在
        AssertUtils.isTrue(hasDuplicated(dto.getId(), "username", dto.getUsername()), ErrorCode.HAS_DUPLICATED_RECORD, "用户名");
        AssertUtils.isTrue(hasDuplicated(dto.getId(), "mobile", dto.getMobile()), ErrorCode.HAS_DUPLICATED_RECORD, "手机号");
        if (type == 1) {
            // 更新
            UserEntity existEntity = getById(dto.getId());
            AssertUtils.isNull(existEntity, ErrorCode.DB_RECORD_NOT_EXISTED);
            // 检查是否需要修改密码,对于null的不会更新字段
            dto.setPassword(StringUtils.isEmpty(dto.getPassword()) ? null : PasswordUtils.encode(dto.getPassword()));
        } else {
            // 新增
            dto.setPassword(PasswordUtils.encode(dto.getPassword()));
        }
    }

    @Override
    protected void afterSaveOrUpdateDto(boolean ret, UserDTO dto,UserEntity existedEntity, int type) {
        // 保存角色用户关系
        roleUserService.saveOrUpdate(dto.getId(), dto.getRoleIdList());
    }

    /**
     * 合并帐号
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean merge(String mergeTo, List<String> mergeFrom) {
        // 删除被合并帐号
        logicDeleteByIds(mergeFrom);
        // 将被删除业务数据中的create_id/update_id更新为mergeTo
        return true;
    }

}
