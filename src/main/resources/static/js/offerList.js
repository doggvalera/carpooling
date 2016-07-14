function loadOffers() {
    return fetch('http://localhost:8080/users/offers')
        .then(function(response) {
            return response.json();
        });
}

function drawOfferList() {
    loadOffers().then(function(offers) {
        var offerListTemplate = Handlebars.compile(document.querySelector('#offer-list').innerHTML);
        var offerTemplate = Handlebars.compile(document.querySelector('#offer').innerHTML);

        var offerList = '';
        offers.forEach(function(offer) {
            offerList += offerTemplate(offer);
        });

        var offerList = offerListTemplate({
            body: offerList
        });

        var offerListContainer = document.createElement('div');
        offerListContainer.innerHTML = offerList;
        document.body.appendChild(offerListContainer);
    });
}

function handleSubmit(event){

    var form = new FormData(document.offer);
    fetch('http://localhost:8080/users/offers', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",

        body: JSON.stringify({
            id: document.offer.id.value,
            car: document.offer.car.value


            //lastName: document.user.lastName.value
        })

    })
        .then(function() {
            var offerList = document.querySelector(".offer-list");
            document.body.removeChild(offerList.parentNode)
            drawOfferList();
        });

    event.preventDefault();
}

function removeElement(event, id){
    console.log(id);
    fetch('http://localhost:8080/offers/'+id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "DELETE"})
        .then(function() {
            var offerList = document.querySelector(".offer-list");
            document.body.removeChild(offerList.parentNode)
            drawOfferList();

        });
    event.preventDefault();
}

drawOfferList();