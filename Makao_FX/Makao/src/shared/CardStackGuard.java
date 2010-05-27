package shared;


public class CardStackGuard {

	private CardStack stack = null;
	private Card[] selection = null;
	private Request incRequest = null;
	public CardStackGuard(CardStack localStack) {
		stack = localStack;
		// TODO     	
		//addRequest();
    	//addMessage();
		//bool makeRequest(Request);
    	//packet(); - ready for send by client.
	}
	public void setSelection(Card[] selectedCards) {
		// TODO Auto-generated method stub
		selection = selectedCards;
	}
	
	public void setIncomingRequest(Request req)
	{
	   this.incRequest = req;
	}
	
	public boolean setOutgoingRequest(Request request) {
		Card bottomCard = selection[0];
		if (request!=null)
		{
			if (bottomCard.getName() == Card.Name.JACK)
			{
				if (request.getID() == Request.REQUEST_CARD_NAME) 
				{
					Card.Name name = (Card.Name)request.getArg();
					if ((name.equals(Card.Name.FIVE)) || 
							(name.equals(Card.Name.SIX))  ||
							(name.equals(Card.Name.SEVEN)) ||
							(name.equals(Card.Name.EIGHT)) ||
							(name.equals(Card.Name.NINE)) ||
							(name.equals(Card.Name.TEN))  ||
							(name.equals(Card.Name.QUEEN)))
						return true;
				}
			}
			else if (bottomCard.getName() == Card.Name.ACE)
			{		 
			   if (request.getID() == Request.REQUEST_CARD_SUIT) 
			   {
				   Card.Suit suit = (Card.Suit)request.getArg();
				   return true;
			   }
			}
		}
		else return true; 
		return false;
	}
	
	public boolean testSelection()
	{ 
	  Card topStack = null; Card bottomCard = null;	
	  if (stack!=null)
	  {
		  Card[] cards = stack.getArray();
		  topStack = cards[stack.getCount()-1];
	  }	  
	  if (selection!=null) 
	  {
		  bottomCard = selection[0];
		  for (int i=0;i<selection.length;i++) if (!bottomCard.getName().equals(selection[i].getName())) return false; 	  
	  }
	  
	  if (incRequest!=null) if (incRequest.getID() == Request.REQUEST_WAITING ) return false;
	  if ((topStack != null) && (bottomCard!=null)) if (topStack.getName() == Card.Name.QUEEN || bottomCard.getName() == Card.Name.QUEEN) return true;
	  if (incRequest!=null && bottomCard!=null)
	  {	
		  if ( incRequest.getID() == Request.REQUEST_CARD_NAME )
		  {			  
			  Card.Name name = (Card.Name)incRequest.getArg();			  
			  return ( bottomCard.getName().equals(name) );			  			    	
		  }		 
		  else
			  if ( incRequest.getID() == Request.REQUEST_CARD_NAMES )
			  {			  
				  Card.Name[] names = (Card.Name[])incRequest.getArg();			  
				  boolean ret = false;
				  for (Card.Name name : names)
				  {
					 if (bottomCard.getName().equals(name)) ret = true;	
				  }
				  return ret;			  			    	
			  }
			  else 
				  if ( incRequest.getID() == Request.REQUEST_CARD_SUIT )
				  {
					  Card.Suit suit = (Card.Suit)incRequest.getArg();
					  return ( bottomCard.getSuit().equals(suit) ); 				   				
				  }		  	 	
	  }
	  if ((topStack != null) && (bottomCard!=null)) return ((topStack.getName() == bottomCard.getName()) || (topStack.getSuit() == bottomCard.getSuit()));
	  return true;
	}

	


}
