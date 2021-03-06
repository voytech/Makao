package shared;

import java.util.*;

public class CardStack {
	   protected List<Card> cards = new ArrayList<Card>(); 
	   
	   public int getCount()
       {
          return cards.size();
       }
	   
       public Card[] getArray()
       {
    	   return this.cards.toArray(new Card[0]);
       }
       public void push(Card card)
       {
    	  for (int i= 0; i<cards.size();i++)
    	  {
    	     Card c = cards.get(i);
    	     if ((c.getName() == card.getName()) && (c.getSuit() == card.getSuit()))
    	     {
    	       return;  
    	     }
    	  }
          cards.add(card);
       }
       public void push(Card[] cardsA)
       {
    	  for (int i= 0; i<cards.size();i++)
    	  {
    		 for (int j = 0; j<cardsA.length; j++)
    		 {
    			 Card c = cards.get(i);
    			 Card cp = cardsA[j];
    			 if ((c.getName() == cp.getName()) && (c.getSuit() == cp.getSuit())) return;    			
    		 }
    	  }
          for(int i=0;i<cardsA.length;i++) cards.add(cardsA[i]);
       }
       public Card pop()
       { 
    	  if (cards.size() > 0) return cards.remove(cards.size()-1);  
          return null;
       }
       public Card[] pop(int number)
       { 
    	  Card[] cardsA = new Card[number]; 
    	  int i=0;
    	  while (i<number)
    	  {
    	     cardsA[i] = cards.remove(cards.size()-1);
    	     i++;
    	  }  
          return cardsA;
       }
}
