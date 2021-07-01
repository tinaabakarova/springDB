    $(document).ready(function() {
            var action = $('#create-form').attr('action');
            if (action == '/edit') {

                var bookAuthor;
                var bookGenre;
                var id = $.urlParam('id', window.location.href);
                $.ajax({
                        type: "get",
                        url: "/api/books",
                        data: 'id=' + id,
                        success: function (book) {
                            $('#id-input').val(book.id);
                            $('#name-input').val(book.name);
                            bookAuthor = book.author.name;
                            bookGenre = book.genre.name;
                            $.ajax({
                                type: "get",
                                url: "/api/authors/all",
                                success: function (authors) {
                                    authors.forEach(function (author) {
                                        $('#author-input').append(`
                                        <option value="${author.name}">${author.name}</option>
                                        `)
                                    });
                                    $("select#author-input").val(bookAuthor).change();
                                }
                            });
                            $.ajax({
                                type: "get",
                                url: "/api/genres/all",
                                success: function (genres) {
                                    genres.forEach(function (genre) {
                                        $('#genre-input').append(`
                                        <option value="${genre.name}">${genre.name}</option>
                                        `)
                                    });
                                    $("select#genre-input").val(bookGenre).change();
                                }
                            });
                        }
                    });
                $(document).delegate('#submit', 'click', function(event) {
                	event.preventDefault();

                	var id = $('#id-input').val();
                	var name = $('#name-input').val();
                	var author = $('#author-input').val();
                	var genre = $('#genre-input').val();


                	$.ajax({
                		type: "PUT",
                		contentType: "application/json; charset=utf-8",
                		url: "/api/books",
                		data: JSON.stringify({'id': id, 'name': name, 'authorName': author, 'genreName': genre}),
                		cache: false,
                		success: function(result) {
                			window.location = "/";
                		}
                	});
                });
            }
            if (action == '/create') {
                $.ajax({
                    type: "get",
                    url: "/api/authors/all",
                    success: function (authors) {
                        authors.forEach(function (author) {
                            $('#author-input').append(`
                            <option value="${author.name}">${author.name}</option>
                            `)
                        });
                    }
                });
                $.ajax({
                    type: "get",
                    url: "/api/genres/all",
                    success: function (genres) {
                        genres.forEach(function (genre) {
                            $('#genre-input').append(`
                            <option value="${genre.name}">${genre.name}</option>
                            `)
                        });
                    }
                });

                $(document).delegate('#submit', 'click', function(event) {
                	event.preventDefault();

                	var name = $('#name-input').val();
                	var author = $('#author-input').val();
                	var genre = $('#genre-input').val();

                	$.ajax({
                		type: "POST",
                		contentType: "application/json; charset=utf-8",
                		url: "/api/books",
                		data: JSON.stringify({'name': name, 'authorName': author, 'genreName': genre}),
                		cache: false,
                		success: function(result) {
                			window.location = "/";
                		}
                	});
                });
            }

    });