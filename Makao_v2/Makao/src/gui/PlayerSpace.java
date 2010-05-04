package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.Player;

import shared.Card;
import shared.CardStack;
import shared.CardStackGuard;
import shared.Messenger;
import shared.Packet;
import shared.PacketListener;
import shared.Request;

public class PlayerSpace extends CardNodeContainer implements MouseListener , MouseMotionListener, PacketListener
{
	 private TableCardContainer table;
	 private boolean dragging = false;
	 private CardNode temporaryNode = null;
	 private CardStackGuard guard = null;
	 private boolean playerEnabled = false;
	 private Messenger player = null;
	 private Request incoming = null;
	 PlayerSpace(TableCardContainer b,CardStackGuard p,Messenger player)
	 {
		this.guard=p;		
		this.table = b;
		this.player = player;
		this.player.addPacketListener(this);
		this.setLayout(null);
			    

	    this.setOpaque(false);
	    this.addMouseMotionListener(this);
	    this.addMouseListener(this);
	    this.add(b);
	    
	 }
	 public void pushCardNode(CardNode node)
	 {
		 super.pushCardNode(node);		 
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
	 public void setSize(int i, int j) 
	 {
		super.setSize(i, j);
		table.setSize(i/3,j/3);
		int th = table.getHeight();
		int tw = table.getWidth();	
		table.setLocation(new Point((this.getWidth()/2)-(tw/2),(this.getHeight()/2)-(th/2)));		
		reorder();
	 } 
	 private int getStackRightX()
	 {
		 int x = 0;
		 int width = this.getWidth();
		 x = (width/2)-(((this.getComponentCount()-1)*cardOffset)/2);
		 return x;
	 }
	 private int getStackMiddleY()
	 {
		 int y =0;		
		 y = this.getHeight()-this.cardHeight-30;
		 return y;
	 }
	 private void reorder()
	 {
		 //
		 //int width = this.getWidth();
		 int x = getStackRightX();
		 int y = getStackMiddleY();
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
	 private void removeCards(Card[] cards)
	 {
		 for (int i=this.getComponentCount()-1;i>=0;i--)
		 {
			 if (this.getComponent(i) instanceof CardNode)
			 {
				 CardNode node = (CardNode)this.getComponent(i);
				 Card card = (Card)node.getNestedObject();
				 for (Card pcard : cards)
				 {
					 if (card.getName().equals(pcard.getName()) && card.getSuit().equals(pcard.getSuit()))
					 {
						 this.remove(node);
					 }
				 }
			 }
		 } 		 
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
				CardStackGuard performer = new CardStackGuard(null);
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
					guard = new  CardStackGuard(stack);
					guard.setSelection(scards);
					guard.setIncomingRequest(incoming);
					if(guard.testSelection())
					{
						if (playerEnabled)
						{
							Packet packet = new Packet();
							packet.setRequest(new Request(Request.REQUEST_PUSH,scards));
							try {
							   player.sendPakcet(packet);
							} catch (IOException e) {
								//JOptionPane.showM// TODO Auto-generated catch block
								e.printStackTrace();
							}
							incoming = null;
						}
							//table.pushCardNodes(getAndRemoveSelectedNodes());
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
	@Override
	public void packetReceived(Packet packet) {
		Request request =  packet.getRequest();
		//String message = packet.getMessage();
		//JOptionPane.showMessageDialog(null,message,"message",JOptionPane.INFORMATION_MESSAGE);
		if (request!=null)
		{
			if (request.getID() == Request.COMPOUND_REQUEST)
			{
				Request[] reqs = request.getCompound();
				for (Request req : reqs)
				{
					performOnSingleRequest(req);
				}
			}
			else performOnSingleRequest(request);
		}
			
	}
	private void performOnSingleRequest(Request request)
	{
		/*Packet p = new Packet();
		p.setRequest(new Request(Request.REQUEST_CONSUMED));
		try {
			player.sendPakcet(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//incoming = null;
		if (request.getID() == Request.REQUEST_TAKE)
		{
			Card[] cards = request.getCards();
			this.pushCards(cards);
			
		}
		else
			if (request.getID() == Request.REQUEST_CARDSTACK_ACTUALIZATION)
			{
				Card[] cards = request.getCards();
				table.pushCards(cards);
				removeCards(cards);
				reorder();
				//table.repaint();
			}
			else 
				if (request.getID() == Request.REQUEST_ENABLE_PLAYER)
				{
					playerEnabled = true;
				}
				else
					if (request.getID() == Request.REQUEST_DISABLE_PLAYER)
					{
						playerEnabled = false;
					}
					else
						if (request.getID() == Request.REQUEST_CARD_NAME || request.getID() == Request.REQUEST_CARD_SUIT)							
						{
							incoming = request;
						}
		
	}
}
