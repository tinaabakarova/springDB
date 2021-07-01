package ru.otus.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.domain.Author;

@Data
@NoArgsConstructor
public class AuthorDTO {
    private String id;
    private String name;

    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
    }
}
