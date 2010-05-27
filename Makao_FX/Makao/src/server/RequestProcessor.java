package server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import shared.Card;
import shared.CardStack;
import shared.CardStackGuard;
import shared.DeckOfCards;
import shared.Request;

public class RequestProcessor {
    //ServerControlCentre
	private CardStack stack = null;
    private DeckOfCards all = null;
    private CardStackGuard guard = null;
    private ServerControl controlCentre = null;
    // ServerStateData
    private int  takeTimes = 0;
     
    //private Request
    private Request previousRequest = null;
    private Request requestBuffer = null;
    private PlayerHandle requestInvoker = null;
    //private Request lastGenerated = null;	
    private Request output = null;
	private Card requestStarterCard = null;
	
	private Card[] array = null;
	//private Card top=null;
	
	private Logger logger = Logger.getLogger("server.RequestProcessor");
    public RequestProcessor(ServerControl control) 
    {
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
				requestStarterCard = null;
				previousRequest = null;
			}					
    	}
		else 
			{
				if (takeTimes==2) takeTimes = 0;
			}	
    }
    private void processSingleRequest(Request player_req) throws IOException
    {
    	if (player_req!=null)
		{
    		logger.log(Level.INFO,"Incoming request is not empty");
	        byte reqID =player_req.getID();
			if (reqID == Request.REQUEST_PUSH)
			{
				logger.log(Level.INFO,"Card push requested");
		    	if (requestBuffer !=null)
		    	{
		    		logger.log(Level.INFO,"\"SUIT OR CARD\" request mode");
		    		if ((requestBuffer.getID() == Request.REQUEST_CARD_NAME) || (requestBuffer.getID() == Request.REQUEST_CARD_SUIT))
		    		{
		    			if (!controlCentre.isRoundCompleted())
		    			{			
		    				Request comp = new Request(Request.REQUEST_PUSH,new Request[]{requestBuffer}); // add all possible request from player here.
		    				comp.setCards(player_req.getCards());
		    				onCompoundPush(comp);
		    			}
		    			else 
		    			{
		    				logger.log(Level.INFO,"Exiting \"SUIT OR CARD\" request mode");		
		    				this.requestBuffer = null;
		    				this.requestInvoker = null;
		    				guard.setIncomingRequest(null);
		    				onCompoundPush(player_req);
		    			}
		    		}
		    		else onCompoundPush(player_req);
		    	}
		    	else onCompoundPush(player_req);
			}
			else 
				if (reqID == Request.REQUEST_TAKE)
				{
					onTake();
				}
		}
		//else requestFromTable();
    }
	public void processRequest(Request player_req) throws IOException 
	{
		logger.log(Level.WARNING,"Starting request processing...");
		zerothRecentRequest();		
		output = null;	
		if (player_req!=null)
		{
			processSingleRequest(player_req);
		}
		logger.log(Level.WARNING,"Request processing completed...");
	}
	
	private void onTake() throws IOException
	{
		logger.log(Level.INFO,"\"TAKE\" request mode");		
		if (takeTimes==0)
		{
			logger.log(Level.INFO,"\"FIRST TAKE\" request mode");    		
			output = new Request(Request.REQUEST_TAKE,all.pop(1));
			(requestInvoker = controlCentre.getCurrentlyServed()).sendRequest(output);
			takeTimes=1;
		}
		else
		if (takeTimes==1)
		{
			logger.log(Level.INFO,"\"SECOND TAKE\" request mode"); 		
			int number_c = 0;
			if (requestStarterCard!=null)
			{
				logger.log(Level.INFO,"Request starter is active - underlaying pending request");	
				array = stack.getArray();
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
							for (int i=array.length-1;i>=0;i--)
							{
								Card c = array[i];
								if (c.getName().equals(Card.Name.KING) && (requestStarterCard.getSuit().equals(Card.Suit.HEART) || requestStarterCard.getSuit().equals(Card.Suit.SPADE))) number_c+=5;
								if (c.equals(requestStarterCard)) break;		
							}
							output = new Request(Request.REQUEST_TAKE,all.pop(number_c-1));
						}
						else output = null;
			}			
			controlCentre.getCurrentlyServed().sendRequest(output);
			controlCentre.actualizePlayersStatuses();
			controlCentre.nextPlayerTurn();
			takeTimes = 2;										
		}	
	}
	private void onCompoundPush(Request incomingRequest) throws IOException
	{
		logger.log(Level.INFO,"CARD AND REQUEST PUSH request mode");
		Request[] reqs = incomingRequest.getCompound();
		if (reqs!=null) 
			//if (reqs.length>0)
		{
			Request makaoReq=null,suitNameReq=null,punishment = null;
			for (Request req : reqs)
			{
				if (req.getID() == Request.REQUEST_MAKAO ) makaoReq = req;
				else if (req.getID() == Request.REQUEST_PUNISHMENT) punishment = req;
				else if ((req.getID() == Request.REQUEST_CARD_NAME) || (req.getID() == Request.REQUEST_CARD_SUIT)) suitNameReq = req;
			}
			if (makaoReq!=null)
			{
				PlayerHandle phandle = controlCentre.getCurrentlyServed();
			    Card[] cards = phandle.getStackReference();
			    if (cards.length==1)
			    {
			    	phandle.makaoSubmited(true);		    	
			    }
			}
			if (punishment!=null)
			{
				controlCentre.executeMakaoPunishment();
			}
			if ( suitNameReq != null )
			{
				boolean goForward = true;
				Card[] cards = incomingRequest.getCards();
				guard.setSelection(cards);		        
				if (this.requestBuffer!=null) guard.setIncomingRequest(suitNameReq);//?
				else  goForward = guard.setOutgoingRequest(suitNameReq);       	
				if (guard.testSelection() && goForward)
				{
					takeTimes=0;        	   
					stack.push(cards);
					controlCentre.actualizeStacks(cards); 
		           	controlCentre.actualizePlayersStatuses(); 
		         	this.requestInvoker = controlCentre.getCurrentlyServed();
		         	if (this.requestBuffer == null) 
		         	{
		         		controlCentre.markRoundStart();
		         		this.requestBuffer = suitNameReq;
		         	}		         	
		         	controlCentre.nextPlayerTurn().sendRequest(suitNameReq);		         	    
				}
				else 
				{
					controlCentre.rejectPlayer(); //This doesn't work correctly
				}
				guard.setIncomingRequest(null);
				guard.setSelection(null); 					
			}
			else 
			{
				Request req = new Request(Request.REQUEST_PUSH);
				req.setCards(incomingRequest.getCards());
				onPush(req);
			}
		}
		else 
		{
			onPush(incomingRequest);
		}
	}
	private void onPush(Request incomingRequest) throws IOException
	{
		//secondTake = false;                   
        logger.log(Level.INFO,"CARD PUSH request mode");
        Card[] cards = incomingRequest.getCards();
        //guard.setIncomingRequest(null);
        guard.setSelection(cards);
        if (previousRequest!=null){
        	guard.setIncomingRequest(previousRequest);
        	previousRequest = null;
        }
        if (guard.testSelection())
        {
        	   takeTimes=0;        	   
        	   stack.push(cards);
               controlCentre.actualizeStacks(cards); 
         	   output = requestFromTable(cards);
         	   //previousRequest = output;
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
         				   if (req.getID() == Request.REQUEST_CARD_SUIT && req.getArg().equals(Card.Suit.HEART)) match++;	   		   
         			   }
         			   if (match==2)
    				   { 
         				   controlCentre.previousPlayerTurn().sendRequest(output);
    				       guard.setIncomingRequest(null);
    				       guard.setSelection(null);
    					   return;
    				   }         	
         		   }
         	   }
         	   controlCentre.nextPlayerTurn().sendRequest(output);
        }
        else 
        {
        	controlCentre.rejectPlayer();
        }
        guard.setIncomingRequest(null);
        guard.setSelection(null);   
	}
	private Request requestFromTable(Card[] fromPreviousPlayer)
	{
		previousRequest =  null;
		logger.log(Level.INFO,"TABLE RAISED request mode");
		Card top = fromPreviousPlayer[fromPreviousPlayer.length-1];	
		Card bottom = fromPreviousPlayer[0];
		{
			if (top.getName().equals(Card.Name.TWO) || top.getName().equals(Card.Name.THREE))
			{
				output = new Request(Request.REQUEST_CARD_NAMES,new Card.Name[]{Card.Name.TWO,Card.Name.THREE});
				if (requestStarterCard == null) requestStarterCard = bottom;
			}
			if (top.getName().equals(Card.Name.FOUR))
			{
				output = new Request(Request.REQUEST_CARD_NAME,Card.Name.FOUR);
				if (requestStarterCard == null) requestStarterCard = bottom;
			}
			if (top.getName().equals(Card.Name.KING) && bottom.getSuit().equals(Card.Suit.SPADE))
			{
				output = new Request(Request.COMPOUND_REQUEST,new Request[]{new Request(Request.REQUEST_CARD_NAME,Card.Name.KING),
																			new Request(Request.REQUEST_CARD_SUIT,Card.Suit.HEART)});
				if (requestStarterCard == null) requestStarterCard = bottom;
			}
			if (top.getName().equals(Card.Name.KING) && top.getSuit().equals(Card.Suit.HEART))
			{
				output = new Request(Request.COMPOUND_REQUEST,new Request[]{new Request(Request.REQUEST_CARD_NAME,Card.Name.KING),																			new Request(Request.REQUEST_CARD_SUIT,Card.Suit.SPADE)});
				if (requestStarterCard == null) requestStarterCard = bottom;
			}
			
		}
		previousRequest = output;
		return output;
	}

}

