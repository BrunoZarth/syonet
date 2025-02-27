package com.brunozarth.syonet.controller;

import com.brunozarth.syonet.DTO.NewsDTO;
import com.brunozarth.syonet.service.NewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class NewsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NewsService newsService;

    @InjectMocks
    private NewsController newsController;

    private ObjectMapper objectMapper;
    private NewsDTO newsDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();
        objectMapper = new ObjectMapper();

        newsDTO = new NewsDTO("Title 1", "Description 1", "http://link1.com");
    }

    @Test
    void shouldCreateNews() throws Exception {
        when(newsService.createNews(any(NewsDTO.class))).thenReturn(newsDTO);

        mockMvc.perform(post("/api/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(newsDTO.getTitle()));

        verify(newsService, times(1)).createNews(any(NewsDTO.class));
    }

    @Test
    void shouldGetAllNews() throws Exception {
        when(newsService.getAllNews()).thenReturn(List.of(newsDTO));

        mockMvc.perform(get("/api/news")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(newsService, times(1)).getAllNews();
    }

    @Test
    void shouldDeleteNews() throws Exception {
        doNothing().when(newsService).deleteNews(anyLong());

        mockMvc.perform(delete("/api/news/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(newsService, times(1)).deleteNews(1L);
    }
}
