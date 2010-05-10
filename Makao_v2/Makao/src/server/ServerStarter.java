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
		Logger logger = Logger.getLogger("server.GameServer");
		logger.addHandler(handler);
		server = new GameServer(9090);
		server.run();
	}

}
