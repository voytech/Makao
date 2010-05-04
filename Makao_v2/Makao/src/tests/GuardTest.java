package tests;

import java.util.ArrayList;

import org.junit.* ;



import shared.Card;
import shared.CardStack;
import shared.CardStackGuard;
import shared.Request;
import static org.junit.Assert.* ;

public class GuardTest 
{
    @Test
    public void testWheterWeCanPutNotFunctionCardWithTheSameNameAsOnTheTopOfStack()
    {
    	CardStackGuard cs;
    		for (Card.Name name1 : Card.Name.values())
    		{
    			for (Card.Suit suit1 : Card.Suit.values())
    			{
    				for (Card.Name name2 : Card.Name.values())
    	    		{
    	    			for (Card.Suit suit2 : Card.Suit.values())
    	    			{
    	    				if (((name1==name2) || (suit1==suit2)) && (name1!=Card.Name.QUEEN) && (name2!=Card.Name.QUEEN))
    	    				{
    	    					Card[] selectedCards = null;
    	    			    	CardStack pack = new CardStack();
    	    			    	cs = new CardStackGuard(pack);
    	    			    	
    	    					selectedCards = new Card[] {new Card(name1,suit1)};
    	    					pack.push(new Card(name2,suit2));
    	    					cs.setSelection(selectedCards);    
    	    					Assert.assertTrue(cs.testSelection());
    	    				}
    	    				else Assert.assertFalse(false);
    	    			}
					}
    			}
    		}
    }
    @Test
    public void testWheterWeCan_NOT_PutCardWithTheAnotherSuitAndAnotherName()
    {
    	CardStackGuard cs;
    		for (Card.Name name1 : Card.Name.values())
    		{
    			for (Card.Suit suit1 : Card.Suit.values())
    			{
    				for (Card.Name name2 : Card.Name.values())
    	    		{
    	    			for (Card.Suit suit2 : Card.Suit.values())
    	    			{
    	    				if ((name1!=name2) && (suit1!=suit2) && (name1!=Card.Name.QUEEN) && (name2!=Card.Name.QUEEN))
    	    				{
    	    					Card[] selectedCards = null;
    	    			    	CardStack pack = new CardStack();
    	    			    	cs = new CardStackGuard(pack);
    	    			    	
    	    					selectedCards = new Card[] {new Card(name1,suit1)};
    	    					pack.push(new Card(name2,suit2));
    	    					cs.setSelection(selectedCards);    
    	    					Assert.assertFalse(cs.testSelection());
    	    				}
    	    				else Assert.assertFalse(false);
    	    			}
					}
    			}
    		}			  
    }
    @Test
    public void testWhetherCanPushIfOnStackTopIsQueen()
    {
    	CardStackGuard cs;
    
    		for (Card.Name name1 : Card.Name.values())
    		{
    			for (Card.Suit suit1 : Card.Suit.values())
    			{
    				for (Card.Suit suit2 : Card.Suit.values())
        			{
    					Card[] selectedCards = null;
    					CardStack pack = new CardStack();
    					cs = new CardStackGuard(pack);	    	
    					selectedCards = new Card[] {new Card(name1,suit1)};
    					pack.push(new Card(Card.Name.QUEEN,suit2));
    					cs.setSelection(selectedCards);    
    					Assert.assertTrue(cs.testSelection());
        			}
    	    	}
    		}
    		
    	
    }
    @Test
    public void testWhetherCanPushQueenOnStack()
    {
    	CardStackGuard cs;
    	for (Card.Name name1 : Card.Name.values())
		{
			for (Card.Suit suit1 : Card.Suit.values())
			{
				for (Card.Suit suit2 : Card.Suit.values())
    			{
    					Card[] selectedCards = null;
    					CardStack pack = new CardStack();
    					cs = new CardStackGuard(pack);
    	    			    	
    					selectedCards = new Card[] {new Card(Card.Name.QUEEN,suit2)};
    					pack.push(new Card(name1,suit1));
    					cs.setSelection(selectedCards);    
    					Assert.assertTrue(cs.testSelection());
        			}
    	    	}
    		}
    	
    }

