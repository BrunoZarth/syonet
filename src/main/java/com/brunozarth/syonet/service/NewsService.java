package com.brunozarth.syonet.service;

import com.brunozarth.syonet.model.News;
import com.brunozarth.syonet.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public News save(News news) {
        return newsRepository.save(news);
    }

    public Optional<News> findById(Long id) {
        return newsRepository.findById(id);
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}
