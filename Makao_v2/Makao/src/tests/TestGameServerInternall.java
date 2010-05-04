package tests;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import server.GameServer;
import shared.FileLogHandler;


public class TestGameServerInternall {
	@Test
	public void testGameLoop()
	{
		FileLogHandler handler = null;
		try {
			handler = new FileLogHandler();
			handler.setFileName("game-loop-test");
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//SimpleFormatter formatter = new SimpleFormatter();
	    //handler.setFormatter(formatter);
		Logger sLogger = Logger.getLogger("players-queue");
		Logger logger = Logger.getLogger("game-server");
		
		sLogger.addHandler(handler);
		logger.addHandler(handler);
		
		GameServer server = new GameServer(9090);
		server.run();
		
	}
}
