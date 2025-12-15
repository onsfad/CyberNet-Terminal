//package com.austine.blog.service;
//
//import com.austine.blog.exceptions.BlogException;
//import com.austine.blog.model.NotificationEmail;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import lombok.extern.slf4j.XSlf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.mail.javamail.MimeMessagePreparator;
//import org.springframework.scheduling.annotation.Async;
//@Slf4j
//public class MailService {
//
//
//    @Autowired
//    private JavaMailSender mailSender;
//    @Autowired
//    private MailContentBuilder mailContentBuilder;
//
//
//
//    private static final Logger log = LoggerFactory.getLogger(MailService.class);
//
//    @Async
//    void sendMail(NotificationEmail notificationEmail) {
//        MimeMessagePreparator messagePreparator = mimeMessage -> {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//            messageHelper.setFrom("pheelzconnect@email.com");
//            messageHelper.setTo(notificationEmail.getRecipient());
//            messageHelper.setSubject(notificationEmail.getSubject());
//            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
//        };
//        try {
//            mailSender.send(messagePreparator);
//            log.info("Activation email sent!!");
//        } catch (MailException e) {
//            log.error("Exception occurred when sending mail", e);
//            throw new BlogException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
//        }
//    }
//
//}
