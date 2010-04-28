package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import client.ImagesBuffer;

import shared.Card;

public class Board extends JPanel    {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardNodeContainer playerCards;
	private TableCardContainer tableCards;
	private int playerCardsIndex = 0; //we will be adding cards into that index which is pointed by number of last components size;
	private int cardTableIndex = 0;
    public  Board()
    {
    	this.setLayout(null);
    	//this.addMouseListener(this);
        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));       
        tableCards = new TableCardContainer();
        //tableCards.setLocation(250, 100);
        tableCards.pushCardNode(new CardNode(new Card(Card.Name.FIVE,Card.Suit.CLUB)));
        tableCards.pushCardNode(new CardNode(new Card(Card.Name.EIGHT,Card.Suit.CLUB)));
        tableCards.pushCardNode(new CardNode(new Card(Card.Name.FOUR,Card.Suit.HEART)));
        tableCards.pushCardNode(new CardNode(new Card(Card.Name.FIVE,Card.Suit.HEART)));
        tableCards.pushCardNode(new CardNode(new Card(Card.Name.KING,Card.Suit.CLUB)));
        
        playerCards = new PlayerCards(tableCards,null);
        playerCards.setLocation(150, 100);
        playerCards.setSize(900,850);
      
        playerCards.pushCardNode(new CardNode(new Card(Card.Name.ACE,Card.Suit.HEART)));
        playerCards.pushCardNode(new CardNode(new Card(Card.Name.ACE,Card.Suit.DIAMOND)));
        playerCards.pushCardNode(new CardNode(new Card(Card.Name.ACE,Card.Suit.CLUB)));
        playerCards.pushCardNode(new CardNode(new Card(Card.Name.ACE,Card.Suit.SPADE)));
        playerCards.pushCardNode(new CardNode(new Card(Card.Name.EIGHT,Card.Suit.SPADE)));                      
        //this.addFocusListener(l)
        this.add(playerCards);
        PlayerInterface pi =new PlayerInterface();
        pi.setLocation(10,770);
        pi.setSize(300,180);
        this.add(pi);
       // this.add(tableCards);

    }
    public void addPlayerCardComponent(CardNode node)
    {
    	this.add(node, 0);
    	cardTableIndex++;
    }
    public int getPlayerCardCount()
    {
    	return cardTableIndex;
    }
    public void removePlayerCardComponent(CardNode node)
    {
    	//if (this.)
    	this.remove(node);
    	cardTableIndex--;
    }
    public void addTableCardComponent(CardNode node)
    {
    	this.add(node,cardTableIndex);
    }
    public void removeTableCardComponent(CardNode node)
    {
    	this.remove(node);
    	//cardTableIndex++;
    }
    public  Board(Rectangle rect)
    {

       this.setLocation(rect.x, rect.y);
       this.setSize(rect.width, rect.height);
      // this.addMouseListener(this);
       
    }
    public void setSize(Dimension d)
    {
      super.setSize(d);

      playerCards.setLocation(this.getWidth()/2, 750);
    }
    //@Override
	public void paintComponent(Graphics g)
	{		
		//playerCards.setLocation(this.getWidth()/2, 750);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		
		BufferedImage table = ImagesBuffer.getInstance().getEntry("table");
		g2.drawImage(table, 0, 0,this.getWidth(),this.getHeight(),  null);
		g2.setColor(Color.yellow);
		Stroke stroke;
	
		//g2.setStroke(new Stroke());
		//g2.drawOval(this.getWidth()/2-150, this.getHeight()/2-150, 300, 300);
		//

	}


	

}
