package by.questionary.service.impl;

import by.questionary.domain.User;
import by.questionary.service.MailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class MailSenderServiceImpl implements MailSenderService {

    private static final boolean SENT = true;
    private static final boolean NOT_SENT = false;
    private static final String ACTIVATION_MAIL_SUBJECT = "Activation code";

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender mailSender;
    private final MessageCreatorServiceImpl messageCreatorServiceImpl;

    @Autowired
    public MailSenderServiceImpl(JavaMailSender mailSender, MessageCreatorServiceImpl messageCreatorServiceImpl) {
        this.mailSender = mailSender;
        this.messageCreatorServiceImpl = messageCreatorServiceImpl;
    }

    private void send(String mailTo, String subject, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(mailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);

    }

    @Async
    @Override
    public CompletableFuture<Boolean> sendActivationMessage(User user) {

        String email = user.getEmail();
        boolean result = NOT_SENT;

        if (!StringUtils.isEmpty(email)) {

            String message = messageCreatorServiceImpl.createEmailMessage(user);
            send(email, ACTIVATION_MAIL_SUBJECT, message);
            result = SENT;
        }

        log.info("Activation code has been sent");

        return CompletableFuture.completedFuture(result);
    }
}
