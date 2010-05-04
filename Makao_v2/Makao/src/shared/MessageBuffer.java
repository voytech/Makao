package shared;
import java.util.*;

public class MessageBuffer {
	private Hashtable<String,String> buffer = new Hashtable<String,String>();
	public MessageBuffer()
	{	}
	
	
	public void put(String key, String value) 
	{
		// TODO Auto-generated method stub
		if (buffer.containsKey(key))
		{
			buffer.remove(key);		
		}
		buffer.put(key, value);
		
	}
	public String get(String key) 
	{
		String val = null;
		if (buffer.containsKey(key))
		{
			val = buffer.get(key);
		}
		return val;
	}
}
