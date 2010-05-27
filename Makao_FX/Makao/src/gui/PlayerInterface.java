package gui;

import gui.Buttons.PassButton;
import gui.Buttons.ReadyButton;
import gui.Buttons.TakeButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import shared.Card;
import shared.Messenger;
import shared.RequestListener;
import shared.Request;


public class PlayerInterface extends JPanel implements ActionListener, RequestListener{
	private JButton takeCard,ready;
	private JCheckBox requestSuit,requestName, makao, nomakao;
	private TakeButton takeButton = new TakeButton();
	private ReadyButton readyButton = new ReadyButton();
	private PassButton passButton = new PassButton();
	private JLabel tourIndicator;
	private JComboBox rSuit=null,rName=null;
	private Messenger player = null;
	private StateIndicator indicator= null;
	ButtonGroup bgroup = null;
	public PlayerInterface(Messenger player)
	{
		this.setLayout(null);
	    this.player = player;
	    this.player.addRequestListener(this);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		takeCard = new JButton("Take");
		takeCard.addActionListener(this);
		requestSuit = new JCheckBox("Request suit");
		rSuit = new JComboBox();
		requestSuit.addActionListener(this);
		requestName = new JCheckBox("Request name");
		rSuit.addActionListener(this);
		rName = new JComboBox();
		requestName.addActionListener(this);
		rName.addActionListener(this);
		makao = new JCheckBox("Makao");
		makao.addActionListener(this);
		ready = new JButton("Ready !!!");
		ready.addActionListener(this);
		nomakao = new JCheckBox("And Makao !?");
		nomakao.addActionListener(this);
		tourIndicator = new JLabel("Waiting for players");
		takeCard.setLocation(20, 10);
		takeCard.setSize(150, 30);
		requestSuit.setLocation(20, 70);
		requestSuit.setSize(150, 30);
		rSuit.setLocation(180, 70);
		rSuit.setSize(150, 30);
		requestName.setLocation(20, 100);
		requestName.setSize(150, 30);
		rName.setLocation(180, 100);
		rName.setSize(150,30);
		passButton.setLocation(160,50);
		passButton.setSize(150,70);	
		takeButton.setLocation(10,50);
		takeButton.setSize(150,70);
		readyButton.setLocation(10,10);
		readyButton.setSize(120,40);
		
		bgroup = new ButtonGroup();
		bgroup.add(requestName);
		bgroup.add(requestSuit);
		makao.setLocation(180, 10);
		makao.setSize(150, 30);
		nomakao.setLocation(180, 40);
		nomakao.setSize(150, 30);
		ready.setLocation(20, 40);
		ready.setSize(150, 30);
		tourIndicator.setLocation(20, 120);
		tourIndicator.setSize(300, 70);
		//Font f = new Font(Font.SERIF,Font.BOLD,18);
		//tourIndicator.setFont(f);
		tourIndicator.setForeground(Color.WHITE);
		
		indicator = new StateIndicator();
		indicator.setLocation(330, 10);
		indicator.setSize(100, 100);
		this.initCombos();
		this.add(takeCard);
		this.add(requestSuit);
		this.add(requestName);

		this.add(makao);
		this.add(ready);
		this.add(tourIndicator);
		this.add(rName);
		this.add(rSuit);
		this.add(nomakao);
		this.add(indicator);
		this.setBackground(Color.GRAY);
		//this.add(takeButton);
		//this.add(readyButton);
		//this.add(passButton);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	}
	private void initCombos()
	{
		
		for (int i = 5; i< 11;i++) rName.addItem(i+"");
		rName.addItem("Jack");
		rName.addItem("Queen");
		rName.addItem("King");
		rSuit.addItem("Clubs");
		rSuit.addItem("Spades");
		rSuit.addItem("Hearts");
		rSuit.addItem("Diamonds");
	}
	@Override
	public void actionPerformed(ActionEvent a) {
		// TODO Auto-generated method stub
		Object source = a.getSource();
		if (source.equals(makao))
		{
			Request req = new Request(Request.REQUEST_MAKAO);
			try {
				player.sendRequest(req);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//player = new Player(new Socket("127.0.0.1",9090));
		}
		else 
			if (source.equals(ready))
			{
				try {
					Request request = new Request(Request.REQUEST_READY);
					player.sendRequest(request);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else 
				if (source.equals(takeCard))
				{
					try {
						player.sendRequest(new Request(Request.REQUEST_TAKE));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
	}
	@Override
	public void requestReceived(Request request) {
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
		if (request.getID() == Request.REQUEST_ENABLE_PLAYER)
		{
			tourIndicator.setText("Your turn");
			indicator.turnGreenLight();
		}
		else
			if (request.getID() == Request.REQUEST_DISABLE_PLAYER)
			{
				tourIndicator.setText("Not Your turn");
				indicator.turnRedLight();
			}
			else
				if (request.getID() == Request.REQUEST_WINNER)
				{
					String mess = request.getMessage();
					tourIndicator.setText(mess);
				}
				//else if(request.get)
		this.repaint();
		
	}
	boolean isMakaoPunishmentChecked()
	{
		return nomakao.isSelected();
	}
	boolean isMakaoChecked()
	{
		return makao.isSelected();
	}
	public Request getSelectedRequest()
	{
		Request request = null;
		if (requestSuit.isSelected())
		{
			String suit = (String)rSuit.getSelectedItem();
			if (suit.equals("Clubs")) request = new Request(Request.REQUEST_CARD_SUIT,Card.Suit.CLUB);
			else 
				if (suit.equals("Diamonds")) request = new Request(Request.REQUEST_CARD_SUIT,Card.Suit.DIAMOND);	
				else 
					if (suit.equals("Spades")) request = new Request(Request.REQUEST_CARD_SUIT,Card.Suit.SPADE);							
					else 
						if (suit.equals("Hearts")) request = new Request(Request.REQUEST_CARD_SUIT,Card.Suit.HEART);
		}
		else 
			if (requestName.isSelected())
			{
				String name = (String)rName.getSelectedItem();
				if (name.equals("5")) request = new Request(Request.REQUEST_CARD_NAME,Card.Name.FIVE);
				else 
					if (name.equals("6")) request = new Request(Request.REQUEST_CARD_NAME,Card.Name.SIX);	
					else 
						if (name.equals("7")) request = new Request(Request.REQUEST_CARD_NAME,Card.Name.SEVEN);							
						else 
							if (name.equals("8")) request = new Request(Request.REQUEST_CARD_NAME,Card.Name.EIGHT);
							else
								if (name.equals("9")) request = new Request(Request.REQUEST_CARD_NAME,Card.Name.NINE);
								else
									if (name.equals("10")) request = new Request(Request.REQUEST_CARD_NAME,Card.Name.TEN);
									else
										if (name.equals("Jack")) request = new Request(Request.REQUEST_CARD_NAME,Card.Name.JACK);
										else
											if (name.equals("Queen")) request = new Request(Request.REQUEST_CARD_NAME,Card.Name.QUEEN);
			}
		//bgroup.clearSelection();	
		return request;
	}
}
