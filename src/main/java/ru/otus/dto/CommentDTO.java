package ru.otus.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

@Data
@NoArgsConstructor
public class CommentDTO {
    private String id;
    private String comment;
    private Book book;
    private String userName;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.book = comment.getBook();
        this.userName = comment.getUserName();
    }
}
