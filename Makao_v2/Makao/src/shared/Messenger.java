package shared;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



import tests.MessengerTest;



public class Messenger implements Runnable{
    private Socket socket = null;
	private ArrayList<PacketListener> listeners = new ArrayList<PacketListener>();
    private Thread listeningThread = null;
    private ObjectOutputStream output = null;
    private ObjectInputStream input = null;
	//private ArrayList<PacketListener>
    public Messenger(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		listeningThread = new Thread(this);
		listeningThread.start();
	}
    public void reinitializeOutputStream() throws IOException
    {
    	synchronized(this)
    	{
    		OutputStream os = socket.getOutputStream();
    		output = new ObjectOutputStream(os);
    	}
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
    	input = new ObjectInputStream(is);
    }

	public Socket getSocket() 
	{
		// TODO Auto-generated method stub
		return socket;
	}
	public void sendPakcet(Packet packet) throws IOException 
	{
		if (output==null) reinitializeOutputStream();
		output.writeObject(packet);		
	}

	public void addPacketListener(PacketListener plistener) 
	{
		synchronized(this)
		{
			listeners.add(plistener);
		}
	}

	@Override
	public void run() {
		while(true)
		{
			//System.out.println("working");
			synchronized(this)
			{
		    //System.out.println("checking listener");	
			if (listeners.size()==0) continue;
			else 
			{
                if  (input==null)
                {
					try {
						reinitializeInputStream();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
			    Object obj=null;
				try {
					obj = input.readObject();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					//System.out.println("after read");
				if (obj instanceof Packet)
				{
					for (PacketListener listener : listeners)
					{
						listener.packetReceived((Packet)obj);
					}
				}
			}
			/*try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			}
		}	
	}
    	
	
	
}
