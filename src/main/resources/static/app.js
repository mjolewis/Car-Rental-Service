var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#reservationConfirmationTable").show();
    }
    else {
        $("#reservationConfirmationTable").hide();
    }
    $("#confirmation").html("");
}

/**
 * Establish a websocket connection and register subscribers.
 */
function connect() {
    var socket = new SockJS('/reservationsystem');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        // Register the reservation request
        stompClient.subscribe('/reservation/request', function (response) {

            window.reservationResponse = JSON.parse(response.body);

            if (window.reservationResponse.available) {
                confirmReservation();
            } else {
                carWasNotFound();
            }
        });

        // register the reservation confirmation
        stompClient.subscribe('/reservation/confirmation', function (confirmationNumber) {
            displayReservationNumber(JSON.parse(confirmationNumber.body).confirmationNumber);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

/**
 * Send the customer information to the controller to check if a car is available.
 */
function sendReservationRequest() {
    var location = document.getElementById("location").value;
    var carType = document.getElementById("carType").value;
    var reservationStartDateAndTime = document.getElementById("reservationStartDateAndTime").value;
    var reservationEndDateAndTime = document.getElementById("reservationEndDateAndTime").value;
    var firstName = document.getElementById("firstName").value;
    var lastName = document.getElementById("lastName").value;
    var emailAddress = document.getElementById("emailAddress").value;
    var creditCardNumber = document.getElementById("creditCardNumber").value;

    stompClient.send("/app/request", {}, JSON.stringify(
        {'location': location,
                'carType' : carType,
                'reservationStartDateAndTime' : reservationStartDateAndTime,
                'reservationEndDateAndTime' : reservationEndDateAndTime,
                'firstName' : firstName,
                'lastName' : lastName,
                'emailAddress' : emailAddress,
                'creditCardNumber' : creditCardNumber},));
}

function confirmReservation() {
    stompClient.send("/app/confirmation", {}, JSON.stringify({'confirmation': $("").val()}));
}

function displayReservationNumber(confirmationNumber) {
    $("#confirmation").append("<tr><td>" + "Your confirmation number is: " + confirmationNumber + "</td></tr>");
}

function carWasNotFound() {
    $("#confirmation").append("<tr><td>" + 'There are no cars available with your requirements. ' +
        'Try a different location or time.' + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#sendReservationRequest" ).click(function() { sendReservationRequest(); });
});