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
    private shared.Request waitForRequest(PlayerHandle player)
    {
    	shared.Request req = null;
		logger.log(Level.INFO,"Waiting for packet from (player)");
		while(true)
		{		
			req = player.getCurrentRequest();	
			if (req!=null) break;
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				logger.log(Level.SEVERE,"Could not fall asleep..");
				e.printStackTrace();
			}				
		}
		return req;
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
		logger.log(Level.INFO,"Steady !!! Ready !!! GooOOooOOoOOOOooo !!! (Game started)");
		control.prepareGame();			
		PlayerHandle phandle = null;
		RoundCounter rCounter = RoundCounter.getInstance();
		while(true)
		{
			logger.log(Level.INFO,"------------------------");
			logger.log(Level.WARNING,"Starting round :"+rCounter.getRoundNumber());
			logger.log(Level.INFO,"------------------------");
			phandle = control.getCurrentlyServed();
			shared.Request in = this.waitForRequest(phandle);
			try {
				requester.processRequest(in);
			} catch (IOException e) {
				logger.log(Level.SEVERE,"Fatal error occured while processing request"+e.getLocalizedMessage());e.printStackTrace();
			}		
			int player_cards_num = phandle.getStackReference().length;
			if (player_cards_num == 0)
			{
				logger.log(Level.INFO,"----------------");
				logger.log(Level.SEVERE, "GAME OVER... ");
				logger.log(Level.INFO,"----------------");
				try {
					control.announceWinner();
				} catch (IOException e) {
					logger.log(Level.SEVERE,"Fatal error occured while announcing winner"+e.getLocalizedMessage());
					e.printStackTrace();
				}
				break;
			}
			rCounter.nextRound();
		}
		
	}
  
	public void bye() 
	{
		// TODO Auto-generated method stub
		
	}

}
