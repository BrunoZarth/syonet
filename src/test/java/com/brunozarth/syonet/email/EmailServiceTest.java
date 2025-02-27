/* package com.brunozarth.syonet.email;

import com.brunozarth.syonet.model.Client;
import com.brunozarth.syonet.model.News;
import com.brunozarth.syonet.repository.ClientRepository;
import com.brunozarth.syonet.repository.NewsRepository;
import com.brunozarth.syonet.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private NewsRepository newsRepository;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    public void shouldSendEmailWithDailyNews() {
        Client client = new Client(null, "Carlos Souza", "carlos@email.com", "1990-05-15");
        clientRepository.save(client);

        News news1 = new News(null, "News 1", "News description 1", "https://link1.com", false);
        News news2 = new News(null, "News 2", "News description 2", "https://link2.com", false);
        newsRepository.saveAll(List.of(news1, news2));

        emailService.sendDailyNewsletter();

        List<News> unprocessedNews = newsRepository.findByProcessedFalse();
        assertTrue(unprocessedNews.isEmpty());
    }

    @Test
    public void shouldIncludeBirthdayMessageIfClientBirthday() {
        Client client = new Client(null, "Ana Clara", "ana@email.com", "1990-05-15");
        clientRepository.save(client);

        News news = new News(null, "Congratulations!", "Today is a special day!", "https://happy-birthday.com", false);
        newsRepository.save(news);

        emailService.sendDailyNewsletter();

        ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender).send(captor.capture());

        MimeMessage sentMessage = captor.getValue();
        assertTrue(sentMessage.getContent().toString().contains("Happy Birthday"));
    }
}
*/