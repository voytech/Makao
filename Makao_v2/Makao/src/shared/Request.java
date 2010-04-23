package shared;

public class Request {
	 public static final byte REQUEST_CARD_NAME = 0;
	 public static final byte REQUEST_CARD_SUIT = 1;
	 public static final byte REQUEST_WAITING = 2;
		 	 
	 private byte request;
	 private Object arg = null;
     public Request(byte request, Object arg) 
     {
		// TODO Auto-generated constructor stub
    	 this.request = request;
    	 this.arg = arg;
     }
     public Request(byte request) 
     {
		// TODO Auto-generated constructor stub
    	 this.request = request;
    	
     }
     public byte getID() 
     {
		// TODO Auto-generated method stub
		return request;
     }
     public Object getArg() 
     {
		// TODO Auto-generated method stub
		return arg;
     }	

	
}