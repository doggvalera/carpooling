function loadOffers() {
    return $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/users/offers',
        dataType: 'json',
        statusCode: {
            200: function (data) {
                return data;
            }
        }
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

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/users/offers',
        data: JSON.stringify({
            "id": document.offer.id.value,
            "carDescription": document.offer.car.value
        }),
        contentType: 'application/json',
        dataType: 'json',
        statusCode: {
            201: function () {
                var offerList = document.querySelector(".offer-list");
                if (offerList) {
                    document.body.removeChild(offerList.parentNode)
                }
                drawOfferList();
            }
        }
    });
    event.preventDefault();
}

function removeElement(event, id){
    console.log(id);
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/offers/' + id,
        statusCode: {
            200: function () {
                var offerList = document.querySelector(".offer-list");
                document.body.removeChild(offerList.parentNode)
                drawOfferList();
            }
        }
    });
    event.preventDefault();
}

drawOfferList();