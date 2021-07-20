package ru.otus.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.dao.BooksDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BooksDao repository;

    private Author author2 = new Author(2, "unit testovich");
    private Author author1 = new Author(1, "Pushkin A.S");
    private Genre genre = new Genre(1, "poem");
    private Book book1 = new Book(1, "Ruslan and Ludmila", author1, genre);
    private Book book2 = new Book(2, "King Saltan fairytale", author1, genre);

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void shouldGetAllBooksByAdmin() throws Exception {
        List<Book> books = List.of(book1, book2);
        given(repository.findAll()).willReturn(books);

        List<BookDTO> expectedResult = books.stream()
                .map(BookDTO::new).collect(Collectors.toList());

        mvc.perform(get("/api/books/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void shouldGetBookByAdmin() throws Exception {
        given(repository.findById(book1.getId())).willReturn(Optional.of(book1));
        BookDTO expectedResult = new BookDTO(book1);

        mvc.perform(get("/api/books").param("id", String.valueOf(book1.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void shouldUpdateBookByAdmin() throws Exception {
        given(repository.findById(book1.getId())).willReturn(Optional.of(book1));

        Book expectedBook = new Book(book1.getId(), "Updated", author2, genre);
        BookDTO expectedResult = new BookDTO();
        expectedResult.setId(book1.getId());
        expectedResult.setName("Updated");
        expectedResult.setAuthorName(author2.getName());
        expectedResult.setGenreName(genre.getName());

        mvc.perform(put("/api/books")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(expectedResult)))
                .andExpect(status().isOk());

        mvc.perform(get("/api/books").param("id", String.valueOf(book1.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(new BookDTO(expectedBook))));

    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void shouldGetAllBooksByUser() throws Exception {
        List<Book> books = List.of(book1, book2);
        given(repository.findAll()).willReturn(books);

        List<BookDTO> expectedResult = books.stream()
                .map(BookDTO::new).collect(Collectors.toList());

        mvc.perform(get("/api/books/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void shouldGetBookByUser() throws Exception {
        given(repository.findById(book1.getId())).willReturn(Optional.of(book1));
        BookDTO expectedResult = new BookDTO(book1);

        mvc.perform(get("/api/books").param("id", String.valueOf(book1.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void shouldUpdateBookByUser() throws Exception {
        given(repository.findById(book1.getId())).willReturn(Optional.of(book1));

        BookDTO expectedResult = new BookDTO();
        expectedResult.setId(book1.getId());
        expectedResult.setName("Updated");
        expectedResult.setAuthorName(author2.getName());
        expectedResult.setGenreName(genre.getName());

        mvc.perform(put("/api/books")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(expectedResult)))
                .andExpect(status().is(403));

    }
}