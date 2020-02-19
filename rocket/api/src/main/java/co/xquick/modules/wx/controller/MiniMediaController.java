package co.xquick.modules.wx.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.sys.service.ParamService;
import co.xquick.modules.wx.config.WxProp;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 *  小程序临时素材接口
 *
 * @author  Binary Wang
 */
@RestController
@RequestMapping("/wx/mini/media")
public class MiniMediaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ParamService paramService;

    /**
     * 上传临时素材
     *
     * @return 素材的media_id列表，实际上如果有的话，只会有一个
     */
    @PostMapping("/upload")
    public List<String> uploadMedia(@RequestParam String paramCod, HttpServletRequest request) throws WxErrorException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        if (!resolver.isMultipart(request)) {
            return Lists.newArrayList();
        }

        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        Iterator<String> it = multiRequest.getFileNames();
        List<String> result = Lists.newArrayList();
        while (it.hasNext()) {
            try {
                MultipartFile file = multiRequest.getFile(it.next());
                File newFile = new File(Files.createTempDir(), file.getOriginalFilename());
                this.logger.info("filePath is ：" + newFile.toString());
                file.transferTo(newFile);
                WxMediaUploadResult uploadResult = getWxService(paramCod).getMediaService().uploadMedia(WxMaConstants.KefuMsgType.IMAGE, newFile);
                this.logger.info("media_id ： " + uploadResult.getMediaId());
                result.add(uploadResult.getMediaId());
            } catch (IOException e) {
                this.logger.error(e.getMessage(), e);
            }
        }

        return result;
    }

    /**
     * 下载临时素材
     */
    @GetMapping("/download")
    public File getMedia(@RequestParam String paramCode, @RequestParam String mediaId) throws WxErrorException {
        File file = getWxService(paramCode).getMediaService().getMedia(mediaId);
        return file;
    }

    /**
     * 获取微信Service
     * @param paramCode 参数编码
     * @return 微信Service
     */
    private WxMaService getWxService(String paramCode) {
        // 从参数表获取参数配置
        WxProp wxConfigProperties = paramService.getContentObject(paramCode, WxProp.class, null);
        AssertUtils.isNull(wxConfigProperties, ErrorCode.WX_CONFIG_ERROR);
        // 初始化service
        WxMaService wxService = new WxMaServiceImpl();
        wxService.setWxMaConfig(wxConfigProperties.toWxMaDefaultConfigImpl());
        return wxService;
    }

}
