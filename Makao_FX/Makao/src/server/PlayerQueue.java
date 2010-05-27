package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import shared.ReadErrorListener;

public class PlayerQueue implements Runnable, ReadErrorListener 
{
	private Thread thread = null;
	private ServerSocket server = null;
	private ArrayList<PlayerHandle> players = new ArrayList<PlayerHandle>();
	private volatile boolean isClosed = false;
	private volatile boolean isCleared = false;
	private Logger logger;
	public PlayerQueue(ServerSocket server) 
	{
		logger = Logger.getLogger("server.PlayerQueue");
		logger.setLevel(Level.ALL);
		this.server = server;
		try {
			this.server.setSoTimeout(200);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread = new Thread(this);
		thread.start();
	}

	private boolean checkIfClosed() {
			int count = 0;
			logger.log(Level.INFO, "checking players status");
			for (PlayerHandle player : players)
			{
				if (player.isReady()) 
			    {
					count++;
					logger.log(Level.INFO, "player ready - ( Player "+count+" )");
			    }
				logger.log(Level.INFO, "player not ready - ( Player "+count+" )");
			}
			if ((count == players.size()) && (players.size()>=2)) isClosed = true;
		return isClosed;
	}

	public int getPlayersCount() {
		// TODO Auto-generated method stub
		return players.size();
	}
	public boolean isClosed()
	{
		return isClosed;
	}
	public boolean isCleared()
	{
		return isCleared;
	}
	@Override
	public void run() {
		while(!checkIfClosed())
		{
				if (this.server==null) 
				{
					logger.log(Level.SEVERE, "Server panic - socket not initialized");
					break;
				}			
				Socket player_s = null;
				try {
					logger.log(Level.INFO, "listening incomming connections");
					player_s = server.accept();
				} catch (IOException e) {
					e.printStackTrace();
					logger.log(Level.WARNING, e.getMessage());
					//server.
				}
				if (player_s!=null)
				{
					PlayerHandle player = new PlayerHandle(player_s);
					player.addReadErrorListener(this);
					players.add(player);
					logger.log(Level.INFO, "new player joined, total players : "+players.size());
				}
		}
	}

	public PlayerHandle selectPlayer(int i) 
	{
		// TODO Auto-generated method stub
		if (players.size()>i) return players.get(i);
		return null;
	}

	public PlayerHandle[] getPlayers() 
	{
		// TODO Auto-generated method stub
		return players.toArray(new PlayerHandle[0]);
	}

	public void remove(int playerID) 
	{
		players.remove(playerID);
	}
	@Override
	public void readErrorOccured(Exception arg0,Object invoker) {
		if (invoker instanceof PlayerHandle)
		{
			PlayerHandle handle = (PlayerHandle)invoker;
			int index = players.indexOf(handle);
			logger.log(Level.SEVERE, "Player's connection rejected (Player - "+index+")");			
			players.remove(invoker);
			logger.log(Level.SEVERE, "Player removed (Player - "+index+")") ;
		}
		if (players.size() < 2)
		{
			players.clear();
			isCleared = true;
		}
	}	
}
