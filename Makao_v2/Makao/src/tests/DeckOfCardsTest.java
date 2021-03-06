package tests;
import org.junit.* ;
import static org.junit.Assert.* ;
import shared.*;

public class DeckOfCardsTest 
{
	@Test
	public void testIfNumberOfAllCardsIs52AfterInitialization()
	{
	    CardStack all_cards = new DeckOfCards();
	    Assert.assertEquals(52, all_cards.getCount());
	    Assert.assertEquals(52,all_cards.getArray().length);
	}
	@Test
	public void testIfDeskOfCardsArrayNotNull()
	{
		Card[] array =  new DeckOfCards().getArray();
		Assert.assertNotNull(array);
	}	
	@Test
	public void testIfDeckOfCardsContains52ObjectsAfterInit()
	{
	    CardStack all_cards = new DeckOfCards();
	    Card[] cards = all_cards.getArray();
	    int counter = 0;
	    if (cards!=null)
	    {
	    	for (int i=0;i<cards.length;i++)
	    	{
	    		if ( cards[i] != null ) counter++;	    		  
	    	}
	    }
	    Assert.assertEquals(52, counter);	    
	}
	@Test
	public void assureThereAreNoTwoTheSameCardsInDeckOfCards()
	{
		boolean noTwoSame = true;
		CardStack all_cards = new DeckOfCards();
	    Card[] cards = all_cards.getArray();
	    for (int i=0; i<cards.length;i++)
	    {
	      for (int j=0;j<cards.length;j++)
	      {
	    	 if (j!=i)
	    	 {
	    		 if ((cards[j].getName() == cards[i].getName()) && (cards[j].getSuit() == cards[i].getSuit())) noTwoSame = false;
	    		 //if (cards[i].equals(cards[j])) noTwoSame = false;
	    		 Assert.assertTrue(noTwoSame);	  
	    	 }
	      }
	    } 
	}
	@Test
	public void TestShuffleCards()
	{
		boolean shuffleSucceed = true;
		DeckOfCards deck = new DeckOfCards();
		Card[] before = deck.getArray();
		deck.shuffle();
		Card[] after = deck.getArray();
		int j = 0;
		for (int i=0;i<before.length;i++) 
			if (before[i].equals(after[i])) j++; 
		if (j >= 10) shuffleSucceed = false; 
		Assert.assertTrue(shuffleSucceed);
	}
	
}
