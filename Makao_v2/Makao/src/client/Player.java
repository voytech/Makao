package client;

import gui.PlayerSpace;

import java.net.Socket;

import server.PlayerHandle;
import shared.Card;
import shared.CardStack;
import shared.Packet;
import shared.Request;

public class Player extends PlayerHandle{
   // private CardStack playerCards = new CardStack();
   // private CardStack table =new CardStack();
    public Player(Socket pSocket) {
		super(pSocket);
	}

	/*public Card[] getPlayerCards() {
		// TODO Auto-generated method stub
		return playerCards.getArray();
	}
	public void packetReceived(Packet packet)
	{

	}*/

}
