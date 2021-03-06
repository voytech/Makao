package tests;
import gui.CardNode;

import java.util.List;

import org.junit.*; 

import client.ClientApplication;
import client.ImagesBuffer;
import static org.junit.Assert.* ;
import shared.*;
import java.awt.image.*;

public class ImageBufferTest {
	
	@Test
	public void testAddingBufferedImageUnderKeyAndRetrievingIt()
	{
		ImagesBuffer buffer = ImagesBuffer.getInstance();
		buffer.addEntry("all","cards.jpg");
		BufferedImage image = buffer.getEntry("all");
		Assert.assertNotNull(image);
		//buffer.splitImagesFromSource("cards.jpg",50,100);
		//CardNode node= new CardNode(new Card(Card.Name.ACE,Card.Suit.SPADE));
		
	}
	@Test
	public void testWetherAfterAddingEntryNumberOfEntriesEcountered()
	{
		ImagesBuffer buffer = ImagesBuffer.getInstance();
		buffer.clear();
		buffer.addEntry("all","cards.jpg");
		int size = buffer.size();
		Assert.assertEquals(1, size);		 
	}
	@Test
	public void testWetherAfterAddingEntryAndRemovingItSizesAreChanging()
	{
		ImagesBuffer buffer = ImagesBuffer.getInstance();
		buffer.clear();
		buffer.addEntry("all","cards.jpg");
		Assert.assertEquals(1, buffer.size());
		buffer.removeEntry("all");
		Assert.assertEquals(0, buffer.size());
	}
	@Test
	public void testAddingTheSameEntires()
	{	
		ImagesBuffer buffer = ImagesBuffer.getInstance();
		buffer.clear();
		buffer.addEntry("all","cards.jpg");
		buffer.addEntry("all","cards.jpg");
		Assert.assertEquals(1,buffer.size());
	}
	@Test
	public void testGettingElementWhichIsNotPresent()
	{	
		ImagesBuffer buffer = ImagesBuffer.getInstance();
		buffer.clear();
		BufferedImage im = buffer.getEntry("dasd");
		Assert.assertEquals(null,im);
	}
	@Test
	public void testRemovingElementWhichIsNotPresent()
	{	
		ImagesBuffer buffer = ImagesBuffer.getInstance();
		buffer.clear();
		buffer.removeEntry("dasd");
		Assert.assertEquals(0,buffer.size());
	}

	

}
