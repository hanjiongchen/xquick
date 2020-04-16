package com.nb6868.xquick.modules.sys.oss;

import com.nb6868.xquick.booster.exception.ErrorCode;
import com.nb6868.xquick.booster.exception.XquickException;
import com.nb6868.xquick.booster.pojo.Kv;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

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

        return config.getDomain() + (config.getDomain().endsWith("/") ? "" : "/") + path;
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
            Date expiration = new Date(System.currentTimeMillis() + expire);
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            URL url = ossClient.generatePresignedUrl(config.getBucketName(), objectName, expiration);
            return url.toString();
        } catch (Exception e) {
            throw new XquickException(ErrorCode.OSS_UPLOAD_FILE_ERROR, e, "");
        } finally {
            ossClient.shutdown();
        }
    }

    @Override
    public Kv getSts() {
        try {
            // 添加endpoint（直接使用STS endpoint，无需添加region ID）
            DefaultProfile.addEndpoint("", "Sts", "sts." + config.getRegion() + ".aliyuncs.com");
            // 构造default profile（参数留空，无需添加region ID）
            IClientProfile profile = DefaultProfile.getProfile("", config.getAccessKeyId(), config.getAccessKeySecret());
            // 用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);

            AssumeRoleRequest request = new AssumeRoleRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysProtocol(ProtocolType.HTTPS);
            request.setRoleArn(config.getRoleArn());
            request.setRoleSessionName(config.getRoleSessionName());
            // 若policy为空，则用户将获得该角色下所有权限
            request.setPolicy(null);
            // 设置凭证有效时间
            request.setDurationSeconds(config.getStsDurationSeconds());
            AssumeRoleResponse response = client.getAcsResponse(request);
            return Kv.init()
                    .set("accessKeyId", response.getCredentials().getAccessKeyId())
                    .set("accessKeySecret", response.getCredentials().getAccessKeySecret())
                    .set("securityToken", response.getCredentials().getSecurityToken())
                    .set("expiration", response.getCredentials().getExpiration())
                    .set("region", "oss-" + config.getRegion())
                    .set("prefix", config.getPrefix())
                    .set("domain", config.getDomain())
                    .set("secure", config.getSecure())
                    .set("bucketName", config.getBucketName());
        } catch (ClientException e) {
            //throw new XquickException(ErrorCode.OSS_CONFIG_ERROR);
            throw new XquickException(e.getErrMsg());
        }
    }
}
