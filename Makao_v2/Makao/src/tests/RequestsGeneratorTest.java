package tests;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.* ;

import server.RequestGenerator;
import shared.Card;
import shared.CardStack;
import shared.DeckOfCards;
import shared.Messenger;
import shared.Packet;
import shared.Request;
import server.*;
import static org.junit.Assert.* ;


public class RequestsGeneratorTest {
	@Test
	public void testWetherGeneratesTwoOrThreeRequest()
	{
		CardStack stack = new CardStack();
		DeckOfCards deck = new DeckOfCards();
		stack.push(new Card(Card.Name.ACE,Card.Suit.DIAMOND));
		stack.push(new Card(Card.Name.ACE,Card.Suit.HEART));
		stack.push(new Card(Card.Name.TWO,Card.Suit.DIAMOND));
	
		RequestGenerator generator = new RequestGenerator(stack,deck);
		Request s_request = generator.prepareRequest(null);
		Assert.assertEquals(Request.REQUEST_CARD_NAME, s_request.getID());
		Card.Name names = (Card.Name)s_request.getArg();
		
		Assert.assertEquals(Card.Name.TWO, names);			
	}
	@Test
	public void testAfter()
	{
		CardStack stack = new CardStack();
		DeckOfCards deck = new DeckOfCards();
		stack.push(new Card(Card.Name.ACE,Card.Suit.DIAMOND));
		stack.push(new Card(Card.Name.ACE,Card.Suit.HEART));
		stack.push(new Card(Card.Name.TWO,Card.Suit.DIAMOND));
	
		RequestGenerator generator = new RequestGenerator(stack,deck);
		Request s_request = generator.prepareRequest(null);
		Assert.assertEquals(Request.REQUEST_CARD_NAME, s_request.getID());
		Card.Name names = (Card.Name)s_request.getArg();
		
		Assert.assertEquals(Card.Name.TWO, names);			
	}
	@Test
	public void testWetherGeneratesTakeRequest()
	{
		Request s_request;
		DeckOfCards deck = new DeckOfCards();
		CardStack stack = new CardStack();
		RequestGenerator generator = new RequestGenerator(stack,deck);
		stack.push(new Card(Card.Name.ACE,Card.Suit.DIAMOND));
		s_request = generator.prepareRequest(null);
		stack.push(new Card(Card.Name.ACE,Card.Suit.HEART));
		s_request = generator.prepareRequest(null);
		//Above cards doesn't matter
		//Below someone has put two, server generates a request of two's 	
		stack.push(new Card(Card.Name.TWO,Card.Suit.DIAMOND));
		s_request = generator.prepareRequest(null);
		// Below -  Suppose someone to whom the request was directed, had two, so he put it 
		stack.push(new Card(Card.Name.TWO,Card.Suit.HEART));	
		s_request = generator.prepareRequest(null);
		// Below -  But next player doesn't have neither 3 nor 2 so he is forced to make a take request 		
		// Server should now generate request which forces player to take one card from stack -
		// if taken card is two then, but if he still doesn't have two he will be forced to take 3 cards (4-1) 
		s_request = generator.prepareRequest(new Request(Request.REQUEST_TAKE));
		Assert.assertEquals(Request.REQUEST_TAKE, s_request.getID());
		Card[] cards  = s_request.getCards();	
		Assert.assertEquals(1, cards.length);
		// Below - OOOoops still do not have two. Must take 3 cards 
		s_request = generator.prepareRequest(new Request(Request.REQUEST_TAKE));
		Assert.assertEquals(Request.REQUEST_TAKE, s_request.getID());
		int count  = s_request.getCards().length;	
		Assert.assertEquals(3, count);
		// Below - and now next player will play - he do not have to put either 3 or 2.
		// The rules are basic.
		// but unfortunately he doesn't have in his possession any card that meets stack requirements
		// He generates take 
	
		s_request = generator.prepareRequest(null);	
		Assert.assertEquals(null,s_request);

	}
	@Test
	public void testWetherGeneratesKingSpadeOrHearRequestAndTakesFiveCards()
	{
		Request s_request;
		CardStack stack = new CardStack();
		DeckOfCards deck = new DeckOfCards();
		RequestGenerator generator = new RequestGenerator(stack, deck);
		stack.push(new Card(Card.Name.ACE,Card.Suit.DIAMOND));
		s_request = generator.prepareRequest(null);
		stack.push(new Card(Card.Name.ACE,Card.Suit.HEART));
		s_request = generator.prepareRequest(null);
		//Above cards doesn't matter
		//Below someone has put two, server generates a request of two's 	
		stack.push(new Card(Card.Name.KING,Card.Suit.HEART));
		s_request = generator.prepareRequest(null);
		Assert.assertEquals(Request.COMPOUND_REQUEST,s_request.getID());
		s_request = generator.prepareRequest(new Request(Request.REQUEST_TAKE));
		Assert.assertEquals(1,s_request.getCards().length);
		
		s_request = generator.prepareRequest(new Request(Request.REQUEST_TAKE));
		Assert.assertEquals(5,s_request.getCards().length);						
	}
	public void testWetherGeneratesKingSpadeOrHearRequestAndHasOneOfThoseCard()
	{
		Request s_request;
		CardStack stack = new CardStack();
		DeckOfCards deck = new DeckOfCards();
		deck.shuffle();
		RequestGenerator generator = new RequestGenerator(stack,deck);
		stack.push(new Card(Card.Name.ACE,Card.Suit.DIAMOND));
		s_request = generator.prepareRequest(null);
		stack.push(new Card(Card.Name.ACE,Card.Suit.HEART));
		s_request = generator.prepareRequest(null);
		//Above cards doesn't matter
		//Below someone has put two, server generates a request of two's 	
		stack.push(new Card(Card.Name.KING,Card.Suit.HEART));
		s_request = generator.prepareRequest(null);
		Assert.assertEquals(Request.COMPOUND_REQUEST,s_request.getID());
		s_request = generator.prepareRequest(new Request(Request.REQUEST_TAKE));
		Assert.assertEquals(new Integer(1),s_request.getArg());
			
		stack.push(new Card(Card.Name.KING,Card.Suit.SPADE));		
		s_request = generator.prepareRequest(null); //player has requested card
		
		s_request = generator.prepareRequest(new Request(Request.REQUEST_TAKE_MORE));
		Assert.assertEquals(new Integer(10),s_request.getArg());		
	}
	@Test
	public void testRequestingCardNames()
	{
		Request s_request;
		CardStack stack = new CardStack();
		DeckOfCards deck = new DeckOfCards();
		deck.shuffle();
		RequestGenerator generator = new RequestGenerator(stack,deck);
		stack.push(new Card(Card.Name.ACE,Card.Suit.DIAMOND));
		s_request = generator.prepareRequest(new Request(Request.REQUEST_CARD_NAME,Card.Name.FIVE));
		Assert.assertEquals(Request.REQUEST_CARD_NAME,s_request.getID());
		Assert.assertEquals(Card.Name.FIVE,s_request.getArg());
		
	}
	@Test
	public void testRequestingCardSuits()
	{
		Request s_request;
		CardStack stack = new CardStack();
		DeckOfCards deck = new DeckOfCards();
		deck.shuffle();
		RequestGenerator generator = new RequestGenerator(stack,deck);
		stack.push(new Card(Card.Name.ACE,Card.Suit.DIAMOND));
		s_request = generator.prepareRequest(new Request(Request.REQUEST_CARD_SUIT,Card.Suit.DIAMOND));
		Assert.assertEquals(Request.REQUEST_CARD_SUIT,s_request.getID());
		Assert.assertEquals(Card.Suit.DIAMOND,s_request.getArg());	
	}
}
