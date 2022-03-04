var stompClient = null;

function connect() {
    var socket = new SockJS('/messenger');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/test/Reply',
        function(message) {
              // incoming message from subscription title
              //function to deal with said message we know the response
              displayMessage(JSON.parse(message.body).id);
              message.ack();

            },{ack: 'client'});
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function displayMessage(text){
    $("#messages").append("<br>Sever reply: "+text);
     console.log("appending text: "+text);
}

function sendMessage(){
    stompClient.send("/app/test", {},JSON.stringify({'id':'Hello'}));
    console.log("message sent");
}


$(function(){
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#sender" ).click(function() { sendMessage(); });
    });
