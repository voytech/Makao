package gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import client.Performer;

import shared.Card;

public class TableCardContainer extends CardNodeContainer {
	 public TableCardContainer()
	 {
		 this.setLayout(null);
		 this.setOpaque(false);
		 setSize(400,400);
	 }
	 public void pushCardNode(CardNode node)
	 {
		 super.pushCardNode(node);		 
		 throwOnTable(node); 
		 //node.addMouseListener(this);
		// node.addMouseMotionListener(this);
		 //board.repaint();
		 this.repaint();
	 }
     public void pushCardNodes(CardNode[] nodes)
     {
    	 super.pushCardNodes(nodes);
    	 throwOnTable(nodes);
    	 this.repaint();
     }
	 private void throwOnTable(CardNode node)
	 {		 
		 Random r = new Random();
		 node.setLocation(new Point((this.getWidth()/2)-(cardWidth/2)+r.nextInt(40),(this.getHeight()/2)-(cardHeight/2)+r.nextInt(40)));	
		 node.setSize(new Dimension(cardWidth,cardHeight));		 
	 }
	 private void throwOnTable(CardNode[] nodes)
	 {		 
		 Random r = new Random();
		 for (CardNode node : nodes)
		 {
		    node.setLocation(new Point((this.getWidth()/2)-(cardWidth/2)+r.nextInt(40),(this.getHeight()/2)-(cardHeight/2)+r.nextInt(40)));	
		    node.setSize(new Dimension(cardWidth,cardHeight));
		 }
	 }


}
