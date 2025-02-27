package com.brunozarth.syonet.email;

import com.brunozarth.syonet.model.Client;
import com.brunozarth.syonet.model.News;
import com.brunozarth.syonet.repository.ClientRepository;
import com.brunozarth.syonet.repository.NewsRepository;
import com.brunozarth.syonet.service.EmailService;
import com.brunozarth.syonet.service.ScheduledEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class ScheduledEmailServiceTest {

    @Mock private ClientRepository clientRepository;
    @Mock private NewsRepository newsRepository;
    @Mock private EmailService emailService;

    @InjectMocks
    private ScheduledEmailService scheduledEmailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSendEmailsAndMarkNewsAsProcessed() {
        List<Client> clients = List.of(new Client());
        List<News> news = List.of(new News());

        when(newsRepository.findByProcessedFalse()).thenReturn(news);
        when(clientRepository.findAll()).thenReturn(clients);

        scheduledEmailService.sendDailyNewsEmails();

        verify(emailService, times(1)).sendNewsEmail(any(), any());
        verify(newsRepository, times(1)).saveAll(news);
    }
}

