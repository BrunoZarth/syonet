package com.brunozarth.syonet.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NewsTest {
    @Test
    void testNewsConstructorAndGetters() {
        News news = new News(1L, "Title 1", "Description 1", "http://example.com");

        assertEquals(1L, news.getId());
        assertEquals("Title 1", news.getTitulo());
        assertEquals("Description 1", news.getDescricao());
        assertEquals("http://example.com", news.getLink());
    }

    @Test
    void testSetters() {
        News news = new News();
        news.setId(2L);
        news.setTitle("Title 2");
        news.setDescription("Description 2");
        news.setLink("http://example2.com");

        assertEquals(2L, news.getId());
        assertEquals("Title 2", news.getTitle());
        assertEquals("Description 2", news.getDescription());
        assertEquals("http://example2.com", news.getLink());
    }
}
