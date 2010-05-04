package shared;

import java.io.Serializable;

public class Card implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2395473563743537699L;
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