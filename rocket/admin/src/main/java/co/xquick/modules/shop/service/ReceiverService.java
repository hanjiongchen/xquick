package co.xquick.modules.shop.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.shop.dto.ReceiverDTO;
import co.xquick.modules.shop.entity.ReceiverEntity;

/**
 * 收件地址
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface ReceiverService extends CrudService<ReceiverEntity, ReceiverDTO> {

    /**
     * 将地址设置为默认项
     */
    boolean setDefaultItem(Long id);

}
