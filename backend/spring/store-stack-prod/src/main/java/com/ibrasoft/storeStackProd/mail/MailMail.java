package com.ibrasoft.storeStackProd.mail;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service("mailMail")
public class MailMail {

    private JavaMailSender mailSender;

    private SimpleMailMessage customeMailMessage;

    public MailMail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String dear, String content, String fileName) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(customeMailMessage.getFrom());
            helper.setTo(customeMailMessage.getTo());
            helper.setSubject(customeMailMessage.getSubject());
            helper.setText(String.format(
                    customeMailMessage.getText(), dear, content));
            FileSystemResource file = new FileSystemResource(fileName);
            helper.addAttachment(file.getFilename(), file);
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
        mailSender.send(message);
    }

    public void sendMailAlert(String subject, String content, String emailTo) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("cmcb@ibrasoft.eu");
            helper.setTo(emailTo);
            helper.setSubject(subject);
            helper.setText(String.format(content));
            mailSender.send(message);
        }catch(Exception e){
            throw new MailParseException(e);
        }
    }
}
