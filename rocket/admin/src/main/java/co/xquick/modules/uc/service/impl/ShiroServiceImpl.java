package co.xquick.modules.uc.service.impl;

import co.xquick.modules.uc.UcConst.UserTypeEnum;
import co.xquick.modules.uc.dao.*;
import co.xquick.modules.uc.entity.UserEntity;
import co.xquick.modules.uc.service.ShiroService;
import co.xquick.modules.uc.user.UserDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * shiro
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private MenuDao menuMapper;
    @Autowired
    private UserDao userMapper;
    @Autowired
    private RoleDao roleMapper;
    @Autowired
    private TokenDao tokenMapper;
    @Autowired
    private RoleDataScopeDao roleDataScopeMapper;

    @Value("${redis.open: false}")
    private boolean open;

    @Override
    public Set<String> getUserPermissions(UserDetail user) {
        // 系统管理员，拥有最高权限
        List<String> permissionsList;
        if (user.getType() == UserTypeEnum.ADMIN.value()) {
            permissionsList = menuMapper.getPermissionsList();
        } else {
            permissionsList = menuMapper.getUserPermissionsList(user.getId());
        }

        // 用户权限列表
        Set<String> set = new HashSet<>();
        for (String permissions : permissionsList) {
            if (StringUtils.isBlank(permissions)) {
                continue;
            }
            set.addAll(Arrays.asList(permissions.trim().split(",")));
        }

        return set;
    }

    @Override
    public Set<String> getUserRoles(UserDetail user) {
        List<String> roleList;
        if (user.getType() == UserTypeEnum.ADMIN.value()) {
            roleList = roleMapper.getRoleList();
        } else {
            roleList = roleMapper.getRoleList(user.getId());
        }

        // 用户角色列表
        Set<String> set = new HashSet<>();
        for (String role : roleList) {
            if (StringUtils.isBlank(role)) {
                continue;
            }
            set.addAll(Arrays.asList(role.trim().split(",")));
        }

        return set;
    }

    @Override
    public Long getUserIdByToken(String token) {
        return tokenMapper.getUserIdByToken(token);
    }

    @Override
    public UserEntity getUser(Long userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public List<Long> getDataScopeList(Long userId) {
        return roleDataScopeMapper.getDataScopeList(userId);
    }

    @Override
    public void renewalToken(String token) {
        // tokenMapper.renewalToken(token, )
        // 实现ParamsService.getValueObject
        // 如果Autowired和Redis相关内容，会出现RedisAspect不处理的问题
        /*String code = Constant.LOGIN_CONFIG_KEY + "_" + token.getLoginType().toUpperCase();
        String content;
        if (open) {
            ParamsRedis sysParamsRedis = new ParamsRedis();
            content = sysParamsRedis.get(code);
            if (content == null) {
                content = sysParamsDao.getContentByCode(code);
                sysParamsRedis.set(code, content);
            }
        } else {
            content = sysParamsDao.getContentByCode(code);
        }

        LoginConfigDTO loginConfig = null;
        if (StringUtils.isNotBlank(content)) {
            loginConfig = JSON.parseObject(content, LoginConfigDTO.class);
        }
        if (null == loginConfig) {
            throw new XquickException(ErrorCode.UNKNOWN_LOGIN_TYPE);
        }
        if (loginConfig.getRenewal()) {
            sysUserTokenDao.renewalToken(token.getToken(), loginConfig.getExpire());
        }*/
    }
}
