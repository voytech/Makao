package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import shared.Card;
import shared.Messenger;
import shared.Packet;
import shared.PacketListener;
import shared.Request;

import client.Player;

public class PlayerInterface extends JPanel implements ActionListener, PacketListener{
	private JButton takeCard,putCard,requestSuit,requestName,connect,ready;
	private JLabel tourIndicator;
	private Messenger player = null;
	public PlayerInterface(Messenger player)
	{
		this.setLayout(null);
	    this.player = player;
	    this.player.addPacketListener(this);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		takeCard = new JButton("Take");
		takeCard.addActionListener(this);
		putCard = new JButton("Put");
		putCard.addActionListener(this);
		requestSuit = new JButton("Request suit");
		requestSuit.addActionListener(this);
		requestName = new JButton("Request name");
		requestName.addActionListener(this);
		connect = new JButton("Connect");
		connect.addActionListener(this);
		ready = new JButton("Ready !!!");
		ready.addActionListener(this);
		tourIndicator = new JLabel("It is");
		takeCard.setLocation(20, 10);
		takeCard.setSize(150, 30);
		putCard.setLocation(20, 40);
		putCard.setSize(150, 30);
		requestSuit.setLocation(20, 70);
		requestSuit.setSize(150, 30);
		requestName.setLocation(20, 100);
		requestName.setSize(150, 30);
		
		connect.setLocation(180, 10);
		connect.setSize(150, 30);
		ready.setLocation(180, 40);
		ready.setSize(150, 30);
		tourIndicator.setLocation(180, 75);
		tourIndicator.setSize(150, 50);
		tourIndicator.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		this.add(takeCard);
		this.add(putCard);
		this.add(requestSuit);
		this.add(requestName);
		this.add(connect);
		this.add(ready);
		this.add(tourIndicator);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	@Override
	public void actionPerformed(ActionEvent a) {
		// TODO Auto-generated method stub
		Object source = a.getSource();
		if (source.equals(connect))
		{
			//player = new Player(new Socket("127.0.0.1",9090));
		}
		else 
			if (source.equals(ready))
			{
				try {
					player.sendPakcet(new Packet("status=ready"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else 
				if (source.equals(takeCard))
				{
					Packet packet = new Packet();
					packet.setRequest(new Request(Request.REQUEST_TAKE));
					try {
						player.sendPakcet(packet);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
	}
	@Override
	public void packetReceived(Packet packet) {
		Request request =  packet.getRequest();
		if (request!=null)
		{
			if (request.getID() == Request.COMPOUND_REQUEST)
			{
				Request[] reqs = request.getCompound();
				for (Request req : reqs)
				{
					performOnSingleRequest(req);
				}
			}
			else performOnSingleRequest(request);
		}
		
	}
	private void performOnSingleRequest(Request request)
	{
		if (request.getID() == request.REQUEST_ENABLE_PLAYER)
		{
			tourIndicator.setText("Your tourn");
		}
		else
			if (request.getID() == request.REQUEST_DISABLE_PLAYER)
			{
				tourIndicator.setText("Not Your tourn");
			}
		
	}
}
