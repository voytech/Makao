package tests;

import org.junit.*; 
import static org.junit.Assert.* ;
import shared.*;
public class RequestTest {

	@Test
	public void testWhetherAfterCreatingRequestCanReturnRequestIDAndArgs()
	{
	//for example we can request cards named TWO.
	   for (byte id = 0; id<15;id++)
	   {
		   Request req =new Request(id,new Object[]{new Byte(id)});
		   Assert.assertEquals(id, req.getID());
           Object[] ret = (Object[])req.getArg();		   
		   Assert.assertEquals(new Byte(id), (Byte)ret[0]);
	   }
	}	
}
