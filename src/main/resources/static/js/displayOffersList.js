function loadOffers() {
    return fetch('http://localhost:8080//users/offers')
        .then(function(response) {
            return response.json();
        });
}

function drawUserList() {
    loadOffers().then(function(offers) {
        var userListTemplate = Handlebars.compile(document.querySelector('#user-list').innerHTML);
        var userTemplate = Handlebars.compile(document.querySelector('#user').innerHTML);

        var userList = '';
        offers.forEach(function(offer) {
            userList += userTemplate(offer);
        });

        var userList = userListTemplate({
            body: userList
        });

        var userListContainer = document.createElement('div');
        userListContainer.innerHTML = userList;
        document.body.appendChild(userListContainer);
    });
}

function handleRemove(id){
    fetch("http://localhost:8080/students/"+id,
        {
            method: "DELETE",
            headers: {
                'Accept':'application/json',
                'Content-Type':'application/json'
            }
        })
        .then(function(){
            var userList = document.querySelector(".user-list");
            document.body.removeChild(userList.parentNode);
            drawUserList();
        });
}

drawUserList();
