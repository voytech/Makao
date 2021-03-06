package tests;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import shared.*;
import org.junit.* ;

import server.PlayerHandle;
import static org.junit.Assert.* ;
public class ClientHandleTest {
	@Test
	public void testWetherCanReadFromClientHandle()
	{
		Packet packet = new Packet();
		packet.setMessage("nick=Ciechu");
		Socket client = null;
		ServerSocket ssocket = null;
		try {
			ssocket = new ServerSocket(9090);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			client = new Socket("127.0.0.1",9090);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Messenger cMessenger = new Messenger(client);
		try {
			cMessenger.sendPakcet(packet);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (ssocket!=null)
		{
			Socket clientS = null;
			try {
				clientS = ssocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (clientS!=null)
			{
				PlayerHandle handle = new PlayerHandle(clientS);	
				while(!handle.hasPacketReceived())
				{	
					//waiting for packet
				}
				try {
					clientS.close();
					client.close();
					ssocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Packet packetRecieved = handle.getCurrentPacket();	
				Assert.assertEquals(packetRecieved.getMessage(), packet.getMessage());
			}
		}		
	}
	@Test
	public void testWetherCanReadFromMessageBuffer()
	{
		Packet packet = new Packet();
		packet.setMessage("nick=Ciechu");
		Socket client = null;
		ServerSocket ssocket = null;
		try {
			ssocket = new ServerSocket(9090);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			client = new Socket("127.0.0.1",9090);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Messenger cMessenger = new Messenger(client);
		try {
			cMessenger.sendPakcet(packet);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (ssocket!=null)
		{
			Socket clientS = null;
			try {
				clientS = ssocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (clientS!=null)
			{
				PlayerHandle handle = new PlayerHandle(clientS);	
				while(!handle.hasPacketReceived())
				{	
					//waiting for packet
				}
				try {
					clientS.close();
					client.close();
					ssocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String nick = handle.getPlayerMessage("nick");	
				Assert.assertEquals("Ciechu", nick);
			}
		}		
	}
}
