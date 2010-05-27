package shared;

import java.util.Random;

public class DeckOfCards extends CardStack{
      public DeckOfCards()
      {
         for(Card.Name name : Card.Name.values()) 
         {
        	 for (Card.Suit suit : Card.Suit.values())
			 cards.add(new Card(name,suit));				   
         }
      }

	public void shuffle() {
		// TODO Auto-generated method stub
		int size = cards.size();
		Random r = new Random();
		for (int i = 0; i< size; i++)
		{			
			int rPos = r.nextInt(size-1);
			while (rPos == i) rPos = r.nextInt(size-1);
			Card c1 = cards.remove(i);
			Card c2 = cards.remove(rPos);
			cards.add(rPos, c1);
			cards.add(i,c2);			
		}
	}
}
