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

         stompClient.subscribe("/game/Turn", function (turn) {
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

function takeTurn(message){
    console.log("turn message received");
    if(message.id==id){
        if(!message.turn){
            updateHand(message.cards);
        }else{
           updateHand(message.cards);
            if(message.type==1){$("#nextTurn").show();}
            if(message.type==2){
                $("#yes").show();
                $("#no").show();
            }
        }
    }
}

function updateHand(hand){
    $("#hand").empty();
    Object.keys(hand).forEach(key => {
      let value = hand[key];
      var img = $("<img>");
      img.attr("src","/images/"+value+".png");
      img.addClass("pc");
      img.addClass("card");
      img.attr("id",key);
      img.on("click",function(){
         updatePlayArea(img);
      });
      $("#hand").append(img);
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
    console.log("in function replies:message received: "+message);
    if(id==1){
        if(message=="Not Enough Players!"){
            alert(message);
        }
    }
    if(message=="Game Started!"){
        if(id==1){
            $("#start").hide();
        }
        alert(message);
    }
}


function finishTurn(){
    if($(".playArea").html()==""){
        alert("Play a Card!");
    }else{
        console.log("sever sent: "+$(".pa").attr("src").slice(8,-4));
        stompClient.send("/app/playedCard",{},id+$(".pa").attr("src").slice(8,-4));
        //hope for no race condition may need to make send callback to next line
        $(".playArea").html()="";
        $("#nextTurn").hide();
    }
}

$(function(){
    $( "#connect" ).click(function() { connect(); });
    $(".pc").click(function() { updatePlayArea($(this)); });
    $("#start").click(function(){start(); });
    $("#nextTurn").click(function(){finishTurn();});
    });

