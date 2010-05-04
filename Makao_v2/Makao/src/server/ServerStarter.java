package server;

import java.io.File;
import java.util.logging.Logger;

import shared.FileLogHandler;

public class ServerStarter {

	/**
	 * @param args
	 */
	private static GameServer server = null;
	public static void main(String[] args) {
		File f = new File("game-server-test.html");
		f.delete();
		File f2 = new File("messenger-log.html");
		f2.delete();
		
		FileLogHandler handler = null,handler2=null;
		try {
			handler = new FileLogHandler();
			handler.setFileName("game-server-test");
			handler2 = new FileLogHandler();
			handler2.setFileName("messenger-log");
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Logger sLogger = Logger.getLogger("players-queue");
		Logger logger = Logger.getLogger("game-server");
		Logger llogger = Logger.getLogger("shared.Messenger");
		sLogger.addHandler(handler);
		logger.addHandler(handler);
		llogger.addHandler(handler2);
		server = new GameServer(9090);
		server.run();
	}

}
