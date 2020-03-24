package co.xquick.booster.injector;

import co.xquick.booster.injector.methods.LogicDeleteBatchByIdsWithFill;
import co.xquick.booster.injector.methods.LogicDeleteByIdWithFill;
import co.xquick.booster.injector.methods.LogicDeleteByWrapperWithFill;
import co.xquick.booster.injector.methods.SelectCountById;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SQL注入器
 *
 * @author Charles
 */
@Component
public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        // 增加自动填充逻辑删除
        methodList.add(new LogicDeleteByIdWithFill());
        methodList.add(new LogicDeleteBatchByIdsWithFill());
        methodList.add(new LogicDeleteByWrapperWithFill());
        methodList.add(new SelectCountById());
        return methodList;
    }

}
