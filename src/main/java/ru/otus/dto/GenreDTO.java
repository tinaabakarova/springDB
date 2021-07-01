package ru.otus.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.domain.Genre;

@Data
@NoArgsConstructor
public class GenreDTO {
    private String id;
    private String name;

    public GenreDTO(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }
}
