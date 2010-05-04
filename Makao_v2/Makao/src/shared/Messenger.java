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
    private ArrayList<PacketListener> listeners = new ArrayList<PacketListener>();
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
	public void sendPakcet(Packet packet) throws IOException 
	{
		//if (output==null) reinitializeOutputStream();;	
		output.writeObject(packet);	
		//output.reset();
		output.flush();
	}

	public void addPacketListener(PacketListener plistener) 
	{
		//synchronized(this)
		{
			listeners.add(plistener);
		}
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
				} catch (ClassNotFoundException e) {
					logger.log(Level.SEVERE,"Exception during read : "+e.getMessage());
				}
				/*byte[] buffer = new byte[2000];
				try {
					socketInput.read(buffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bais = new ByteArrayInputStream(buffer);
				try {
					input = new ObjectInputStream(bais);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					obj = input.readObject();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				if (obj!=null)
				{
					if (obj instanceof Packet)
					{
						for (PacketListener listener : listeners)
						{
							logger.log(Level.INFO,"Received packet redirected to listener: "+listener.toString());
							listener.packetReceived((Packet)obj);
						}
					}
				}
			}
			/*try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}*/
		}	
	}
    	
	
	
}
