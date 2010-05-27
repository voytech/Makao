package server;

public class RoundCounter {
	private static RoundCounter instance = null;
	private int currentRound = 0;
	private int lastMarkedRound = 0;
	private RoundCounter(){};
	public static RoundCounter getInstance()
	{
		if (instance == null) instance = new RoundCounter();
		return instance;
	}
	public void mark()
	{
		lastMarkedRound = currentRound;
	}
	public boolean isNextRound()
	{
		if ((currentRound-lastMarkedRound)==1) return true;
		return false;
	}
	public int roundsFromMark()
	{
		return (currentRound-lastMarkedRound);
	}
	public void nextRound()
	{
		synchronized(this)
		{
			currentRound++;
		}
	}
	public int getRoundNumber()
	{
		return currentRound;
	}
	
}
