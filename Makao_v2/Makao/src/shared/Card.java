package shared;

public class Card {
	public enum Name
	{
		TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE
	}
	public enum Suit
	{
		CLUB,
        DIAMOND, 
        HEART, 
        SPADE
	}
    private Name name;
    private Suit suit;
    public Card(Name n,Suit s) 
    { 
    	this.name = n;
    	this.suit = s;
    }
    public Name getName()
    {
       return name;
    }
    public Suit getSuit()
    {
    	return suit;
    }
}