package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import shared.Messenger;
import shared.Packet;
import shared.PacketListener;

public class PlayerHandle extends Messenger implements PacketListener{
	private Packet packet = null;
	private boolean received = false;
	private Hashtable<String,String> buffer = new Hashtable<String,String>();
	private ArrayList<Packet> packetBuffer = new ArrayList<Packet>();
	public PlayerHandle(Socket p_socket)
	{
		super(p_socket);
		addPacketListener(this);
	}

	public boolean hasPacketReceived() {
		return received;
	}

	public Packet getCurrentPacket() {
		received = false;
		if (packetBuffer.size()>0) return packetBuffer.remove(0);
		return null;	
	}

	public String getPlayerMessage(String key) {
		//if (packetBuffer.size()>0) packetBuffer.remove(0);
		if (buffer.containsKey(key)) return buffer.get(key);
		return "";
	}

	@Override
	public void packetReceived(Packet packet) {
		String message= packet.getMessage();
		if (message!=null)
		{
			if (!message.equals(""))
			{
				String[] keyValue = message.split("=");		
				if (buffer.containsKey(keyValue[0])) buffer.remove(keyValue[0]);
				this.buffer.put(keyValue[0], keyValue[1]);
			    //return;	
			}
		}
		this.received = true;
		this.packet = packet;
		packetBuffer.add(0,packet);	
	
	}
}
