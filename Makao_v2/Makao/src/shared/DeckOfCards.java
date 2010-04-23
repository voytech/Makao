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
		Random r = new Random();
		for (int i = 0; i< cards.size(); i++)
		{			
			int rPos = r.nextInt(51);
			while (rPos == i) rPos = r.nextInt(51);
			Card c1 = cards.remove(i);
			Card c2 = cards.remove(rPos);
			cards.add(rPos, c1);
			cards.add(i,c2);			
		}
	}
}
