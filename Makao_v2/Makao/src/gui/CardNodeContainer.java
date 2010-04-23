package gui;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class CardNodeContainer extends JPanel{
    protected  ArrayList<CardNode> nodes = new ArrayList<CardNode>();
	protected int x,y,cardOffset=20,cardHeight=140,cardWidth=90;
    public void setCardOffset(int offset)
    {
    	this.cardOffset = offset;	
    }
    public void pushCardNode(CardNode node)
    {
    	nodes.add(node);
    }
    
   /* public void draw(Graphics g)
	{	
	}*/
}
