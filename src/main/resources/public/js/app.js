var gameModel;
var scan_results;
var scan_counter = 0;

$( document ).ready(function() {
  // Handler for .ready() called.
  $.getJSON("model", function( json ) {
  gameModel = json;
    console.log( "JSON Data: " + json );
   });
});

function cancelShips() {
    div = document.getElementById('place-ships');
    div.style.display = "none";
    document.getElementById("scan").disabled = false;
    document.getElementById("fire").disabled = false;
}
function scan() {
    if (scan_counter < 3){
    console.log($( "#rowFire" ).val());
    console.log($( "#colFire" ).val());
//var menuId = $( "ul.nav" ).first().attr( "id" );
    var request = $.ajax({
        url: "/scan/"+$( "#rowScan" ).val()+"/"+$( "#colScan" ).val(),
        method: "post",
        data: JSON.stringify(gameModel),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
   });


   request.done(function( currModel ) {
        if (currModel.error_message != null){
               alert(currModel.error_message)
        }else{
            displayGameState(currModel);
            gameModel = currModel;
            scan_counter++;}
     //test purpose
     //alert("this works")

   });

   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: Scan out of bound" /*+ textStatus */ );
   });
    }
    else{
        document.getElementById("scan").disabled = true;
        $( document.getElementById('scan')  ).css("background-color", "gray");
    }
//    alert("I'm an error message. I can be styled if you're interested in a small external framework. Check out SweetAlert");
}

function placeShip() {
   console.log($( "#shipSelec" ).val());
   console.log($( "#rowSelec" ).val());
   console.log($( "#colSelec" ).val());
   console.log($("input[name='orientationSelec']:checked").val());

   //var menuId = $( "ul.nav" ).first().attr( "id" );
   var request = $.ajax({
     url: "/placeShip/"+$( "#shipSelec" ).val()+"/"+$( "#rowSelec" ).val()+"/"+$( "#colSelec" ).val()+"/"+$("input[name='orientationSelec']:checked").val(),
     method: "post",
     data: JSON.stringify(gameModel),
     contentType: "application/json; charset=utf-8",
     dataType: "json"
   });

   request.done(function( currModel ) {
     displayGameState(currModel);
     gameModel = currModel;

   });

   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: " + textStatus  );
   });
}


function fire(){
 console.log($( "#rowFire" ).val());
 console.log($( "#colFire" ).val());
//var menuId = $( "ul.nav" ).first().attr( "id" );
   var request = $.ajax({
     url: "/fire/"+$( "#rowFire" ).val()+"/"+$( "#colFire" ).val(),
     method: "post",
     data: JSON.stringify(gameModel),
     contentType: "application/json; charset=utf-8",
     dataType: "json"
   });

   request.done(function( currModel ) {

        if (currModel.error_message != null){
            alert(currModel.error_message)
        }else if(currModel.playerHitpoints == 14){
            displayGameState(currModel);
            gameModel = currModel;
            alert(currModel.AI_win);
        }else if(currModel.computerHitpoints == 14){
            displayGameState(currModel);
            gameModel = currModel;
            alert(currModel.Player_win)
        }else{
            displayGameState(currModel);
            gameModel = currModel;}
   });

   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: Invalid fire point." /*+ textStatus */ );
   });

}

function log(logContents){
    console.log(logContents);
}

function displayGameState(gameModel){
$( '#MyBoard td'  ).css("background-color", "blue");
$( '#TheirBoard td'  ).css("background-color", "blue");

displayShip(gameModel.aircraftCarrier);
displayShip(gameModel.battleship);
displayShip(gameModel.dinghy);
displayShip(gameModel.clipper);
displayShip(gameModel.submarine);


for (var i = 0; i < gameModel.computerMisses.length; i++) {
   $( '#TheirBoard #' + gameModel.computerMisses[i].Across + '_' + gameModel.computerMisses[i].Down ).css("background-color", "green");
}
for (var i = 0; i < gameModel.computerHits.length; i++) {
   $( '#TheirBoard #' + gameModel.computerHits[i].Across + '_' + gameModel.computerHits[i].Down ).css("background-color", "red");
}

for (var i = 0; i < gameModel.playerMisses.length; i++) {
   $( '#MyBoard #' + gameModel.playerMisses[i].Across + '_' + gameModel.playerMisses[i].Down ).css("background-color", "green");
}
for (var i = 0; i < gameModel.playerHits.length; i++) {
   $( '#MyBoard #' + gameModel.playerHits[i].Across + '_' + gameModel.playerHits[i].Down ).css("background-color", "red");
}



}



function displayShip(ship){
 startCoordAcross = ship.start.Across;
 startCoordDown = ship.start.Down;
 endCoordAcross = ship.end.Across;
 endCoordDown = ship.end.Down;
// console.log(startCoordAcross);
 if(startCoordAcross > 0){
    if(startCoordAcross == endCoordAcross){
        for (i = startCoordDown; i <= endCoordDown; i++) {
            $( '#MyBoard #'+startCoordAcross+'_'+i  ).css("background-color", "yellow");
        }
    } else {
        for (i = startCoordAcross; i <= endCoordAcross; i++) {
            $( '#MyBoard #'+i+'_'+startCoordDown  ).css("background-color", "yellow");
        }
    }
 }
}

