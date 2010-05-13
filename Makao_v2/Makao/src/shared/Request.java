package shared;

import java.io.Serializable;

public class Request implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -7750097963118966885L;
	/**
	 * 
	 */
	 public static final byte REQUEST_CARD_NAME = 0;
	 public static final byte REQUEST_CARD_SUIT = 1;
	 public static final byte REQUEST_WAITING = 2;
	 public static final byte REQUEST_TAKE = 3; 	 
	 public static final byte COMPOUND_REQUEST = 4;
	 public static final byte REQUEST_TAKE_MORE = 5;
	 public static final byte REQUEST_CARDSTACK_ACTUALIZATION = 6;
	 public static final byte REQUEST_ENABLE_PLAYER = 7;
	 public static final byte REQUEST_DISABLE_PLAYER = 8;
	 public static final byte REQUEST_SHOW_YOURSELF = 9;
	 public static final byte REQUEST_PUSH = 10;
	 public static final byte REQUEST_CONSUMED = 11;
	 public static final byte REQUEST_STRING_MESSAGE = 12;
	 public static final byte REQUEST_MAKAO = 13;
	 public static final byte REQUEST_CARD_NAMES = 14;
	 public static final byte REQUEST_WINNER = 15;
	 public static final byte REQUEST_PUSH_WITH_REQUEST = 16;
	 public static final byte REQUEST_READY = 17;
	 public static final byte REQUEST_PUNISHMENT = 18;
	 private byte request;
	 private Object arg = null;
	 private Card[] cards = null;
	 private String r_message = "";
	 private int number = 0;
	 private Request[] compound = null;
	 private Request inner = null;
     public Request(byte request, Object arg) 
     {
		// TODO Auto-generated constructor stub
    	 this.request = request;
    	 this.arg = arg;
     }
     public Request(byte request, Card[] cards) 
     {
		// TODO Auto-generated constructor stub
    	 this.request = request;
    	 this.cards = cards;
     }
     public Request(byte request, int number) 
     {
		// TODO Auto-generated constructor stub
    	 this.request = request;
    	 this.number = number;
     }
     public Request(byte request, Request[] compound) 
     {
		// TODO Auto-generated constructor stub
    	 this.request = request;
    	 this.compound = compound;
     }
     public Request(byte request,String message) 
     {
		// TODO Auto-generated constructor stub
    	 this.request = request;
    	 this.r_message = message;
    	
     }
     public Request(byte request,Request innerReq) 
     {
		// TODO Auto-generated constructor stub
    	 this.request = request;
    	 this.inner = innerReq;
    	
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
	public Card[] getCards() {
		return cards;
	}	
	public int getNumber() {
		return number;
	}
	public Request[] getCompound()
	{
		return compound;
	}
	public Request getEncapsulatedRequest()
	{
		return inner;
	}
	public String getMessage()
	{
		return r_message;
	}
	public void setCards(Card[] scards) 
	{
		this.cards = scards;
		
	}

	
}
