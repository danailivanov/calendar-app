$(document).ready(function (ev) {

    $("#day-events-button").click(function (ev) {
        $("main")
            .html(
                '<div class="container">' +
                '<div class="row justify-content-center align-items-center">' +
                '<div class="col-md-6">' +
                '<div class="col-md-12">' +
                '<form id="create-event-form" class="form" action="" method="post">' +
                '<div class="form-group">' +
                '<label for="Date" >Date:</label>' +
                '<br>' +
                '<input type="date" name="date" id="date" class="form-control">' +
                '</div>' +
                '<div class="form-group">' +
                '<button id = "submit-day-button" type="button" class="btn btn-secondary" name="submit" value="submit">Submit</button>' +
                '</div>' +
                '</form>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
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
        $("main").off().on("click", "#submit-day-button", function (ev) {

            ev.preventDefault();
            let date = $("#date").val();

            $.ajax({
                type: "GET",
                url: 'http://localhost:8080/events/day/' + date,
                headers: {
                    "Content-Type": "application/json",
                },
            }).done((data) => {

                $("main")
                    .html(
                        '<div class="container">' +
                        '<div class="row justify-content-center align-items-center">' +
                        '<div class="col-md-6">' +
                        '<div class="col-md-12">' +
                        '<form id="create-event-form" class="form" action="" method="post">' +
                        '<div class="form-group">' +
                        '<label for="Date" >Date:</label>' +
                        '<br>' +
                        '<input type="date" name="date" id="date" class="form-control">' +
                        '</div>' +
                        '<div class="form-group">' +
                        '<button id = "submit-day-button" type="button" class="btn btn-secondary" name="submit" value="submit">Submit</button>' +
                        '</div>' +
                        '</form>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
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
                            '<td>' + json_obj[i].id + '</td>' +
                            '<td>' + json_obj[i].title + '</td>' +
                            '<td>' + json_obj[i].date + '</td>' +
                            '<td>' + json_obj[i].startTime + '</td>' +
                            '<td>' + json_obj[i].endTime + '</td>' +
                            '<td>' + json_obj[i].description + '</td>' +
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
        });
    });
});