function showDiv() {
    document.getElementById('carDescription').style.display = "block";
}
function hideDiv() {
    document.getElementById('carDescription').style.display = 'none';
}
var state;
function setMode(_state){
    if (state=="driver"){
        hideDiv();
        $("#rideModeOption1").removeClass("active")
        $("#rideModeOption2").addClass("active")
    }
    else {
        showDiv();
        $("#rideModeOption2").removeClass("active")
        $("#rideModeOption1").addClass("active")
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
