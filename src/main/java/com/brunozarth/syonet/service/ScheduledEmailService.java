package com.brunozarth.syonet.service;

import com.brunozarth.syonet.DTO.EmailMessageDTO;
import com.brunozarth.syonet.model.Client;
import com.brunozarth.syonet.model.News;
import com.brunozarth.syonet.repository.ClientRepository;
import com.brunozarth.syonet.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledEmailService {

    private final ClientRepository clientRepository;
    private final NewsRepository newsRepository;
    private final EmailProducer emailProducer;

    @Scheduled(cron = "0 0 8 * * ?")
    public void sendDailyNewsEmails() {
        List<News> unprocessedNews = newsRepository.findByProcessedFalse();

        if (unprocessedNews.isEmpty()) return;

        List<Client> clients = clientRepository.findAll();

        for (Client client : clients) {
            StringBuilder content = new StringBuilder();
            content.append("Good morning, ").append(client.getName()).append("!<br>");

            if (client.getBirthdate() != null && client.getBirthdate().equals(LocalDate.now())) {
                content.append("<b>🎉 Happy Birthday! 🎉</b><br><br>");
            }

            content.append("Here are today's news:<br><br>");

            for (News news : unprocessedNews) {
                if (news.getLink() != null && !news.getLink().isEmpty()) {
                    content.append("<a href=\"").append(news.getLink()).append("\"><b>").append(news.getTitle()).append("</b></a><br>");
                } else {
                    content.append("<b>").append(news.getTitle()).append("</b><br>");
                }
                content.append(news.getDescription()).append("<br><br>");
            }

            content.append("See you next time!");

            EmailMessageDTO emailMessageDTO = new EmailMessageDTO(
                    client.getEmail(),
                    "Daily News!",
                    content.toString()
            );

            emailProducer.sendEmailToQueue(emailMessageDTO);
        }

        unprocessedNews.forEach(news -> news.setProcessed(true));
        newsRepository.saveAll(unprocessedNews);
    }
}
