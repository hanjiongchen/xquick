package co.xquick.modules.uc.shiro;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.MessageUtils;
import co.xquick.modules.uc.UcConst;
import co.xquick.modules.uc.entity.UserEntity;
import co.xquick.modules.uc.service.ShiroService;
import co.xquick.modules.uc.user.UserDetail;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Component
public class Oauth2Realm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Oauth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserDetail user = (UserDetail) principals.getPrimaryPrincipal();
        // 用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(user);
        // 用户角色列表
        Set<String> rolesSet = shiroService.getUserRoles(user);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 塞入权限
        info.setStringPermissions(permsSet);
        // 塞入角色
        info.setRoles(rolesSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();
        // 根据accessToken，查询用户信息
        Long userId = shiroService.getUserIdByToken(accessToken);
        // token失效
        if (userId == null) {
            throw new IncorrectCredentialsException(MessageUtils.getMessage(ErrorCode.TOKEN_INVALID));
        }

        // 查询用户信息
        UserEntity userEntity = shiroService.getUser(userId);

        if (userEntity == null) {
            // 账号不存在
            throw new XquickException(MessageUtils.getMessage(ErrorCode.ACCOUNT_NOT_EXIST));
        } else if (userEntity.getStatus() != UcConst.UserStatusEnum.ENABLED.value()) {
            // 账号锁定
            throw new LockedAccountException(MessageUtils.getMessage(ErrorCode.ACCOUNT_LOCK));
        }

        // 转换成UserDetail对象
        UserDetail userDetail = ConvertUtils.sourceToTarget(userEntity, UserDetail.class);

        // 将token放到user中
        userDetail.setToken(accessToken);

        // 获取用户对应的部门数据权限
        /*List<Long> deptIdList = shiroService.getDataScopeList(userDetail.getId());
        userDetail.setDeptIdList(deptIdList);*/

        // 判断是否需要续token的过期时间
        shiroService.renewalToken(accessToken);

        return new SimpleAuthenticationInfo(userDetail, accessToken, getName());
    }

}
