package com.nb6868.xquick.common.util;

import cn.hutool.core.map.MapUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/**
 * 模板引擎
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public class TemplateUtils {

    /**
     * 获取Freemarker渲染后的内容
     *
     * @param raw    模板原始内容
     * @param params 参数
     */
    public static String getTemplateContent(String templateName, String raw, Map<String, Object> params) {
        if (StringUtils.isEmpty(raw) || MapUtil.isEmpty(params)) {
            return raw;
        }
        // 模板
        try {
            Template template = new Template(templateName, new StringReader(raw), null, "utf-8");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return raw;
        }
    }

}
