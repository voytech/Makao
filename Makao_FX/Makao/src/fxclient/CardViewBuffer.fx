package fxclient;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import shared.*;



/**
 * @author Zosia i S³awek
 */public class CardViewBuffer
{
 	public-read var content : CardView[];
 	postinit
 	{
 	     for (name in Card.Name.values()) 
 	    	for (suit in Card.Suit.values())
 	    		insert CardView{card : new Card(name,suit)} into content 
 	}
 	public function getContentByCard(card : Card) : CardView
 	{
 	 	for (element in content)
 	 	   if ((element.card.getName() == card.getName()) and (element.card.getSuit() == card.getSuit())) return element; 
 	    return null;
 	}	 
 	public function getContentByCards(cards : Card[]) : CardView[]
 	 	{
 	 	    var ret:CardView[] = [null];
 	 	 	for (element in content)
 	 	 	   for (card in cards)
 	 	 	      if ((element.card.getName() == card.getName()) and (element.card.getSuit() == card.getSuit()))
 	 	 	      insert element into ret; 
 	 	    return ret;
 	 	}	 
}
