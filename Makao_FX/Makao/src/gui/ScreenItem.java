package gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class ScreenItem extends JComponent{
    protected Object nestedObject = null;
    private boolean selected=false;
    //private boolean clicked;
	protected BufferedImage image = null;
	public void setSelected(boolean sel){ selected = sel; }
	public boolean isSelected() {return selected;}
	public Object getNestedObject() {
		// TODO Auto-generated method stub
		return nestedObject;
	}
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return image;
	}
}