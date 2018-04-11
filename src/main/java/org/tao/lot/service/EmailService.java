package org.tao.lot.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String             emailFrom;

    @Value("${spring.mail.host}")
    private String             host;

    @Value("${spring.mail.port}")
    private int                port;

    @Value("${spring.mail.password}")
    private String             pwd;

    @Autowired
    private JavaMailSenderImpl mailSender;

    static boolean             initFlag = false;

    private void configMailSender() throws UnsupportedEncodingException {
        mailSender.setDefaultEncoding("UTF-8");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");//开启认证  
        properties.setProperty("mail.smtp.timeout", "1000");//设置链接超时  
        properties.setProperty("mail.smtp.port", Integer.toString(port));//设置端口  
        properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(port));//设置ssl端口  
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.properties.mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.properties.mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.properties.mail.smtp.starttls.required", "true");
        mailSender.setJavaMailProperties(properties);
    }

    public void sendSimpleMail(String titel, String content) throws UnsupportedEncodingException {

        if (!initFlag) {
            configMailSender();
            initFlag = true;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(emailFrom);
        message.setSubject(titel);
        message.setText(content);
        mailSender.send(message);
    }
}
