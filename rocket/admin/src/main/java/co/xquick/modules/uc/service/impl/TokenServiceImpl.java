package co.xquick.modules.uc.service.impl;

import co.xquick.booster.service.impl.BaseServiceImpl;
import co.xquick.modules.uc.dao.TokenDao;
import co.xquick.modules.uc.dto.LoginCfg;
import co.xquick.modules.uc.entity.TokenEntity;
import co.xquick.modules.uc.service.TokenService;
import co.xquick.modules.uc.shiro.TokenGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * token
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Service
public class TokenServiceImpl extends BaseServiceImpl<TokenDao, TokenEntity> implements TokenService {

    @Override
    public String createToken(Long userId, LoginCfg loginConfig) {
        // 当前时间
        Date now = new Date();
        // 过期时间
        Date expireTime = new Date(now.getTime() + loginConfig.getExpire() * 1000);
        // 生成的token
        if (loginConfig.isMultiLogin()) {
            // 支持多点登录
        } else {
            // 不支持多点登录,注销该用户所有token
            deleteTokenByUserId(userId, loginConfig.getType());
        }
        // 不管逻辑，永远都是重新生成一个token
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(TokenGenerator.generateValue());
        tokenEntity.setUpdateTime(now);
        tokenEntity.setExpireDate(expireTime);
        tokenEntity.setType(loginConfig.getType());

        //保存token
        this.save(tokenEntity);

        return tokenEntity.getToken();
    }

    @Override
    public Long getUserIdByToken(String token) {
        return baseMapper.getUserIdByToken(token);
    }

    @Override
    public TokenEntity getByToken(String token) {
        return baseMapper.getByToken(token);
    }

    @Override
    public boolean renewalToken(String token) {
        // todo token时间获取
        return baseMapper.renewalToken(token, 100000L) > 0;
    }

    @Override
    public boolean logout(Long userId) {
        // 生成一个token
        String token = TokenGenerator.generateValue();

        // 修改token
        return baseMapper.updateToken(userId, token) > 0;
    }

    @Override
    public boolean deleteToken(String token) {
        // 修改token
        baseMapper.deleteByWrapperWithFill(new TokenEntity(), new QueryWrapper<TokenEntity>().eq("token", token));
        return true;
    }

    @Override
    public boolean deleteTokenByUserId(Long userId, Integer type) {
        return update(new TokenEntity(), new UpdateWrapper<TokenEntity>().set("deleted", 1).eq("user_id", userId).eq("type", type));
    }

}
