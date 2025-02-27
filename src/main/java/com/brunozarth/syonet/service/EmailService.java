package com.brunozarth.syonet.service;

import com.brunozarth.syonet.model.Client;
import com.brunozarth.syonet.model.News;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendNewsEmail(Client client, List<News> newsList) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(client.getEmail());
            helper.setSubject("Daily News!");

            StringBuilder content = new StringBuilder();
            content.append("Good morning, ").append(client.getName()).append("!<br>");

            if (client.getBirthdate() != null && client.getBirthdate().equals(LocalDate.now())) {
                content.append("<b>🎉 Happy Birthday! 🎉</b><br><br>");
            }

            content.append("Here are today's news:<br><br>");

            for (News news : newsList) {
                if (news.getLink() != null && !news.getLink().isEmpty()) {
                    content.append("<a href=\"").append(news.getLink()).append("\"><b>").append(news.getTitle()).append("</b></a><br>");
                } else {
                    content.append("<b>").append(news.getTitle()).append("</b><br>");
                }
                content.append(news.getDescription()).append("<br><br>");
            }

            content.append("See you next time!");

            helper.setText(content.toString(), true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email to " + client.getEmail(), e);
        }
    }
}
