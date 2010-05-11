package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import shared.Card;
import shared.CardStack;
import shared.CardStackGuard;
import shared.DeckOfCards;
import shared.Packet;
import shared.Request;

public class ServerControl {
	private CardStack table = new CardStack();
    private DeckOfCards all = new DeckOfCards();
    private CardStackGuard guard = new CardStackGuard(table);
    private PlayerQueue queue = null;
    private PlayerHandle currentlyServed = null;
    private int playerID = 0; 
    private int roundPoint = 0;
    private Logger logger = Logger.getLogger("server.GameServer");
    public CardStack getTableStack()
    {
    	return table;
    }
    public DeckOfCards getDeck()
    {
    	return all;
    }
    public PlayerHandle getCurrentlyServed()
    {
    	currentlyServed = queue.selectPlayer(playerID);
    	return currentlyServed;
    }
    public PlayerHandle getSelectedPlayer(int i)
    {
    	return queue.selectPlayer(i);
    }
    public PlayerQueue initPlayersQueue(int port)
    {
    	try {
			queue = new PlayerQueue(new ServerSocket(port));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return queue;
    }
	public int getNextPlayerID()
	{
		if (playerID < roundPoint) restoreRoundPoint();
		if (playerID == queue.getPlayersCount()-1) playerID=0;
		else playerID++;
		roundPoint = playerID;
		return playerID;
	}
	public int getPreviousPlayerID()
	{
		if (playerID == 0) playerID = queue.getPlayersCount()-1;
		else playerID--;
		return playerID;
	}
	public void actualizeStacks(Card[] toActualize) throws IOException
	{
		for (int i=0;i<queue.getPlayersCount();i++)
		{
			PlayerHandle player = queue.selectPlayer(i);
			Packet packet = new Packet();
			packet.setRequest(new shared.Request(shared.Request.REQUEST_CARDSTACK_ACTUALIZATION,toActualize));
			player.sendPakcet(packet);
		}
	}
	public void actualizePlayersStatuses() throws IOException
	{
		for (int i=0;i<queue.getPlayersCount();i++)
		{
			PlayerHandle player = queue.selectPlayer(i);
			if (!player.equals(currentlyServed))
			{
				String status = "opponents_cards=";
				Packet packet = new Packet();	
				for (int j=0;j<queue.getPlayersCount();j++)
				{
					if (i!=j)
					{
						int count = queue.selectPlayer(j).getStackReference().length;
						status+=(j+":"+count+";");
					}
				}
				packet.setRequest(new shared.Request(shared.Request.REQUEST_STRING_MESSAGE,status));
				player.sendPakcet(packet);
			}
		}
	}
	public int restoreRoundPoint()
	{
		playerID = roundPoint;
		return playerID;	
	}
	public void sendPacketToCurrentlyServed(Packet packet) throws IOException
	{
		currentlyServed.sendPakcet(packet);	
	}
	public PlayerHandle previousPlayerTurn() throws IOException
	{		
		getPreviousPlayerID();
    	int i=0;
    	while (i<queue.getPlayersCount())
		{
			PlayerHandle player = queue.selectPlayer(i);
			Packet packet = new Packet();
			if (i==playerID) 
			{
				int waitings = player.getWaitingRoundsCount();
				if (waitings > 0)
				{
					//packet.setRequest(new shared.Request(shared.Request.REQUEST_DISABLE_PLAYER));
					player.updateWaitingRoundsState(--waitings);
					getPreviousPlayerID();
					i=0;
					continue;
				}				
				else 
					{
						packet.setRequest(new shared.Request(shared.Request.REQUEST_ENABLE_PLAYER));
						//player.state().setActive(true);
					}
			}
			else 
				{
					packet.setRequest(new shared.Request(shared.Request.REQUEST_DISABLE_PLAYER));
					//player.state().setActive(false);
				}
			player.sendPakcet(packet);
			i++;
		}	
    	this.currentlyServed = queue.selectPlayer(playerID); 
		return currentlyServed;
	}
    public PlayerHandle nextPlayerTurn() throws IOException
    {
    	getNextPlayerID();
    	int i=0;
    	while (i<queue.getPlayersCount())
		{
			PlayerHandle player = queue.selectPlayer(i);
			Packet packet = new Packet();
			if (i==playerID) 
			{
				int waitings = player.getWaitingRoundsCount();
				if (waitings > 0)
				{
					//packet.setRequest(new shared.Request(shared.Request.REQUEST_DISABLE_PLAYER));
					player.updateWaitingRoundsState(--waitings);
					getNextPlayerID();
					i=0; 
					continue;
				}				
				else 
					{
						packet.setRequest(new shared.Request(shared.Request.REQUEST_ENABLE_PLAYER));
						//player.state().setActive(true);
					}
			}
			else 
				{
					packet.setRequest(new shared.Request(shared.Request.REQUEST_DISABLE_PLAYER));
					//player.state().setActive(false);
				}
			player.sendPakcet(packet);
			i++;
		}	
    	this.currentlyServed = queue.selectPlayer(playerID); 
		return currentlyServed;
    }
    public void rejectPlayer()
    {
    	PlayerHandle handle   = queue.selectPlayer(playerID);
    	Card[] cardsToRestore = handle.getStackReference();
    	queue.remove(playerID);
    	all.push(cardsToRestore);
    	all.shuffle();
    }
    public boolean prepareGame()
    {
    	logger.log(Level.INFO,"Game initialization...");
    	all.shuffle();
    	logger.log(Level.INFO,"Deck of cards shuffled...");
    	int playerNum = 0;
    	Card tcard = all.pop();
    	table.push(tcard);
    	try {
			this.actualizeStacks(new Card[]{tcard});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	logger.log(Level.INFO,"table initialized with first card...");
    	while(playerNum<queue.getPlayersCount())
    	{
    		logger.log(Level.INFO,"Initializing player ("+playerNum+") game space...(Players total"+queue.getPlayersCount()+")");
    		PlayerHandle handle = queue.selectPlayer(playerNum);
    		Packet packet = new Packet();	
    		shared.Request req = new shared.Request(shared.Request.REQUEST_TAKE,all.pop(5));				                       
    		packet.setRequest(req);
    		try {
				handle.sendPakcet(packet);
				Packet p = new Packet();
				if (playerNum==0)
				{
					p.setRequest(new shared.Request(Request.REQUEST_ENABLE_PLAYER));
					//handle..setActive(true);
				}
				else 
					{
						p.setRequest(new shared.Request(Request.REQUEST_DISABLE_PLAYER));
						//handle.state().setActive(false);
					}
				handle.sendPakcet(p);
				logger.log(Level.INFO,"Starter cards sent to player ("+playerNum+")");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.log(Level.SEVERE,"Could not send starter cards to player ("+playerNum+")");
				e.printStackTrace();
			}
    		playerNum++;
    	}
    	try {
			this.actualizePlayersStatuses();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return true;
    }
    public void announceWinner() throws IOException
    {
    	PlayerHandle winner = null;
    	for (PlayerHandle player : queue.getPlayers())
    	{
    		Card[] sRef = player.getStackReference();
    		if (sRef.length == 0) {winner = player; break;}
    	}
    	if (winner!=null)
    	{
    		for (PlayerHandle player : queue.getPlayers())
    		{
    			Packet packet = new Packet();
    			Request req = null;
    			if (player.equals(winner)) req = new Request(Request.REQUEST_WINNER,"You are WINNER !!!");	
    			else req =new Request(Request.REQUEST_WINNER,"You have FAILED :("); 
    			packet.setRequest(req);
    			player.sendPakcet(packet);	
    		}
    	}
    }
}
