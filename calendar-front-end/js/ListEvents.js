$(document).ready(function (ev) {

    $("#list-events-button").click(function (ev) {
        $("main").empty()
        ev.preventDefault();

        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/events/all',
            headers: {
                "Content-Type": "application/json",
            }
        }).done((data) => {

            $("main")
                .html(
                    '<table class="table table-hover" id="table">' +
                    '<thead>' +
                    '<tr>' +
                    '<th scope="col">ID</th>' +
                    '<th scope="col">Title</th>' +
                    '<th scope="col">Date</th>' +
                    '<th scope="col">Start Time</th>' +
                    '<th scope="col">End Time</th>' +
                    '<th scope="col">Description</th>' +
                    '</tr>' +
                    '</thead>' +
                    '<tbody>'
                );

            let json_obj = data;
            for (let i in json_obj) {
                console.log(i);
                $('table')
                    .append(
                        '<tr >' +
                        '<td >' + json_obj[i].id + '</td>' +
                        '<td>' + json_obj[i].title + '</td>' +
                        '<td>' + json_obj[i].date + '</td>' +
                        '<td>' + json_obj[i].startTime + '</td>' +
                        '<td>' + json_obj[i].endTime + '</td>' +
                        '<td>' + json_obj[i].description + '</td>' +
                        // check
                        '<td><button type="button mr-auto" class="btn btn-secondary" id="edit-event-button">Edit</button></td>' +
                        '<td><button type="button mr-auto" class="btn btn-secondary" id="delete-event-button">Delete</button></td>' +
                        '</tr>' +
                        '</th>' +
                        '</tbody>' +
                        '</table>'
                    )
            }
        }).fail(function (xhr, status, error) {
            new Noty({
                text: 'ERROR [' + xhr['status'] + ']: ' + xhr['responseText'],
                layout: 'topCenter',
                type: 'error',
                theme: 'mint',
                timeout: 3000
            }).show();
        });

        $('main').off().on('click', '#delete-event-button', function (e) {
            e.preventDefault();
            var currow = $(this).closest('tr');
            var id = currow.find('td:eq(0)').text();
            var title = currow.find('td:eq(1)').text();

            $.ajax({
                type: 'DELETE',
                url: 'http://localhost:8080/events/delete/' + id,
                headers: {
                    "Content-Type": "application/json",
                }
            }).done(function (body) {
                new Noty({
                    text: "SUCCESSFULLY DELETE EVENT -" + title + "!",
                    layout: 'topCenter',
                    type: 'success',
                    theme: 'mint',
                    timeout: 3000
                }).show();
            }).fail(function (xhr, status, error) {
                new Noty({
                    text: 'ERROR [' + xhr['status'] + ']: ' + xhr['responseText'],
                    layout: 'topCenter',
                    type: 'error',
                    theme: 'mint',
                    timeout: 3000
                }).show();

            });
            $("main").empty();
        });

        $('main').on('click', '#edit-event-button', function (e) {
            e.preventDefault();

            var currow = $(this).closest('tr');
            var id = currow.find('td:eq(0)').text();

            $.ajax({

                type: 'GET',
                url: 'http://localhost:8080/events/' + id,
                headers: {
                    "Content-Type": "application/json",
                }
            }).done((data) => {
                debugger
                $("main")
                    .html(
                        '<br>' +
                        '<br>' +
                        '<div class="container">' +
                        '<div class="row justify-content-center align-items-center">' +
                        '<div class="col-md-6">' +
                        '<div class="col-md-12">' +
                        '<form id="create-event-form" class="form" action="" method="post">' +
                        '<h3 class="text-center">Edit Event</h3>' +
                        '<div class="form-group row">' +
                        '<label for="id" >ID:</label>' +
                        '<br>' +
                        '<input type="text" name="id" id="id" value = "' + data.id + '" class="form-control" disabled>' +
                        '</div>' +
                        '<div class="form-group">' +
                        '<label for="title" >Title:</label>' +
                        '<br>' +
                        '<input type="text" name="title" id="title" class="form-control">' +
                        '</div>' +
                        '<div class="form-group">' +
                        '<label for="Date" >Date:</label>' +
                        '<br>' +
                        '<input type="date" name="date" id="date" class="form-control">' +
                        '</div>' +
                        '<div class="form-group">' +
                        '<label for="start-time" >Start Time:</label>' +
                        '<br>' +
                        '<input type="time" name="start-time" id="start-time" class="form-control">' +
                        '</div>' +
                        '<div class="form-group">' +
                        '<label for="end-time" >End Time:</label>' +
                        '<br>' +
                        '<input type="time" name="end-time" id="end-time" class="form-control">' +
                        '</div>' +
                        '<div class="form-group">' +
                        '<label for="description" >Description:</label>' +
                        '<br>' +
                        '<input type="text" name="description" id="description" class="form-control">' +
                        '</div>' +
                        '<div class="form-group">' +
                        '<button id = "save-event-button" type="button" class="btn btn-secondary" name="submit" value="submit">Submit</button>' +
                        '</div>' +
                        '</form>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>'
                    );
            }).fail(function (xhr, status, error) {
                new Noty({
                    text: 'ERROR [' + xhr['status'] + ']: ' + xhr['responseText'],
                    layout: 'topCenter',
                    type: 'error',
                    theme: 'mint',
                    timeout: 3000
                }).show();
            });

            $('main').off().on('click', '#save-event-button', function (ev) {
                ev.preventDefault();
                let id = $('#id').val();
                let title = $("#title").val();
                let date = $("#date").val();
                let startTime = $("#start-time").val();
                let endTime = $("#end-time").val();
                let description = $("#description").val();


                $.ajax({
                    type: 'PUT',
                    url: 'http://localhost:8080/events/edit',
                    headers: {
                        "Content-Type": "application/json",
                    },
                    data: JSON.stringify({
                        "id": id,
                        "title": title,
                        "date": date,
                        "startTime": startTime,
                        "endTime": endTime,
                        "description": description

                    })

                }).done(function (body) {
                    new Noty({
                        text: "SUCCESSFULLY EDIT EVENT - " + title + "!",
                        layout: 'topCenter',
                        type: 'success',
                        theme: 'mint',
                        timeout: 3000
                    }).show();
                }).fail(function (xhr, status, error) {
                    new Noty({
                        text: 'ERROR [' + xhr['status'] + ']: ' + xhr['responseText'],
                        layout: 'topCenter',
                        type: 'error',
                        theme: 'mint',
                        timeout: 3000
                    }).show();
                });
                $("main").empty();
            });
        });
    });
});