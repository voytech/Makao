package gui;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import client.ImagesBuffer;

import shared.Card;

public class CardNode extends ScreenItem {
	//private int posX,posY,sizeX,sizeY;
    public CardNode(Card card) {
		// TODO Auto-generated constructor stub
		nestedObject = card;
		Card.Name name = card.getName();
		Card.Suit suit = card.getSuit();
		ImagesBuffer buffer = ImagesBuffer.getInstance();	     
		String relatedImageKey = null;
		if (name == Card.Name.ACE) relatedImageKey = getSuitRelatedString(suit)+"1";
		if (name == Card.Name.TWO) relatedImageKey = getSuitRelatedString(suit)+"2";
		if (name == Card.Name.THREE) relatedImageKey = getSuitRelatedString(suit)+"3";
		if (name == Card.Name.FOUR) relatedImageKey = getSuitRelatedString(suit)+"4";
		if (name == Card.Name.FIVE) relatedImageKey = getSuitRelatedString(suit)+"5";
		if (name == Card.Name.SIX) relatedImageKey = getSuitRelatedString(suit)+"6";
		if (name == Card.Name.SEVEN) relatedImageKey = getSuitRelatedString(suit)+"7";
		if (name == Card.Name.EIGHT) relatedImageKey = getSuitRelatedString(suit)+"8";
		if (name == Card.Name.NINE) relatedImageKey = getSuitRelatedString(suit)+"9";
		if (name == Card.Name.TEN) relatedImageKey = getSuitRelatedString(suit)+"10";
		if (name == Card.Name.JACK) relatedImageKey = getSuitRelatedString(suit)+"j";
		if (name == Card.Name.QUEEN) relatedImageKey = getSuitRelatedString(suit)+"q";
		if (name == Card.Name.KING) relatedImageKey = getSuitRelatedString(suit)+"k";
		image =  buffer.getEntry(relatedImageKey);	
	}
	private String getSuitRelatedString(Card.Suit suit)
	{
		if (suit.equals(Card.Suit.CLUB)) return "c";
		if (suit.equals(Card.Suit.DIAMOND)) return "d";
		if (suit.equals(Card.Suit.HEART)) return "h";
		if (suit.equals(Card.Suit.SPADE)) return "s";
		return null;
	}
	public void setRotation(double theta)
	{
		//super.setLocation(x-, y)
	}
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		//g2.drawImage(image,null,posX, posY);
		//g2.rotate(0.9f);
		g2.drawImage(image, 0, 0,this.getSize().width,this.getSize().height,  null);
	}
	

}