    @Test
    public void testWhetherCanPushCardIfServerHasGeneratedRequest()
    {
       for (byte i=0;i<3;i++)
       {
    	   for (Card.Name name1 : Card.Name.values())
   		   {
   				for (Card.Suit suit1 : Card.Suit.values())
   			    {
   					Request req = null;
   					if (i==0) req = new Request(i,name1);
   					if (i==1) req = new Request(i,suit1);
   					if (i==2) req = new Request(i);
   					CardStack stack = new CardStack();
   					stack.push(new Card(Card.Name.ACE,Card.Suit.CLUB));
   					CardStackGuard performer = new CardStackGuard(stack);
   					performer.setIncomingRequest(req);
   					for (Card.Name name : Card.Name.values())
   					{
   						for (Card.Suit suit : Card.Suit.values())
   						{
   							
   							Card card = null;
   							card = new Card(name,suit);
   							performer.setSelection(new Card[]{card});
   							if (Request.REQUEST_WAITING == i) Assert.assertFalse(performer.testSelection());
   							if ((card.getName() == Card.Name.QUEEN) && (Request.REQUEST_WAITING != i)) Assert.assertTrue(performer.testSelection());
   							if (Request.REQUEST_CARD_NAME == i)
   							{
   								if (card.getName().equals(req.getArg())) Assert.assertTrue(performer.testSelection()); 
   							}
   							if (Request.REQUEST_CARD_SUIT == i)
   							{
   								if (card.getSuit().equals(req.getArg())) Assert.assertTrue(performer.testSelection());
   							}				 		       		  
   						}
   					}
   			    }
   		   }
       }
    }
    @Test
    public void testWhetherSelectionCanHaveOnlyCardsWithSameName()
    {
    	CardStackGuard p = new CardStackGuard(null);
        Card[] selection = null;	
        
        // for one kind of names - assert True         
         for (Card.Name name0 : Card.Name.values())
         {
             for (Card.Name name1 : Card.Name.values())
             {
                 for (Card.Name name2 : Card.Name.values())
                 {
                     for (Card.Name name3 : Card.Name.values())
                     {                    	 
                    	 selection = new Card[]{new Card(name0,Card.Suit.CLUB), new Card(name1,Card.Suit.DIAMOND)};                    	 
                    	 p.setSelection(selection);
                    	 if (name0.equals(name1)) Assert.assertTrue(p.testSelection());
                    	 else Assert.assertFalse(p.testSelection());
                    	 
                    	 selection = new Card[]{new Card(name0,Card.Suit.CLUB), new Card(name1,Card.Suit.DIAMOND), new Card(name2,Card.Suit.HEART)};
                     	 p.setSelection(selection);
                     	 if (name0.equals(name1) && name1.equals(name2)) Assert.assertTrue(p.testSelection());
                   	     else Assert.assertFalse(p.testSelection());
                   	 
                     	 selection = new Card[]{new Card(name0,Card.Suit.CLUB), new Card(name1,Card.Suit.DIAMOND), new Card(name2,Card.Suit.HEART), new Card(name3,Card.Suit.SPADE)};
                     	 p.setSelection(selection);
                     	 if (name0.equals(name1) && name1.equals(name2) && name2.equals(name3)) Assert.assertTrue(p.testSelection());
                   	     else Assert.assertFalse(p.testSelection());                   	 
                     }
                 }
             }
        	 
         }         
    }
    @Test
    public void testWhetherCanMakeNameRequest()
    {
    	CardStackGuard perf = new CardStackGuard(null);
		for (Card.Name name : Card.Name.values())
		{
			for (Card.Suit suit : Card.Suit.values())
    		{	
				Card card = new Card(name,suit);					
				perf.setSelection(new Card[]{card});
				for (byte k=0; k<3; k++) 
 			    {
					//Object arg;
					//Request req;
					if (k==0) 
					{ 
						for (Card.Name name1 : Card.Name.values())							
						{  
							if (card.getName() == Card.Name.JACK)
							{  	   
								   if ((name1.equals(Card.Name.FIVE)) || 
									  (name1.equals(Card.Name.SIX))  ||
									  (name1.equals(Card.Name.SEVEN)) ||
									  (name1.equals(Card.Name.EIGHT)) ||
									  (name1.equals(Card.Name.NINE)) ||
									  (name1.equals(Card.Name.TEN))  ||
									  (name1.equals(Card.Name.QUEEN))) Assert.assertTrue(perf.setOutgoingRequest(new Request(k,name1)));
								   else Assert.assertFalse(perf.setOutgoingRequest(new Request(k,name1)));   
		    			    }
							else Assert.assertFalse(perf.setOutgoingRequest(new Request(k,name1)));
						}
					}
					
    			}			
    	    }
		}
    }
    @Test
    public void testWhetherCanMakeSuitRequest()
    {
    	CardStackGuard perf = new CardStackGuard(null);
		for (Card.Name name : Card.Name.values())
		{
			for (Card.Suit suit : Card.Suit.values())
    		{	
				Card card = new Card(name,suit);					
				perf.setSelection(new Card[]{card});
				for (byte k=0; k<3; k++) 
 			    {
					//Object arg;
					//Request req;
					if (k==1) 
					{ 
						for (Card.Suit name1 : Card.Suit.values())							
						{  
							if (card.getName() == Card.Name.ACE)
							{  	   
								   Assert.assertTrue(perf.setOutgoingRequest(new Request(k,name1)));
								  // else Assert.assertFalse(perf.setOutgoingRequest(new Request(k,name1)));   
		    			    }
							else Assert.assertFalse(perf.setOutgoingRequest(new Request(k,name1)));
						}
					}
					
    			}			
    	    }
		}
    }
}
