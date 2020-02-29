package co.xquick.modules.uc.service;

import co.xquick.booster.pojo.Result;
import co.xquick.booster.service.CrudService;
import co.xquick.modules.uc.dto.LoginRequestDTO;
import co.xquick.modules.uc.dto.UserDTO;
import co.xquick.modules.uc.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface UserService extends CrudService<UserEntity, UserDTO> {

    /**
     * 登录
     */
    Result<?> login(HttpServletRequest request, LoginRequestDTO loginDTO);

    /**
     * 通过用户名获取用户
     */
    UserDTO getByUsername(String username);

    /**
     * 通过手机号获取用户
     */
    UserDTO getByMobile(String mobile);

    /**
     * 通过手机号区域和手机号获取用户
     */
    UserDTO getByMobile(String mobileArea, String mobile);

    /**
     * 修改状态
     */
    boolean changeStatus(UserDTO dto);

    /**
     * 修改密码
     *
     * @param id          用户ID
     * @param newPassword 新密码
     */
    boolean updatePassword(Long id, String newPassword);

    /**
     * 根据部门ID，查询用户数
     */
    int getCountByDeptId(Long deptId);

    /**
     * 判断用户名是否存在
     */
    boolean isUsernameExisted(String username, Long id);

    /**
     * 判断手机号是否存在
     */
    boolean isMobileExisted(String mobile, Long id);

    /**
     * 合并帐号,将mergeFrom数据合并到mergeTo
     */
    boolean merge(String mergeTo, List<String> mergeFrom);

}
