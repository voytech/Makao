package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import client.ImagesBuffer;

public class OpponentPlayer extends JPanel{
	private BufferedImage image= ImagesBuffer.getInstance().getEntry("cardBack");	
	private ArrayList<Point> cards =  new ArrayList<Point>(); 
	public void setNumberOfCards(int n)
	{
		cards.clear();	
	    int x=0;
	    int y=0;
		for (int i=0;i<n;i++)
	    {
	    	Point point = new Point(x,y);
	    	cards.add(point);
	    	x+=20;
	    }
		this.repaint();
	}	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		for (Point p : cards)
		{			
			g2.drawImage(image, p.x,p.y,image.getWidth(),image.getHeight(),  null);
		}
	}
}
