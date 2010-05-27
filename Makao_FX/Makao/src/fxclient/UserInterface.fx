package fxclient;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.Node;
import shared.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;




/**
 * @author Zosia i S�awek
 */public class UserInterface 
{
	public var x:Integer;
	public var y:Integer;
	public var messenger:MessengerFX;
	var reqPanelSwitch = false;
	public-read var suitRequestPanel = SuitRequestPanel
	{
	    x: bind x+130;
	    y: bind y+150;
	}
	public-read var nameRequestPanel = NameRequestPanel
		{
		    x: bind x+130;
		    y: bind y+150;
		}
	public-read var indicators = IndicatorPanel
	{
		    x: bind x+90;
		    y: bind y;
    }
	public-read var takeButton = TakeButton
	{
	    x : bind x-50;
	    y : bind y-10; 
	}
    public-read var makaoButton = MakaoButton
	{
	    x : bind x-200;
	    y : bind y-10; 
	}
    public-read var packOfCards = ImageView
    {
        image : Image{url: "{__DIR__}images/pack.png"};
        x : bind x-40;
        y : bind y; 
    }

	postinit{
	    takeButton.prepare();
	    takeButton.onMouseClicked = function(e:MouseEvent):Void
	    {
	        takeButton.defaultOnClick();
	        take();
	    }
	    makaoButton.prepare();
	    makaoButton.visible = false;
	    takeButton.visible = false;
	    packOfCards.visible = false;
	    suitRequestPanel.hide();
	    nameRequestPanel.hide();
	}
	function take()
	{
	    messenger.sendRequest(new Request(Request.REQUEST_TAKE));
	}
	public function raisedRequest():Request
	{
	    var req;
	    if (reqPanelSwitch)	req = suitRequestPanel.implicateRequest()  
	    else  req = nameRequestPanel.implicateRequest();
	 	return req;   
	}
	public function showSuitRequestPanel(show:Boolean)
	{	    
	    if (show) 
	    {
	    	suitRequestPanel = SuitRequestPanel
	    	{
	    	    x: bind x+140;
	    	    y: bind y+120;
	    	}	
	    	reqPanelSwitch = true;    	 		
	    }
	    else	suitRequestPanel.hide();	
	}

	public function showNameRequestPanel(show:Boolean)
	{
	    if (show) 
	    {
	       	nameRequestPanel = NameRequestPanel
	    	{
	    	    x: bind x+140;
	    	    y: bind y+120;
	    	}	    	 		
	    	reqPanelSwitch = false;
	     }
	     else nameRequestPanel.hide();
	}
}
