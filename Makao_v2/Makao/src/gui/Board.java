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
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
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
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import client.ImagesBuffer;
import client.Player;

import shared.Card;
import shared.Messenger;

public class Board extends JPanel  implements HierarchyBoundsListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PlayerSpace playerCards=null;
	private TableCardContainer tableCards=null;
	private Messenger player = null;
	private int playerCardsIndex = 0; //we will be adding cards into that index which is pointed by number of last components size;
	private int cardTableIndex = 0;
    private LoginPanel lpanel = new LoginPanel();
	public  Board(Dimension size)
    {
       // this.setSize(size);	     
    	this.setLayout(null); 
    	//this.addMouseListener(this);
        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));       
        
        tableCards = new TableCardContainer();
        //tableCards.setLocation(250, 100);
        this.addHierarchyBoundsListener(this);
             
        try {
			player = new Player(new Socket("127.0.0.1",9090));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lpanel.setLocation(200,200);
		lpanel.setSize(800, 500);
		playerCards = new PlayerSpace(tableCards,null,player);
        playerCards.setLocation(0, 0);
        playerCards.setSize(this.getSize().width,this.getSize().height);  
        this.add(playerCards);
        this.add(lpanel);
       // this.add(tableCards);

    }
    public PlayerSpace getPlayerSpace()
    {
    	return playerCards;
    }
    public TableCardContainer getTableContainer()
    {
    	return tableCards;
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
	@Override
	public void ancestorMoved(HierarchyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void ancestorResized(HierarchyEvent a) {
		// TODO Auto-generated method stub
		//playerCards.setLocation(p)
		playerCards.setSize(this.getSize().width,this.getSize().height);
		
	}


	

}
