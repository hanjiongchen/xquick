package co.xquick.booster.validator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 校验工具类
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public class AssertUtils {

    public static void isBlank(String str, String... params) {
        isBlank(str, ErrorCode.NOT_NULL, params);
    }

    public static void isBlank(String str, Integer code, String... params) {
        if (code == null) {
            throw new XquickException(ErrorCode.NOT_NULL, "code");
        }

        if (StringUtils.isBlank(str)) {
            throw new XquickException(code, params);
        }
    }

    public static void isNull(Object object, String... params) {
        isNull(object, ErrorCode.NOT_NULL, params);
    }

    public static void isNull(Object object, Integer code, String... params) {
        if (code == null) {
            throw new XquickException(ErrorCode.NOT_NULL, "code");
        }

        if (object == null) {
            throw new XquickException(code, params);
        }
    }

    public static void isArrayEmpty(Object[] array, String... params) {
        isArrayEmpty(array, ErrorCode.NOT_NULL, params);
    }

    public static void isArrayEmpty(Object[] array, Integer code, String... params) {
        if (code == null) {
            throw new XquickException(ErrorCode.NOT_NULL, "code");
        }

        if (ArrayUtil.isEmpty(array)) {
            throw new XquickException(code, params);
        }
    }

    public static void isListEmpty(List<?> list, String... params) {
        isListEmpty(list, ErrorCode.NOT_NULL, params);
    }

    public static void isListEmpty(List<?> list, Integer code, String... params) {
        if (code == null) {
            throw new XquickException(ErrorCode.NOT_NULL, "code");
        }

        if (CollUtil.isEmpty(list)) {
            throw new XquickException(code, params);
        }
    }

    public static void isMapEmpty(Map map, String... params) {
        isMapEmpty(map, ErrorCode.NOT_NULL, params);
    }

    public static void isMapEmpty(Map map, Integer code, String... params) {
        if (code == null) {
            throw new XquickException(ErrorCode.NOT_NULL, "code");
        }

        if (MapUtil.isEmpty(map)) {
            throw new XquickException(code, params);
        }
    }
}
