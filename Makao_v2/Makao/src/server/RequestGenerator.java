package server;

import shared.Card;
import shared.CardStack;
import shared.Request;

public class RequestGenerator {
    private CardStack stack = null;
    private boolean requestReleased = false;
    private boolean firstTakeRequest = true;
	private Request lastGenerated = null;
	private Card requestStarterCard = null;
    public RequestGenerator(CardStack stack) {
		this.stack = stack;
	}
    private void zerothRecentRequest()
    {
		if (lastGenerated!=null)
		{
			if (lastGenerated.getID() == Request.REQUEST_WAITING)
			{
			    requestReleased = true;
			    requestStarterCard = null;
			    lastGenerated = null;
			}
			else
			if (lastGenerated.getID() == Request.REQUEST_TAKE)
			{
				Integer number = (Integer)lastGenerated.getArg();
				if (number > 1)
				{
					requestReleased = true;
					lastGenerated = null;
					requestStarterCard = null;
					//firstTakeRequest = true;
				}
			}
		}
		else requestReleased = false;
    }
	public Request prepareRequest(Request player_req) {
		// TODO Auto-generated method stub
		zerothRecentRequest();
		
		Request output = null;
		Card[] array = stack.getArray();
		Card top = array[array.length-1];	

		if (player_req!=null)
		{
			if (player_req.getID() == Request.REQUEST_TAKE)
			{
				output = new Request(Request.REQUEST_TAKE,new Integer(1));		
			}
			else 
				if (player_req.getID() == Request.REQUEST_TAKE_MORE)
				{
					int number_c = 0;
					if (requestStarterCard.getName().equals(Card.Name.TWO) || requestStarterCard.getName().equals(Card.Name.THREE))
					{
						for (int i=array.length-1;i>=0;i--)
						{
							Card c = array[i];
							if (c.getName().equals(Card.Name.TWO)) number_c+=2;
							else if (c.getName().equals(Card.Name.THREE)) number_c+=3;
							if (c.equals(requestStarterCard)) break;
						}
						output = new Request(Request.REQUEST_TAKE,new Integer(number_c-1));
					}
					else
						if (requestStarterCard.getName().equals(Card.Name.FOUR))
						{
							for (int i=array.length-1;i>=0;i--)
							{
								Card c = array[i];
								if (c.getName().equals(Card.Name.FOUR)) number_c+=1;
								if (c.equals(requestStarterCard)) break;
							}
							output = new Request(Request.REQUEST_WAITING,new Integer(number_c));
						}
						else
							if (requestStarterCard.getName().equals(Card.Name.KING) && (requestStarterCard.getSuit().equals(Card.Suit.HEART) || requestStarterCard.getSuit().equals(Card.Suit.SPADE)))
							{
								number_c += 5;
								if (array.length-2 > 0)
								{
									Card under = array[array.length-2];
									if (under.getName().equals(Card.Name.KING) && (under.getSuit().equals(Card.Suit.HEART) || under.getSuit().equals(Card.Suit.SPADE)))
									{
										number_c+=5;
									}
								}
								output = new Request(Request.REQUEST_TAKE,new Integer(number_c));
							}
							
				}	
				else
					if ((player_req.getID() == Request.REQUEST_CARD_NAME) || (player_req.getID() == Request.REQUEST_CARD_SUIT))
					{
						output = player_req;
					}
			if (output != null) {
				
				lastGenerated = output;
				return output;
			}
		}
		//consumeRequest();
		//Only if the player has consumed request the card on top has a special functionality. 
		if (!requestReleased)
		{
			if (top.getName().equals(Card.Name.TWO) || top.getName().equals(Card.Name.THREE))
			{
				output = new Request(Request.REQUEST_CARD_NAME,new Card.Name[]{Card.Name.TWO,Card.Name.THREE});
				if (requestStarterCard == null) requestStarterCard = top;
			}
			if (top.getName().equals(Card.Name.FOUR))
			{
				output = new Request(Request.REQUEST_CARD_NAME,Card.Name.FOUR);
				if (requestStarterCard == null) requestStarterCard = top;
			}
			if (top.getName().equals(Card.Name.KING) && top.getSuit().equals(Card.Suit.SPADE))
			{
				output = new Request(Request.COMPOUND_REQUEST,new Request[]{new Request(Request.REQUEST_CARD_NAME,Card.Name.KING),
																			new Request(Request.REQUEST_CARD_SUIT,Card.Suit.HEART)});
				if (requestStarterCard == null) requestStarterCard = top;
			}
			if (top.getName().equals(Card.Name.KING) && top.getSuit().equals(Card.Suit.HEART))
			{
				output = new Request(Request.COMPOUND_REQUEST,new Request[]{new Request(Request.REQUEST_CARD_NAME,Card.Name.KING),
																			new Request(Request.REQUEST_CARD_SUIT,Card.Suit.SPADE)});
				if (requestStarterCard == null) requestStarterCard = top;
			}
			
		}
		lastGenerated = output;
		return output;
	}

}

