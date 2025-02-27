package com.brunozarth.syonet.service;

import com.brunozarth.syonet.DTO.NewsDTO;
import com.brunozarth.syonet.model.News;
import com.brunozarth.syonet.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsService newsService;

    private News news;
    private NewsDTO newsDTO;

    @BeforeEach
    void setUp() {
        news = new News(1L, "Title", "Description", "http://link.com");
        newsDTO = new NewsDTO("Title", "Description", "http://link.com");
    }

    @Test
    void shouldCreateNews() {
        when(newsRepository.save(any(News.class))).thenReturn(news);

        NewsDTO createdNews = newsService.createNews(newsDTO);

        assertThat(createdNews.getTitle()).isEqualTo(news.getTitle());
        verify(newsRepository, times(1)).save(any(News.class));
    }

    @Test
    void shouldReturnAllNews() {
        when(newsRepository.findAll()).thenReturn(List.of(news));

        List<NewsDTO> newsList = newsService.getAllNews();

        assertThat(newsList).hasSize(1);
        verify(newsRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnNewsById() {
        when(newsRepository.findById(1L)).thenReturn(Optional.of(news));

        Optional<NewsDTO> foundNews = newsService.getNewsById(1L);

        assertThat(foundNews).isPresent();
        assertThat(foundNews.get().getTitle()).isEqualTo(news.getTitle());
        verify(newsRepository, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateNews() {
        when(newsRepository.findById(1L)).thenReturn(Optional.of(news));
        when(newsRepository.save(any(News.class))).thenReturn(news);

        NewsDTO updatedNews = newsService.updateNews(1L, newsDTO);

        assertThat(updatedNews.getTitle()).isEqualTo(newsDTO.getTitle());
        verify(newsRepository, times(1)).save(news);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentNews() {
        when(newsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> newsService.updateNews(1L, newsDTO));
    }

    @Test
    void shouldDeleteNews() {
        doNothing().when(newsRepository).deleteById(1L);

        newsService.deleteNews(1L);

        verify(newsRepository, times(1)).deleteById(1L);
    }
}
