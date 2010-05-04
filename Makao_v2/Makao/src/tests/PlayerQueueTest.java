package tests;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.*; 
import static org.junit.Assert.* ;
import server.PlayerHandle;
import server.PlayerQueue;
import shared.*;

public class PlayerQueueTest {
	
	@Test
	public void testCollectingPlayersToQueue()
	{
		int pc =0;
		ServerSocket server = null;
		Messenger player1 = null,player2= null;
		PlayerQueue pqueue = null;
		try
		{
			server = new ServerSocket(9090);
			pqueue = new PlayerQueue(server);

			
			Socket player1s = new Socket("127.0.0.1",9090);
			Socket player2s = new Socket("127.0.0.1",9090);
			player1 = new Messenger(player1s);
			player2 = new Messenger(player2s);
		}catch(Exception e){e.printStackTrace();};	
		Assert.assertNotNull(server);
			
		try {
			player1.sendPakcet(new Packet("status=ready"));
			player2.sendPakcet(new Packet("status=ready"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!pqueue.isClosed()){}
        
		pc = pqueue.getPlayersCount();	
		Assert.assertEquals(2, pc);
		Assert.assertTrue(pqueue.isClosed());
		//
	}	
	//@Test
	public void testSelectingPlayer()
	{
		int pc =0;
		ServerSocket server = null;
		Messenger player1 = null,player2= null;
		try
		{
			server = new ServerSocket(9090);
			Socket player1s = new Socket("127.0.0.1",9090);
			Socket player2s = new Socket("127.0.0.1",9090);
			player1 = new Messenger(player1s);
			player2 = new Messenger(player2s);
		}catch(Exception e){e.printStackTrace();};	
		Assert.assertNotNull(server);
		PlayerQueue pqueue = new PlayerQueue(server);	
		PlayerHandle player0 = pqueue.selectPlayer(0);
		//player0.sendPakcet(packet)
		
		
	}	
}
