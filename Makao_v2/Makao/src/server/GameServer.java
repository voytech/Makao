package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.runner.Request;

import shared.Card;
import shared.CardStack;
import shared.CardStackGuard;
import shared.DeckOfCards;
import shared.Packet;

public class GameServer {

	private int port = 9090;
	private volatile boolean  stopped = true;
	private DeckOfCards cardPack = new DeckOfCards();
	private CardStack table = new CardStack();
	private CardStackGuard guard = new CardStackGuard(table);
	private RequestGenerator requester = new RequestGenerator(table,cardPack);
	private PlayerQueue queue = null;
	private Logger logger = null;
	public GameServer(int port) 
	{
		logger = Logger.getLogger("game-server");
		logger.setLevel(Level.ALL);
		logger.log(Level.INFO,"---------------------------------------------------------------");
		logger.log(Level.INFO,"Welcome to Makao Game Server v2. Have a good time and good luck");
		logger.log(Level.INFO,"---------------------------------------------------------------");
		this.port = port;
	    try {
			queue = new PlayerQueue(new ServerSocket(port));			
			logger.log(Level.INFO, "server port - "+port);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}	
	}

	public void run() 
	{
		while(!queue.isClosed())
	    {
			logger.log(Level.WARNING,"Game server can not start game... (sleeping)");
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
				logger.log(Level.SEVERE,"Could not fall asleep..");
				e.printStackTrace();
			}
	    }
		stopped=false;
		logger.log(Level.INFO,"Steady !!! Ready !!! GooOOooOOoOOOOooo !!! (Game started)");
		int tours = 1;
		int players = queue.getPlayersCount();
		prepare();	
		int i=0,ref_i=0;
		PlayerHandle phandle = null;
		try {
			phandle = this.setPlayerTourn(i);
			logger.log(Level.INFO,"Player switched to... (player - "+i+")");
		} catch (IOException e1) {
			logger.log(Level.SEVERE,"could not switched to... (player - "+i+")");
			e1.printStackTrace();
		}
		while(true)
		{
			logger.log(Level.INFO,"Starting tour : "+tours);
		while (i<players)
		{
			Packet cpacket = null;
			logger.log(Level.INFO,"Waiting for packet from (player - "+i+")");
			while(true)
			{		
				cpacket = phandle.getCurrentPacket();	
				if (cpacket!=null)
				{
					if (cpacket.getRequest()!=null) break;
				}
				try {
					Thread.currentThread().sleep(100);
				} catch (InterruptedException e) {
					logger.log(Level.SEVERE,"Could not fall asleep..");
					e.printStackTrace();
				}				
			}			
			{
				logger.log(Level.INFO,"Packet received from (player - "+i+")");
				shared.Request incomingRequest = cpacket.getRequest();
                if (incomingRequest.getID()==shared.Request.REQUEST_PUSH)
                {
                   ref_i=0;
                   logger.log(Level.INFO,"Player requested card push (player - "+i+")");
                   Card[] cards = incomingRequest.getCards();
                   guard.setSelection(cards);
                   if (guard.testSelection())
                   {
                       logger.log(Level.INFO,"Packet is valid due to rules (player - "+i+")");
                       table.push(cards);
                       try {
                    	   actualizeStacks(cards);
                    	   logger.log(Level.INFO,"Stack atualization succeeded... (player - "+i+")");
                       } catch (IOException e) {
                    	   logger.log(Level.SEVERE,"Stack atualization failed... (player - "+i+")");
                    	   e.printStackTrace();
                       }
                       logger.log(Level.INFO,"Preparing requests for next in queue (player - "+i+")");
                       shared.Request generatedRequest = requester.prepareRequest(null);   // What is the hack with this piece of shit.

                       int next_id = getNextPlayer(i);
                       PlayerHandle next = queue.selectPlayer(next_id);
                       Packet p = new Packet();
                       p.setRequest(generatedRequest);
                       try {
                    	   next.sendPakcet(p);
                       } catch (IOException e) {
								// TODO Auto-generated catch block
                    	   e.printStackTrace();
                       }
                       //this.waitUntilConsumed(next);
                       try {
                    	   phandle = this.setPlayerTourn(next_id);              
                    	   i=next_id;
                    	   logger.log(Level.INFO,"Player switched to... (player - "+i+")");
                       } catch (IOException e1) {
						// TODO Auto-generated catch block
                    	   logger.log(Level.SEVERE,"Can not switched to... (player - "+i+")");
                    	   e1.printStackTrace();
                       }
                   }
                   else 
                   {
                   		logger.log(Level.SEVERE,"Packet is not valid due to rules - game client may be incompatible !!!  (player"+i+")");
                   		logger.log(Level.SEVERE,"Player is going to be removed from queue (player"+i+")");
                   		//stopped = true;
                   }
                }
                else 
                	if (incomingRequest.getID()==shared.Request.REQUEST_TAKE)
                    {      
                			Packet p = new Packet();                	
                			logger.log(Level.INFO,"Preparing requests for current in queue (player - "+i+")");
                			shared.Request generatedRequest = requester.prepareRequest(incomingRequest);
                			if (generatedRequest==null) 
                			{
                				 int next_id = getNextPlayer(i);
                    			 try {
                              	   phandle = this.setPlayerTourn(next_id);
                              	   i=next_id;
                              	   logger.log(Level.INFO,"Player switched to... (player - "+i+")");
                                 } catch (IOException e1) 
                                 {      						
                                	 logger.log(Level.SEVERE,"Can not switched to... (player - "+i+")");
                                	 e1.printStackTrace();                          	  
                                 }
                                 //ref_i=0;
                			}
                			else
                			{
                				p.setRequest(generatedRequest);
                				try {
                					phandle.sendPakcet(p);
                				} catch (IOException e) {
                					// TODO Auto-generated catch block
                					e.printStackTrace();
                				}
                				if (generatedRequest.getID() == shared.Request.REQUEST_TAKE)
                				{
                					Card[] cards = generatedRequest.getCards();
                					if (cards!=null) 
                						if (cards.length>1) 
                						{
                							int next_id = getNextPlayer(i);
                               			 	try {
                                         	   phandle = this.setPlayerTourn(next_id);
                                         	   i=next_id;
                                         	   logger.log(Level.INFO,"Player switched to... (player - "+i+")");
                                            } catch (IOException e1) 
                                            {      						
                                           	 logger.log(Level.SEVERE,"Can not switched to... (player - "+i+")");
                                           	 e1.printStackTrace();                          	  
                                            }
                						}
                				}
                			//ref_i++;
                			}
                		}
                    //}                				
			}
		}
		tours++;
		}
	}

    private void waitUntilConsumed(PlayerHandle player)
    {
    	while(true)
    	{
    		Packet packet = null;
    		packet = player.getCurrentPacket();
    		if (packet.getRequest()!=null)
    			if (packet.getRequest().getID() == shared.Request.REQUEST_CONSUMED)
    				break;
    	}
    }

	private void actualizeStacks(Card[] toActualize,PlayerHandle[] clients) throws IOException
	{
		for (PlayerHandle player : clients)
		{
			Packet packet = new Packet();
			packet.setRequest(new shared.Request(shared.Request.REQUEST_CARDSTACK_ACTUALIZATION,toActualize));
			player.sendPakcet(packet);
		}
	}
	private void actualizeStacks(Card[] toActualize) throws IOException
	{
		for (int i=0;i<queue.getPlayersCount();i++)
		{
				PlayerHandle player = queue.selectPlayer(i);
				Packet packet = new Packet();
				packet.setRequest(new shared.Request(shared.Request.REQUEST_CARDSTACK_ACTUALIZATION,toActualize));
				player.sendPakcet(packet);
		}

	}
	private int getNextPlayer(int i)
	{
		if (i==queue.getPlayersCount()-1) return 0;
		i++;
		return i;
	}
	private PlayerHandle setPlayerTourn(int id) throws IOException
	{
		for (int i=0;i<queue.getPlayersCount();i++)
		{
			PlayerHandle player = queue.selectPlayer(i);
			Packet packet = new Packet();
			if (i==id) packet.setRequest(new shared.Request(shared.Request.REQUEST_ENABLE_PLAYER));
			else packet.setRequest(new shared.Request(shared.Request.REQUEST_DISABLE_PLAYER));
			player.sendPakcet(packet);
		}	
		return queue.selectPlayer(id);
	}
    public boolean prepare()
    {
    	logger.log(Level.INFO,"Game initialization...");
    	cardPack.shuffle();
    	logger.log(Level.INFO,"Deck of cards shuffled...");
    	int playerNum = 0;
    	Card tcard = cardPack.pop();
    	table.push(tcard);
    	logger.log(Level.INFO,"table initialized with first card...");
    	while(playerNum<queue.getPlayersCount())
    	{
    		logger.log(Level.INFO,"Initializing player ("+playerNum+") game space...(Players total"+queue.getPlayersCount()+")");
    		PlayerHandle handle = queue.selectPlayer(playerNum);
    		try {
				actualizeStacks(new Card[]{tcard},new PlayerHandle[]{handle});
				logger.log(Level.INFO,"Stack actualized...");
			} catch (IOException e1) {
				logger.log(Level.SEVERE,"Player ("+playerNum+") - stack atualization failed...");
				e1.printStackTrace();
			} 		
    		Packet packet = new Packet();
    			
    		shared.Request req = new shared.Request(shared.Request.REQUEST_TAKE,cardPack.pop(5));				                       
    		packet.setRequest(req);
    		try {
				handle.sendPakcet(packet);
				logger.log(Level.INFO,"Starter cards sent to player ("+playerNum+")");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.log(Level.SEVERE,"Could not send starter cards to player ("+playerNum+")");
				e.printStackTrace();
			}
    		playerNum++;
    	}
    	return true;
    }
	public void bye() 
	{
		// TODO Auto-generated method stub
		
	}

}
