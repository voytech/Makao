package tests;
import org.junit.*;

import shared.Card;
import shared.Packet;
import shared.Request;
import static org.junit.Assert.* ;

public class PacketTest {
	@Test
	public void testCreatingPacketAndGettingItsComponents()
	{
	     
		 Packet packet = new Packet();
	     
		 Card[] cards = new Card[]{new Card(Card.Name.ACE,Card.Suit.CLUB), new Card(Card.Name.FIVE,Card.Suit.CLUB)};
		 Request req = new Request(Request.REQUEST_CARD_NAME,Card.Name.ACE);
		 
		 packet.setMessage("Hello world");
	     packet.setRequest(req);
	     packet.setCards(cards);
	     
	     Assert.assertEquals(packet.getMessage(), "Hello world");
	     Assert.assertEquals(packet.getRequest(), req);
	     Assert.assertArrayEquals(packet.getCards(), cards);
	}
}
