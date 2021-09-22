insert into authors (author_id, author_name) values(1, 'Pushkin A.S');
insert into authors (author_id, author_name) values(2, 'unit testovich');
insert into genres (genre_id, genre_name) values (1, 'poem');
insert into genres (genre_id, genre_name) values (2, 'comedy');
insert into books (id, name, author, genre) values (1, 'Ruslan and Ludmila', 1, 1);
insert into books (id, name, author, genre) values (2, 'King Saltan fairytale', 1, 1);
insert into comments (id, comment, book, user_name) values (1, 'very interesting book!', 1, 'user12345');
insert into comments (id, comment, book, user_name) values (2, 'great!', 1, 'user1234');
insert into users (user_id, login, password, role, is_account_non_expired, is_account_non_locked,
                   is_credentials_non_expired, is_enabled) values (1, 'user',
                                                           '$2a$10$T1QEejHy6..ivfYCFAqHCevkbNpiAiRyc1zz2LaksR/ZqkuZKi35a',
                                                           'ROLE_USER', true, true, true, true);
insert into users (user_id, login, password, role, is_account_non_expired, is_account_non_locked,
                   is_credentials_non_expired, is_enabled) values (2, 'admin',
                                                                   '$2a$10$T1QEejHy6..ivfYCFAqHCevkbNpiAiRyc1zz2LaksR/ZqkuZKi35a',
                                                                   'ROLE_ADMIN', true, true, true, true);