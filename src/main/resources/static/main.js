    $(document).ready(function() {
        $.get('/api/books/all').done(function (books) {
            books.forEach(function (book) {
                $('tbody').append(`
                <tr>
                        <td>${book.id}</td>
                        <td>${book.name}</td>
                        <td>${book.author.name}</td>
                        <td>${book.genre.name}</td>
                        <td>
                            <a href="/edit?id=${book.id}">Edit</a>
                        </td>
                        <td>
                            <a href="/delete?id=${book.id}" id="delete">Delete</a>
                        </td>
                        <td>
                            <a href="/comments?id=${book.id}">Comments</a>
                        </td>
                 </tr>
                `)
            });
        });
        $(document).delegate('#delete', 'click', function() {
        	var href =$(this).attr('href');
            var bookId = $.urlParam('id', href);
            $.ajax({
                url: '/api/books?id=' + bookId,
                method: 'DELETE',
                contentType: 'application/json',
                success: function(result) {
                    window.location = "/";
                }
            });
        });

    });
