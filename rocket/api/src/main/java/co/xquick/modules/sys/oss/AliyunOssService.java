package co.xquick.modules.sys.oss;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * 阿里云存储
 * see {https://help.aliyun.com/document_detail/32008.html}
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public class AliyunOssService extends AbstractOssService {

    public AliyunOssService(OssProp config) {
        this.config = config;
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        OSS ossClient = new OSSClientBuilder().build(config.getEndPoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        try {
            ossClient.putObject(config.getBucketName(), path, inputStream);
        } catch (Exception e) {
            throw new XquickException(ErrorCode.OSS_UPLOAD_FILE_ERROR, e, "");
        } finally {
            ossClient.shutdown();
        }

        return config.getDomain() + "/" + path;
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
    public String generatePresignedUrl(String objectName, long expire) {
        OSS ossClient = new OSSClientBuilder().build(config.getEndPoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        try {
            // 设置URL过期时间为1小时。
            Date expiration = new Date(new Date().getTime() + expire);
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            URL url = ossClient.generatePresignedUrl(config.getBucketName(), objectName, expiration);
            return url.toString();
        } catch (Exception e) {
            throw new XquickException(ErrorCode.OSS_UPLOAD_FILE_ERROR, e, "");
        } finally {
            ossClient.shutdown();
        }

    }
}
