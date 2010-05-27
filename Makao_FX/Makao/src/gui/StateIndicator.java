package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import client.ImagesBuffer;

public class StateIndicator extends JPanel{
	private byte state = 0;
	private BufferedImage green=null,red=null,yellow=null;
	public StateIndicator()
	{
		this.setLayout(null);
		ImagesBuffer buffer = ImagesBuffer.getInstance();
		green = buffer.getEntry("greenLight");
		red = buffer.getEntry("redLight");
		yellow = buffer.getEntry("yellowLight");
	}
	public void turnRedLight()
	{
	   	state = 1;
	   	this.repaint();
	}
	public void turnGreenLight()
	{
		state = 2;
		this.repaint();
	}
	public void turnYellowLight()
	{
		state = 0;
		this.repaint();
	}
	public void paintComponent(Graphics g)
	{
		//super.paintComponents(g);
		Graphics2D g2 = (Graphics2D)g;
		if (state == 0) g2.drawImage(yellow, 0, 0,yellow.getWidth(),yellow.getHeight(),  null);
		else
			if (state == 1) g2.drawImage(red, 0, 0,red.getWidth(),red.getHeight(),  null);
			else
				if (state == 2) g2.drawImage(green, 0, 0,green.getWidth(),green.getHeight(),  null);
	}
}
