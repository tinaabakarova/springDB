insert into authors (author_id, author_name) values(1, 'unit testovich');
insert into genres (genre_id, genre_name) values (1, 'poem');
insert into books (id, name, author, genre) values (1, 'Ruslan and Ludmila', 1, 1);
insert into books (id, name, author, genre) values (2, 'King Saltan fairytale', 1, 1);
insert into users (user_id, login, password, role, is_account_non_expired, is_account_non_locked,
                   is_credentials_non_expired, is_enabled) values (1, 'user',
                                                                   '$2a$10$T1QEejHy6..ivfYCFAqHCevkbNpiAiRyc1zz2LaksR/ZqkuZKi35a',
                                                                   'user', true, true, true, true);
