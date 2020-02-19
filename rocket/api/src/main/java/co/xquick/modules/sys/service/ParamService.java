package co.xquick.modules.sys.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.sys.dto.ParamDTO;
import co.xquick.modules.sys.entity.ParamEntity;

/**
 * 参数管理
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public interface ParamService extends CrudService<ParamEntity, ParamDTO> {

    /**
     * 通过code获取参数
     * @param code
     * @return
     */
    ParamDTO getByCode(String code);

    /**
     * 根据参数编码，获取参数的value值
     *
     * @param code  参数编码
     */
    String getContent(String code);

    /**
     * 根据参数编码，获取value的Object对象
     * @param code  参数编码
     * @param clazz  Object对象
     */
    <T> T getContentObject(String code, Class<T> clazz);

    /**
     * 根据参数编码，获取value的Object对象
     * @param code  参数编码
     * @param clazz  Object对象
     * @param defaultObject  默认传回值
     */
    <T> T getContentObject(String code, Class<T> clazz, T defaultObject);

    /**
     * 根据参数编码，更新value
     * @param code  参数编码
     * @param value  参数值
     */
    void updateContentByCode(String code, String value);

}
