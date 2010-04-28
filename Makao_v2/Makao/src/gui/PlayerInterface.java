package gui;

import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class PlayerInterface extends JPanel{
	public PlayerInterface()
	{
		this.setLayout(null);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		JButton takeCard = new JButton("Take");
		JButton putCard = new JButton("Put");
		JButton requestSuit = new JButton("Request suit");
		JButton requestName = new JButton("Request name");
		takeCard.setLocation(80, 10);
		takeCard.setSize(150, 30);
		putCard.setLocation(80, 50);
		putCard.setSize(150, 30);
		requestSuit.setLocation(80, 90);
		requestSuit.setSize(150, 30);
		requestName.setLocation(80, 130);
		requestName.setSize(150, 30);
		
		
		
		this.add(takeCard);
		this.add(putCard);
		this.add(requestSuit);
		this.add(requestName);
	}
	public void paintComponent(Graphics g)
	{
		
	}
}
