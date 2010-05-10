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
	private Logger logger;
	public PlayerQueue(ServerSocket server) 
	{
		logger = Logger.getLogger("players-queue");
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

	private boolean checkInternallyIfClosed() {
		// TODO Auto-generated method stub
		//synchronized(players)
		//{
			int count = 0;
			logger.log(Level.INFO, "checking players status");
			int q=0;
			for (PlayerHandle player : players)
			{
				String is_ready = player.getPlayerMessage("status");
				if (is_ready.equals("ready")) count++;
				else is_ready = "not ready"; 
				logger.log(Level.INFO, "Player "+(q++)+" - "+is_ready);
			}
			if ((count == players.size()) && (players.size()>=2)) isClosed = true;
		//}
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
	@Override
	public void run() {
		while(!checkInternallyIfClosed())
		{
			//synchronized(players)
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
				// TODO Auto-generated catch block
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
	}

	public PlayerHandle selectPlayer(int i) 
	{
		// TODO Auto-generated method stub
		return players.get(i);
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
			try {
				handle.closeInput();
				handle.closeOutput();
				handle.getSocket().close();
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Error while removing player handle");
				e.printStackTrace();
			}
		}
		players.remove(invoker);
	}	
}
