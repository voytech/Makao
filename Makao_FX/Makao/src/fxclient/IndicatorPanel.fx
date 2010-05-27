package fxclient;
import shared.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * @author Wojtek
 */
public class IndicatorPanel extends PanelBase
{
    public-read var statusLamp=NeonLight 
    {
        x : bind x+100;
        y : bind y+14;
    }
    var cimage:Image;
    public-read var pendingRequest = ImageView
    {
        x : bind x+10;
        y : bind y+28;
        image : bind cimage;
    }
    postinit
    {
        background.opacity = 0.4;
        width = 200;
        height = 100;
        statusLamp.play();
    }
    public function clearPendingRequests()
    {
        cimage = null;//Image{url:"{__DIR__}images/2or3.png"}
    }
    public function setSourceRequest(request:Request)
    {
        if (request.getID() == Request.REQUEST_CARD_NAMES)
        {
             cimage = Image{url:"{__DIR__}images/2or3.png";}
             var cName:Card.Name[] = (request.getArg() as Card.Name[]);
             for (c in cName)
             {
                 if (c==Card.Name.TWO) cimage = Image{url:"{__DIR__}images/2or3.png";}
             }                          
        }
        else
        if (request.getID() == Request.REQUEST_CARD_NAME)
        {
              var cName = request.getArg();  
              if (cName == Card.Name.FOUR)
              {
                 cimage = Image{url:"{__DIR__}images/4.png";}
              }   
              if (cName == Card.Name.FIVE)
              {
                  cimage = Image{url:"{__DIR__}images/five.png";}
              }
              if (cName == Card.Name.SIX)
              {
                  cimage = Image{url:"{__DIR__}images/six.png";}               
              }
              if (cName == Card.Name.SEVEN)
              {
                 cimage = Image{url:"{__DIR__}images/seven.png";} 
              }
              if (cName == Card.Name.EIGHT)
              {
                 cimage = Image{url:"{__DIR__}images/eight.png";}
              }
              if (cName == Card.Name.NINE)
              {
                 cimage = Image{url:"{__DIR__}images/nine.png";}
              }
              if (cName == Card.Name.TEN)
              {
                 cimage = Image{url:"{__DIR__}images/ten.png";}
              }      
        }
        else 
        if (request.getID() == Request.REQUEST_CARD_SUIT)
        {
            var cSuit = request.getArg();     
            if (cSuit == Card.Suit.CLUB)
            {
                cimage = Image{url:"{__DIR__}images/club.png";}
            }
            if (cSuit == Card.Suit.DIAMOND)
            {
                cimage = Image{url:"{__DIR__}images/diamond.png";}               
            }
            if (cSuit == Card.Suit.HEART)
            {
                cimage = Image{url:"{__DIR__}images/heart.png";} 
            }
            if (cSuit == Card.Suit.SPADE)
            {
                cimage = Image{url:"{__DIR__}images/spade.png";}
            }   
        }    
    }
    public override function getContent():ImageView[]
    {
       return [background,pendingRequest,statusLamp];   
    } 
    
}