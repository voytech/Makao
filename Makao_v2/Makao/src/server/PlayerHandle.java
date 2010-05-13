package server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import shared.Card;
import shared.Messenger;
import shared.Packet;
import shared.ReadErrorListener;
import shared.Request;
import shared.RequestListener;

public class PlayerHandle extends Messenger implements RequestListener{	
	private Packet packet = null;
	private boolean received = false;
	//private Hashtable<String,String> buffer = new Hashtable<String,String>();
	private boolean enabled = false;
	private ArrayList<Request> requestBuffer = new ArrayList<Request>();
	
	private ArrayList<Card> pcards= new ArrayList<Card>();
	private boolean active = false;
	private int disabledRoundCount = 0; 
	private boolean makaoState = false;
	
	public PlayerHandle(Socket p_socket)
	{
		super(p_socket);
		addRequestListener(this);
	}
	public void sendRequest(Request request) throws IOException
	{
		super.sendRequest(request);
		//Request req = packet.getRequest();
		if (request!=null)
		{
			if (request.getID() == Request.REQUEST_TAKE) addReferenceCards(request.getCards());
			else  if (request.getID() == Request.REQUEST_WAITING) updateWaitingRoundsState(request.getNumber());
			else if (request.getID() == Request.REQUEST_ENABLE_PLAYER) setActive(true);
			else if (request.getID() == Request.REQUEST_DISABLE_PLAYER) setActive(false);
		}	
	}

	public boolean hasPacketReceived() {
		return received;
	}

	public Request getCurrentRequest() {
		received = false;
		if (requestBuffer.size()>0) return requestBuffer.remove(0);
		return null;	
	}

	/*public String getPlayerMessage(String key) {
		//if (packetBuffer.size()>0) packetBuffer.remove(0);
		if (buffer.containsKey(key)) return buffer.get(key);
		return "";
	}*/
	private void addReferenceCards(Card[] cards)
	{
		for (Card card : cards)
		{
			pcards.add(card);
		}
	}
	private void removeReferenceCards(Card[] cards)
	{
		ArrayList<Card> temp= new ArrayList<Card>();
		for (Card card : cards)
		{
			for (Card inA : pcards)
			{
				if (card.getName().equals(inA.getName()) && card.getSuit().equals(inA.getSuit()))
				{
					temp.add(inA);
				}
			}
		}
		for (Card card : temp) pcards.remove(card);
	}
	public void updateWaitingRoundsState(int drc)
	{
		disabledRoundCount = drc;
		active = false;
	}
	public void makaoSubmited(boolean state)
	{
		this.makaoState = true;
	}
	public boolean isMakaoSubmited()
	{
		return this.makaoState;
	}
	private void setActive(boolean active)
	{
		this.active = active;
	}
	public boolean isActive()          {return active;}
	public boolean isInQueue()          {return enabled;}
	public Card[] getStackReference()  {return pcards.toArray(new Card[0]);}
	public int getWaitingRoundsCount() {return disabledRoundCount;}
	@Override
	public void requestReceived(Request request) {	
		//String message= request.getMessage();
		if (request!=null)
		{
			if (request.getID() == Request.REQUEST_READY) enabled = true;
			if (isActive())
			{
				if (request.getID() == Request.REQUEST_PUSH)
				{
					Card[] cardsToPush  = request.getCards();
					removeReferenceCards(cardsToPush);	
				}
				this.received = true;
				requestBuffer.add(0,request);
			}
		}			
	}

}
