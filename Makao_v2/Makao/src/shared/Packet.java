package shared;

import java.io.Serializable;

public class Packet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7247532442113329344L;
	/**
	 * 
	 */
	
	private String message = null;
	private Card[] cards = null;
	private Request request = null;
	public Packet(String string) {
		this.message = string;
	}
	public Packet(String string,Request req) {
		this.message = string;
		this.request = req;
	}
	public Packet(String string, Request req, Card[] cards) {
		this.message = string;
		this.request = req;
		this.cards = cards;
	}
	public Packet() {
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
		
	}
	public void setRequest(Request req) {
		this.request = req;
		
	}
	public void setCards(Card[] cards) {
		this.cards = cards;
		
	}
	public Request getRequest() {
		// TODO Auto-generated method stub
		return request;
	}
	public Card[] getCards() {
		// TODO Auto-generated method stub
		return cards;
	}

}
