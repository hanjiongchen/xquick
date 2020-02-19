package co.xquick.modules.uc.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.uc.dto.LoginDTO;
import co.xquick.modules.uc.dto.UserDTO;
import co.xquick.modules.uc.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
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
    Map<String, Object> login(HttpServletRequest request, LoginDTO loginDTO);

    /**
     * 获取用户名
     */
    UserDTO getByUsername(String username);

    UserDTO getByMobile(String mobile);

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
     * 用户名是否存在
     * @param username 用户名
     * @param id 对比id
     * @return
     */
    boolean isUsernameExisted(String username, Long id);

    /**
     * 手机号是否存在
     * @param mobile 手机号
     * @param id 对比id
     * @return
     */
    boolean isMobileExisted(String mobile, Long id);

}
