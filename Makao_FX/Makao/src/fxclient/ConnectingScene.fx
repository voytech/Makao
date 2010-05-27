package fxclient;
import shared.*;
import javafx.stage.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import java.net.Socket;






/**
 * @author Wojtek
 */
public class ConnectingScene extends Scene
{
    public var messengerTask:MessengerTask;
    public var player:Player;
    public var selector:SceneSelector;
    var sboxXloc:Number;
    var serverBox = TextBox 
                    {
       	              text: "127.0.0.1"
       	              columns: 30
       	              selectOnFocus: true
       	              translateX: bind width/2 - 110// bind sboxXloc
       	              translateY:  bind height/2
       	              scaleX : 2.0
       	              scaleY : 3.0
       	              opacity : 0.4
       	            }
    var ok = OkButton
    {
        x: bind width/2 - 70;
        y: bind height/2 + 100;
    }

    postinit
    {
            sboxXloc = width/2-serverBox.width/2;
            ok.prepare();
            ok.play();
            ok.onMouseClicked = function(e:MouseEvent):Void
            {
                ok.defaultOnClick();
                tryConnect();                          
            }
            content = 
                [
                 	ImageView {
                 	          image : Image{url: "{__DIR__}images/intro.png"};
                 	          fitHeight : bind height;
                 	          fitWidth  : bind width;
                 	       	 }   
                 	ImageView {
                 	          image : Image{url: "{__DIR__}images/tittle1.png"};
                 	          x : bind width/2 - Image{url: "{__DIR__}images/tittle1.png"}.width/2;
                 	          y : bind height/5;
                 	          scaleX : 0.7;
                 	          scaleY : 0.7;
                 	       	 } 
                 	serverBox,
                 	ok
                ]     
    }
    function tryConnect()
    {
        messengerTask = MessengerTask 
                        {
                           	socket : new Socket(serverBox.rawText,9090)
                           	listeners: bind [
                           	               player              
                           			        ]  
                        }
        messengerTask.start();
        player.messenger = messengerTask.messenger;
        selector.nextScene();
    }
}