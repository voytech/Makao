package server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import shared.Card;
import shared.Messenger;
import shared.Packet;
import shared.PacketListener;
import shared.Request;

public class PlayerHandle extends Messenger implements PacketListener{	
	private PlayerState state = null;
	private Packet packet = null;
	private boolean received = false;
	private Hashtable<String,String> buffer = new Hashtable<String,String>();
	private ArrayList<Packet> packetBuffer = new ArrayList<Packet>();
	class PlayerState
	{
		private ArrayList<Card> pcards= new ArrayList<Card>();
		private boolean active = false;
		private int disabledRoundCount = 0; 
		void addReferenceCards(Card[] cards)
		{
			for (Card card : cards)
			{
				pcards.add(card);
			}
		}
		void removeReferenceCards(Card[] cards)
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
		public void setActive(boolean active)
		{
			this.active = active;
		}
		public boolean isActive()          {return active;}
		public Card[] getStackReference()  {return pcards.toArray(new Card[0]);}
		public int getWaitingRoundsCount() {return disabledRoundCount;}
	}
	public PlayerHandle(Socket p_socket)
	{
		super(p_socket);
		addPacketListener(this);
		state = new PlayerState();
	}
	public void sendPakcet(Packet packet) throws IOException
	{
		super.sendPakcet(packet);
		Request req = packet.getRequest();
		if (req!=null)
		{
			if (req.getID() == Request.REQUEST_TAKE) state.addReferenceCards(req.getCards());
			else  if (req.getID() == Request.REQUEST_WAITING) state.updateWaitingRoundsState(req.getNumber());
			else if (req.getID() == Request.REQUEST_ENABLE_PLAYER) state.setActive(true);
			else if (req.getID() == Request.REQUEST_DISABLE_PLAYER) state.setActive(false);
		}	
	}
    public PlayerState state()
    {
    	return state;
    }
	public boolean hasPacketReceived() {
		return received;
	}

	public Packet getCurrentPacket() {
		received = false;
		if (packetBuffer.size()>0) return packetBuffer.remove(0);
		return null;	
	}

	public String getPlayerMessage(String key) {
		//if (packetBuffer.size()>0) packetBuffer.remove(0);
		if (buffer.containsKey(key)) return buffer.get(key);
		return "";
	}

	@Override
	public void packetReceived(Packet packet) {	
		String message= packet.getMessage();
		if (message!=null)
		{
			if (!message.equals(""))
			{
				String[] keyValue = message.split("=");		
				if (buffer.containsKey(keyValue[0])) buffer.remove(keyValue[0]);
				this.buffer.put(keyValue[0], keyValue[1]);
			    //return;	
			}
		}
		if (state.isActive())
		{
			Request req = packet.getRequest();
			if (req!=null)
			{
				if (req.getID() == Request.REQUEST_PUSH)
				{
					Card[] cardsToPush  = req.getCards();
					this.state.removeReferenceCards(cardsToPush);	
				}
			}
			this.received = true;
			this.packet = packet;
			packetBuffer.add(0,packet);
			
		}	
	}
}
