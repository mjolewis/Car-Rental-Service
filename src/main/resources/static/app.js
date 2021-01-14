var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#reservationRequestTable").show();
        $("#reservationConfirmationTable").show();
    }
    else {
        $("#reservationRequestTable").hide();
        $("#reservationConfirmationTable").hide();
    }
    $("#reservationRequest").html("");
    $("#reservationConfirmation").html("");
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
        stompClient.subscribe('/reservation/request/response', function (response) {
            //showGreeting(JSON.parse(greeting.body).content);

            window.reservationResponse = JSON.parse(response.body);

            if (window.reservationResponse.available) {
                carWasFound();
            } else {
                carWasNotFound();
            }
        });

        // register the reservation confirmation
        stompClient.subscribe('/reservation/confirmation/response', function (response) {
            window.reservationConfirmation = JSON.parse(response.body);

            reservationHasBeenMade();
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

function sendLocation() {
    stompClient.send("/app/location", {}, JSON.stringify({'location': $("#location").val()}));
}

function sendCarType() {
    stompClient.send("/app/carType", {}, JSON.stringify({'carType': $("#carType").val()}));
}

function sendReservationStartDateAndTime() {
    stompClient.send("/app/start", {}, JSON.stringify({'reservationStartDateAndTime': $("#reservationStartDateAndTime").val()}));
}

function sendReservationEndDateAndTime() {
    stompClient.send("/app/end", {}, JSON.stringify({'reservationEndDateAndTime': $("#reservationEndDateAndTime").val()}));
}

function sendFirstName() {
    stompClient.send("/app/firstName", {}, JSON.stringify({'firstName': $("#firstName").val()}));
}

function sendLastName() {
    stompClient.send("/app/lastName", {}, JSON.stringify({'lastName': $("#lastName").val()}));
}

function sendEmailAddress() {
    stompClient.send("/app/emailAddress", {}, JSON.stringify({'emailAddress': $("#emailAddress").val()}));
}

function sendCreditCardNumber() {
    stompClient.send("/app/creditCardNumber", {}, JSON.stringify({'creditCardNumber': $("#creditCardNumber").val()}));
}

function sendReservationRequest() {
    stompClient.send("/app/request", {}, JSON.stringify({'request': $("#request").val()}));
}

function carWasFound() {
    $("#reservationRequest").append("<tr><td>" + 'Great news! We have a car for you. Confirm your reservation' + "</td></tr>");
}

function carWasNotFound() {
    $("#reservationConfirmation").append("<tr><td>" + 'There are no cars available with your requirements. ' +
        'Try a different location' + "</td></tr>");
}

function confirmReservation() {
    stompClient.send("/app/confirmation", {}, JSON.stringify({'confirmation': $("#confirmation").val()}));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#sendLocation" ).click(function() { sendLocation(); });
    $( "#sendCarType" ).click(function() { sendCarType(); });
    $( "#sendReservationStartDateAndTime" ).click(function() { sendReservationStartDateAndTime(); });
    $( "#sendReservationEndDateAndTime" ).click(function() { sendReservationEndDateAndTime(); });
    $( "#sendFirstName" ).click(function() { sendFirstName(); });
    $( "#sendLastName" ).click(function() { sendLastName(); });
    $( "#sendEmailAddress" ).click(function() { sendEmailAddress(); });
    $( "#sendCreditCardNumber" ).click(function() { sendCreditCardNumber(); });
    $( "#sendReservationRequest" ).click(function() { sendReservationRequest(); });
    $( "#confirmReservation" ).click(function() { confirmReservation(); });
});

// var substringMatcher = function(strs) {
//     return function findMatches(q, cb) {
//         var matches, substrRegex;
//
//         // an array that will be populated with substring matches
//         matches = [];
//
//         // regex used to determine if a string contains the substring `q`
//         substrRegex = new RegExp(q, 'i');
//
//         // iterate through the pool of strings and for any string that
//         // contains the substring `q`, add it to the `matches` array
//         $.each(strs, function(i, str) {
//             if (substrRegex.test(str)) {
//                 matches.push(str);
//             }
//         });
//
//         cb(matches);
//     };
// };
//
// var location = ['Boston', 'Burlington', 'Cambridge'];
//
// $('#locations').locations({
//     dataSource: location,
//     hint: true,
//     highlight: true,
//     minLength: 1
// });