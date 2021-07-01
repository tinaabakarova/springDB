package ru.otus.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author pushkin;
    private Genre poem;
    private Author tolstoy;
    private Genre drama;
    private Book ruslanAndLudmila;
    private Book kingSultanFairytale;
    private Comment comment1;
    private Comment comment2;

    @ChangeSet(order = "000", id = "dropDB", author = "Abakarova", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "Abakarova", runAlways = true)
    public void initAuthors(MongockTemplate mongockTemplate){
        pushkin = mongockTemplate.save(new Author("Pushkin A.S"));
        tolstoy = mongockTemplate.save(new Author("Tolstoy L.N"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "Abakarova", runAlways = true)
    public void initGenres(MongockTemplate mongockTemplate){
        drama = mongockTemplate.save(new Genre("drama"));
        poem = mongockTemplate.save(new Genre("poem"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "Abakarova", runAlways = true)
    public void initBooks(MongockTemplate mongockTemplate){
        ruslanAndLudmila = mongockTemplate.save(new Book("Ruslan and Ludmila", pushkin, poem));
        kingSultanFairytale = mongockTemplate.save(new Book("King Saltan fairytale", pushkin, poem));
    }

    @ChangeSet(order = "004", id = "initComments", author = "Abakarova", runAlways = true)
    public void initComments(MongockTemplate mongockTemplate){
        comment1 = mongockTemplate.save( new Comment("very interesting book!", ruslanAndLudmila, "user12345"));
        comment2 = mongockTemplate.save(new Comment("great!", ruslanAndLudmila, "user1234"));
    }
}
