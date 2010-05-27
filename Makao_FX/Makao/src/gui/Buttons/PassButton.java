package gui.Buttons;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import client.ImagesBuffer;

public class PassButton extends JComponent{
	private BufferedImage img = null;
	public PassButton()
	{
		this.img = ImagesBuffer.getInstance().getEntry("passButton");
	}
	public void paint(Graphics g)
	{
		if (img!=null)
		{
			Graphics2D g2 =(Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

			g2.drawImage(img, 0, 0,this.getSize().width,this.getSize().height,  null);
		}
	}
}
