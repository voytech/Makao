package tests;
import org.junit.* ;

import shared.*;
import static org.junit.Assert.*; 


public class CardStackTest {
    @Test
    public void testIfAfterInitializationThereIs_O_Elements()
    {
       CardStack stack = new CardStack();
       Assert.assertEquals(0, stack.getCount());
    }
    @Test 
    public void testIfAfterInitializationArrayReturns_O_Elements()
    {
       CardStack stack = new CardStack();
       Card[] elems = stack.getArray();
       Assert.assertEquals(0, elems.length);
    }
    @Test 
    public void testWhetherAfterPushingElementElementCountIncreasesByOne()
    {
       CardStack stack = new CardStack();
       int bef = stack.getCount();
	   stack.push(new Card(Card.Name.ACE,Card.Suit.SPADE));
       int length = stack.getArray().length;
       int length2 = stack.getCount();
       Assert.assertEquals(bef+1, length);
       Assert.assertEquals(length2, length);
    }
    @Test 
    public void testAddingTwoSameElements()
    {
       CardStack stack = new CardStack();
       stack.push(new Card(Card.Name.ACE,Card.Suit.SPADE));     
       stack.push(new Card(Card.Name.ACE,Card.Suit.SPADE));
       Assert.assertEquals(1, stack.getCount());
    }
    @Test 
    public void testAddingElementAndPopping()
    {
       CardStack stack = new CardStack();
       Card card0 = null;
	   card0 = new Card(Card.Name.ACE,Card.Suit.SPADE);
       stack.push(card0); 
       Card card = stack.pop();
       Assert.assertEquals(card0,card);
    }
    @Test//(expected=ArrayOutOfBound.class) 
    public void testPopingAnElemntIfStackIsEmpty()// throws Exception
    {
       CardStack stack = new CardStack();
       Card card = stack.pop();
       Assert.assertNull(card);
    }
    @Test 
    public void testWhetherAfterPopingElementElementCountDecreasesByOne()
    {
       CardStack stack = new CardStack();
	   stack.push(new Card(Card.Name.ACE,Card.Suit.SPADE));
       int bef = stack.getCount();
       stack.pop();
       int length = stack.getArray().length;
       int length2 = stack.getCount();
       Assert.assertEquals(bef-1, length);
       Assert.assertEquals(length2, length);
    }
    @Test
    public void testpopManyCardsAndCheckIfCountOfCardsHasDecreadedByTheNumber()
    {
       CardStack stack = new CardStack();
       stack.push(new Card(Card.Name.ACE,Card.Suit.SPADE));
	   stack.push(new Card(Card.Name.ACE,Card.Suit.CLUB));
	   stack.push(new Card(Card.Name.KING,Card.Suit.SPADE));
	   int len = stack.getCount();
       stack.pop(2);
       Assert.assertEquals(len-2,stack.getCount());
    }
    @Test
    public void testPopCardsAndCheckIfAreSameAsPushed()
    {
       CardStack stack = new CardStack();
       Card ace_of_clubs = null;
	   ace_of_clubs = new Card(Card.Name.ACE,Card.Suit.CLUB);
       Card king_of_spades = null;
	   king_of_spades = new Card(Card.Name.KING,Card.Suit.SPADE);
	   stack.push(new Card(Card.Name.ACE,Card.Suit.SPADE));
	   stack.push(ace_of_clubs);
       stack.push(king_of_spades);
       Card[] retC = stack.pop(2);
       Assert.assertEquals(king_of_spades,retC[0]);
       Assert.assertEquals(ace_of_clubs,retC[1]);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testPoppingMoreCardsThanSizeOfPack() throws Exception
    {
       CardStack stack = new CardStack();
       Card ace_of_clubs = new Card(Card.Name.ACE,Card.Suit.CLUB);
       Card king_of_spades = new Card(Card.Name.KING,Card.Suit.SPADE);
       stack.push(ace_of_clubs);
       stack.push(king_of_spades);
       Card[] retC = stack.pop(3);
       Assert.assertEquals(null, retC);
    }
    public void testPushingMoreThanOneCardAtOnceAndPoppingThemGivesCardsInReversedOrder()
    {
    	Card[] cards = new Card[]{new Card(Card.Name.ACE,Card.Suit.CLUB), new Card(Card.Name.KING,Card.Suit.SPADE)};
		CardStack stack = new CardStack();
    	stack.push(cards);
        Card[] cardsPopped = stack.pop(2);
        Assert.assertEquals(cardsPopped[0], cards[1]);
        Assert.assertEquals(cardsPopped[1], cards[0]);
       	
    }
    public void testPushingMoreThanOneCardIncreasesLengthOfPack()
    {
    	Card[] cards = new Card[]{new Card(Card.Name.ACE,Card.Suit.CLUB), new Card(Card.Name.KING,Card.Suit.SPADE)};
    	CardStack stack = new CardStack();
    	int c1 = stack.getCount();
    	stack.push(cards);
        Assert.assertEquals(c1,stack.getCount()-cards.length);
    }
}
