package server;

import java.net.Socket;

import shared.Messenger;
import shared.Packet;
import shared.PacketListener;

public class PlayerHandle implements PacketListener{
    private Messenger messenger = null;
	private Packet packet = null;
    public PlayerHandle(Socket p_socket)
	{
		messenger = new Messenger(p_socket);
		messenger.addPacketListener(this);
	}

	public boolean hasPacketReceived() {
		if (packet!=null) return true;
		return false;
	}

	public Packet getCurrentPacket() {
		return packet;	
	}

	public void getPlayerInfo() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void packetReceived(Packet packet) {
		this.packet = packet;	
	}
}
