package co.xquick.modules.sys.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.sys.dto.RegionDTO;
import co.xquick.modules.sys.dto.RegionTreeDTO;
import co.xquick.modules.sys.entity.RegionEntity;

import java.util.List;
import java.util.Map;

/**
 * 行政区域
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface RegionService extends CrudService<RegionEntity, RegionDTO> {

    List<RegionTreeDTO> treeList(Map<String, Object> params);

}
