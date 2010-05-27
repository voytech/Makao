package gui;

import gui.Buttons.ReadyButton;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import client.ImagesBuffer;

public class LoginPanel extends JPanel implements ActionListener{
	private BufferedImage image = null;
	private ReadyButton ready = null;
	public LoginPanel()
	{
		this.setLayout(null);
		image = ImagesBuffer.getInstance().getEntry("loginPanel");
		ready = new ReadyButton();
		this.setSize(image.getWidth(), image.getHeight());
		ready.setLocation(this.getWidth()/2-100,this.getHeight()-35);
		//ready.setSize(150, 55);
		ready.addActionListener(this);
		this.add(ready);
	}
	public void paintComponent(Graphics g)
	{
		ready.setLocation(this.getWidth()/2-100,this.getHeight()-ready.getSize().height-20);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(image, 0, 0,this.getSize().width,this.getSize().height,  null);
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(ready))
		{
			
		}		
	}
}
