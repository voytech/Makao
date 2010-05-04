package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Random;

public class TableCardContainer extends CardNodeContainer {
	 class RandomedCoordinates
	 {
		 private int x=0,y=0;
		 RandomedCoordinates(int x, int y)
		 {
			this.x = x; this.y=y; 
		 }
		 int getX(){return x;}
		 int getY(){return y;}	 
	 }
	 private Hashtable<Component,RandomedCoordinates> coordsBuffer = new Hashtable<Component,RandomedCoordinates>();
	 public TableCardContainer()
	 {
		 this.setLayout(null);
		 this.setOpaque(false);
	 }
	 public void pushCardNode(CardNode node)
	 {
		 super.pushCardNode(node);		 
		 throwOnTable(node); 
		// redraw();
		 //node.addMouseListener(this);
		// node.addMouseMotionListener(this);
		 //board.repaint();
		 this.repaint();
	 }
     public void pushCardNodes(CardNode[] nodes)
     {
    	 super.pushCardNodes(nodes);
    	 throwOnTable(nodes);
    	// redraw();
    	 this.repaint();
     }
     private void redraw()
     {
    	 for (int i=0; i<this.getComponentCount(); i++)
    	 {
    		 Component comp = this.getComponent(i);
    		 if (comp instanceof CardNode)
    		 {
    			 RandomedCoordinates coords = coordsBuffer.get(comp);
    			 comp.setLocation(new Point((this.getWidth()/2)-(cardWidth/2)-coords.getX(),(this.getHeight()/2)-(cardHeight/2)-coords.getY()));	
    			 comp.setSize(new Dimension(cardWidth,cardHeight));
    		 }
    	 }
     }
	 private void throwOnTable(CardNode node)
	 {		 
		 Random r = new Random();
		// coordsBuffer.put(node, new RandomedCoordinates(r.nextInt(40),r.nextInt(40)));
		 node.setLocation(new Point((this.getWidth()/2)-(cardWidth/2)-r.nextInt(40),(this.getHeight()/2)-(cardHeight/2)-r.nextInt(40)));	
		 node.setSize(new Dimension(cardWidth,cardHeight));		 
	 }
	 private void throwOnTable(CardNode[] nodes)
	 {		 
		 Random r = new Random();
		 for (CardNode node : nodes)
		 {
			//coordsBuffer.put(node, new RandomedCoordinates(r.nextInt(40),r.nextInt(40)));			  
		    node.setLocation(new Point((this.getWidth()/2)-(cardWidth/2)-r.nextInt(40),(this.getHeight()/2)-(cardHeight/2)-r.nextInt(40)));	
		    node.setSize(new Dimension(cardWidth,cardHeight));
		 }
	 }


}
