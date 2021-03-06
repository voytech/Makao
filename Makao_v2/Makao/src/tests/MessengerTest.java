package tests;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.* ;
import client.ImagesBuffer;
import shared.*;

public class MessengerTest {
	class Listener implements PacketListener
	{ 
		private String message = "Something different";
		private String s= "";
		public Listener(String s){this.s= s;}
		@Override
		public void packetReceived(Packet arg0) {
			message= arg0.getMessage();
		}
		public String getMessage()
		{
			return s+message;
		}
	}
	@Test
	public void testWhetherCanCreateMessengerOnSpecifiedSocket()
	{
		Socket client = new Socket();
		Messenger messenger = new Messenger(client);
		Assert.assertEquals(client, messenger.getSocket());
        //messenger.sendPacket();
		//messenger.receivePacket();
		//messenger.addPacketListener();
	}
	@Test
	public void testWhetherCanSendPacketByAMessenger()
	{
		Socket client = null;
		ServerSocket ssocket = null;
		Packet packet = new Packet("Hello world");
		try {
			ssocket = new ServerSocket(9090);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
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
		Socket handle = null;
		try {
			 handle = ssocket.accept();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Messenger messenger = new Messenger(client);
		try {
			messenger.sendPakcet(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (handle!=null)
		{
			ObjectInputStream ois = null;
		    try {
				ois = new ObjectInputStream(handle.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (ois!=null)
			{
				Packet read = null;
				try {
					Object or = ois.readObject();
					if (or instanceof Packet)
					{
						read = (Packet)or;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    try {
					client.close();
					ssocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Assert.assertEquals(packet.getMessage(), read.getMessage());			
			}
		}
		
        //messenger.sendPacket();
		//messenger.receivePacket();
		//messenger.addPacketListener();
	}
	@Test
	public void testWhetherCanSendPacketByAMessengerAndReceive()
	{
		Socket client = null;
		ServerSocket ssocket = null;
		Packet packet = new Packet("Hello world");
		try {
			ssocket = new ServerSocket(9090);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
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
		Socket handle = null;
		try {
			 handle = ssocket.accept();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Messenger messenger = new Messenger(client);
		   try {
				messenger.sendPakcet(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (handle!=null)
		{
		   Messenger messenger_receiver = new Messenger(handle);  
		   Listener l = new Listener("");
		   messenger_receiver.addPacketListener(l);
		   try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
				client.close();
				ssocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   Assert.assertEquals(packet.getMessage(), l.getMessage());
		}
        //messenger.sendPacket();
		//messenger.receivePacket();
		//messenger.addPacketListener();
	}
	@Test
	public void testWhetherCanSendPacketByAMessengerAndReceiveWhenMoreThanOneListeners()
	{
		Socket client = null;
		ServerSocket ssocket = null;
		Packet packet = new Packet("Hello world");
		try {
			ssocket = new ServerSocket(9090);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
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
		Socket handle = null;
		try {
			 handle = ssocket.accept();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Messenger messenger = new Messenger(client);
		   try {
				messenger.sendPakcet(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (handle!=null)
		{
		   Messenger messenger_receiver = new Messenger(handle);  
		   Listener l = new Listener("l1");
		   Listener l2 = new Listener("l2");
		   messenger_receiver.addPacketListener(l);
		   messenger_receiver.addPacketListener(l2);
		   try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		  try {
				client.close();
				ssocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   Assert.assertEquals("l1"+packet.getMessage(), l.getMessage());
		   Assert.assertEquals("l2"+packet.getMessage(), l2.getMessage());
		}
        //messenger.sendPacket();
		//messenger.receivePacket();
		//messenger.addPacketListener();
		
        //messenger.sendPacket();
		//messenger.receivePacket();
		//messenger.addPacketListener();
	}	
}
