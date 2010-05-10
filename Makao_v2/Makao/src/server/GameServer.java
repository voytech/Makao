package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Enumeration;
import java.util.Hashtable;
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
	//private DeckOfCards cardPack = new DeckOfCards();
	//private CardStack table = new CardStack();
	//private CardStackGuard guard = new CardStackGuard(table);
	private PlayerQueue queue = null;
	private ServerControl control = new ServerControl(); 
	private RequestProcessor requester = new RequestProcessor(control);
	private Logger logger = null;
	//private Hashtable<PlayerHandle,Integer> waitings = new Hashtable<PlayerHandle,Integer>();
	public GameServer(int port) 
	{
		logger = Logger.getLogger("server.GameServer");
		logger.setLevel(Level.ALL);
		logger.log(Level.INFO,"---------------------------------------------------------------");
		logger.log(Level.INFO,"Welcome to Makao Game Server v2. Have a good time and good luck");
		logger.log(Level.INFO,"---------------------------------------------------------------");
		this.port = port;
		queue = control.initPlayersQueue(port);
	}
    private Packet waitForPacket(PlayerHandle player)
    {
    	Packet cpacket = null;
		logger.log(Level.INFO,"Waiting for packet from (player)");
		while(true)
		{		
			cpacket = player.getCurrentPacket();	
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
		return cpacket;
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
		//stopped=false;
		logger.log(Level.INFO,"Steady !!! Ready !!! GooOOooOOoOOOOooo !!! (Game started)");
		int tours = 1;
		//int players = queue.getPlayersCount();
		//int i=0;
		control.prepareGame();			
		PlayerHandle phandle = null;
		
		while(true)
		{
			phandle = control.getCurrentlyServed();
			//phandle.state().setActive(true);
			Packet packet = this.waitForPacket(phandle);
			shared.Request in = packet.getRequest();
			shared.Request out = null;
			try {
				out = requester.processRequest(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			/*int player_cards_num = phandle.state().getStackReference().length;
			if (player_cards_num == 0)
			{
				
			}*/
			//decreaseWaitingTours();
			tours++;
		}
		
	}
   /* private void registerWaitingPlayer(PlayerHandle player,int rounds)
    {
       	if (waitings.containsKey(player))
       	{
       		waitings.remove(player);
       	}
       	waitings.put(player, rounds);		
    }
    private void decreaseWaitingTours()
    {
    	for (int i=0;i<queue.getPlayersCount();i++)
    	{
    		PlayerHandle ph = queue.selectPlayer(i);
    		if (waitings.containsKey(ph))
    		{
    			int rounds = waitings.get(ph);
    			rounds--;
    			waitings.remove(ph);
    			if (rounds>0) waitings.put(ph, rounds); 
    		}	
    	} 	
    }
    
	
    
	public void bye() 
	{
		// TODO Auto-generated method stub
		
	}*/

}
