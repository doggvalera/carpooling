function loadRequests() {
    return fetch('http://localhost:8080/users/requests')
        .then(function(response) {
            return response.json();
        });
}

function drawRequestList() {
    loadRequests().then(function(requests) {
        var requestListTemplate = Handlebars.compile(document.querySelector('#request-list').innerHTML);
        var requestTemplate = Handlebars.compile(document.querySelector('#request').innerHTML);

        var requestList = '';
        requests.forEach(function(request) {
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

function handleSubmit(event){

    var form = new FormData(document.request);
    fetch('http://localhost:8080/users/requests', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",

        body: JSON.stringify({
            id: document.request.id.value,
            radius: document.request.radius.value


            //lastName: document.user.lastName.value
        })

    })
        .then(function() {
            var requestList = document.querySelector(".request-list");
            document.body.removeChild(requestList.parentNode)
            drawRequestList();
        });

    event.preventDefault();
}

function removeElement(event, id){
    console.log(id);
    fetch('http://localhost:8080/requests/'+id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "DELETE"})
        .then(function() {
            var requestList = document.querySelector(".request-list");
            document.body.removeChild(requestList.parentNode)
            drawRequestList();

        });
    event.preventDefault();
}

drawRequestList();
