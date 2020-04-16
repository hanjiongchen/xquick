package com.nb6868.xquick.modules.shop.service;

import com.nb6868.xquick.booster.service.CrudService;
import com.nb6868.xquick.modules.shop.dto.ReceiverDTO;
import com.nb6868.xquick.modules.shop.entity.ReceiverEntity;

/**
 * 收件地址
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface ReceiverService extends CrudService<ReceiverEntity, ReceiverDTO> {

    /**
     * 将地址设置为默认项
     *
     * @param id 地址id
     * @return 结果
     */
    boolean setDefaultItem(Long id);

}
