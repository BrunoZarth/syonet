package com.brunozarth.syonet.service;

import com.brunozarth.syonet.model.News;
import com.brunozarth.syonet.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsService newsService;

    private News news;

    @BeforeEach
    void setUp() {
        news = new News(1L, "Breaking News", "This is a test news", "http://news.com");
    }

    @Test
    void shouldSaveNews() {
        when(newsRepository.save(news)).thenReturn(news);

        News savedNews = newsService.save(news);

        assertThat(savedNews).isNotNull();
        assertThat(savedNews.getId()).isEqualTo(1L);
        verify(newsRepository, times(1)).save(news);
    }

    @Test
    void shouldFindNewsById() {
        when(newsRepository.findById(1L)).thenReturn(Optional.of(news));

        Optional<News> foundNews = newsService.findById(1L);

        assertThat(foundNews).isPresent();
        assertThat(foundNews.get().getTitle()).isEqualTo("Breaking News");
        verify(newsRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturnEmptyWhenNewsNotFound() {
        when(newsRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<News> foundNews = newsService.findById(2L);

        assertThat(foundNews).isEmpty();
        verify(newsRepository, times(1)).findById(2L);
    }

    @Test
    void shouldFindAllNews() {
        List<News> newsList = Arrays.asList(news, new News(2L, "Another News", "Another Description", "http://another.com"));
        when(newsRepository.findAll()).thenReturn(newsList);

        List<News> foundNews = newsService.findAll();

        assertThat(foundNews).hasSize(2);
        verify(newsRepository, times(1)).findAll();
    }

    @Test
    void shouldDeleteNewsById() {
        doNothing().when(newsRepository).deleteById(1L);

        newsService.deleteById(1L);

        verify(newsRepository, times(1)).deleteById(1L);
    }
}
