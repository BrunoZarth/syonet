package com.brunozarth.syonet.service;

import com.brunozarth.syonet.DTO.NewsDTO;
import com.brunozarth.syonet.model.News;
import com.brunozarth.syonet.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsDTO createNews(NewsDTO newsDTO) {
        News news = new News(null, newsDTO.getTitle(), newsDTO.getDescription(), newsDTO.getLink());
        News savedNews = newsRepository.save(news);
        return new NewsDTO(savedNews.getTitle(), savedNews.getDescription(), savedNews.getLink());
    }

    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(news -> new NewsDTO(news.getTitle(), news.getDescription(), news.getLink()))
                .collect(Collectors.toList());
    }

    public Optional<NewsDTO> getNewsById(Long id) {
        return newsRepository.findById(id)
                .map(news -> new NewsDTO(news.getTitle(), news.getDescription(), news.getLink()));
    }

    public NewsDTO updateNews(Long id, NewsDTO newsDTO) {
        return newsRepository.findById(id)
                .map(existingNews -> {
                    existingNews.setTitle(newsDTO.getTitle());
                    existingNews.setDescription(newsDTO.getDescription());
                    existingNews.setLink(newsDTO.getLink());
                    News updatedNews = newsRepository.save(existingNews);
                    return new NewsDTO(updatedNews.getTitle(), updatedNews.getDescription(), updatedNews.getLink());
                })
                .orElseThrow(() -> new IllegalArgumentException("News with ID " + id + " not found"));
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}
