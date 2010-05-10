package server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import shared.Card;
import shared.CardStack;
import shared.CardStackGuard;
import shared.DeckOfCards;
import shared.Packet;
import shared.Request;

public class RequestProcessor {
    //ServerControlCentre
	private CardStack stack = null;
    private DeckOfCards all = null;
    private CardStackGuard guard = null;
    private ServerControl controlCentre = null;
    // ServerStateData
    private boolean requestReleased = false;
    private int  takeTimes = 0;
    private Request lastGenerated = null;	
    private Request output = null;
	private Card requestStarterCard = null;
	
	private Card[] array = null;
	private Card top=null;
	
	private Logger logger = Logger.getLogger("server.RequestProcessor");
    public RequestProcessor(ServerControl control) {
		this.controlCentre = control;
    	this.stack = control.getTableStack();
		array = stack.getArray();
    	this.all = control.getDeck();
		guard = new CardStackGuard(this.stack);
	}
    private void zerothRecentRequest() throws IOException
    {
		if (requestStarterCard!=null)	
    	{
				if (takeTimes==2)
				{
					takeTimes = 0;
					requestReleased = true;
					//lastGenerated = null;
					requestStarterCard = null;
				}					
    	}
		else 
			{
				requestReleased = false;
				if (takeTimes==2) takeTimes = 0;
			}	
    }
	public void processRequest(Request player_req) throws IOException 
	{
		zerothRecentRequest();		
		output = null;
		array = stack.getArray();
		top = array[array.length-1];	
		if (player_req!=null)
		{
	        byte reqID =player_req.getID();
			if (reqID == Request.REQUEST_PUSH)
			{		
				onPush(player_req);
			}
			else 
				if (reqID == Request.REQUEST_TAKE)
				{
					onTake();
				}
				else 
					if ((player_req.getID() == Request.REQUEST_CARD_NAME) || (player_req.getID() == Request.REQUEST_CARD_SUIT))
					{
						output = player_req;
						Packet packet = new Packet();
						packet.setRequest(output);						
						controlCentre.nextPlayerTurn().sendPakcet(packet);						
					}		
		}
		else requestFromTable();
		lastGenerated = output;
	}
	
	private void onTake() throws IOException
	{
		Packet packet = new Packet();
		if (takeTimes==0)
		{
			output = new Request(Request.REQUEST_TAKE,all.pop(1));
			packet.setRequest(output);
			controlCentre.sendPacketToCurrentlyServed(packet);
			takeTimes=1;
		}
		else
		if (takeTimes==1)
		{					
			int number_c = 0;
			// sometimes should be not null but it is null indeed.
			if (requestStarterCard!=null)
			{
				if (requestStarterCard.getName().equals(Card.Name.TWO) || requestStarterCard.getName().equals(Card.Name.THREE))
				{
					for (int i=array.length-1;i>=0;i--)
					{
						Card c = array[i];
						if (c.getName().equals(Card.Name.TWO)) number_c+=2;
						else if (c.getName().equals(Card.Name.THREE)) number_c+=3;
						if (c.equals(requestStarterCard)) break;
					}
					output = new Request(Request.REQUEST_TAKE,all.pop(number_c-1));
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
						output = new Request(Request.REQUEST_WAITING,number_c);							
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
							output = new Request(Request.REQUEST_TAKE,all.pop(number_c));
							//if (number_c>1) takeTimes = 0;
						}
						else output = null;
			}
			
			packet.setRequest(output);
			controlCentre.sendPacketToCurrentlyServed(packet);
			controlCentre.actualizePlayersStatuses();
			controlCentre.nextPlayerTurn();
			takeTimes = 2;										
		}	
	}
	
	private void onPush(Request incomingRequest) throws IOException
	{
		//secondTake = false;                   
        logger.log(Level.INFO,"Player requested card push (player - )");
        Card[] cards = incomingRequest.getCards();
        guard.setSelection(cards);
        if (guard.testSelection())
        {
        	   takeTimes=0;        	   
        	   stack.push(cards);
               controlCentre.actualizeStacks(cards); 
         	   output = requestFromTable();
         	   Packet packet = new Packet();
         	   packet.setRequest(output);
         	   controlCentre.actualizePlayersStatuses();
         	   if (output!=null)
         	   {
         		   if (output.getID() == Request.COMPOUND_REQUEST)
         		   {
         			   Request[] ingredients = output.getCompound();
         			   byte match = 0 ;
         			   for (Request req : ingredients)
         			   {
         				   if (req.getID() == Request.REQUEST_CARD_NAME && req.getArg().equals(Card.Name.KING)) match++;
         				   if (req.getID() == Request.REQUEST_CARD_SUIT && req.getArg().equals(Card.Suit.SPADE)) match++;
         				   if (match==2)
         				   {         				    
         					   controlCentre.previousPlayerTurn().sendPakcet(packet);
         					   return;
         				   }         			   
         			   }
         		   }
         	   }
         	   controlCentre.nextPlayerTurn().sendPakcet(packet);
        }
        else 
        {
        	controlCentre.rejectPlayer();
        }
        
	}
	private Request requestFromTable()
	{
		//Request output = null;
		Card[] array = stack.getArray();
		Card top = array[array.length-1];	
		if (!requestReleased)
		{
			if (top.getName().equals(Card.Name.TWO) || top.getName().equals(Card.Name.THREE))
			{
				output = new Request(Request.REQUEST_CARD_NAME,Card.Name.TWO);
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
		//lastGenerated = output;
		return output;
	}

}

