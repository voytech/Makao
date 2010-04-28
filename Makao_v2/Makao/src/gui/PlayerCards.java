package gui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;

import client.ClientApplication;
import client.ImagesBuffer;
import client.Performer;

import shared.Card;
import shared.CardStack;

public class PlayerCards extends CardNodeContainer implements MouseListener , MouseMotionListener
{
	 private TableCardContainer table;
	 private boolean dragging = false;
	 private CardNode temporaryNode = null;
	 private Performer performer = null;
	 PlayerCards(TableCardContainer b,Performer p)
	 {
		this.performer=p;
		b.setLocation(300, 100);
		this.table = b;
	    this.setLayout(null);
	    this.setOpaque(false);
	    this.addMouseMotionListener(this);
	    this.addMouseListener(this);
	    this.add(b);
	    /*dndSource = DragSource.getDefaultDragSource();
	    dndSource.createDefaultDragGestureRecognizer(this, // What component
	              DnDConstants.ACTION_COPY_OR_MOVE, // What drag types?
	              this);
	    dndTarget = new DropTarget(b,b);
	    this.setDropTarget(dndTarget);*/
	    
	 }
	 public void pushCardNode(CardNode node)
	 {
		 super.pushCardNode(node);		 
		 //board.addPlayerCardComponent(node); 
         reorder(); 
		 node.addMouseListener(this);
		// node.addMouseMotionListener(this);		 
	 }
	 public void paintComponent(Graphics g)
	 {
		 super.paintComponent(g);
	 }
	 public void setLocation(int i, int j) 
	 {
		super.setLocation(i,j);
		reorder();
	 } 
	 private int getStackRightX()
	 {
		 int x =0;
		 int width = this.getWidth();
		 x = (width/2)+((this.getComponentCount()*cardOffset)/2);
		 return x;
	 }
	 private int getStackMiddleY()
	 {
		 int y =0;		
		 y = this.getHeight()-this.cardHeight-100;
		 return y;
	 }
	 private void reorder()
	 {
		 //
		 int width = this.getWidth();
		 int x = (width/2)+((this.getComponentCount()*cardOffset)/2);
		 int y = getStackMiddleY()+100;
		 for (int i=0;i<this.getComponentCount();i++)
		 {
			 if (this.getComponent(i) instanceof CardNode)
			 {
				 CardNode node = (CardNode)this.getComponent(i);
				 if (node.isSelected())
				 {
					 node.setLocation(new Point(x,y-20));				 
				 }
				 else node.setLocation(new Point(x,y));
				 node.setSize(new Dimension(cardWidth,cardHeight));   
				 x-=cardOffset;
			 }
			 
		 }
	 }
	 private void insertCardAt(int index,CardNode node)
	 {
		 remove(node);
		 add(node,index);
		 //nodes.add(index, node);
		 //node.setLocation(this.x+(cardOffset*index),y+30);
	 }
	 private CardNode[] getSelectedNodes()
	 {
		 ArrayList<CardNode> nodess = new ArrayList<CardNode>();
		 for (int i=this.getComponentCount()-1;i>=0;i--)
		 {
			 if (this.getComponent(i) instanceof CardNode)
			 {
				 CardNode node = (CardNode)this.getComponent(i);
				 if (node.isSelected())
				 {
					 nodess.add(node); 				 
				 }
			 }
		 } 
		 return nodess.toArray(new CardNode[0]);
	 }
	 private CardNode[] getAndRemoveSelectedNodes()
	 {
		 ArrayList<CardNode> nodess = new ArrayList<CardNode>();
		 for (int i=this.getComponentCount()-1;i>=0;i--)
		 {
			 if (this.getComponent(i) instanceof CardNode)
			 {
				 CardNode node = (CardNode)this.getComponent(i);
				 if (node.isSelected())
				 {
					 nodess.add(node);
					 this.remove(node);
				 }
			 }
		 } 
		 return nodess.toArray(new CardNode[0]);
	 }
	 public ArrayList<Card> getSelectedCards()
	 {
		 ArrayList<Card> cs= new ArrayList<Card>();
		 for (int i=this.getComponentCount()-1;i>=0;i--)
		 {
			 if (this.getComponent(i) instanceof CardNode)
			 {
				 CardNode node = (CardNode)this.getComponent(i);
				 if (node.isSelected()) 
				 {
					 Card card = (Card)node.getNestedObject();
					 cs.add(card);
				 }
			 }
		 }
		 return cs;
	 }
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() instanceof CardNode)
		{
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
		}
		else
		{
		CardNode[] tNodes = getSelectedNodes();
		for (CardNode n : tNodes) n.setSelected(false);
		}
		//board.repaint();
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
		if (arg0.getSource() instanceof CardNode) temporaryNode = (CardNode)arg0.getSource();
		//temporaryNode.setLocation(arg0.getPoint());
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (dragging)
		{
			Point point = arg0.getPoint();
			Rectangle rectangle = new Rectangle(table.getLocation(),table.getSize());
		    if (rectangle.contains(point))
			{
		    	Card[] scards = this.getSelectedCards().toArray(new Card[0]);
				if (scards.length > 0)
				{
					CardStack stack = this.table.getCards();
					performer = new Performer(stack);
					performer.setSelection(scards);
					if(performer.testSelection())
					{
						table.pushCardNodes(getAndRemoveSelectedNodes());
					}
				}
			}
			else
			{
				int xPos = this.getStackRightX() - arg0.getPoint().x;
				int index = 0;
				if (xPos>0)  index = (int)xPos/cardOffset;
				if (index<this.getComponentCount()-1) insertCardAt(index,getSelectedNodes()[0]);
			}	
			dragging = false;
		}
		reorder();
	}
	@Override
	public void mouseDragged(MouseEvent arg0) 
	{
		dragging=true;
			
		CardNode[] tNodes = getSelectedNodes();
		for (int i= 0; i< tNodes.length;i++)
		{
			CardNode node = tNodes[i];
			if (node!=null)
			{
				Point point = arg0.getPoint();			
				point.x =point.x + (i*cardOffset);
				node.setLocation(point);			
			}
		}
	}
	@Override
	public void mouseMoved(MouseEvent arg0) 
	{

		
	}
}
