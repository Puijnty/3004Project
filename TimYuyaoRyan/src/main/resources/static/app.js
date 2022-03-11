var stompClient = null;
var id;
var handSize=0;
function connect() {
    var getId=new XMLHttpRequest()
    getId.onload = function(){
    id=Number(getId.responseText);
    console.log(id);
    }
    getId.open("GET", "id");
    getId.send();
     var socket = new SockJS('/knights');
     stompClient = Stomp.over(socket);

     stompClient.connect({}, function (frame) {
         updateConnect();
         stompClient.send("/app/join",{},id);
         console.log('Connected: ' + frame);

         stompClient.subscribe('/game/Turn', function (turn) {
            takeTurn(JSON.parse(turn.body));
            });
        stompClient.subscribe('/game/Reply', function (message) {
                        replies(message.body);
                    });
        stompClient.subscribe('/game/Discard', function (message) {
                                updateDiscard(message.body);
                            });
     });
 }


function updateConnect(){
    $("#connect").hide();
    if(id==1){
        $("#start").show();
    }
    if(id>4) {
        disconnect();
    }

}

function setUp(){
    $("#start").hide();
    $("#nextTurn").hide();
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function updatePlayArea(card){
    if($(".playArea").html()==""){
        $(".playArea").append(card.removeClass("pc").addClass("pa"));
    }else{
        $(".hand").append($(".pa").removeClass("pa").addClass("pc"));
        $(".playArea").append(card.removeClass("pc").addClass("pa"));
    }
}
function start(){
    stompClient.send("/app/start",{},"start!");
}
function replies(message){
    if(id==1){
        if(message=="Not Enough Players!"){
            alert(message);
        }
    }else{
        if(message=="Game Started!"){
            alert(message);
        }
    }

}

$(function(){
    $( "#connect" ).click(function() { connect(); });
    $(".pc").click(function() { updatePlayArea($(this)); });
     $("#start").click(function(){start(); })
    });

