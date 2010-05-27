package fxclient;
import shared.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.paint.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;



/**
 * @author Wojtek
 */
public class LobbyScene extends Scene
{
     public var messenger:MessengerFX;
     public var player:Player;
     public var selector:SceneSelector;
     var players:Text[];
     var ok = ReadyButton
     {
        x: bind width/2 - 70;
        y: bind height - 100;
     }
     public override var content = bind  
     [
       	ImageView {
      	          image : Image{url: "{__DIR__}images/intro.png"};
       	          fitHeight : bind height;
       	          fitWidth  : bind width;
       	       	 } 
       	ImageView {
       	          image : Image{url: "{__DIR__}images/tittle3.png"};
       	          x : bind width/2 - Image{url: "{__DIR__}images/tittle3.png"}.width/2;
       	          y : bind height/5;
       	          scaleX : 0.7;
       	          scaleY : 0.7;
       	      	 } 
       	players,  
       	ok
     ];
     postinit
     {
          ok.prepare();
          ok.onMouseClicked = function(e:MouseEvent):Void
          {
             ok.defaultOnClick();
             ready();                          
          } 
          ok.play();        
     }
     function ready()
     {    
          messenger.sendRequest(new Request(Request.REQUEST_READY));
     }
     public function updatePlayersInfo(infos:PlayerInfo[])
     {
        var lx = width/2 - 130;
        var ly = height/2 - height/7;
        players = [];
       	var counter = 0;
       	for (info in infos)
       	{
       	    var ready  = "not ready";
       	    if (info.isReady()) {
       	        ready = "ready";
       	        counter ++;
       	    }
       	    insert Text {
       	        font: Font {
       	            size: 30
       	        },
       	        fill : Color.BLACK,
       	        x: bind lx,
       	        y: ly,
       	        content: "{info.getNickName()} is {ready}"
       	    } into players;
       	    ly += 30; 
       	}   
       	if (counter == infos.size()) selector.nextScene();
     }
}