package fxclient;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


/**
 * @author Wojtek
 */
public class OpponentCards
{
    public var x:Integer;
    public var y:Integer;
    public var cardNum : Integer on replace oldValue { refresh(cardNum); }
    public-read var content: ImageView[];  
    function refresh(num:Integer)
    {
        var lx = x;
        for (i in [1..num])
        {
            insert ImageView
            {
               image: Image{url: "{__DIR__}cards/b2fv.gif"};
               x:lx
               y:y    
            } into content;
            lx += 20;
        }   
    }
}