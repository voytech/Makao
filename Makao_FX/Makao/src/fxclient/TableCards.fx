package fxclient;
import java.util.Random;
//import java.awt.Rectangle;
import shared.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * @author Zosia i S³awek
 */
public class TableCards extends CardViewList
{
    public var inputArea : Rectangle;
    public var tableArea: Rectangle;
    postinit
    {   
        tableArea = Rectangle {
            x: bind x-50,
        	y: bind y-50,
            width: 200,
        	height: 200,
            fill: Color.BLACK
            opacity: 0.1
        } 
    }
    
    override function onMoved()
    {
        //for (element in content)
        //{
        //    element.x = x ;
        //    element.y = y ;
        //}
        onContentChanged(content);
    }
    override function onContentChanged(cv : CardView[])
    {
     	for (element in cv)
     	{
     	    var random : Random = new Random();
     	    var lx  = random.nextInt(50);
     	    var ly = random.nextInt(50);
     	    element.x = x + lx;
     	    element.y = y + ly;
     	   
     	    element.rotate = random.nextInt(360)

     	}   
    }
    public function getTableStack() : CardStack
    {
        var stack: CardStack = new CardStack();
        for (elem in content) 
           stack.push(elem.card);
        return stack;
    }
}