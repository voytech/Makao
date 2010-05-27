package server;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import shared.FileLogHandler;

public class ServerStarter {

	/**
	 * @param args
	 */
	private static GameServer server = null;
	public static void main(String[] args) {
		File f = new File("game-server.html");
		f.delete();
		FileLogHandler handler = null;
		try {
			handler = new FileLogHandler();
			handler.setFileName("game-server");
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Logger qLogger = Logger.getLogger("server.PlayerQueue");
		Logger logger = Logger.getLogger("server.GameServer");
		logger.addHandler(handler);
		qLogger.addHandler(handler);
		try {
			server = new GameServer(9090);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.start();
	}

}
