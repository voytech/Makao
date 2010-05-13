package shared;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



import tests.MessengerTest;



public class Messenger implements Runnable{
    private Socket socket = null;
    private ArrayList<RequestListener> listeners = new ArrayList<RequestListener>();
    private ArrayList<ReadErrorListener> e_listeners = new ArrayList<ReadErrorListener>();
    private Thread listeningThread = null;
    private Logger logger = Logger.getLogger("shared.Messenger");
    

    private ObjectOutputStream output = null;
    private ObjectInputStream input = null;

    public Messenger(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		logger.log(Level.INFO,"Messenger will start listening");
		try {
			reinitializeOutputStream();
			reinitializeInputStream();					
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listeningThread = new Thread(this);
		listeningThread.start();
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

	public Socket getSocket() 
	{
		// TODO Auto-generated method stub
		return socket;
	}
	public void sendRequest(Request request) throws IOException 
	{	
		output.writeObject(request);	
		output.flush();
	}

	public void addRequestListener(RequestListener plistener) 
	{
		listeners.add(plistener);
	}
	public void addReadErrorListener(ReadErrorListener elistener) 
	{
		e_listeners.add(elistener);
	}
	
	@Override
	public void run() {
		while(true)
		{
			if (listeners.size()==0) continue;
			else 
			{
				Object obj=null;
				try {
					logger.log(Level.INFO,"Trying to read packet");
					obj = input.readObject();
				} catch (IOException e) {
					logger.log(Level.SEVERE,"Exception during read : "+e.getMessage());
					for (ReadErrorListener listener : e_listeners)
					{
						listener.readErrorOccured(e,this);
					}
				} catch (ClassNotFoundException e) {
					logger.log(Level.SEVERE,"Exception during read : "+e.getMessage());
				}
				if (obj!=null)
				{
					if (obj instanceof Request)
					{
						for (RequestListener listener : listeners)
						{
							logger.log(Level.INFO,"Received packet redirected to listener: "+listener.toString());
							listener.requestReceived((Request)obj);
						}
					}
				}
			}
		}	
	}	
}
