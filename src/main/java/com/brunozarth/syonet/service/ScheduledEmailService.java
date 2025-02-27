package com.brunozarth.syonet.service;

import com.brunozarth.syonet.model.Client;
import com.brunozarth.syonet.model.News;
import com.brunozarth.syonet.repository.ClientRepository;
import com.brunozarth.syonet.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledEmailService {

    private final ClientRepository clientRepository;
    private final NewsRepository newsRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 8 * * ?")
    public void sendDailyNewsEmails() {
        List<News> unprocessedNews = newsRepository.findByProcessedFalse();

        if (unprocessedNews.isEmpty()) return;

        List<Client> clients = clientRepository.findAll();

        for (Client client : clients) {
            emailService.sendNewsEmail(client, unprocessedNews);
        }

        unprocessedNews.forEach(news -> news.setProcessed(true));
        newsRepository.saveAll(unprocessedNews);
    }
}
