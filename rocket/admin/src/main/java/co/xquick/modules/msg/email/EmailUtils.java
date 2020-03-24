package co.xquick.modules.msg.email;

import cn.hutool.core.map.MapUtil;
import co.xquick.booster.constant.Constant;
import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.util.JacksonUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.common.util.TemplateUtils;
import co.xquick.modules.msg.dto.MailSendRequest;
import co.xquick.modules.msg.entity.MailLogEntity;
import co.xquick.modules.msg.entity.MailTplEntity;
import co.xquick.modules.msg.service.MailLogService;
import co.xquick.modules.msg.service.MailTplService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

/**
 * 邮件工具类
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Component
public class EmailUtils {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MailLogService mailLogService;
    @Autowired
    private MailTplService mailTplService;

    /**
     * 实现邮件发送器
     *
     * @param mailTpl 邮件模板
     * @return 邮件发送器
     */
    private JavaMailSenderImpl createMailSender(MailTplEntity mailTpl) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(mailTpl.getSenderHost());
        sender.setPort(mailTpl.getSenderHostPort());
        sender.setUsername(mailTpl.getSenderUsername());
        sender.setPassword(mailTpl.getSenderPassword());
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", "10000");
        p.setProperty("mail.smtp.auth", "false");
        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 发送邮件
     *
     * @param mailSendRequest 邮件发送请求
     * @return true：成功   false：失败
     */
    public boolean sendMail(MailSendRequest mailSendRequest) {
        MailTplEntity mailTpl = mailTplService.getByCode(mailSendRequest.getTplCode());
        AssertUtils.isEmpty(mailTpl, ErrorCode.MAIL_CONFIG_ERROR);

        String title = TemplateUtils.getTemplateContent("mailTitle", mailTpl.getTitle(), JacksonUtils.jsonToMap(mailSendRequest.getTitleParam()));
        String content = TemplateUtils.getTemplateContent("mailContent", mailTpl.getContent(), JacksonUtils.jsonToMap(mailSendRequest.getContentParam()));

        JavaMailSenderImpl mailSender = createMailSender(mailTpl);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            //设置utf-8编码
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(mailTpl.getSenderUsername());
            //收件人
            messageHelper.setTo(StringUtils.split(mailSendRequest.getMailTo(), ","));
            //抄送
            messageHelper.setCc(StringUtils.split(mailSendRequest.getMailCc(), ","));
            // 主题
            messageHelper.setSubject(title);
            //邮件正文
            messageHelper.setText(content, true);
            // 附件
            if (ObjectUtils.isNotEmpty(mailSendRequest.getAttachments())) {
                for (File attachment : mailSendRequest.getAttachments()) {
                    messageHelper.addAttachment(MimeUtility.encodeWord(attachment.getName()), attachment);
                }
            }
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new XquickException("构建邮件发送器失败");
        }

        int status = Constant.SUCCESS;
        //发送邮件
        try {
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            status = Constant.FAIL;
            logger.error("send error", e);
        }

        MailLogEntity log = new MailLogEntity();
        log.setTplId(mailTpl.getId());
        log.setTplCode(mailTpl.getCode());
        log.setMailFrom(mailTpl.getSenderUsername());
        log.setMailTo(mailSendRequest.getMailTo());
        log.setMailCc(mailSendRequest.getMailCc());
        log.setSubject(title);
        log.setContent(content);
        log.setStatus(status);
        mailLogService.save(log);

        return status == Constant.SUCCESS;
    }

}
