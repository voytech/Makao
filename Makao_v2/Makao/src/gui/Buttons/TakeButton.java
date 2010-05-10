package gui.Buttons;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;

import client.ImagesBuffer;

public class TakeButton extends JComponent{
	private BufferedImage img = null;
	public TakeButton()
	{
		this.img = ImagesBuffer.getInstance().getEntry("takeButton");
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		if (img!=null)
		{
			Graphics2D g2 =(Graphics2D)g;
			//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			//g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2.drawImage(img, 0, 0,this.getSize().width,this.getSize().height,  null);
		}
	}
}
