package co.xquick.modules.uc.service.impl;

import co.xquick.booster.util.JacksonUtils;
import co.xquick.modules.sys.dao.ParamDao;
import co.xquick.modules.uc.UcConst;
import co.xquick.modules.uc.UcConst.UserTypeEnum;
import co.xquick.modules.uc.dao.*;
import co.xquick.modules.uc.dto.LoginChannelCfg;
import co.xquick.modules.uc.entity.TokenEntity;
import co.xquick.modules.uc.entity.UserEntity;
import co.xquick.modules.uc.service.ShiroService;
import co.xquick.modules.uc.user.UserDetail;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.base.Splitter;
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
    private ParamDao paramMapper;
    @Autowired
    private RoleDataScopeDao roleDataScopeMapper;

    @Value("${redis.open: false}")
    private boolean open;

    @Override
    public Set<String> getPermissionsByRoles(List<String> roleCodes) {
        List<String> permissionsList;
        permissionsList = menuMapper.getPermissionsByRoles(roleCodes);
        // 用户权限列表
        Set<String> set = new HashSet<>();
        for (String permissions : permissionsList) {
            if (StringUtils.isBlank(permissions)) {
                continue;
            }
            // 去除中间的空内容
            set.addAll(Splitter.on(',').trimResults().omitEmptyStrings().splitToList(permissions.trim()));
        }

        return set;
    }

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
            // 去除中间的空内容
            set.addAll(Splitter.on(',').trimResults().omitEmptyStrings().splitToList(permissions.trim()));
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
    public TokenEntity getUserIdAndTypeByToken(String token) {
        return tokenMapper.getUserIdAndTypeByToken(token);
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
    public LoginChannelCfg getLoginCfg(Integer type) {
        String paramContent = paramMapper.getContentByCode(UcConst.LOGIN_CHANNEL_CFG_PREFIX + type);
        LoginChannelCfg loginCfg = JacksonUtils.jsonToPojo(paramContent, LoginChannelCfg.class);
        return loginCfg == null ? LoginChannelCfg.getDefaultCfg(type) : loginCfg;
    }

    @Override
    public boolean renewalToken(String token, Long expire) {
        return SqlHelper.retBool(tokenMapper.renewalToken(token, expire));
    }
}
