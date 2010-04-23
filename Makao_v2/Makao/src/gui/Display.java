package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class Display extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardNodeContainer playerCards;
	private CardNode n2 = null;
    public  Display()
    {
    	this.setLayout(null);
    	this.addMouseListener(this);
        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));       
        playerCards = new PlayerCards();
        playerCards.setLocation(100, 750);
        playerCards.setSize(new Dimension(500,200));
        
        n2 = new CardNode(new Card(Card.Name.ACE,Card.Suit.HEART));
        playerCards.pushCardNode(new CardNode(new Card(Card.Name.ACE,Card.Suit.HEART)));
        playerCards.pushCardNode(new CardNode(new Card(Card.Name.ACE,Card.Suit.DIAMOND)));
        playerCards.pushCardNode(new CardNode(new Card(Card.Name.ACE,Card.Suit.CLUB)));
        playerCards.pushCardNode(new CardNode(new Card(Card.Name.ACE,Card.Suit.SPADE)));
        playerCards.pushCardNode(new CardNode(new Card(Card.Name.EIGHT,Card.Suit.SPADE)));
        n2.setLocation(new Point(0,0));
        n2.setSize(new Dimension(200,200));
         n2.setBounds(0, 0, 200, 200);  
         n2.setVisible(true);
        //this.add(n2);

        this.add(playerCards);
        //this.validate();

    }
    public  Display(Rectangle rect)
    {

       this.setLocation(rect.x, rect.y);
       this.setSize(rect.width, rect.height);
       this.addMouseListener(this);
       
    }
    
    //@Override
	public void paintComponent(Graphics g)
	{		

		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2.setColor(Color.GRAY);
		BufferedImage table = ImagesBuffer.getInstance().getEntry("table");
		g2.drawImage(table, 0, 0,this.getWidth(),this.getHeight(),  null);
		//
	}

	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		System.out.println("from display panel");		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}
	

}
