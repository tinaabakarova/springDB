package ru.otus.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.CommentsDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;


@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author pushkin;
    private Genre poem;
    private Book ruslanAndLudmila;
    private Book kingSultanFairytale;
    private Comment comment1;
    private Comment comment2;

    @ChangeSet(order = "000", id = "dropDB", author = "Abakarova", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "Abakarova", runAlways = true)
    public void initAuthors(AuthorDao repository){
        pushkin = repository.save(new Author("Pushkin A.S"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "Abakarova", runAlways = true)
    public void initGenres(GenreDao repository){
        poem = repository.save(new Genre("poem"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "Abakarova", runAlways = true)
    public void initBooks(BookDao repository){
        ruslanAndLudmila = repository.save(new Book("Ruslan and Ludmila", pushkin, poem));
        kingSultanFairytale = repository.save(new Book("King Saltan fairytale", pushkin, poem));
    }

    @ChangeSet(order = "004", id = "initComments", author = "Abakarova", runAlways = true)
    public void initComments(CommentsDao repository){
        comment1 = repository.save( new Comment("very interesting book!", ruslanAndLudmila, "user12345"));
        comment2 = repository.save(new Comment("great!", ruslanAndLudmila, "user1234"));
    }
}
