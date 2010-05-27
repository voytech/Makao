package fxclient;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javafx.async.RunnableFuture;
import shared.Request;
import shared.RequestListener;

import com.sun.javafx.runtime.Entry;

public class MessengerFX implements RunnableFuture{
	private Socket socket = null;
    private ArrayList<RequestListener> listeners = new ArrayList<RequestListener>();
    private ObjectOutputStream output = null;
	private ObjectInputStream input = null;
    class DefferRunnable implements Runnable
    {
    	private Object read;
    	private RequestListener listener;
    	public DefferRunnable(Object read,RequestListener listener)
    	{
    		this.read = read;
    		this.listener = listener;
    	}
		@Override
		public void run() {
			listener.requestReceived((Request)read);			
		}
    	
    }
	public MessengerFX(Socket socket) {
			// TODO Auto-generated constructor stub
			this.socket = socket;
			try {
				reinitializeOutputStream();
				reinitializeInputStream();					
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
	  public void reinitializeOutputStream() throws IOException
	  {
	   		OutputStream os = socket.getOutputStream();    	
	   		output = new ObjectOutputStream(new BufferedOutputStream(os));
	   		output.flush();
	  }
	  public void closeOutput() throws IOException
	  {
	    	output.close();
	  }
	  public void closeInput() throws IOException
	  {
	       input.close();	
	  }
	  public void reinitializeInputStream() throws IOException
	  {
	     	InputStream is = socket.getInputStream();   	
	    	input = new ObjectInputStream(new BufferedInputStream(is));
	  }

	  public void sendRequest(Request request) throws IOException 
	  {	
		output.writeObject(request);	
		output.flush();
	  }
	  public void addListener(RequestListener listener)
	  {
		  if (!listeners.contains(listener)) listeners.add(listener);
	  }
	@Override
	  public void run() throws Exception {
		while(true)
		{
			 if (listeners.size()>0) 			
		     {
		           		Object obj=null;
		           		try {
		           			obj = input.readObject();
		           		} catch (IOException e) {
      	           		} catch (ClassNotFoundException e) {
		           		}
		           		if (obj!=null)
		           		{
		           			if (obj instanceof Request)
		           			{
		           				for (RequestListener listener : listeners)
		           				{
		           					Entry.deferAction(new DefferRunnable(obj,listener));
		           				}
		           			}
		          		}		         	
		     }		
		}			
	}
}
