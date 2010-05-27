package client;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.*;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImagesBuffer {
    private static ImagesBuffer iBuffer = null;
	private Hashtable<String,BufferedImage> buff = new Hashtable<String,BufferedImage>();
    private ImagesBuffer(){}
	
    public static ImagesBuffer getInstance() 
    {
		if (iBuffer== null) iBuffer=new ImagesBuffer();
    	// TODO Auto-generated method stub
		return iBuffer;
	}
    private BufferedImage read(String source)
    {
    	BufferedImage bimg = null;
    	try {  
		        bimg = ImageIO.read(new File(source));  
		    } catch (Exception e) {  
		          e.printStackTrace();  
		    }
		 return bimg;
    }
	public void addEntry(String key,String source) 
	{
		BufferedImage bimg = read(source);
		buff.put(key, bimg);		
	}

	public void resizeImage(String key,int newW, int newH)
	{
		BufferedImage img = buff.get(key);
		int w = img.getWidth();  
		int h = img.getHeight();  
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());  
		Graphics2D g = dimg.createGraphics();  
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
		g.dispose();
		buff.remove(key);
		buff.put(key, dimg);
	}
	public BufferedImage getEntry(String key) 
	{
		// TODO Auto-generated method stub
		return buff.get(key);
	}

	public int size() {
		return buff.size();
	}

	public void removeEntry(String key) {
		buff.remove(key);	
	}

	public void clear() {
		buff.clear();
		
	}

}
