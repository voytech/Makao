package tests;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import server.GameServer;
import shared.FileLogHandler;
import shared.Messenger;
import shared.Packet;
import shared.PacketListener;

import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import junit.framework.Assert;

public class TestGameServer {
    class Player extends Messenger implements PacketListener
    {
    	public ArrayList<Packet> list = new ArrayList<Packet>(); 
		public Player(Socket socket) 
		{
			super(socket);
			this.addPacketListener(this);
		}
		@Override
		public void packetReceived(Packet arg0) 
		{
			list.add(arg0);	
		}
    	
    }
	@Test
	public void testInitializingServerAndCollecting()
	{
		FileLogHandler handler = null;
		try {
			handler = new FileLogHandler();
			handler.setFileName("game-server-test");
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//SimpleFormatter formatter = new SimpleFormatter();
	    //handler.setFormatter(formatter);
		Logger sLogger = Logger.getLogger("players-queue");
		Logger logger = Logger.getLogger("game-server");
		
		sLogger.addHandler(handler);
		logger.addHandler(handler);
		
		GameServer game = new GameServer(9090);
		
		Player player1=null,player2=null,player3=null;
		try {
			player1 = new Player(new Socket("127.0.0.1",9090));
			player2 = new Player(new Socket("127.0.0.1",9090));
			player3 = new Player(new Socket("127.0.0.1",9090));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Assert.assertNotNull(game);
        try {
        	player1.sendPakcet(new Packet("status=ready"));
			player2.sendPakcet(new Packet("status=ready"));
			player3.sendPakcet(new Packet("status=ready"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		game.run();
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	
	}
}
