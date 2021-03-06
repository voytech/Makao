package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import shared.Card;
import shared.CardStack;

public class CardNodeContainer extends JPanel{
	protected  ArrayList<CardNode> nodes = new ArrayList<CardNode>();
	protected int x,y,cardOffset=20,cardHeight=140,cardWidth=90;
    public void setCardOffset(int offset)
    {
    	this.cardOffset = offset;	
    }
    public void pushCardNode(CardNode node)
    {
    	this.add(node,0);
    }
    public void pushCardNodes(CardNode[] nodess)
    {
    	for (CardNode n : nodess) add(n,0);
    }
    public CardNode[] takeCardNodes()
    {
    	return null;
    }
    public void pushCard(Card card)
    {
    	CardNode cNode = new CardNode(card);
    	pushCardNode(cNode);
    }
    public void pushCards(Card[] cards)
    {
    	for (Card card : cards)
    	{
    		CardNode cNode = new CardNode(card);
    		pushCardNode(cNode);
    	}
    }
    public CardStack getCards()
    {
    	CardStack stack = new CardStack();    	
    	for (int i=this.getComponentCount()-1;i>=0;i--)
    	{
    		Component comp = this.getComponent(i);
    		if (comp instanceof CardNode)
    		{
    		   Object obj = ((CardNode)comp).getNestedObject();
    	   	   stack.push((Card)obj);
    		}
    	}
    	return stack;
    }
}
