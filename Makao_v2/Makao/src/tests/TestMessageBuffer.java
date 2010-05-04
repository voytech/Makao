package tests;

import org.junit.*; 
import static org.junit.Assert.* ;
import shared.*;
public class TestMessageBuffer {

	@Test
	public void testAddingMessageForPlayer()
	{
		//Yet MessageParser should be implemented
		//parser.getKey()
		//parser.getValue()
		//
		MessageBuffer buffer = new MessageBuffer();
		buffer.put("nick","wojtek");
		String val = buffer.get("nick");
		Assert.assertEquals("wojtek", val);
			
	}
	
}
