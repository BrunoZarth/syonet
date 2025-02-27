package com.brunozarth.syonet.repository;

import com.brunozarth.syonet.model.News;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class NewsRepositoryTest {

    @Autowired
    private NewsRepository newsRepository;

    private News news1, news2;

    @BeforeEach
    void setUp() {
        news1 = new News(null, "Title 1", "Description 1", "http://link1.com", false);
        news2 = new News(null, "Title 2", "Description 2", "http://link2.com", false);
        newsRepository.saveAll(List.of(news1, news2));
    }

    @Test
    void shouldSaveNews() {
        News news = new News(null, "New Title", "New Description", "http://newlink.com", false);
        News savedNews = newsRepository.save(news);

        assertThat(savedNews).isNotNull();
        assertThat(savedNews.getId()).isNotNull();
    }

    @Test
    void shouldFindNewsById() {
        Optional<News> foundNews = newsRepository.findById(news1.getId());

        assertThat(foundNews).isPresent();
        assertThat(foundNews.get().getTitle()).isEqualTo("Title 1");
    }

    @Test
    void shouldFindAllNews() {
        List<News> allNews = newsRepository.findAll();

        assertThat(allNews).hasSize(2);
    }

    @Test
    void shouldDeleteNews() {
        newsRepository.deleteById(news1.getId());
        Optional<News> foundNews = newsRepository.findById(news1.getId());

        assertThat(foundNews).isEmpty();
    }
}
