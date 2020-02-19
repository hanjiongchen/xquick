package co.xquick.modules.sys.controller;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.common.annotation.LogOperation;
import co.xquick.modules.sys.dto.OssDTO;
import co.xquick.modules.sys.entity.OssEntity;
import co.xquick.modules.sys.oss.OssFactory;
import co.xquick.modules.sys.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * 素材库
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("sys/oss")
@Api(tags = "素材库")
public class OssController {
    @Autowired
    private OssService ossService;

    @GetMapping("page")
    @ApiOperation(value = "分页")
    @RequiresPermissions("sys:oss:page")
    public Result page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<OssDTO> page = ossService.pageDto(params);

        return new Result<>().ok(page);
    }

    /*@PostMapping
    @ApiOperation(value = "保存云存储配置信息")
    @LogOperation("保存云存储配置信息")
    @RequiresPermissions("sys:oss:all")
    public Result saveConfig(@RequestBody CloudStorageConfig config) {
        //校验类型
        ValidatorUtils.validateEntity(config);

        if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            //校验阿里云数据
            ValidatorUtils.validateEntity(config, AliyunGroup.class);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            //校验腾讯云数据
            ValidatorUtils.validateEntity(config, QcloudGroup.class);
        }

        paramsService.updateContentByCode(KEY, new Gson().toJson(config));

        return new Result();
    }*/

    @GetMapping("presignedUrl")
    @ApiOperation(value = "获得授权访问地址")
    public Result presignedUrl(@RequestParam(required = false, defaultValue = "OSS_CONFIG_KEY_PRIVATE") String paramCode) {
        String url = OssFactory.build(paramCode).generatePresignedUrl("oss/20191105/aba10b6b6ea2442ab05cc969f7c380ae.png", 3600 * 1000);

        Map<String, Object> data = new HashMap<>(1);
        data.put("src", url);

        return new Result<>().ok(data);
    }

    @PostMapping("upload")
    @ApiOperation(value = "上传单个文件")
    public Result upload(@RequestParam(required = false, defaultValue = "OSS_CONFIG_KEY_PUBLIC") String paramCode, @RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return new Result().error(ErrorCode.UPLOAD_FILE_EMPTY);
        }

        // 上传文件
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String url = OssFactory.build(paramCode).uploadSuffix(file.getBytes(), extension);

        //保存文件信息
        OssEntity oss = new OssEntity();
        oss.setUrl(url);
        oss.setFilename(file.getOriginalFilename());
        oss.setSize(file.getSize());
        oss.setContentType(file.getContentType());
        ossService.save(oss);

        Map<String, Object> data = new HashMap<>(1);
        data.put("src", url);

        return new Result<>().ok(data);
    }

    @PostMapping("uploadMulti")
    @ApiOperation(value = "上传多个文件")
    public Result uploadMulti(@RequestParam(required = false, defaultValue = "OSS_CONFIG_KEY_PUBLIC") String paramCode, @RequestParam("file") MultipartFile[] files) throws Exception {
        if (files == null || files.length <= 0) {
            return new Result().error(ErrorCode.UPLOAD_FILE_EMPTY);
        }

        List<String> srcList = new ArrayList<>();
        for (MultipartFile file : files) {
            // 上传文件
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String url = OssFactory.build(paramCode).uploadSuffix(file.getBytes(), extension);

            //保存文件信息
            OssEntity oss = new OssEntity();
            oss.setUrl(url);
            oss.setFilename(file.getOriginalFilename());
            oss.setSize(file.getSize());
            oss.setContentType(file.getContentType());
            ossService.save(oss);
            srcList.add(url);
        }

        Map<String, Object> data = new HashMap<>(1);
        data.put("src", StringUtils.join(srcList, ","));

        return new Result<>().ok(data);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:oss:delete")
    public Result delete(@RequestBody List<Long> ids) {
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        ossService.logicDeleteByIds(ids);

        return new Result();
    }

}
