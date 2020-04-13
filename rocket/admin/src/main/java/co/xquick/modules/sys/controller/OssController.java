package co.xquick.modules.sys.controller;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.pojo.Kv;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<OssDTO> page = ossService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("presignedUrl")
    @ApiOperation(value = "获得授权访问地址")
    public Result<?> presignedUrl(@RequestParam(required = false, defaultValue = "OSS_CFG_PRI") String paramCode, @RequestParam String objectName, @RequestParam(required = false, defaultValue = "3600000")  long expiration) {
        String url = OssFactory.build(paramCode).generatePresignedUrl(objectName, expiration);

        return new Result<>().ok(Kv.init().set("src",url));
    }

    @GetMapping("getSts")
    @ApiOperation(value = "获得STS临时访问token")
    public Result<?> getSts(@RequestParam(required = false, defaultValue = "OSS_CFG_PUB") String paramCode) {
        Kv kv = OssFactory.build(paramCode).getSts();
        return new Result<>().ok(kv);
    }

    @PostMapping("upload")
    @ApiOperation(value = "上传单个文件")
    public Result<?> upload(@RequestParam(required = false, defaultValue = "OSS_CFG_PUB") String paramCode, @RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return new Result<>().error(ErrorCode.UPLOAD_FILE_EMPTY);
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

        return new Result<>().ok(Kv.init().set("src",url));
    }

    @PostMapping("uploadMulti")
    @ApiOperation(value = "上传多个文件")
    public Result<?> uploadMulti(@RequestParam(required = false, defaultValue = "OSS_CFG_PUB") String paramCode, @RequestParam("file") MultipartFile[] files) throws Exception {
        if (files == null || files.length <= 0) {
            return new Result<>().error(ErrorCode.UPLOAD_FILE_EMPTY);
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

        return new Result<>().ok(Kv.init().set("src", StringUtils.join(srcList, ",")));
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:oss:delete")
    public Result<?> delete(@RequestParam Long id) {
        //效验数据
        AssertUtils.isEmpty(id, "id");

        ossService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:oss:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        ossService.logicDeleteByIds(ids);

        return new Result<>();
    }

}
