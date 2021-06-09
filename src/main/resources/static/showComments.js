$(function () {
        var bookId = $.urlParam('id', window.location.href);
        $.get('/api/comments', {id: bookId}).done(function (comments) {
            comments.forEach(function (comment) {
                $('tbody').append(`
                <tr>
                         <td>${comment.id}</td>
                         <td>${comment.comment}</td>
                         <td>${comment.book.name}</td>
                         <td>${comment.userName}</td>
                 </tr>
                `)
            });
        })
    });