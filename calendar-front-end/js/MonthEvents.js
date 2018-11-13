$(document).ready(function (ev) {

    $("#month-events-button").click(function (ev) {
        $("main")
            .html(
                '<div class="container">' +
                '<div class="row justify-content-center align-items-center">' +
                '<div class="col-md-6">' +
                '<div class="col-md-12">' +
                '<form id="create-event-form" class="form" action="" method="post">' +
                '<div class="form-group">' +
                '<label for="Month" >Month:</label>' +
                '<br>' +
                '<input type="month" name="month" id="month" class="form-control">' +
                '</div>' +
                '<div class="form-group">' +
                '<button id = "submit-month-button" type="button" class="btn btn-secondary" name="submit" value="submit">Submit</button>' +
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
        $("main").off().on("click", "#submit-month-button", function (ev) {

            ev.preventDefault();
            let month = $("#month").val();
            debugger

            $.ajax({
                type: "GET",
                url: 'http://localhost:8080/events/month/' + month,
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
                        '<label for="Month" >Month:</label>' +
                        '<br>' +
                        '<input type="month" name="month" id="month" class="form-control">' +
                        '</div>' +
                        '<div class="form-group">' +
                        '<button id = "submit-month-button" type="button" class="btn btn-secondary" name="submit" value="submit">Submit</button>' +
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