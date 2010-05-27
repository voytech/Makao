package fxclient;
import shared.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.*;
import java.util.ArrayList;




/**
 * @author Zosia i S�awek
 */
public class Player extends RequestListener
{
    public var scene:Scene;
    public var lobby:LobbyScene;
    public var userInterface = UserInterface
    {
        x: bind scene.width-300;
        y: 10;
    } 
    public var playerCards:PlayerCards;// = new PlayerCards();
    public var tableCards = TableCards
    {
        x: bind scene.width/2;
        y: bind scene.height/2;
    }
    public var cardBuffer:CardViewBuffer = new CardViewBuffer(); 
    public var opponentsCards : OpponentCards[];  
    public var output = PlayerOutput {
        								tableCards :  bind tableCards 
   									 }
    public var messenger:MessengerFX on replace oldValue {userInterface.messenger = messenger; output.messenger = messenger; }
    
    postinit
    {
        playerCards = PlayerCards
        {
            output : bind  output;
            x : bind (scene.width/2) ;
            y : bind (scene.height-150);
            ui : userInterface;
        }
    }
    override function requestReceived(request:Request) 
    {
        if (request!=null)
        {
            if (request.getID() == Request.REQUEST_LOBBY_INFO)
            {
                var cont:ArrayList = request.getArg() as ArrayList;  
             	var seq = for (i in cont) i as PlayerInfo;
             	lobby.updatePlayersInfo(seq);   
            }
            else
    		if (request.getID() == Request.REQUEST_TAKE)
    		{
    			var cards : Card[] = request.getCards();
    			insert cardBuffer.getContentByCards(cards) into playerCards.content;
    		}
    		else
    			if (request.getID() == Request.REQUEST_CARDSTACK_ACTUALIZATION)
    			{
    				var cards : Card[] = request.getCards();
    				for (card in cards) delete cardBuffer.getContentByCard(card) from playerCards.content;
    				insert cardBuffer.getContentByCards(cards) into tableCards.content;    			
    			}
    			else 
    	    		if (request.getID() == Request.REQUEST_ENABLE_PLAYER)
    		    	{
    		    	    output.enabled = true;
    		    	    userInterface.packOfCards.visible = true;
    		    	    userInterface.takeButton.visible = true;
    		    	    userInterface.makaoButton.visible = true;
    		    	    userInterface.indicators.statusLamp.turnOnGreenLight();
    				}
    				else
    					if (request.getID() == Request.REQUEST_DISABLE_PLAYER)
    					{
    						output.enabled = false;
    						output.currentRequest = null;
    						userInterface.takeButton.visible = false;
    						userInterface.makaoButton.visible = false;
    		 				userInterface.indicators.statusLamp.turnOnRedLight();
    		 				//claearing userInterface
    		 				userInterface.showSuitRequestPanel(false);
    		 				userInterface.showNameRequestPanel(false);
    		 				userInterface.indicators.clearPendingRequests();
    					}
    					else
    						if ((request.getID() == Request.REQUEST_CARD_NAME) or (request.getID() == Request.REQUEST_CARD_SUIT) or (request.getID() == Request.REQUEST_CARD_NAMES))							
    						{
    						    //output.outgoingRequest = null;
    							output.currentRequest = request;
    							userInterface.indicators.setSourceRequest(request);
    						}
    						else 
    							if (request.getID() == Request.REQUEST_STRING_MESSAGE)
    							{
    						    	var messages:String = request.getMessage();
    						        var message = messages.split("=");
    						        if (message.length == 2)
    						        {
    						            if (message[0].equals("opponents_cards"))
    						            {
    						                var opp = message[1].split(";");
    						                opponentsCards = [];
    						                for (opponent in opp)
    						                {
    						                	if (not opponent.equals("") and opponent.contains(":"))
    											{
    											    var key_val = opponent.split(":");
    											    var opponent_id = Integer.valueOf(key_val[0]);
    											    var cards_num = Integer.valueOf(key_val[1]);    											    
    											    {
    											       insert OpponentCards{ cardNum : cards_num; x: bind scene.x; y: bind scene.y + 100; } into opponentsCards;
    											    }	
    											}
    						                }
    						            }
    						        }
    							}
        }
    }
}