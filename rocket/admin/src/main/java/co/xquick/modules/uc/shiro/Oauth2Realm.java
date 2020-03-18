package co.xquick.modules.uc.shiro;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.MessageUtils;
import co.xquick.modules.uc.UcConst;
import co.xquick.modules.uc.dto.LoginCfg;
import co.xquick.modules.uc.entity.TokenEntity;
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

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 根据登录配置中的roleBase和permissionBase设置SimpleAuthorizationInfo
        LoginCfg loginCfg = user.getLoginCfg();
        if (loginCfg != null) {
             if (loginCfg.isRoleBase()) {
                 // 塞入角色列表
                 info.setRoles(shiroService.getUserRoles(user));
             }
             if (loginCfg.isPermissionsBase()) {
                 // 塞入权限列表
                 info.setStringPermissions(shiroService.getUserPermissions(user));
             }
        }
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        String accessToken = (String) authToken.getPrincipal();
        // 根据accessToken，查询用户信息
        TokenEntity token = shiroService.getUserIdAndTypeByToken(accessToken);
        // token失效
        if (token == null) {
            throw new IncorrectCredentialsException(MessageUtils.getMessage(ErrorCode.TOKEN_INVALID));
        }

        // 查询用户信息
        UserEntity userEntity = shiroService.getUser(token.getUserId());

        if (userEntity == null) {
            // 账号不存在
            throw new XquickException(MessageUtils.getMessage(ErrorCode.ACCOUNT_NOT_EXIST));
        } else if (userEntity.getStatus() != UcConst.UserStatusEnum.ENABLED.value()) {
            // 账号锁定
            throw new LockedAccountException(MessageUtils.getMessage(ErrorCode.ACCOUNT_LOCK));
        }

        // 转换成UserDetail对象
        UserDetail userDetail = ConvertUtils.sourceToTarget(userEntity, UserDetail.class);

        // 将登录配置塞入user
        LoginCfg loginCfg = shiroService.getLoginCfg(token.getType());
        userDetail.setLoginCfg(loginCfg);

        // 将token塞入user
        userDetail.setToken(accessToken);

        // 获取用户对应的部门数据权限
        /*List<Long> deptIdList = shiroService.getDataScopeList(userDetail.getId());
        userDetail.setDeptIdList(deptIdList);*/

        // 更新token
        if (loginCfg.isRenewalToken()) {
            shiroService.renewalToken(accessToken, loginCfg.getExpire());
        }

        return new SimpleAuthenticationInfo(userDetail, accessToken, getName());
    }

}
