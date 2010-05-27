package fxclient;
import javafx.scene.input.MouseEvent;
import java.awt.Rectangle;
import java.awt.Point;
import shared.*;

import  javafx.scene.paint.Color;



/**
 * 

 * @author Zosia i S³awek
 */public class PlayerCards extends CardViewList
{
    public var xCardOffset : Integer = 20;
    public var output : PlayerOutput;
    public var ui : UserInterface;
    var dragged = false;
  
	public function sort() : Void
	{
	   var lx =  x;
	   var ly =  y;
	   for (view in content)
	   {
	       view.x = lx;
	       view.y = ly;
	       lx+=xCardOffset;
	       if (view.selected == true) {view.y = ly - 20;}

	   } 
	}
	function updatePositions(x:Integer, y:Integer)
	{
	    var lx=x;	    	    	    
	    for (card in content)
	    {
	    	if (card.selected)
	    	{
	    	    card.x = lx ;
	        	card.y = y ;
	        	lx+=xCardOffset;
	    	}	    	
	    } 
	}
	function getSelected() : Card[]
	{
	    var selected:Card[]; 
	    for (cardV in content) if (cardV.selected)  insert cardV.card into selected;
	    return selected;
	}
    function getSelectedViews() : CardView[]
	{
	    var selected:CardView[]=null; 
	    for (cardV in content) if (cardV.selected)  insert cardV into selected;
	    return selected;
	}
	override function onMoved()
	{
	    sort();
	}
	override function onContentChanged(cv : CardView[])
	{
	    for (cview in cv)
	    { 
	       cview.blocksMouse = true;
	       cview.onMouseClicked = function( e: MouseEvent ): Void 
	       {     
	          	if (cview.selected == false)  
	          	{
	          		cview.selected = true;
	          		if (cview.card.getName() == Card.Name.ACE) ui.showSuitRequestPanel(true) 
	          		else ui.showSuitRequestPanel(false);
	          	    
	          	    if (cview.card.getName() == Card.Name.JACK) ui.showNameRequestPanel(true) 
	          		else ui.showNameRequestPanel(false);
	          	}
	          	else  
	          	{
	          		cview.selected = false;
	          		ui.showSuitRequestPanel(false);
	          		ui.showNameRequestPanel(false);
	          	}
	          	sort();
	       }
	       cview.onMouseDragged = function( e: MouseEvent ): Void 
	       {
	           updatePositions(e.sceneX,e.sceneY);
	           dragged = true;
	       }
	       cview.onMouseReleased = function( e: MouseEvent ): Void 
	       {
	           var tableArea = output.tableCards.tableArea;
	           output.selection = null;
	           if (tableArea!=null)
	           {
	               if (e.sceneY > y and dragged)
	               {
	               		var index:Integer = 0;
	               		var views = getSelectedViews();
	               		if (views != null)
	               		{
	               			var inLoop = false;
	               			for (card in content) if (card.x < views[0].x) {index++; inLoop = true;}
	               			if (inLoop) index--;
	               			for (card in views) delete card from content;
	               			insert views before content[index];
	               		}	               			               
	               }
	               else 
	               if (new Rectangle(tableArea.x,tableArea.y,tableArea.width,tableArea.height).contains(new Point(e.sceneX,e.sceneY)))
	               {
	                	output.selection = getSelected();
	                	output.outgoingRequest = ui.raisedRequest();
	                	if (output.isValid())
	                	{
	                        output.flush();
	                	}          		
	               }  
	           }
	           cview.opacity = 1;
	           sort();
	           dragged = false;
	       }
	    } 
	    sort();
	}
}
