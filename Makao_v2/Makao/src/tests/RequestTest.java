package tests;

import org.junit.*; 
import static org.junit.Assert.* ;
import shared.*;
public class RequestTest {

	@Test
	public void testWhetherAfterCreatingRequestCanReturnRequestIDAndArgs()
	{
	//for example we can request cards named TWO.
	   for (byte id = 0; id<10;id++)
	   {
		   Request req =new Request(id,new Object[]{new Byte(id)});
		   Assert.assertEquals(id, req.getID());
           Object[] ret = (Object[])req.getArg();		   
		   Assert.assertEquals(new Byte(id), (Byte)ret[0]);
	   }
	}
	@Test
	public void testWhetherAfterCreatingCardsRequestCanReturnRequestIDAndCards()
	{
	//for example we can request cards named TWO.
	   
	   {
		   Card[] cards = new Card[]{new Card(Card.Name.ACE,Card.Suit.CLUB),new Card(Card.Name.ACE,Card.Suit.DIAMOND)};
		   Request req =new Request(Request.REQUEST_PUSH,cards);
		   Assert.assertEquals(Request.REQUEST_PUSH, req.getID());
           Card[] ret = req.getCards();		   
		   Assert.assertArrayEquals(cards, ret);
	   }
	}
	@Test
	public void testWhetherAfterCreatingCompoundRequestCanReturnRequestIDAndCompound()
	{
	//for example we can request cards named TWO.
	   
	   {
		   Request[] c = new Request[]{new Request(Request.REQUEST_SHOW_YOURSELF),new Request(Request.REQUEST_WAITING)};
		   Request req =new Request(Request.COMPOUND_REQUEST,c);
		   Assert.assertEquals(Request.COMPOUND_REQUEST, req.getID());
           Request[] ret = req.getCompound();		   
		   Assert.assertArrayEquals(c, ret);
	   }
	}

}
