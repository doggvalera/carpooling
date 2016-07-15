function loadRequests() {
    return $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/users/requests',
        dataType: 'json',
        statusCode: {
            200: function (data) {
                return data;
            }
        }
    });

}

function drawRequestList() {
    loadRequests().then(function (requests) {
        var requestListTemplate = Handlebars.compile(document.querySelector('#request-list').innerHTML);
        var requestTemplate = Handlebars.compile(document.querySelector('#request').innerHTML);

        var requestList = '';
        requests.forEach(function (request) {
            requestList += requestTemplate(request);
        });

        var requestList = requestListTemplate({
            body: requestList
        });

        var requestListContainer = document.createElement('div');
        requestListContainer.innerHTML = requestList;
        document.body.appendChild(requestListContainer);
    });
}

function handleSubmit(event) {

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/users/requests',
        data: JSON.stringify({
            "id": document.request.id.value,
            "radius": document.request.radius.value
        }),
        contentType: 'application/json',
        dataType: 'json',
        statusCode: {
            201: function () {
                var requestList = document.querySelector(".request-list");
                if (requestList) {
                    document.body.removeChild(requestList.parentNode)
                }
                drawRequestList();
            }
        }
    });
    event.preventDefault();
}

function removeElement(event, id) {
    console.log(id);
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/requests/' + id,
        statusCode: {
            200: function () {
                var requestList = document.querySelector(".request-list");
                document.body.removeChild(requestList.parentNode)
                drawRequestList();
            }
        }
    });
    event.preventDefault();
}

drawRequestList();
