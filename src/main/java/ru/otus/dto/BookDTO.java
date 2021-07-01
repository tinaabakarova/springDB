package ru.otus.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

@Data
@NoArgsConstructor
public class BookDTO {
    private String id;
    private String name;
    private Author author;
    private Genre genre;
    private String authorName;
    private String genreName;

    public BookDTO(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.genre = book.getGenre();
        this.author = book.getAuthor();
    }
}
