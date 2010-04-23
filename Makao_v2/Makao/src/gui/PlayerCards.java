package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

import client.ImagesBuffer;
import client.Performer;

import shared.Card;

public class PlayerCards extends CardNodeContainer implements MouseListener , MouseMotionListener
{
	 private int lastCardPointer;
	 private CardNode temporaryNode = null;
	 PlayerCards()
	 {
	    this.setLayout(null);
	    this.setOpaque(false);
	    //this.addMouseMotionListener(this);
	 }
	 public void pushCardNode(CardNode node)
	 {
		 super.pushCardNode(node);		 
         reorder(); 
		 this.add(node);
		 node.addMouseListener(this);
		 node.addMouseMotionListener(this);
		 this.validate();
	 }
	 private void reorder()
	 {
		 int x = this.getWidth()-this.cardWidth;
		 for (CardNode node : nodes)
		 {
			 if (node.isSelected())
			 {
				 node.setLocation(new Point(x,10));				 
			 }
			 else node.setLocation(new Point(x,30));
			 node.setSize(new Dimension(cardWidth,cardHeight));   
			 x-=cardOffset; 		  
		 }
	 }
	 private CardNode[] getSelectedNodes()
	 {
		 ArrayList<CardNode> nodess = new ArrayList<CardNode>();
		 for (CardNode node : nodes)
		 {
			 if (node.isSelected())
			 {
				nodess.add(node); 				 
			 }		  		  
		 } 
		 return nodess.toArray(new CardNode[0]);
	 }
	 public ArrayList<Card> getSelectedCards()
	 {
		 ArrayList<Card> cs= new ArrayList<Card>();
		 for (CardNode node : nodes)
		 {
			 if (node.isSelected()) 
			 {
				Card card = (Card)node.getNestedObject();
				cs.add(card);
			 }			 
		 }
		 return cs;
	 }
	 public void paintComponent(Graphics g)
	 { 		 
		 super.paintComponent(g);
		 Graphics2D g2 =(Graphics2D)g;
		 g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	 }
	@Override
	public void mouseClicked(MouseEvent arg0) {
		CardNode node = (CardNode)arg0.getSource();
		if (!node.isSelected())
		{
			Performer performer = new Performer(null);
			ArrayList<Card> c = getSelectedCards();
			c.add((Card)node.getNestedObject());
			performer.setSelection(c.toArray(new Card[0]));
			if (performer.testSelection())
			{
				node.setLocation(node.getX(), node.getY()-20);
				node.setSelected(true);
			}
		}
		else 
		{
			node.setLocation(node.getX(), node.getY()+20);
			node.setSelected(false);
		}
		this.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		temporaryNode = (CardNode)arg0.getSource();
		//temporaryNode.setLocation(arg0.getPoint());
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		reorder();
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		//temporaryNode = getSelectedNodes()[0];
		if (temporaryNode!=null)
		{
			Point point = arg0.getPoint();
			
			temporaryNode.setLocation(point);
			
			this.repaint();
			System.out.println("2");
		}
		System.out.println("1");
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {

		
	}
}