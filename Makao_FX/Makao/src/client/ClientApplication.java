package client;
import gui.Board;



import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;


public class ClientApplication extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static ClientApplication ca=null;
	/**
	 * @param args
	 */
	private ClientApplication()
	{
	    setSize(1000, 1000);
	    setTitle("Makao");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static ClientApplication getInstance()
	{
		if (ca== null) ca=new ClientApplication();
    	// TODO Auto-generated method stub
		return ca;
	}
	public void setCustomCursor(BufferedImage image)
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit(); 
		Cursor cursor = toolkit.createCustomCursor(image, new Point(0,0), "Pencil"); 
	    this.setCursor(cursor);
		//this.setCursor(cursor);	
	}
	private static void initializeBuffer()
	{
		File dir = new File("cards"); 
		String[] files = dir.list();
		for (int i=0;i<files.length;i++)
		{
			File temp = new File(files[i]);
			{
				String name =files[i].substring(0, files[i].lastIndexOf("."));
				ImagesBuffer.getInstance().addEntry(name, "cards"+File.separator+temp.getName());
			}
		}	
		ImagesBuffer.getInstance().addEntry("cardBack", "cards"+File.separator+"b2fv.gif");
		ImagesBuffer.getInstance().addEntry("table", "images"+File.separator+"table2.jpg");
		ImagesBuffer.getInstance().addEntry("takeButton", "images"+File.separator+"takeButton.gif");
		ImagesBuffer.getInstance().addEntry("readyButton", "images"+File.separator+"readyButton.gif");
		ImagesBuffer.getInstance().addEntry("passButton", "images"+File.separator+"passButton.gif");
		ImagesBuffer.getInstance().addEntry("greenLight", "images"+File.separator+"green_light.gif");
		ImagesBuffer.getInstance().addEntry("redLight", "images"+File.separator+"red_light.gif");
		ImagesBuffer.getInstance().addEntry("yellowLight", "images"+File.separator+"yellow_light.gif");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		 initializeBuffer();
         ClientApplication app = ClientApplication.getInstance();
         Container pane = app.getContentPane();
         
         //app.setSi
         Board d =new Board(app.getSize());
         
         d.setSize(500,500);
         //d.setSize(app.getSize());
        // d.setLocation(0, 10);
         pane.add(d);
         app.setVisible(true);
	}

}
