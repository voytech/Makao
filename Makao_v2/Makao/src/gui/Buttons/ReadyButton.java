package gui.Buttons;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.AbstractButton;
import javax.swing.JComponent;

import client.ImagesBuffer;

public class ReadyButton extends AbstractButton implements ActionListener{
	private BufferedImage img = null, imgPushed = null;
	private boolean pushed = false;
	public ReadyButton()
	{
		this.addActionListener(this);
		this.img = ImagesBuffer.getInstance().getEntry("readyButton");
		this.imgPushed = ImagesBuffer.getInstance().getEntry("readyButtonPushed");
		this.setSize(img.getWidth(), img.getHeight());
	}
	public void paint(Graphics g)
	{
		if (img!=null)
		{
			Graphics2D g2 =(Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			if (pushed) g2.drawImage(imgPushed, 0, 0,this.getSize().width,this.getSize().height,  null);
			else g2.drawImage(img, 0, 0,this.getSize().width,this.getSize().height,  null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this))
		{
			pushed = true;
		}
		
	}
}
