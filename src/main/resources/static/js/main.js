
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
        format:'Y-m-d'
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

                    "carDescription": document.offerRequest.carDescription.value,
                    "coordinateFrom": {
                        "latitude": document.offerRequest.latFrom.value,
                        "longitude": document.offerRequest.longFrom.value
                    },
                    "date": {
                        "earliestDeparture": document.offerRequest.datepicker.value + "T" + document.offerRequest.time1picker.value,
                        "latestDeparture": document.offerRequest.datepicker.value + "T" + document.offerRequest.time2picker.value
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

                    "radius": document.offerRequest.us2radius,
                    "coordinateFrom": {
                        "latitude": document.offerRequest.latFrom.value,
                        "longitude": document.offerRequest.longFrom.value
                    },
                    "date": {
                        "earliestDeparture": document.offerRequest.datepicker.value + "T" + document.offerRequest.time1picker.value,
                        "latestDeparture": document.offerRequest.datepicker.value + "T" + document.offerRequest.time2picker.value
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