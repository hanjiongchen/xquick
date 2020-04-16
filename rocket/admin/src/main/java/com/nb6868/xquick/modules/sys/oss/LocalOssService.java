package com.nb6868.xquick.modules.sys.oss;

import com.nb6868.xquick.booster.exception.ErrorCode;
import com.nb6868.xquick.booster.exception.XquickException;
import com.nb6868.xquick.booster.pojo.Kv;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 本地上传
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public class LocalOssService extends AbstractOssService {

    public LocalOssService(OssProp config) {
        this.config = config;
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        File file = new File(config.getLocalPath() + File.separator + path);
        try {
            FileUtils.copyToFile(inputStream, file);
        } catch (IOException e) {
            throw new XquickException(ErrorCode.OSS_UPLOAD_FILE_ERROR, e, "");
        }
        return config.getDomain() + File.separator + path;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getPrefix(), suffix));
    }

    @Override
    public String generatePresignedUrl(String objectName, long expiration) {
        return null;
    }

    @Override
    public Kv getSts() {
        return null;
    }
}
