package co.xquick.modules.uc.service;

import co.xquick.booster.service.BaseService;
import co.xquick.modules.uc.dto.LoginConfigDTO;
import co.xquick.modules.uc.entity.TokenEntity;

/**
 * 用户Token
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public interface TokenService extends BaseService<TokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 * @param loginConfig  登录配置
	 */
	String createToken(Long userId, LoginConfigDTO loginConfig);

	/**
	 * token续期
	 * @param token  token
	 */
	boolean renewalToken(String token);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	boolean logout(Long userId);

	/**
	 * 注销token
	 * @param token  用户token
	 */
	boolean deleteToken(String token);

	/**
	 * 删除用户下所有token
	 * @param userId 用户id
	 * @param type 登录类型
	 */
	boolean deleteTokenByUserId(Long userId, Integer type);

}
