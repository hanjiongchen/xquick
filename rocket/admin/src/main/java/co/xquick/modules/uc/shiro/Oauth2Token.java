package co.xquick.modules.uc.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Oauth2Token
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public class Oauth2Token implements AuthenticationToken {

    private String token;

    public Oauth2Token(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
