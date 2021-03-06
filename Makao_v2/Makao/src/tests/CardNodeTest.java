package tests;

import gui.CardNode;

import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.* ;

import client.ImagesBuffer;

import shared.*;
import static org.junit.Assert.*; 

public class CardNodeTest {

	private void initializeBuffer()
	{
		File dir = new File("cards"); 
		String[] files = dir.list();
		for (int i=0;i<files.length;i++)
		{
			File temp = new File(files[i]);
			{
				String name =files[i].substring(0, files[i].lastIndexOf("."));
				ImagesBuffer.getInstance().addEntry(name, "cards"+File.separator+temp.getName());
			}
		}	
	}
	@Test
	public void testCreatingCardNodeAndGettingCard()
	{
		for (Card.Name name : Card.Name.values())
			for (Card.Suit suit : Card.Suit.values())
			{
				CardNode node = new CardNode(new Card(name,suit));
				Card card = (Card)node.getNestedObject();
				Assert.assertEquals(name, card.getName());
				Assert.assertEquals(suit, card.getSuit());
			}
	}
	@Test
	public void testWetherAfterInitializingBufferAndThenCreatingCardImageIsLoaded()
	{	
		initializeBuffer();
		for (Card.Name name : Card.Name.values())
			for (Card.Suit suit : Card.Suit.values())
			{
				CardNode node = new CardNode(new Card(name,suit));
				BufferedImage cardImage = (BufferedImage)node.getImage();
				Assert.assertNotNull(cardImage);
			}
	}
	@Test
	public void testWetherAfterInitializingBufferAndThenCreatingCardImageWeCanRetrieveCardData()
	{	
		initializeBuffer();
		for (Card.Name name : Card.Name.values())
			for (Card.Suit suit : Card.Suit.values())
			{
				Card iC = new Card(name,suit);
				CardNode node = new CardNode(iC);
				Card c = (Card)node.getNestedObject();
				Assert.assertEquals(iC.getName(),c.getName());
				Assert.assertEquals(iC.getSuit(),c.getSuit());
			}
	}
	@Test
	public void testWhetherCanChangeLocationOfCardNode()
	{	
		initializeBuffer();
		CardNode node = new CardNode(new Card(Card.Name.ACE,Card.Suit.SPADE));
		//node.moveTo(300,300);
		//Assert.assertEquals(node.getXPos(),300);
		//Assert.assertEquals(node.getYPos(),300);	
	}
}
