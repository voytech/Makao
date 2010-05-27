package fxclient;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.net.Socket;
import shared.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;



/**
 * @author voytech
 */
var gameScene: Scene;
var connectScene: ConnectingScene;
var lobbyScene : LobbyScene;
var introduceScene: IntroductionScene;
var activeScene:Scene;

var stage: Stage;
var player = Player{ scene : bind gameScene; lobby : bind lobbyScene; }
//var messengerTask:MessengerTask;
function getOpponentsCards(oc:OpponentCards[]) : ImageView[]
{
    var ivs:ImageView[];
    for (cards in oc)  insert cards.content into ivs;
    return ivs;
}

var selector:SceneSelector = SceneSelector
{
    scenes : bind [connectScene,introduceScene,lobbyScene,gameScene]
}
connectScene = ConnectingScene
{
    width: 1000
    height: 700
    player : player
    //messengerTask : bind messengerTask
    selector: bind selector
}
introduceScene = IntroductionScene
{
    width: 1000
    height: 700
    messenger : bind connectScene.messengerTask.messenger
    selector: bind selector
}
lobbyScene = LobbyScene
{
    width: 1000
    height: 700
    messenger : bind connectScene.messengerTask.messenger
    selector: bind selector
}
selector.current = connectScene;

gameScene = Scene
{
     width: 1000
     height: 700
     content: bind 
     [   
       	 ImageView {
          	     image : Image{url: "{__DIR__}images/table2.jpg"};
          	     fitHeight : bind gameScene.height;
           	     fitWidth  : bind gameScene.width;
       	 }   
       	 player.tableCards.content,        
         player.playerCards.content,
         player.userInterface.takeButton,
         player.userInterface.indicators.getContent(),
         player.userInterface.suitRequestPanel.getContent(),
         player.userInterface.nameRequestPanel.getContent(),
         player.userInterface.makaoButton,
         getOpponentsCards(player.opponentsCards),                            	                                	
     ]
}
stage = Stage {
     title : "Makao card game"
     resizable : true
     scene: bind selector.current;
 }
 player.userInterface.takeButton.play();
