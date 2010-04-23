package client;
import gui.Display;


import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;


public class ClientApplication extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public ClientApplication()
	{
	    setSize(1000, 1000);
	    setTitle("Makao");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		ImagesBuffer.getInstance().addEntry("table", "images"+File.separator+"tableUpdated.jpg");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 initializeBuffer();
         ClientApplication app = new ClientApplication();
         Container pane = app.getContentPane();
         Display d =new Display();
         
         d.setSize(500,500);
         //d.setSize(app.getSize());
        // d.setLocation(0, 10);
         pane.add(d);
         app.setVisible(true);
	}

}
