package tests;

import shared.*;

import org.junit.* ;

import shared.Card;
import static org.junit.Assert.*; 

public class CardTest 
{
     @Test
     public void TestWheterAfterCreatingCardICanGetSuitAndName()
     {    	
    	Card c = new Card(Card.Name.ACE,Card.Suit.SPADE);		
    	Assert.assertEquals(Card.Name.ACE, c.getName());
    	Assert.assertEquals(Card.Suit.SPADE, c.getSuit()); 
     }
         
}
