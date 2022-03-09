var stompClient = null;
var host=false;
var id;

function connect() {
     var socket = new SockJS('/knights');
     stompClient = Stomp.over(socket);
     stompClient.connect({}, function (frame) {
         console.log('Connected: ' + frame);
         stompClient.subscribe('/game/playerInfo', function (info) {
             updatePlayerInfo(JSON.parse(info.body));
         });
         stompClient.subscribe('/game/Turn', function (turn) {
            takeTurn(JSON.parse(turn.body));
            });

     });
 }


function updateConnect(connected){
    if(connected){
        $("#connect").hide();
        if(host){
            $("#start").show();
        }
    }
}

function setUp(){
    var getId=new XMLHttpRequest()
    getId.onload = function(){
    id=Number(getId.responseText);
    console.log(id);

    }
    getId.open("GET", "id");
    getId.send();
    $("#start").hide();
    $("#nextTurn").hide();
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

$(function(){
    $( "#connect" ).click(function() { connect(); });
    });

