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
                                updateDiscard(JSON.parse(message.body));
                            });
        stompClient.subscribe('/game/score', function (message) {
            updateLeaderBoard(JSON.parse(message.body));
        });
     });
 }

function takeTurn(message){
    if(message.id==id){
        if(!message.turn){
            updateHand(message.cards);
        }else{
           alert(message.message);
           updateHand(message.cards);
            if(message.type==1){$("#nextTurn").show();}
            if(message.type==2){$(".yeno").show();}
        }
    }
}

function updateDiscard(deck){
console.log(deck);
    $("#pile").empty();
    Object.keys(deck).forEach(key => {
          let value = hand[key];
          var img = $("<img>");
          img.attr("src","/images/"+value+".png");
          img.addClass("card");
          $("#pile").append(img);
          });
}

function updateLeaderBoard(message){
console.log("updating leaderboard");
    $("#first").empty();
    $("#second").empty();
    $("#third").empty();
    $("#fourth").empty();
    Object.keys(message).forEach(key => {
        addToLeaderBoard(key,message[key]);
    });
}
function addToLeaderBoard(pos,playerInfo){
    $("#"+pos).append($("<td></td>").text(playerInfo["player"]));
    $("#"+pos).append($("<td></td>").text(playerInfo["shields"]));
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
    $(".yeno").hide();
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
        stompClient.send("/app/playedCard",{},$(".pa").attr("src").slice(8,-4));
        $(".playArea").empty();
        $("#nextTurn").hide();
    }
}

function yesTurn(){
    stompClient.send("/app/continue",{},true);
    $(".yeno").hide();
}

function noTurn(){
    stompClient.send("/app/continue",{},false);
        $(".yeno").hide();
}

$(function(){
    $( "#connect" ).click(function() { connect(); });
    $(".pc").click(function() { updatePlayArea($(this)); });
    $("#start").click(function(){start(); });
    $("#nextTurn").click(function(){finishTurn();});
    $("#yes").click(function(){yesTurn();});
    $("#no").click(function(){noTurn();});
    });

