$(document).ready(function (ev) {
    $("#create-event-button").click(function () {

        $("main")
            .html(
                '<br>' +
                '<br>' +
                '<div class="container">' +
                '<div class="row justify-content-center align-items-center">' +
                '<div class="col-md-6">' +
                '<div class="col-md-12">' +
                '<form id="create-event-form" class="form" action="" method="post">' +
                '<h3 class="text-center">Create Event</h3>' +
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
                '<button id = "submit-event-button" type="button" class="btn btn-secondary" name="submit" value="submit">Submit</button>' +
                '</div>' +
                '</form>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>'
            );

        $("main").off().on("click", "#submit-event-button", function (ev) {

            ev.preventDefault();

            let title = $("#title").val();
            let date = $("#date").val();
            let startTime = $("#start-time").val();
            let endTime = $("#end-time").val();
            let description = $("#description").val();

            $.ajax({
                type: "POST",
                url: 'http://localhost:8080/events/create',
                headers: {
                    "Content-Type": "application/json",
                },
                data: JSON.stringify({
                    "title": title,
                    "date": date,
                    "startTime": startTime,
                    "endTime": endTime,
                    "description": description
                })
            }).done(function (body) {
                new Noty({
                    text: "SUCCESSFULLY CREATE Event - " + title + "!",
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
        });
    });
});