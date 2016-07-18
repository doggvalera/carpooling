
function showDiv() {
    document.getElementById('carDescription').style.display = "block";
}
function hideDiv() {
    document.getElementById('carDescription').style.display = 'none';
}
var state;
function setMode(_state){
    if (state=="driver"){

        showDiv();
        $("#rideModeOption2").removeClass("active");
        $("#rideModeOption1").addClass("active");

    }
    else {
        hideDiv();
        $("#rideModeOption1").removeClass("active");
        $("#rideModeOption2").addClass("active");
    }


    state=_state;
}

$(function(){
    $('#datepicker').datetimepicker({
        timepicker:false,
        format:'d.m.Y'
    })
    $('#time1picker').datetimepicker({
        datepicker:false,
        format:'H:i'
    })
    $('#time2picker').datetimepicker({
        datepicker:false,
        format:'H:i'
    })
})


$(function () {
    $('form[name="offerRequest"]').submit(function (event) {
        event.preventDefault();
        if (state == "driver") {
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/users/offers',
                data: JSON.stringify({

                    "carDescription": document.offerRequest.carDescription,
                    "coordinateFrom": {
                        "latitude": document.offerRequest.latFrom,
                        "longitude": document.offerRequest.longFrom
                    },
                    "date": {
                        "earliestDeparture": document.offerRequest.dateDeparture+ "T" + document.offerRequest.earliestDeparture,
                        "latestDeparture": document.offerRequest.dateDeparture + "T" + document.offerRequest.latestDeparture
                    }
                }),
                contentType: 'application/json',
                dataType: 'json',
                statusCode: {
                    201: alert("OK")

                }
            });
        }
        else {
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/users/requests',
                data: JSON.stringify({

                    "radius": document.offerRequest.radius,
                    "coordinateFrom": {
                        "latitude": document.offerRequest.latFrom,
                        "longitude": document.offerRequest.longFrom
                    },
                    "date": {
                        "earliestDeparture": document.offerRequest.dateDeparture + "T" + document.offerRequest.earliestDeparture,
                        "latestDeparture": document.offerRequest.dateDeparture + "T" + document.offerRequest.latestDeparture
                    }
                }),
                contentType: 'application/json',
                dataType: 'json',
                statusCode: {
                    201: alert("OK")

                }
            });

        }

    });
});