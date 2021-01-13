
/**********************************************************************************************************************
 * The user interface allows a customer to make a reservation. Implemented with BotUI and full duplex communication
 * with WebSockets.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/

var socket = new SockJS('/reservationsystem');
var stompClient = Stomp.over(socket);
var botui = new BotUI('botui-app');
var location;
var carType;
var reservationStartDateAndTime;
var reservationEndDateAndTime;
var confirmation;
var searchAgain;

/**
 * Establish a websocket connection and register subscribers.
 */
stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);

    // Register the reservation request
    stompClient.subscribe('/reservation/request/response', function (response) {
        window.reservationResponse = JSON.parse(response.body);

        if (window.reservationResponse.isAvailable) {
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

/**
 * Send the location to endpoint
 */
function sendLocation(location) {
    stompClient.send("/app/location", {}, JSON.stringify({'location': location}));
}

/**
 * Send the carType to endpoint.
 */
function sendCarType(carType) {
    stompClient.send("/app/carType", {}, JSON.stringify({"carType": carType}));
}

/**
 * Send the reservation start date and time.
 */
function sendReservationStartDateAndTime(reservationStartDateAndTime) {
    stompClient.send("/app/start", {}, JSON.stringify({"reservationStartDateAndTime": reservationStartDateAndTime}))
}

/**
 * Send the reservation end date and time.
 */
function sendReservationEndDateAndTime(reservationEndDateAndTime) {
    stompClient.send("/app/end", {}, JSON.stringify({"reservationEndDateAndTime": reservationEndDateAndTime}))
}

/**
 * Search for a car that matches the customers request.
 */
function requestReservation() {
    stompClient.send("/app/request", {});
}

/**
 * Customer has confirmed the reservation, so update the database with reservation details.
 */
function confirmReservation() {
    stompClient.send("/app/confirmation", {});
}

//*********************************************************************************************************************
// Start the BotUI Conversation
//*********************************************************************************************************************

/**
 * A conversation with the customer managed by BotUI
 */
botui.message.add({
    delay: 500,
    content: 'Welcome to our reservation system.'
}).then(() => {
    return botui.message.add({
        delay: 2000,
        loading: true,
        content: "I'll ask you for details about your reservation and let you know if we have anything that matches " +
            "your requirements.",
    })
}).then(() => {
    getLocation();
});

/**
 * Let the customer select one of our rental locations
 */
function getLocation() {
    botui.action.select({
        action: {
            delay: 1000,
            placeholder: "Select a location",
            label: 'location',
            options: [
                {value: "Boston", text: "Boston"},
                {value: "Cambridge", text: "Cambridge"},
                {value: "Burlington", text: "Burlington"},
            ],
            button: {
                icon: "check",
                label: "ok"
            }
        }
    }).then(function (res) {
        location = res.value;
        sendLocation(location);
        getCarType();
    })
}

/**
 * Let the customer select the type of car he or she wants to rent.
 */
function getCarType() {
    botui.action.select({
        action: {
            delay: 1000,
            placeholder: "Select a type of car",
            label: 'Type of car',
            options: [
                {value: "Sedan", text: "Sedan"},
                {value: "SUV", text: "SUV"},
                {value: "Van", text: "Van"},
            ],
            button: {
                icon: "check",
                label: "ok"
            }
        }
    }).then(function (res) {
        carType = res.value;
        sendCarType(carType);
        getReservationStartDateAndTime();
    })
}

/**
 * Let the customer select the reservation start date and time.
 */
function getReservationStartDateAndTime() {
    botui.action.text({
        action: {
            delay: 1000,
            placeholder: "Enter a reservation start date and time. Please use the format YYYY-MM-DD HH:MM:SS"
        }
    }).then(function (res) {
        reservationStartDateAndTime = res.value;

        //REGEX to match YYYY-MM-DD HH:MM:SS
        if (reservationStartDateAndTime.matches("[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]")) {
            var currentDateAndTime = getDateTime();
            if (currentDateAndTime < reservationStartDateAndTime) {
                botui.message.add({
                    delay: 1000,
                    content: "You can't make a reservation in the past. Please try again."
                })
                getReservationStartDateAndTime();
            }

            // The customer has entered a valid reservation request.
            sendReservationStartDateAndTime(reservationStartDateAndTime);
            getReservationEndDateAndTime()
        } else {
            botui.message.add({
                delay: 1000,
                content: "Invalid date type. Please use the correct format: YYYY-MM-DD HH:MM:SS"
            });
            getReservationStartDateAndTime();
        }
    })
}

/**
 * Helper method to ensure that the reservation request is in the future. A customer can't make a reservation in the
 * past.
 */
function getDateTime() {
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth()+1;
    var day = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();

    if(month.toString().length === 1) {
        month = '0'+month;
    }

    if(day.toString().length === 1) {
        day = '0'+day;
    }

    if(hour.toString().length === 1) {
        hour = '0'+hour;
    }

    if(minute.toString().length === 1) {
        minute = '0'+minute;
    }

    if(second.toString().length === 1) {
        second = '0'+second;
    }

    return year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
}

/**
 * Let the customer select the reservation end date and time.
 */
function getReservationEndDateAndTime() {
    botui.action.text({
        action: {
            delay: 1000,
            placeholder: "Enter a reservation end date and time. Please use the format YYYY-MM-DD HH:MM:SS"
        }
    }).then(function (res) {
        reservationEndDateAndTime = res.value;

        //REGEX to match YYYY-MM-DD HH:MM:SS
        if (reservationEndDateAndTime.matches("[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]")) {
            sendReservationEndDateAndTime(reservationEndDateAndTime);
            checkIfCarIsAvailable();
        } else {
            botui.message.add({
                delay: 1000,
                content: "Invalid date type. Please use the correct format: YYYY-MM-DD HH:MM:SS"
            });
            getReservationEndDateAndTime();
        }
    })
}

function checkIfCarIsAvailable() {
    requestReservation();

    botui.message.add({
        delay: 1000,
        loading: true,
        content: "Thanks! I am checking to see if we have a car that matches your request..."
    })
}

/**
 * We can offer the customer a reservation, so confirm that he or she actually wants it.
 */
function carWasFound() {
    botui.action.text({
        action: {
            delay: 1000,
            content: "Great news! We have a car available for you."
        }
    }).then(() => {
        return botui.action.select({
            action: {
                delay: 1000,
                placeholder: "Confirm reservation?",
                label: "confirm",
                options: [
                    {value: "yes", text: "yes"},
                    {value: "no", text: "no"}
                ],
                button: {
                    icon: "check",
                    label: "ok"
                }
            }
        })
    }).then(function(res) {
        confirmation = res.value;

        if (confirmation) {
            confirmReservation();
        } else {
            getLocation();
        }
    })
}

/**
 * If we don't have a car that matches the reservation request, then ask you customer to search for something different
 */
function carWasNotFound() {
    botui.action.text({
        action: {
            delay: 1000,
            content: "Sorry, but we don't have anything that matches your criteria. We have plenty of cars though, so " +
                "take a shot at searching for something else."
        }
    }).then(() => {
        return botui.action.select({
            action: {
                delay: 1000,
                placeholder: "Do you want to search again?",
                label: "search again",
                options: [
                    {value: "yes", text: "yes"},
                    {value: "no", text: "no"}
                ],
                button: {
                    icon: "check",
                    label: "ok"
                }
            }
        })
    }).then(function(res) {
        searchAgain = res.value;

        if (searchAgain) {
            getLocation();
        } else {
            botui.action.text({
                action: {
                    delay: 1000,
                    content: "Sorry to see you go. Stop by again and we'll be sure to help you out."
                }
            })
        }
    })
}

/**
 * If we don't have a car that matches the reservation request, then ask you customer to search for something different
 */
function reservationHasBeenMade() {
    botui.action.text({
        action: {
            delay: 1000,
            content: "Your reservation has been confirmed."
        }
    })
}